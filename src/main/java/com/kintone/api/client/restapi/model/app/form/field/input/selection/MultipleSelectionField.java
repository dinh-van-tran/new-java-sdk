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

package com.kintone.api.client.restapi.model.app.form.field.input.selection;

import java.util.ArrayList;
import java.util.List;

import com.kintone.api.client.restapi.constant.FieldType;

public class MultipleSelectionField extends AbstractSelectionField {
    protected List<String> defaultValue = new ArrayList<String>();

    public MultipleSelectionField(String code) {
        this.code = code;
        this.type = FieldType.MULTI_SELECT;
    }

    /**
     * @return the defaultValue
     */
    public List<String> getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(List<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
