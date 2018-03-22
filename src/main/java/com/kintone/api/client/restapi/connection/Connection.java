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
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.Gson;
import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.auth.HTTPHeader;
import com.kintone.api.client.restapi.exception.ErrorResponse;
import com.kintone.api.client.restapi.exception.KintoneAPIExeption;

public class Connection {
    private static final String JSON_CONTENT = "application/json";
    private static Gson gson = new Gson();

    private final String USER_AGENT_KEY = "User-Agent";
    private final String USER_AGENT_VALUE = "kintone-java-SDK";
    private String userAgent = USER_AGENT_VALUE;
    private Auth auth;
    private String domain;
    private int guestSpaceId;
    private List<HTTPHeader> headers = new ArrayList<HTTPHeader>();

    public Connection(String domain, Auth auth, int guestSpaceId) {
        this.domain = domain;
        this.auth = auth;
        this.guestSpaceId = guestSpaceId;
        this.userAgent += "/" + getProperties().getProperty("version");
    }

    public String request(String method, String apiName, String body) throws KintoneAPIExeption {
        HttpsURLConnection connection = null;
        String response = null;

        URL url = null;
        try {
            url = this.getURL(apiName);
        } catch (MalformedURLException e) {
            throw new KintoneAPIExeption("Invalid URL");
        }

        try {
            this.setHTTPHeaders(connection); 
            connection.setRequestMethod(method);
        } catch (IOException e) {
            throw new KintoneAPIExeption("can not open connection"); // TODO change to KintoneAPIException
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
            throw new KintoneAPIExeption(" cannot connect to host"); // TODO change to KintoneAPIException
        }

        if (post) {
            // TODO implement send data in POST/PUT/DELETE
        }

        try {
            checkStatus(connection); // TODO implement checkStatus
            InputStream is = connection.getInputStream();
            try {
                response = readStream(is);
            } finally {
                is.close();
            }
        } catch (IOException e) {
            throw new KintoneAPIExeption("an error occurred while receiving data"); // TODO change to KintoneAPIException
        }

        return response;
    }

    private URL getURL(String apiName) throws MalformedURLException {
        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("URL is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!apiName.contains("https://")) {
            sb.append("https://").append(apiName);
        }

        return new URL(sb.toString());
    }

    private void setHTTPHeaders(HttpURLConnection connection) {
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
    private void checkStatus(HttpURLConnection conn) throws IOException, KintoneAPIExeption {
        int statusCode = conn.getResponseCode();
        if (statusCode == 404) {
            ErrorResponse response = getErrorResponse(conn);
            if (response == null) {
                throw new KintoneAPIExeption("not found");
            } else {
                throw new KintoneAPIExeption(statusCode, response);
            }
        }
        if (statusCode != 200) {
            ErrorResponse response = getErrorResponse(conn);
            if (response == null) {
                throw new KintoneAPIExeption("http status error(" + statusCode + ")");
            } else {
                throw new KintoneAPIExeption(statusCode, response);
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
}
