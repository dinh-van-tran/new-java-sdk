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

package com.kintone.api.client.restapi.parser;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.TimeZone;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;
import com.kintone.api.client.restapi.model.app.form.field.FormFields;
import com.kintone.api.client.restapi.model.app.form.layout.FormLayout;
import com.kintone.api.client.restapi.model.member.UserBase;

public class AppManagementParser {
    private static final Gson gson = new Gson();
    private static final SimpleDateFormat isoDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
    private static final FormLayoutParser formLayoutParser = new FormLayoutParser();

    static {
        isoDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public App parseApp(JsonElement input) throws KintoneAPIException {
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
            Date createdDate = isoDateFormat.parse(jsonObject.get("createdAt").getAsString());
            app.setCreatedAt(createdDate);

            Date modifiedDate = isoDateFormat.parse(jsonObject.get("modifiedAt").getAsString());
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

    public List<App> parseApps(JsonElement input) throws KintoneAPIException {
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

    public FormFields parseFormFields(JsonElement input) throws KintoneAPIException {
        FormFieldParser parser = new FormFieldParser();
        return parser.parse(input);
    }

    public FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
        return formLayoutParser.parseFormLayout(input);
    }
}
