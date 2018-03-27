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

import java.util.ArrayList;
import java.util.List;

import com.kintone.api.client.restapi.app.model.form.field.FieldType;
import com.kintone.api.client.restapi.app.model.form.field.input.InputField;

public class UserSelectionField extends InputField {
    protected List<Entity> defaultValue = new ArrayList<Entity>();
    protected List<Entity> entites = new ArrayList<Entity>();

    public UserSelectionField() {
        this.type = FieldType.USER_SELECT;
    }

    public UserSelectionField(String code) {
        this.code = code;
        this.type = FieldType.USER_SELECT;
    }

    /**
     * @return the defaultValue
     */
    public List<Entity> getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(List<Entity> defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the entites
     */
    public List<Entity> getEntites() {
        return entites;
    }

    /**
     * @param entites
     *            the entites to set
     */
    public void setEntites(List<Entity> entites) {
        this.entites = entites;
    }

    public void addEntities(Entity entity) {
        this.entites.add(entity);
    }

    public void removeEntites(Entity entity) {
        this.entites.remove(entity);
    }
}
