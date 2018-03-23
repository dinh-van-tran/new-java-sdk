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
import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.auth.HTTPHeader;
import com.kintone.api.client.restapi.exception.ErrorResponse;
import com.kintone.api.client.restapi.exception.KintoneAPIException;

public class Connection {
    private static final String JSON_CONTENT = "application/json";
    private static final JsonParser jsonParser = new JsonParser();
    private static final Gson gson = new Gson();

    private final String USER_AGENT_KEY = "User-Agent";
    private final String USER_AGENT_VALUE = "kintone-java-SDK";
    private String userAgent = USER_AGENT_VALUE;
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

        URL url = null;
        try {
            url = this.getURL(apiName);
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

        boolean post = false;
        if ("PUT".equals(method) || "POST".equals(method) || "DELETE".equals(method)) {
            post = true;
        }

        if (post) {
            connection.setDoOutput(true);
            connection.setRequestProperty("Content-Type", JSON_CONTENT); // TODO implement JSON_CONTENT
        }

        try {
            connection.connect();
        } catch (IOException e) {
            throw new KintoneAPIException(" cannot connect to host"); // TODO change to KintoneAPIException
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
                OutputStreamWriter writer = new OutputStreamWriter(os, "UTF-8");
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
            throw new KintoneAPIException("an error occurred while receiving data"); // TODO change to KintoneAPIException
        }

        return jsonParser.parse(response);
    }

    private URL getURL(String apiName) throws MalformedURLException {
        if (this.domain == null || this.domain.isEmpty()) {
            throw new NullPointerException("api is empty");
        }

        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("api is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!this.domain.contains("https://")) {
            sb.append("https://");
        }

        sb.append(this.domain).append("/k/v1/").append(apiName);

        return new URL(sb.toString().replaceAll("\\s", "%20"));
    }

    private void setHTTPHeaders(HttpURLConnection connection) {
        for (HTTPHeader header : this.auth.createHeaderCredentials()) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }

        connection.setRequestProperty(USER_AGENT_KEY, this.userAgent);
        for (HTTPHeader header : this.headers) {
            connection.setRequestProperty(header.getKey(), header.getValue());
        }
    }

    public void setAuth(Auth auth) {
        this.auth = auth;
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
        this.proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress(host,
                port));
    }
}
