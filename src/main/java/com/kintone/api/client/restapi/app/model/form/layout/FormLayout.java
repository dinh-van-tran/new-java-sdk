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

package com.kintone.api.client.restapi.app.model.form.layout;

import java.util.ArrayList;
import java.util.List;

public class FormLayout {
    private String revision;
    private List<ItemLayout> layout = new ArrayList<ItemLayout>();

    public FormLayout() {
        
    }

    /**
     * @return the revision
     */
    public String getRevision() {
        return revision;
    }

    /**
     * @param revision the revision to set
     */
    public void setRevision(String revision) {
        this.revision = revision;
    }

    /**
     * @return the layout
     */
    public List<ItemLayout> getLayout() {
        return layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(List<ItemLayout> layout) {
        this.layout = layout;
    }
}
