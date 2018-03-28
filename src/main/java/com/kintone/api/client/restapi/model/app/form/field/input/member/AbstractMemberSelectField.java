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

package com.kintone.api.client.restapi.model.app.form.field.input.member;

import java.util.ArrayList;
import java.util.List;

import com.kintone.api.client.restapi.model.app.form.field.input.AbstractInputField;

public abstract class AbstractMemberSelectField extends AbstractInputField {
    protected List<MemberSelectEntity> defaultValue = new ArrayList<MemberSelectEntity>();
    protected List<MemberSelectEntity> entites = new ArrayList<MemberSelectEntity>();

    /**
     * @return the defaultValue
     */
    public List<MemberSelectEntity> getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue
     *            the defaultValue to set
     */
    public void setDefaultValue(List<MemberSelectEntity> defaultValue) {
        this.defaultValue = defaultValue;
    }

    /**
     * @return the entites
     */
    public List<MemberSelectEntity> getEntites() {
        return entites;
    }

    /**
     * @param entites
     *            the entites to set
     */
    public void setEntites(List<MemberSelectEntity> entites) {
        this.entites = entites;
    }
}
