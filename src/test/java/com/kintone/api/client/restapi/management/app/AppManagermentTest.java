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

package com.kintone.api.client.restapi.management.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;

public class AppManagermentTest {
    private AppManagement appManagerment;

    @Before
    public void setup() {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        this.appManagerment = new AppManagement(connection);
    }

    @Test
    public void testGetAppShouldSuccess() throws KintoneAPIException {
        App app = this.appManagerment.getApp(139);

        assertNotNull(app);
        assertEquals(Integer.valueOf(139), app.getAppId());
        assertEquals(Integer.valueOf(1), app.getSpaceId());
        assertEquals(Integer.valueOf(1), app.getThreadId());
        assertNotNull(app.getCode());
        assertNotNull(app.getName());
        assertNotNull(app.getDescription());
        assertNotNull(app.getCreatedAt());
        assertNotNull(app.getCreator());
        assertNotNull(app.getModifiedAt());
        assertNotNull(app.getModifier());
    }

    @Test
    public void testGetAppShouldSuccessWhenAppIsNotInSpaceOrThead() throws KintoneAPIException {
        App app = this.appManagerment.getApp(140);

        assertNotNull(app);
        assertEquals(Integer.valueOf(140), app.getAppId());
        assertEquals(null, app.getSpaceId());
        assertEquals(null, app.getThreadId());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppShouldFail() throws KintoneAPIException {
        this.appManagerment.getApp(141);
    }

    @Test
    public void testGetAppsShouldSuccess() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongLimitValue() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getApps(200, null);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsShouldFailWhenGivenWrongOffsetValue() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getApps(1, -2);
        assertTrue(!apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsShouldFailWhenUsingTokenAPI() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setApiToken("Wbxsfd95bDKEjSf9wTtKu7VzcctVhkT0TfKJoIFm");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        AppManagement appManagerment = new AppManagement(connection);
        List<App> apps = appManagerment.getApps(null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsShouldReturnListHasOneElementWhenGivenLimitIsOne() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getApps(1, 0);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldSuccess() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(127);
        appIds.add(129);
        List<App> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithIdsShouldReturnEmptyArrayWhenGivenInvalidIds() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(1);
        appIds.add(2);
        List<App> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsWithIdsShouldFailWhenGivenInvalidIds() throws KintoneAPIException {
        List<Integer> appIds = new ArrayList<Integer>();
        appIds.add(-1);
        appIds.add(-2);
        List<App> apps = this.appManagerment.getAppsByIDs(appIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithCodeShouldSuccess() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("test1");
        codes.add("test2");
        List<App> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertEquals(2, apps.size());
    }

    @Test
    public void testGetAppsWithCodesShouldReturnEmptyArrayWhenGivenNonExistCodes() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("test3");
        codes.add("test4");
        List<App> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsWithCodesShouldFailWhenInvalidFormatCodes() throws KintoneAPIException {
        List<String> codes = new ArrayList<String>();
        codes.add("Invalid Code");
        List<App> apps = this.appManagerment.getAppsByCodes(codes, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldSuccess() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getAppsByName("Testing     app", null, null);
        assertEquals(1, apps.size());
    }

    @Test
    public void testGetAppsWithNameShouldReturnEmptyArrayWhenGivenNonExistName() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getAppsByName("Non exits name", null, null);
        assertTrue(apps.isEmpty());
    }

    @Test
    public void testGetAppsWithNameShouldNotReturnEmptyArrayWhenGivenNoName() throws KintoneAPIException {
        List<App> apps = this.appManagerment.getAppsByName(null, null, null);
        assertTrue(!apps.isEmpty());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldSuccess() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(1);
        List<App> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertEquals(9, apps.size());
    }

    @Test
    public void testGetAppsWithSpaceIdShouldReturnEmptyArrayWhenGivenNonExistSpaceId() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(2);
        List<App> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertTrue(apps.isEmpty());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppsWithSpaceIdShouldFailWhenGivenInvalidSpaceId() throws KintoneAPIException {
        List<Integer> spaceIds = new ArrayList<Integer>();
        spaceIds.add(-1);
        List<App> apps = this.appManagerment.getAppsBySpaceIDs(spaceIds, null, null);
        assertTrue(apps.isEmpty());
    }
}
