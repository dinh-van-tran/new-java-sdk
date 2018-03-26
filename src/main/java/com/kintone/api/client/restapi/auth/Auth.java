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

package com.kintone.api.client.restapi.auth;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class Auth {
    private final String AUTH_HEADER = "X-Cybozu-Authorization";
    private final String API_TOKEN = "X-Cybozu-API-Token";

    private Credential basicAuth;
    private Credential passwordAuth;
    private String apiToken;

    public Auth() {

    }

    /**
     * @return the basicAuth
     */
    public Credential getBasicAuth() {
        return basicAuth;
    }

    /**
     * @param basicAuth
     *            the basicAuth to set
     */
    public void setBasicAuth(String username, String password) {
        this.basicAuth = new Credential(username, password);
    }

    /**
     * @return the passwordAuth
     */
    public Credential getPasswordAuth() {
        return passwordAuth;
    }

    /**
     * @param passwordAuth
     *            the passwordAuth to set
     */
    public void setPasswordAuth(String username, String password) {
        this.passwordAuth = new Credential(username, password);
    }

    /**
     * @return the apiToken
     */
    public String getApiToken() {
        return apiToken;
    }

    /**
     * @param apiToken
     *            the apiToken to set
     */
    public void setApiToken(String apiToken) {
        this.apiToken = apiToken;
    }

    public List<HTTPHeader> createHeaderCredentials() {
        List<HTTPHeader> headers = new ArrayList<HTTPHeader>();

        if (this.passwordAuth != null) {
            String passwordAuthString = this.passwordAuth.getUsername() + ":" + this.passwordAuth.getPassword();
            headers.add(new HTTPHeader(AUTH_HEADER, Base64.getEncoder().encodeToString(passwordAuthString.getBytes())));
        }

        if (this.apiToken != null) {
            headers.add(new HTTPHeader(API_TOKEN, this.apiToken));
        }

        if (this.basicAuth != null) {
            String basicAuthString = this.basicAuth.getUsername() + ":" + this.basicAuth.getPassword();
            headers.add(new HTTPHeader("Authorization", "Basic " + Base64.getEncoder().encodeToString(basicAuthString.getBytes())));
        }

        return headers;
    }
}
