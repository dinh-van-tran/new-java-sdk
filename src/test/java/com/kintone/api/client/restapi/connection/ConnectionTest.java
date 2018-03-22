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

import org.junit.Test;

import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.exception.KintoneAPIExeption;

public class ConnectionTest {

    @Test
    public void testGetRequestShouldSuccess() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "apis.json", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIExeption.class)
    public void testGetRequestShouldFailWhenGivenWrongDomain() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.comm", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "apis.json", "");
    }

    @Test(expected = KintoneAPIExeption.class)
    public void testGetRequestShouldFailWhenGivenWrongUsername() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("Dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "app.json", "");
    }

    @Test(expected = KintoneAPIExeption.class)
    public void testGetRequestShouldFailWhenGivenWrongPassword() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        connection.request("GET", "app.json", "");
    }

    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccess() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithTokenAuthenticationShouldSuccess() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setApiToken("Wbxsfd95bDKEjSf9wTtKu7VzcctVhkT0TfKJoIFm");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIExeption.class)
    public void testGetRequestWithTokenAuthenticationShouldFail() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setApiToken("UZVlLDkvO20252Lbzx1qbzI9V4dtiAMuMNBxnuDU");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithPassAuthenticationShouldSuccessWhenTokenAuthenticationNotAllow() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setApiToken("Wbxsfd95bDKEjSf9wTtKu7VzcctVhkT0TfKJoIFm");
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithBasicAuthenticationShouldSuccess() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("cybozu", "cybozu");
        auth.setBasicAuth("qasi", "qasi");
        Connection connection = new Connection("https://qasi-f14-basic.cybozu-dev.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=4", "");
        assertNotNull(result);
    }

    @Test(expected = KintoneAPIExeption.class)
    public void testGetRequestWithPasswordAuthenticationShouldFailWithBasicAuthenticationSite() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("cybozu", "cybozu");
        Connection connection = new Connection("https://qasi-f14-basic.cybozu-dev.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=4", "");
        assertNotNull(result);
    }

    @Test
    public void testGetRequestWithPasswordAuthenticationShouldSuccessWhenGivenBasicAuthentication() throws KintoneAPIExeption {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        auth.setBasicAuth("qasi", "qasi");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);
        String result = connection.request("GET", "app.json?id=139", "");
        assertNotNull(result);
    }
}
