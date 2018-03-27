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

package com.kintone.api.client.restapi.app.model.form.field;

import java.util.HashMap;
import java.util.Map;

public class FormFields {
    private Integer app;
    private Integer revision;
    private Map<String, FormField> properties;

    public FormFields() {
        app = -1;
        revision = null;
        properties = new HashMap<String, FormField>();
    }

    public FormFields(Integer app, Map<String, FormField> properties, Integer revision) {
        this.app = app;
        this.revision = revision;
        this.properties = properties;
    }

    /**
     * @return the app
     */
    public Integer getApp() {
        return app;
    }

    /**
     * @param app
     *            the app to set
     */
    public void setApp(Integer app) {
        this.app = app;
    }

    /**
     * @return the revision
     */
    public Integer getRevision() {
        return revision;
    }

    /**
     * @param revision
     *            the revision to set
     */
    public void setRevision(Integer revision) {
        this.revision = revision;
    }

    /**
     * @return the properties
     */
    public Map<String, FormField> getProperties() {
        return properties;
    }

    /**
     * @param properties
     *            the properties to set
     */
    public void setProperties(Map<String, FormField> properties) {
        this.properties = properties;
    }
}
