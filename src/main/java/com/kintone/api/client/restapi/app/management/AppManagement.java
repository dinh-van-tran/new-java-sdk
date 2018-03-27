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

package com.kintone.api.client.restapi.app.management;

import java.util.List;

import com.google.gson.JsonElement;
import com.kintone.api.client.restapi.app.model.App;
import com.kintone.api.client.restapi.app.model.form.field.FormFields;
import com.kintone.api.client.restapi.app.model.form.layout.FormLayout;
import com.kintone.api.client.restapi.app.parser.AppManagementParser;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.constant.LanguageSetting;
import com.kintone.api.client.restapi.exception.KintoneAPIException;

public class AppManagement {
    private static final AppManagementParser parser = new AppManagementParser();
    private Connection connection;

    public AppManagement(Connection connection) {
        this.connection = connection;
    }

    public App getApp(int appId) throws KintoneAPIException {
        JsonElement response = connection.request("GET", "app.json?id=" + appId, null);
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

        StringBuilder apiRequest = new StringBuilder();
        apiRequest.append("apps.json");
        apiRequest.append("?limit=").append(limit);
        apiRequest.append("&offset=").append(offset);
        if (ids != null) {
            for (Integer appId : ids) {
                apiRequest.append("&ids=").append(appId);
            }
        }

        if (codes != null) {
            for (String code : codes) {
                apiRequest.append("&codes=").append(code);
            }
        }

        if (name != null) {
            apiRequest.append("&name=").append(name);
        }

        if (spaceIds != null) {
            for (Integer spaceId : spaceIds) {
                apiRequest.append("&spaceIds=").append(spaceId);
            }
        }

        JsonElement response = connection.request("GET", apiRequest.toString(), null);
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

        StringBuilder apiRequest = new StringBuilder();
        if (isPreview != null && isPreview) {
            apiRequest.append("/preview/");
        }
        apiRequest.append("app/form/fields.json");
        apiRequest.append("?app=").append(appId);
        apiRequest.append("&lang=").append(lang);

        JsonElement response = connection.request("GET", apiRequest.toString(), null);
        return parser.parseFormFields(response);
    }

    public FormLayout getFormLayout(Integer appId, Boolean isPreview) throws KintoneAPIException {
        if (appId == null || appId < 0) {
            throw new KintoneAPIException("Invalid app id value: " + appId);
        }

        StringBuilder apiRequest = new StringBuilder();
        if (isPreview != null && isPreview) {
            apiRequest.append("/preview/");
        }
        apiRequest.append("app/form/layout.json");
        apiRequest.append("?app=").append(appId);

        JsonElement response = connection.request("GET", apiRequest.toString(), null);
        return parser.parseFormLayout(response);
    }
}
