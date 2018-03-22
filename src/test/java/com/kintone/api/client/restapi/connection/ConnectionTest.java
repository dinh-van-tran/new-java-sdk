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

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.fail;

import org.junit.Test;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.exception.KintoneAPIException;

public class ConnectionTest {
    private static final JsonParser jsonParser = new JsonParser();

    @Test
    public void testGetRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "apis.json", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongDomain() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.comm", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "apis.json", "");
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongUsername() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("Dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "app.json", "");
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestShouldFailWhenGivenWrongPassword() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "app.json", "");
    }

    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithTokenAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("Wbxsfd95bDKEjSf9wTtKu7VzcctVhkT0TfKJoIFm");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithTokenAuthenticationShouldFail() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("UZVlLDkvO20252Lbzx1qbzI9V4dtiAMuMNBxnuDU");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithPassAuthenticationShouldSuccessWhenTokenAuthenticationNotAllow() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("Wbxsfd95bDKEjSf9wTtKu7VzcctVhkT0TfKJoIFm");
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithBasicAuthenticationShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("cybozu", "cybozu");
        auth.setBasicAuth("qasi", "qasi");
        Connection connection = new Connection("https://qasi-f14-basic.cybozu-dev.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=4", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testGetRequestWithPasswordAuthenticationShouldFailWithBasicAuthenticationSite() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("cybozu", "cybozu");
        Connection connection = new Connection("https://qasi-f14-basic.cybozu-dev.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=4", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccessWhenGivenBasicAuthentication() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        auth.setBasicAuth("qasi", "qasi");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        JsonElement result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testPostRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonObject body = new JsonObject();
        body.addProperty("app", 140);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        body.add("text", textField);

        JsonElement result = connection.request("POST", "record.json", body.toString());
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testPostRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonElement result = connection.request("POST", "record.json", "");
        assertNotNull(result);
    }

    @Test
    public void testPutRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonObject body = new JsonObject();
        body.addProperty("app", 140);
        body.addProperty("id", 1);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test put");

        body.add("text", textField);

        JsonElement result = connection.request("PUT", "record.json", body.toString());
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testPutRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonElement result = connection.request("PUT", "record.json", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIException.class)
    public void testDeleteRequestShouldFailWhenGivenWrongBody() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonElement result = connection.request("PUT", "record.json", "");
        assertNotNull(result);
    }

    @Test
    public void testDeleteRequestShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        int appId = 140;

        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        JsonObject postBody = new JsonObject();
        postBody.addProperty("app", appId);

        JsonObject textField = new JsonObject();
        textField.addProperty("value", "test");

        postBody.add("text", textField);

        JsonElement postResult = connection.request("POST", "record.json", postBody.toString());
        assertNotNull(postResult);

        if(postResult.isJsonObject()) {
            String id = postResult.getAsJsonObject().get("id").getAsString();

            JsonObject deleteBody = new JsonObject();
            deleteBody.addProperty("app", appId);

            JsonArray ids = new JsonArray();
            ids.add(id);

            deleteBody.add("ids", ids);
            JsonElement deleleResult = connection.request("DELETE", "records.json", deleteBody.toString());
            assertNotNull(deleleResult);
        } else {
            fail();
        }
    }
}
