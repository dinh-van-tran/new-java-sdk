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

public class FieldMapping {
    private String field;
    private String relatedField;

    public FieldMapping() {
    }

    public FieldMapping(String field, String relatedFields) {
        this.field = field;
        this.relatedField = relatedFields;
    }

    /**
     * @return the field
     */
    public String getField() {
        return field;
    }

    /**
     * @param field
     *            the field to set
     */
    public void setField(String field) {
        this.field = field;
    }

    /**
     * @return the relatedFields
     */
    public String getRelatedFields() {
        return relatedField;
    }

    /**
     * @param relatedFields
     *            the relatedFields to set
     */
    public void setRelatedFields(String relatedFields) {
        this.relatedField = relatedFields;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((field == null) ? 0 : field.hashCode());
        result = prime * result + ((relatedField == null) ? 0 : relatedField.hashCode());
        return result;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof FieldMapping)) {
            return false;
        }
        FieldMapping other = (FieldMapping) obj;
        if (field == null) {
            if (other.field != null) {
                return false;
            }
        } else if (!field.equals(other.field)) {
            return false;
        }
        if (relatedField == null) {
            if (other.relatedField != null) {
                return false;
            }
        } else if (!relatedField.equals(other.relatedField)) {
            return false;
        }
        return true;
    }
}
