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

import com.kintone.api.client.restapi.constant.LayoutType;

public class GroupLayout extends ItemLayout {
    private String code;
    private List<RowLayout> layout = new ArrayList<RowLayout>();

    public GroupLayout() {
        this.type = LayoutType.GROUP;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the layout
     */
    public List<RowLayout> getLayout() {
        return layout;
    }

    /**
     * @param layout the layout to set
     */
    public void setLayout(List<RowLayout> layout) {
        this.layout = layout;
    }
}
