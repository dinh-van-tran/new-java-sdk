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

package com.kintone.api.client.restapi.app.parser;

import java.util.Iterator;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.app.model.form.layout.FieldLayout;
import com.kintone.api.client.restapi.app.model.form.layout.FieldSize;
import com.kintone.api.client.restapi.app.model.form.layout.FormLayout;
import com.kintone.api.client.restapi.app.model.form.layout.GroupLayout;
import com.kintone.api.client.restapi.app.model.form.layout.ItemLayout;
import com.kintone.api.client.restapi.app.model.form.layout.RowLayout;
import com.kintone.api.client.restapi.app.model.form.layout.SubTableLayout;
import com.kintone.api.client.restapi.exception.KintoneAPIException;

public class FormLayoutParser {
    public FormLayout parseFormLayout(JsonElement input) throws KintoneAPIException {
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

    public ItemLayout parseItemLayout(JsonElement input) throws KintoneAPIException {
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

    public GroupLayout parseGroupLayout(JsonObject root) {
        GroupLayout groupLayout = new GroupLayout();
        groupLayout.setCode(root.get("code").getAsString());

        JsonArray layout = root.get("layout").getAsJsonArray();
        Iterator<JsonElement> iterator = layout.iterator();
        while(iterator.hasNext()) {
            groupLayout.getLayout().add(parseRowLayout(iterator.next().getAsJsonObject()));
        }

        return groupLayout;
    }

    public SubTableLayout parseSubTableLayout(JsonObject root) {
        SubTableLayout subTableLayout = new SubTableLayout();

        subTableLayout.setCode(root.get("code").getAsString());

        JsonArray fields = root.get("fields").getAsJsonArray();
        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            subTableLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return subTableLayout;
    }

    public RowLayout parseRowLayout(JsonObject root) {
        RowLayout rowLayout = new RowLayout();
        JsonArray fields = root.get("fields").getAsJsonArray();

        Iterator<JsonElement> iterator = fields.iterator();
        while(iterator.hasNext()) {
            rowLayout.getFields().add(parseFieldLayout(iterator.next()));
        }

        return rowLayout;
    }

    public FieldLayout parseFieldLayout(JsonElement input) {
        JsonObject root = input.getAsJsonObject();
        FieldLayout fieldLayout = new FieldLayout();

        JsonElement type = root.get("type");
        if (type != null && type.isJsonPrimitive()) {
            fieldLayout.setType(type.getAsString());
        }

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

    public FieldSize parseFieldSize(JsonObject root) {
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
