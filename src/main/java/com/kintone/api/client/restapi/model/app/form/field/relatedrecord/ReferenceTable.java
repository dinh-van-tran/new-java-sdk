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

package com.kintone.api.client.restapi.model.app.form.field.relatedrecord;

import java.util.ArrayList;
import java.util.List;

import com.kintone.api.client.restapi.model.app.form.field.FieldMapping;

public class ReferenceTable {
    private FieldMapping condition;
    private String filterCond;
    private RelatedApp relatedApp;
    private int size;
    private List<String> displayFields = new ArrayList<String>();
    private String sort;

    /**
     * 
     */
    public ReferenceTable() {

    }

    /**
     * @param condition
     * @param filterCond
     * @param relatedApp
     * @param size
     * @param displayFields
     */
    public ReferenceTable(FieldMapping condition, String filterCond, RelatedApp relatedApp, int size,
            List<String> displayFields) {
        this.condition = condition;
        this.filterCond = filterCond;
        this.relatedApp = relatedApp;
        this.size = size;
        this.displayFields = displayFields;
    }

    /**
     * @return the condition
     */
    public FieldMapping getCondition() {
        return condition;
    }

    /**
     * @param condition the condition to set
     */
    public void setCondition(FieldMapping condition) {
        this.condition = condition;
    }

    /**
     * @return the filterCond
     */
    public String getFilterCond() {
        return filterCond;
    }

    /**
     * @param filterCond the filterCond to set
     */
    public void setFilterCond(String filterCond) {
        this.filterCond = filterCond;
    }

    /**
     * @return the relatedApp
     */
    public RelatedApp getRelatedApp() {
        return relatedApp;
    }

    /**
     * @param relatedApp the relatedApp to set
     */
    public void setRelatedApp(RelatedApp relatedApp) {
        this.relatedApp = relatedApp;
    }

    /**
     * @return the size
     */
    public int getSize() {
        return size;
    }

    /**
     * @param size the size to set
     */
    public void setSize(int size) {
        this.size = size;
    }

    /**
     * @return the displayFields
     */
    public List<String> getDisplayFields() {
        return displayFields;
    }

    /**
     * @param displayFields the displayFields to set
     */
    public void setDisplayFields(List<String> displayFields) {
        this.displayFields = displayFields;
    }

    /**
     * @return the sort
     */
    public String getSort() {
        return sort;
    }

    /**
     * @param sort the sort to set
     */
    public void setSort(String sort) {
        this.sort = sort;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((condition == null) ? 0 : condition.hashCode());
        result = prime * result + ((filterCond == null) ? 0 : filterCond.hashCode());
        result = prime * result + ((relatedApp == null) ? 0 : relatedApp.hashCode());
        return result;
    }

    /* (non-Javadoc)
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
        if (!(obj instanceof ReferenceTable)) {
            return false;
        }
        ReferenceTable other = (ReferenceTable) obj;
        if (condition == null) {
            if (other.condition != null) {
                return false;
            }
        } else if (!condition.equals(other.condition)) {
            return false;
        }
        if (filterCond == null) {
            if (other.filterCond != null) {
                return false;
            }
        } else if (!filterCond.equals(other.filterCond)) {
            return false;
        }
        if (relatedApp == null) {
            if (other.relatedApp != null) {
                return false;
            }
        } else if (!relatedApp.equals(other.relatedApp)) {
            return false;
        }
        return true;
    }
}
