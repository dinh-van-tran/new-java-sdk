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

package com.kintone.api.client.restapi.model.app.form.field;

import java.util.HashMap;
import java.util.Map;

import com.kintone.api.client.restapi.constant.FieldType;
import com.kintone.api.client.restapi.model.app.form.field.input.AbstractInputField;

public class Table extends Field {
    protected Map<String, AbstractInputField> fields = new HashMap<String, AbstractInputField>();

    public Table() {
        this.type = FieldType.SUBTABLE;
    }

    public Table(String code) {
        this.code = code;
        this.type = FieldType.SUBTABLE;
    }

    /**
     * @return the fields
     */
    public Map<String, AbstractInputField> getFields() {
        return fields;
    }

    /**
     * @param fields the fields to set
     */
    public void setFields(Map<String, AbstractInputField> fields) {
        this.fields = fields;
    }

    public void addField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }

        fields.put(field.getCode(), field);
    }

    public void removeField(AbstractInputField field) {
        if (field == null || field.getCode() == null || field.getCode().trim().length() == 0) {
            return;
        }
        fields.remove(field.getCode());
    }
}
