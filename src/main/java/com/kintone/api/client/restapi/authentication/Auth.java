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

package com.kintone.api.client.restapi.authentication;

import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

import com.kintone.api.client.restapi.model.authentication.Credential;
import com.kintone.api.client.restapi.model.http.HTTPHeader;

public class Auth {
    public static final String HEADER_KEY_AUTH_PASSWORD = "X-Cybozu-Authorization";
    public static final String HEADER_KEY_AUTH_APITOKEN = "X-Cybozu-API-Token";
    public static final String HEADER_KEY_AUTH_BASIC = "Authorization";
    public static final String AUTH_BASIC_PREFIX = "Basic ";

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
    public Auth setBasicAuth(String username, String password) {
        this.basicAuth = new Credential(username, password);
        return this;
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
    public Auth setPasswordAuth(String username, String password) {
        this.passwordAuth = new Credential(username, password);
        return this;
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
    public Auth setApiToken(String apiToken) {
        this.apiToken = apiToken;
        return this;
    }

    public List<HTTPHeader> createHeaderCredentials() {
        List<HTTPHeader> headers = new ArrayList<HTTPHeader>();

        if (this.passwordAuth != null) {
            String passwordAuthString = this.passwordAuth.getUsername() + ":" + this.passwordAuth.getPassword();
            headers.add(new HTTPHeader(HEADER_KEY_AUTH_PASSWORD, Base64.getEncoder().encodeToString(passwordAuthString.getBytes())));
        }

        if (this.apiToken != null) {
            headers.add(new HTTPHeader(HEADER_KEY_AUTH_APITOKEN, this.apiToken));
        }

        if (this.basicAuth != null) {
            String basicAuthString = this.basicAuth.getUsername() + ":" + this.basicAuth.getPassword();
            headers.add(new HTTPHeader(HEADER_KEY_AUTH_BASIC, AUTH_BASIC_PREFIX + Base64.getEncoder().encodeToString(basicAuthString.getBytes())));
        }

        return headers;
    }
}
