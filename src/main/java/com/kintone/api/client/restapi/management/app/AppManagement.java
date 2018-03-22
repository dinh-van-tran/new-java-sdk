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
import java.util.Date;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;
import com.kintone.api.client.restapi.model.member.UserBase;

// TODO: Divide by component. Setting, Form, Fields, View, Process, Permission, Customization, Deploy/Get/Add
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
}
