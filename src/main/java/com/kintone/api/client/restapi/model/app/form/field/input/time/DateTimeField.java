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

package com.kintone.api.client.restapi.model.app.form.field.input.time;

import com.kintone.api.client.restapi.constant.FieldType;
import com.kintone.api.client.restapi.model.app.form.field.input.AbstractInputField;

public class DateTimeField extends AbstractInputField {
    protected Boolean unique;
    protected String defaultValue;
    protected Boolean defaultNowValue;

    public DateTimeField(String code) {
        this.code = code;
        this.type = FieldType.DATETIME;
    }

    /**
     * @return the unique
     */
    public Boolean getUnique() {
        return unique;
    }

    /**
     * @param unique the unique to set
     */
    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }
}
