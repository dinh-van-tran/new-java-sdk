/**
 * Copyright 2017 Cybozu
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.kintone.api.client.restapi.connection;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kintone.api.client.restapi.authentication.Auth;
import com.kintone.api.client.restapi.exception.ErrorResponse;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.http.HTTPHeader;

public class Connection {
    private static final String JSON_CONTENT = "application/json";
    private static final JsonParser jsonParser = new JsonParser();
    private static final Gson gson = new Gson();

    private String userAgent = ConnectionConstants.USER_AGENT_VALUE;
    private Auth auth;
    private String domain;
    private int guestSpaceId;
    private List<HTTPHeader> headers = new ArrayList<HTTPHeader>();
    private Proxy proxy = null;

    public Connection(String domain, Auth auth, int guestSpaceId) {
        this.domain = domain;
        this.auth = auth;
        this.guestSpaceId = guestSpaceId;
        this.userAgent += "/" + getProperties().getProperty("version");
    }

    public Connection(String domain, Auth auth) {
        this(domain, auth, -1);
    }

    public JsonElement request(String method, String apiName, String body) throws KintoneAPIException {
        HttpsURLConnection connection = null;
        String response = null;

        boolean post = false;
        if (ConnectionConstants.PUT_REQUEST.equals(method)
                || ConnectionConstants.POST_REQUEST.equals(method)
                || ConnectionConstants.DELETE_REQUEST.equals(method)) {
            post = true;
        }

        URL url = null;
        try {
            url = this.getURL(apiName, post ? null : body);
        } catch (MalformedURLException e) {
            throw new KintoneAPIException("Invalid URL");
        }

        try {
            if (this.proxy  == null) {
                connection = (HttpsURLConnection)url.openConnection();
            } else {
                connection = (HttpsURLConnection)url.openConnection(this.proxy);
            }

            this.setHTTPHeaders(connection); 
            connection.setRequestMethod(method);
        } catch (IOException e) {
            throw new KintoneAPIException("can not open connection"); // TODO change to KintoneAPIException
        }

        if (post) {
            connection.setDoOutput(true);
            connection.setRequestProperty(ConnectionConstants.CONTENT_TYPE_HEADER, JSON_CONTENT);
        }

        try {
            connection.connect();
        } catch (IOException e) {
            throw new KintoneAPIException(" cannot connect to host");
        }

        if (post) {
            // send request
            OutputStream os;
            try {
                os = connection.getOutputStream();
            } catch (IOException e) {
                throw new KintoneAPIException("an error occurred while sending data");
            }
            try {
                OutputStreamWriter writer = new OutputStreamWriter(os, StandardCharsets.UTF_8);
                writer.write(body);
                writer.close();
            } catch(IOException e) {
                throw new KintoneAPIException("socket error");
            }
        }

        try {
            checkStatus(connection);
            InputStream is = connection.getInputStream();
            try {
                response = readStream(is);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new KintoneAPIException("an error occurred while receiving data");
        }

        return jsonParser.parse(response);
    }

    private URL getURL(String apiName, String parameters) throws MalformedURLException {
        if (this.domain == null || this.domain.isEmpty()) {
            throw new NullPointerException("domain is empty");
        }

        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("api is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!this.domain.contains(ConnectionConstants.HTTPS_PREFIX)) {
            sb.append(ConnectionConstants.HTTPS_PREFIX);
        }
        sb.append(this.domain);

        String urlString = ConnectionConstants.BASE_URL;
        if (this.guestSpaceId >= 0) {
            urlString = ConnectionConstants.BASE_GUEST_URL.replaceAll("\\{GUEST_SPACE_ID\\}", this.guestSpaceId + "");
        }
        urlString = urlString.replaceAll("\\{API_NAME\\}", apiName);

        sb.append(urlString);
        if (parameters != null) {
            sb.append(parameters);
        }

        return new URL(sb.toString().replaceAll("\\s", "%20"));
    }

    private void setHTTPHeaders(HttpURLConnection connection) {
        for (HTTPHeader header : this.auth.createHeaderCredentials()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        connection.setRequestProperty(ConnectionConstants.USER_AGENT_KEY, this.userAgent);
        for (HTTPHeader header : this.headers) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }

    public Connection setHeader(String key, String value) {
        this.headers.add(new HTTPHeader(key, key));
        return this;
    }

    public Connection setAuth(Auth auth) {
        this.auth = auth;
        return this;
    }

    private String readStream(InputStream is) {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            char[] buffer = new char[1024];
            int line = -1;
            while ((line = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, line);
            }
        } catch (IOException e) {

        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                }
            }
        }

        return sb.toString();
    }

    public String getDomain() {
        return domain;
    }

    public int getGuestSpaceId() {
        return guestSpaceId;
    }

    public Auth getAuth() {
        return auth;
    }

    /**
     * Get pom.properties
     * @return pom properties
     */
    public Properties getProperties() {
        Properties properties = new Properties();
        InputStream inStream = null;
        try {
            inStream = this.getClass().getResourceAsStream("/pom.properties");
            properties.load(inStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return properties;
    }

    /**
     * Checks the status code of the response.
     * @param conn
     *             a connection object
     */
    private void checkStatus(HttpURLConnection conn) throws IOException, KintoneAPIException {
        int statusCode = conn.getResponseCode();
        if (statusCode == 404) {
            ErrorResponse response = getErrorResponse(conn);
            if (response == null) {
                throw new KintoneAPIException("not found");
            } else {
                throw new KintoneAPIException(statusCode, response);
            }
        }

        if (statusCode == 401) {
            throw new KintoneAPIException("401 Unauthorized");
        }

        if (statusCode != 200) {
            ErrorResponse response = getErrorResponse(conn);
            if (response == null) {
                throw new KintoneAPIException("http status error(" + statusCode + ")");
            } else {
                throw new KintoneAPIException(statusCode, response);
            }
        }
    }

    /**
     * Creates an error response object.
     * @param conn
     * @return ErrorResponse object. return null if any error occurred
     */
    private ErrorResponse getErrorResponse(HttpURLConnection conn) {
        InputStream err = conn.getErrorStream();

        String response;
        try {
            if (err == null) {
                err = conn.getInputStream();
            }
            response = parseString(err);
        } catch (IOException e) {
            return null;
        }

        return gson.fromJson(response, ErrorResponse.class);
    }

    /**
     * An utility method converts a stream object to string.
     * @param is input stream
     * @return string
     * @throws IOException
     */
    private String parseString(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is,
                "UTF-8"));
        try {
            char[] b = new char[1024];
            int line;
            while (0 <= (line = reader.read(b))) {
                sb.append(b, 0, line);
            }
        } finally {
            reader.close();
        }
        return new String(sb);
    }

    /**
     * Sets the proxy host.
     * 
     * @param host
     *            proxy host
     * @param port
     *            proxy port
     */
    public void setProxy(String host, int port) {
        this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host, port));
    }
}
