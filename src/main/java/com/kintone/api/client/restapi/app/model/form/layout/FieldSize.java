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

public class FieldSize {
    private String width;
    private String height;
    private String innerHeight;

    public FieldSize() {

    }

    /**
     * @return the width
     */
    public String getWidth() {
        return width;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(String width) {
        this.width = width;
    }

    /**
     * @return the height
     */
    public String getHeight() {
        return height;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(String height) {
        this.height = height;
    }

    /**
     * @return the innerHeight
     */
    public String getInnerHeight() {
        return innerHeight;
    }

    /**
     * @param innerHeight
     *            the innerHeight to set
     */
    public void setInnerHeight(String innerHeight) {
        this.innerHeight = innerHeight;
    }
}
