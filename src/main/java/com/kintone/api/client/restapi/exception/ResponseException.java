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

/**
 * The class {@code Exception} and it's subclasses are a form of
 * {@code Throwable} that indicates conditions that a reasonable
 * application might want to catch.
 * 
 * <p>The class {@code Exception} and any subclasses that are not also
 * subclasses of {@link RuntimeException} are <em>checked exception</em>. 
 * Checked exceptions need to be declared in a method or constructor's {@code throw}
 * clause if they can be thrown by the execution of the method or constructor and propagate
 * outside the method or constructor boundary
 * 
 * @author d001105
 * @see java.lang.Error
 * @since 1.0
 *
 */
public class ResponseException extends Exception {
    private static final long serialVersionUID = 4968297667941072064L;
    private ErrorResponse error;
    private int httpStatus;

    public ResponseException() {
        super();
    }

    public ResponseException(Throwable e) {
        super(e);
    }

    public ResponseException(String message) {
        super(message);
    }

    public ResponseException(String message, Throwable e) {
        super(message, e);
    }

    public ResponseException(int httpStatus, ErrorResponse error) {
        super(error.getMessage());
        this.error = error;
        this.httpStatus = httpStatus;
    }

    /**
     * @return the error
     */
    public ErrorResponse getError() {
        return error;
    }

    /**
     * @param error
     *            the error to set
     */
    public void setError(ErrorResponse error) {
        this.error = error;
    }

    /**
     * @return the httpStatus
     */
    public int getHttpStatus() {
        return httpStatus;
    }

    /**
     * @param httpStatus
     *            the httpStatus to set
     */
    public void setHttpStatus(int httpStatus) {
        this.httpStatus = httpStatus;
    }

    @Override
    public String toString() {
        if (error == null)
            return super.toString();
        StringBuilder sb = new StringBuilder();
        sb.append("id: " + error.getId());
        sb.append(", code: " + error.getCode());
        sb.append(", message: " + error.getMessage());
        sb.append(", status: " + this.getHttpStatus());

        return sb.toString();
    }
}
