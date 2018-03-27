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

package com.kintone.api.client.restapi.app.parser;

import java.util.List;
import java.util.Map;

import com.google.gson.JsonObject;
import com.kintone.api.client.restapi.app.model.form.field.FieldType;
import com.kintone.api.client.restapi.app.model.form.field.input.lookup.LookupItem;
import com.kintone.api.client.restapi.app.model.form.field.input.selection.AlignLayout;
import com.kintone.api.client.restapi.app.model.form.field.input.selection.Entity;
import com.kintone.api.client.restapi.app.model.form.field.input.selection.Option;
import com.kintone.api.client.restapi.app.model.form.field.input.text.LinkProtocol;
import com.kintone.api.client.restapi.app.model.form.field.input.text.NumberFormat;
import com.kintone.api.client.restapi.app.model.form.field.input.text.UnitPosition;
import com.kintone.api.client.restapi.app.model.form.field.relatedrecord.ReferenceTable;

public class FormFieldParseData {
    private String code;
    private FieldType type;
    private String label;
    private String expression;
    private String unit;
    private Boolean noLabel;
    private Boolean openGroup;
    private Boolean enabled;
    private Boolean required;
    private Boolean digit;
    private Boolean unique;
    private Boolean hideExpression;
    private Boolean defaultNowValue;
    private String maxLength;
    private String minLength;
    private String maxValue;
    private String minValue;
    private String thumbnailSize;
    private String displayScale;
    private LookupItem lookup;
    private ReferenceTable referenceTable;
    private List<Entity> entities;
    private Map<String, Option> options;
    private LinkProtocol protocol;
    private AlignLayout align;
    private Object defaultValue;
    private UnitPosition unitPosition;
    private NumberFormat format;
    private JsonObject fields;

    public FormFieldParseData() {

    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public FieldType getType() {
        return type;
    }

    public void setType(FieldType type) {
        this.type = type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public Boolean getNoLabel() {
        return noLabel;
    }

    public void setNoLabel(Boolean noLabel) {
        this.noLabel = noLabel;
    }

    public Boolean getOpenGroup() {
        return openGroup;
    }

    public void setOpenGroup(Boolean openGroup) {
        this.openGroup = openGroup;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getRequired() {
        return required;
    }

    public void setRequired(Boolean required) {
        this.required = required;
    }

    public Boolean getDigit() {
        return digit;
    }

    public void setDigit(Boolean digit) {
        this.digit = digit;
    }

    public Boolean getUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public LinkProtocol getProtocol() {
        return protocol;
    }

    public void setProtocol(LinkProtocol protocol) {
        this.protocol = protocol;
    }

    public Map<String, Option> getOptions() {
        return options;
    }

    public void setOptions(Map<String, Option> options) {
        this.options = options;
    }

    public AlignLayout getAlign() {
        return align;
    }

    public void setAlign(AlignLayout align) {
        this.align = align;
    }

    public Boolean getDefaultNowValue() {
        return defaultNowValue;
    }

    public void setDefaultNowValue(Boolean defaultNowValue) {
        this.defaultNowValue = defaultNowValue;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public void setEntities(List<Entity> entities) {
        this.entities = entities;
    }

    public String getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(String maxLength) {
        this.maxLength = maxLength;
    }

    public String getMinLength() {
        return minLength;
    }

    public void setMinLength(String minLength) {
        this.minLength = minLength;
    }

    public String getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(String maxValue) {
        this.maxValue = maxValue;
    }

    public String getMinValue() {
        return minValue;
    }

    public void setMinValue(String minValue) {
        this.minValue = minValue;
    }

    public String getThumbnailSize() {
        return thumbnailSize;
    }

    public void setThumbnailSize(String thumbnailSize) {
        this.thumbnailSize = thumbnailSize;
    }

    public LookupItem getLookup() {
        return lookup;
    }

    public void setLookup(LookupItem lookup) {
        this.lookup = lookup;
    }

    public ReferenceTable getReferenceTable() {
        return referenceTable;
    }

    public void setReferenceTable(ReferenceTable referenceTable) {
        this.referenceTable = referenceTable;
    }

    public Object getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public Boolean getHideExpression() {
        return hideExpression;
    }

    public void setHideExpression(Boolean hideExpression) {
        this.hideExpression = hideExpression;
    }

    public String getDisplayScale() {
        return displayScale;
    }

    public void setDisplayScale(String displayScale) {
        this.displayScale = displayScale;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public UnitPosition getUnitPosition() {
        return unitPosition;
    }

    public void setUnitPosition(UnitPosition unitPosition) {
        this.unitPosition = unitPosition;
    }

    public NumberFormat getFormat() {
        return format;
    }

    public void setFormat(NumberFormat format) {
        this.format = format;
    }

    public void setFields(JsonObject fields) {
        this.fields = fields;
    }

    /**
     * @return the enabled
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * @return the fields
     */
    public JsonObject getFields() {
        return fields;
    }
}
