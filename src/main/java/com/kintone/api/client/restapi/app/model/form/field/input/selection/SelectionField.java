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

package com.kintone.api.client.restapi.app.model.form.field.input.selection;

import java.util.HashMap;
import java.util.Map;

import com.kintone.api.client.restapi.app.model.form.field.input.InputField;

public class SelectionField extends InputField {
    protected Map<String, Option> options = new HashMap<String, Option>();

    /**
     * @return the options
     */
    public Map<String, Option> getOptions() {
        return options;
    }

    /**
     * @param options
     *            the options to set
     */
    public void setOptions(Map<String, Option> options) {
        this.options = options;
    }

    public void addOption(Option option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.put(option.getLabel(), option);
    }

    public void removeOption(Option option) {
        if (option == null || option.getLabel() == null || option.getLabel().trim().length() == 0) {
            return;
        }

        options.remove(option.getLabel());
    }
}
