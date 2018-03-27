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

package com.kintone.api.client.restapi.model.app.form.field.input.text;
import com.kintone.api.client.restapi.model.app.form.field.FieldType;
import com.kintone.api.client.restapi.model.app.form.field.input.InputField;

public class NumberField extends InputField {
    private Integer displayScale;
    private String unit;
    private UnitPosition unitPosition;
    private Boolean digit;
    private Integer maxValue;
    private Integer minValue;
    private String defaultValue;

    public NumberField(String code) {
        this.code = code;
        this.type = FieldType.NUMBER;
    }

    /**
     * @return the displayScale
     */
    public Integer getDisplayScale() {
        return displayScale;
    }

    /**
     * @param displayScale the displayScale to set
     */
    public void setDisplayScale(Integer displayScale) {
        this.displayScale = displayScale;
    }

    /**
     * @return the unit
     */
    public String getUnit() {
        return unit;
    }

    /**
     * @param unit the unit to set
     */
    public void setUnit(String unit) {
        this.unit = unit;
    }

    /**
     * @return the unitPosition
     */
    public UnitPosition getUnitPosition() {
        return unitPosition;
    }

    /**
     * @param unitPosition the unitPosition to set
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return the digit
     */
    public Boolean getDigit() {
        return digit;
    }

    /**
     * @param digit the digit to set
     */
    public void setDigit(Boolean digit) {
        this.digit = digit;
    }

    /**
     * @return the maxValue
     */
    public Integer getMaxValue() {
        return maxValue;
    }

    /**
     * @param maxValue the maxValue to set
     */
    public void setMaxValue(Integer maxValue) {
        this.maxValue = maxValue;
    }

    /**
     * @return the minValue
     */
    public Integer getMinValue() {
        return minValue;
    }

    /**
     * @param minValue the minValue to set
     */
    public void setMinValue(Integer minValue) {
        this.minValue = minValue;
    }

    /**
     * @return the defaultValue
     */
    public String getDefaultValue() {
        return defaultValue;
    }

    /**
     * @param defaultValue the defaultValue to set
     */
    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
