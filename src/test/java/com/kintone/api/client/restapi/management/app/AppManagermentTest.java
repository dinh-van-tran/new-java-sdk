package com.kintone.api.client.restapi.management.app;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Test;

import com.kintone.api.client.restapi.auth.Auth;
import com.kintone.api.client.restapi.connection.Connection;
import com.kintone.api.client.restapi.exception.KintoneAPIException;
import com.kintone.api.client.restapi.model.app.App;

public class AppManagermentTest {
    @Test
    public void testGetAppShouldSuccess() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        AppManagement appManagerment = new AppManagement(connection);
        App app = appManagerment.getApp(139);

        assertNotNull(app);
        assertEquals(139, app.getAppId());
        assertEquals(new Integer(1), app.getSpaceId());
        assertEquals(new Integer(1), app.getThreadId());
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
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        AppManagement appManagerment = new AppManagement(connection);
        App app = appManagerment.getApp(140);

        assertNotNull(app);
        assertEquals(140, app.getAppId());
        assertEquals(null, app.getSpaceId());
        assertEquals(null, app.getThreadId());
    }

    @Test(expected=KintoneAPIException.class)
    public void testGetAppShouldFail() throws KintoneAPIException {
        Auth auth = new Auth();
        auth.setPasswordAuth("dinh", "Dinh1990");
        Connection connection = new Connection("https://ox806.kintone.com", auth);
        connection.setProxy("10.224.136.41", 3128);

        AppManagement appManagerment = new AppManagement(connection);
        appManagerment.getApp(141);
    }
}
