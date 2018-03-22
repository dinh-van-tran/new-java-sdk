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
import java.net.MalformedURLException;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.auth.IAuth;

public class Connection {
    private static final String JSON_CONTENT = "application/json";

    private String domain;
    private int guestSpaceId;
    private IAuth auth;

    public Connection(String domain, IAuth auth, int guestSpaceId) {
        this.domain = domain;
        this.auth = auth;
        this.guestSpaceId = guestSpaceId;
    }

    public String request(String method, String apiName, String body) throws Exception {
        HttpsURLConnection connection = null;
        String response = null;

        URL url;
        try {
            url = this.getURL(apiName); // TODO implement getURL
        } catch (MalformedURLException e) {
            throw new Exception("Invalid URL"); // TODO change to KintoneAPIException
        }

        try {
            this.setHTTPHeaders(connection); // TODO implement setHTTPHeaders
            connection.setRequestMethod(method);
        } catch (IOException e) {
            throw new Exception("can not open connection"); // TODO change to KintoneAPIException
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
            throw new Exception(" cannot connect to host"); // TODO change to KintoneAPIException
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
            throw new Exception("an error occurred while receiving data"); // TODO change to KintoneAPIException
        }

        return response;
    }

    private URL getURL(String apiName) {
        if (apiName == null || apiName.isEmpty()) {
            throw new NullPointerException("URL is empty");
        }

        StringBuilder sb = new StringBuilder();
        if (!apiName.contains("https://")) {
            sb.append("https://").append(apiName);
        }
        return null;
    }

    private String readStream(InputStream is) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

        char[] buffer = new char[1024];
        int line = -1;
        try {
            while ((line = reader.read(buffer)) >= 0) {
                sb.append(buffer, 0, line);
            }
        } finally {
            reader.close();
        }

        return sb.toString();
    }

    public String getDomain() {
        return domain;
    }

    public int getGuestSpaceId() {
        return guestSpaceId;
    }

    public IAuth getAuth() {
        return auth;
    }
}
