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

package com.kintone.api.client.restapi.exception;

public class KintoneAPIException extends Exception {
    private static final long serialVersionUID = 1L;
    private int httpErrorCode;
    private ErrorResponse errorResponse;

    public KintoneAPIException(int httpErrorCode, ErrorResponse errorResponse) {
        super(errorResponse.getMessage());
        this.httpErrorCode = httpErrorCode;
        this.errorResponse = errorResponse;
    }

    public KintoneAPIException(String error) {
        super(error);
    }

    /**
     * @return the httpErrorCode
     */
    public int getHttpErrorCode() {
        return httpErrorCode;
    }

    /**
     * @return the errorResponse
     */
    public ErrorResponse getErrorResponse() {
        return errorResponse;
    }

    @Override
    public String toString() {
        if (this.errorResponse == null) {
            return super.toString();
        }

        StringBuilder sb = new StringBuilder();
        sb.append("id: " + errorResponse.getId());
        sb.append(", code: " + errorResponse.getCode());
        sb.append(", message: " + errorResponse.getMessage());
        sb.append(", status: " + this.httpErrorCode);

        return sb.toString();
    }
}
