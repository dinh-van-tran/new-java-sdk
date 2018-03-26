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

package com.kintone.api.client.restapi.management.app;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.constant.LanguageSetting;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;
import com.kintone.api.client.restapi.model.app.form.field.FormFields;
import com.kintone.api.client.restapi.model.app.form.layout.FieldLayout;
import com.kintone.api.client.restapi.model.app.form.layout.FieldSize;
import com.kintone.api.client.restapi.model.app.form.layout.FormLayout;
import com.kintone.api.client.restapi.model.app.form.layout.GroupLayout;
import com.kintone.api.client.restapi.model.app.form.layout.ItemLayout;
import com.kintone.api.client.restapi.model.app.form.layout.RowLayout;
import com.kintone.api.client.restapi.model.app.form.layout.SubTableLayout;
import com.kintone.api.client.restapi.model.member.UserBase;
import com.kintone.api.client.restapi.parser.FormFieldParser;

public class AppManagement {
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private Connection connection;

    public AppManagement(Connection connection) {
        this.connection = connection;
    }

    public App getApp(int appId) throws KintoneAPIException {
        JsonElement response = connection.request("GET", "app.json?id=" + appId, null);
        return parseApp(response);
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
        return parseApps(response);
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

    private App parseApp(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        App app = new App();
        JsonObject jsonObject = input.getAsJsonObject();
        app.setAppId(Integer.parseInt(jsonObject.get("appId").getAsString()));
        app.setCode(jsonObject.get("code").getAsString());
        app.setName(jsonObject.get("name").getAsString());
        app.setDescription(jsonObject.get("description").getAsString());

        try {
            Date createdDate = dateParser.parse(jsonObject.get("createdAt").getAsString());
            app.setCreatedAt(createdDate);

            Date modifiedDate = dateParser.parse(jsonObject.get("modifiedAt").getAsString());
            app.setModifiedAt(modifiedDate);
        } catch (ParseException e) {
            throw new KintoneAPIException("Parse date error");
        }

        app.setCreator(gson.fromJson(jsonObject.get("creator"), UserBase.class));
        app.setModifier(gson.fromJson(jsonObject.get("modifier"), UserBase.class));

        JsonElement spaceId = jsonObject.get("spaceId");
        if (spaceId.isJsonPrimitive()) {
            app.setSpaceId(spaceId.getAsInt());
        }

        JsonElement threadId = jsonObject.get("threadId");
        if (threadId.isJsonPrimitive()) {
            app.setThreadId(threadId.getAsInt());
        }

        return app;
    }

    private List<App> parseApps(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonElement apps = input.getAsJsonObject().get("apps");
        if (!apps.isJsonArray()) {
            throw new KintoneAPIException("Parse error");
        }

        List<App> result = new ArrayList<App>();

        Iterator<JsonElement> iterator = apps.getAsJsonArray().iterator();
        while (iterator.hasNext()) {
            result.add(parseApp(iterator.next()));
        }

        return result;
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
        return parseFormFields(response);
    }

    private FormFields parseFormFields(JsonElement response) throws KintoneAPIException {
        FormFieldParser parser = new FormFieldParser();
        return parser.parse(response);
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
        return parseFormLayout(response);
    }

    private FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonObject root = input.getAsJsonObject();
        FormLayout formLayout = new FormLayout();

        formLayout.setRevision(root.get("revision").getAsString());

        JsonArray layoutProperties = root.get("layout").getAsJsonArray();
        Iterator<JsonElement> iterator = layoutProperties.iterator();
        while (iterator.hasNext()) {
            formLayout.getLayout().add(parseItemLayout(iterator.next()));
        }

        return formLayout;
    }

    private ItemLayout parseItemLayout(JsonElement input) throws KintoneAPIException {
        if (!input.isJsonObject()) {
            throw new KintoneAPIException("Parse error");
        }

        JsonObject root = input.getAsJsonObject();
        switch(root.get("type").getAsString()) {
            case "ROW":
                return parseRowLayout(root);
            case "SUBTABLE":
                return parseSubTableLayout(root);
            case "GROUP":
                return parseGroupLayout(root);
            default:
                throw new KintoneAPIException("Layout type is not supported");
        }
    }

    private GroupLayout parseGroupLayout(JsonObject root) {
        GroupLayout groupLayout = new GroupLayout();
        groupLayout.setCode(root.get("code").getAsString());

        JsonArray layout = root.get("layout").getAsJsonArray();
        Iterator<JsonElement> iterator = layout.iterator();
        while(iterator.hasNext()) {
            groupLayout.getLayout().add(parseRowLayout(iterator.next().getAsJsonObject()));
        }

        return groupLayout;
    }

    private SubTableLayout parseSubTableLayout(JsonObject root) {
        SubTableLayout subTableLayout = new SubTableLayout();

        subTableLayout.setCode(root.get("code").getAsString());

        JsonArray fields = root.get("fields").getAsJsonArray();
        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            subTableLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return subTableLayout;
    }

    private RowLayout parseRowLayout(JsonObject root) {
        RowLayout rowLayout = new RowLayout();
        JsonArray fields = root.get("fields").getAsJsonArray();

        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            rowLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return rowLayout;
    }

    private FieldLayout parseFieldLayout(JsonElement input) {
        JsonObject root = input.getAsJsonObject();
        FieldLayout fieldLayout = new FieldLayout();

        JsonElement code = root.get("code");
        if (code != null && code.isJsonPrimitive()) {
            fieldLayout.setCode(code.getAsString());
        }

        JsonElement label = root.get("label");
        if (label != null && label.isJsonPrimitive()) {
            fieldLayout.setLabel(label.getAsString());
        }

        JsonElement elementId = root.get("elementId");
        if (elementId != null && elementId.isJsonPrimitive()) {
            fieldLayout.setElementId(elementId.getAsString());
        }

        fieldLayout.setSize(parseFieldSize(root.get("size").getAsJsonObject()));

        return fieldLayout;
    }

    private FieldSize parseFieldSize(JsonObject root) {
        FieldSize fieldSize = new FieldSize();

        JsonElement width = root.get("width");
        if (width != null && width.isJsonPrimitive()) {
            fieldSize.setWidth(width.getAsString());
        }

        JsonElement height = root.get("height");
        if (height != null && height.isJsonPrimitive()) {
            fieldSize.setHeight(height.getAsString());
        }

        JsonElement innerHeight = root.get("innerHeight");
        if (innerHeight != null && innerHeight.isJsonPrimitive()) {
            fieldSize.setInnerHeight(innerHeight.getAsString());
        }

        return fieldSize;
    }
}
