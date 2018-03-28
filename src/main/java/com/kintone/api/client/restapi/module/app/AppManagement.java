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

package com.kintone.api.client.restapi.module.app;

import java.util.List;

import com.google.gson.JsonElement;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.connection.ConnectionConstants;
import com.kintone.api.client.restapi.constant.LanguageSetting;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;
import com.kintone.api.client.restapi.model.app.form.field.FormFields;
import com.kintone.api.client.restapi.model.app.form.layout.FormLayout;
import com.kintone.api.client.restapi.module.parser.AppManagementParser;

public class AppManagement {
    private static final AppManagementParser parser = new AppManagementParser();
    private Connection connection;

    public AppManagement(Connection connection) {
        this.connection = connection;
    }

    public App getApp(int appId) throws KintoneAPIException {
        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APP, "?id=" + appId);
        return parser.parseApp(response);
    }

    private List<App> getApps(List<Integer> ids, List<String> codes, String name, List<Integer> spaceIds, Integer limit, Integer offset) throws KintoneAPIException {
        if (limit == null) {
            limit = Integer.valueOf(100);
        }

        if (limit < 1 || limit > 100) {
            throw new KintoneAPIException("Limit number must between 1 and 100");
        }

        if (offset == null) {
            offset = Integer.valueOf(0);
        }

        if (offset < 0) {
            throw new KintoneAPIException("Offset number must be between 0 and 2147483647");
        }

        StringBuilder body = new StringBuilder();
        body.append("?limit=").append(limit);
        body.append("&offset=").append(offset);
        if (ids != null) {
            for (Integer appId : ids) {
                body.append("&ids=").append(appId);
            }
        }

        if (codes != null) {
            for (String code : codes) {
                body.append("&codes=").append(code);
            }
        }

        if (name != null) {
            body.append("&name=").append(name);
        }

        if (spaceIds != null) {
            for (Integer spaceId : spaceIds) {
                body.append("&spaceIds=").append(spaceId);
            }
        }

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, ConnectionConstants.APPS, body.toString());
        return parser.parseApps(response);
    }

    public List<App> getApps(Integer limit, Integer offset) throws KintoneAPIException {
        return getApps(null, null, null, null, limit, offset);
    }

    public List<App> getAppsByIDs(List<Integer> ids, Integer limit, Integer offset) throws KintoneAPIException {
        return getApps(ids, null, null, null, limit, offset);
    }

    public List<App> getAppsByCodes(List<String> codes, Integer limit, Integer offset) throws KintoneAPIException {
        return getApps(null, codes, null, null, limit, offset);
    }

    public List<App> getAppsByName(String name, Integer limit, Integer offset) throws KintoneAPIException {
        return getApps(null, null, name, null, limit, offset);
    }

    public List<App> getAppsBySpaceIDs(List<Integer> spaceIds, Integer limit, Integer offset) throws KintoneAPIException {
        return getApps(null, null, null, spaceIds, limit, offset);
    }

    public FormFields getFormFields(Integer appId, LanguageSetting lang, Boolean isPreview) throws KintoneAPIException {
        if (appId == null || appId < 0) {
            throw new KintoneAPIException("Invalid app id value:" + appId);
        }

        if (lang == null) {
            lang = LanguageSetting.DEFAULT;
        }

        String apiRequest = ConnectionConstants.APP_FIELDS;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_FIELDS_PREVIEW;
        }

        StringBuilder body = new StringBuilder();
        body.append("?app=").append(appId);
        body.append("&lang=").append(lang);

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest.toString(), body.toString());
        FormFields formfields = parser.parseFormFields(response);
        formfields.setApp(appId);

        return formfields;
    }

    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        if (appId == null || appId < 0) {
            throw new KintoneAPIException("Invalid app id value: " + appId);
        }

        String apiRequest = ConnectionConstants.APP_LAYOUT;
        if (isPreview != null && isPreview) {
            apiRequest = ConnectionConstants.APP_LAYOUT_PREVIEW;
        }

        JsonElement response = connection.request(ConnectionConstants.GET_REQUEST, apiRequest, "?app=" + appId);
        return parser.parseFormLayout(response);
    }
}
