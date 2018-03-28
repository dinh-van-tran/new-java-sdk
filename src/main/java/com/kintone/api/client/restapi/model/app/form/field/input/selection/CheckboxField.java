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

import com.kintone.api.client.restapi.constant.AlignLayout;
import com.kintone.api.client.restapi.constant.FieldType;

public class CheckboxField extends AbstractSelectionField {
    protected List<String> defaultValue = new ArrayList<String>();
    protected AlignLayout align;

    public CheckboxField() {
        this.type = FieldType.CHECK_BOX;
    }

    public CheckboxField(String code) {
        this.code = code;
        this.type = FieldType.CHECK_BOX;
    }

    /**
     * @return the align
     */
    public AlignLayout getAlign() {
        return align;
    }

    /**
     * @param align
     *            the align to set
     */
    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    public List<String> getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(List<String> defaultValue) {
        this.defaultValue = defaultValue;
    }
}
