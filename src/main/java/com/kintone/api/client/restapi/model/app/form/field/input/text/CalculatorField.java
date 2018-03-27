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

public class CalculatorField extends InputField {
    private String expression;
    private Boolean hideExpression;
    private Integer displayScale;
    private String unit;
    private UnitPosition unitPosition;
    private NumberFormat format;

    public CalculatorField() {
        this.type = FieldType.CALC;
    }

    public CalculatorField(String code) {
        this.code = code;
        this.type = FieldType.CALC;
    }

    /**
     * @return the expression
     */
    public String getExpression() {
        return expression;
    }

    /**
     * @param expression
     *            the expression to set
     */
    public void setExpression(String expression) {
        this.expression = expression;
    }

    /**
     * @return the hideExpression
     */
    public Boolean getHideExpression() {
        return hideExpression;
    }

    /**
     * @param hideExpression
     *            the hideExpression to set
     */
    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
    }

    /**
     * @return the displayScale
     */
    public Integer getDisplayScale() {
        return displayScale;
    }

    /**
     * @param displayScale
     *            the displayScale to set
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
     * @param unit
     *            the unit to set
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
     * @param unitPosition
     *            the unitPosition to set
     */
    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    /**
     * @return the format
     */
    public NumberFormat getFormat() {
        return format;
    }

    /**
     * @param format
     *            the format to set
     */
    public void setFormat(NumberFormat format) {
        this.format = format;
    }
}
