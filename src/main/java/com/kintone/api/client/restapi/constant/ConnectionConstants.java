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
package com.kintone.api.client.restapi.connection;

public class ConnectionConstants {
    public static final String BASE_URL = "/k/v1/{API_NAME}.json";
    public static final String BASE_GUEST_URL = "/k/guest/{GUEST_SPACE_ID}/v1/{API_NAME}.json";
    public static final String GET_REQUEST = "GET";
    public static final String POST_REQUEST = "POST";
    public static final String PUT_REQUEST = "PUT";
    public static final String DELETE_REQUEST = "DELETE";
    public static final String HTTPS_PREFIX = "https://";
    public static final String USER_AGENT_KEY = "User-Agent";
    public static final String USER_AGENT_VALUE = "kintone-java-SDK";
    public static final String CONTENT_TYPE_HEADER = "Content-Type";

    public static final String APP = "app";
    public static final String APP_CUSTOMIZE = "app/customize";
    public static final String APP_CUSTOMIZE_PREVIEW = "preview/app/customize";
    public static final String APP_DEPLOY = "review/app/deploy";
    public static final String APP_DEPLOY_PREVIEW = "preview/app/deploy";
    public static final String APP_FIELDS = "app/form/fields";
    public static final String APP_FIELDS_PREVIEW = "preview/app/form/fields";
    public static final String APP_LAYOUT = "app/form/layout";
    public static final String APP_LAYOUT_PREVIEW = "preview/app/form/layout";
    public static final String APP_PERMISSION = "app/acl";
    public static final String APP_PERMISSION_PREVIEW = "preview/app/acl";
    public static final String APP_PREVIEW = "preview/app";
    public static final String APP_SETTINGS = "app/settings";
    public static final String APP_SETTINGS_PREVIEW = "preview/app/settings";
    public static final String APP_STATUS = "app/status";
    public static final String APP_STATUS_PREVIEW = "preview/app/status";
    public static final String APP_VIEWS = "app/views";
    public static final String APP_VIEWS_PREVIEW = "preview/app/views";
    public static final String APPS = "apps";
    public static final String BULK_REQUEST = "bulkRequest";
    public static final String FIELD_PERMISSION = "field/acl";
    public static final String FILE = "file";
    public static final String GUESTS = "guests";
    public static final String RECORD = "record";
    public static final String RECORD_ASSIGNEES = "record/assignees";
    public static final String RECORD_COMMENT = "record/comment";
    public static final String RECORD_COMMENTS = "record/comments";
    public static final String RECORD_PERMISSION = "record/acl";
    public static final String RECORD_STATUS = "record/status";
    public static final String RECORDS = "records";
    public static final String RECORDS_STATUS = "records/status";
    public static final String SPACE = "space";
    public static final String SPACE_BODY = "space/body";
    public static final String SPACE_GUEST = "space/guests";
    public static final String SPACE_MEMBER = "space/members";
    public static final String SPACE_TEMPLATE = "template/space";
    public static final String SPACE_THREAD = "space/thread";
    public static final String SPACE_THREAD_COMMENT = "space/thread/comment";
}
