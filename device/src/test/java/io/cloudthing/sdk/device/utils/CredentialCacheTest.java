package io.cloudthing.sdk.device.utils;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kleptoman on 01.12.16.
 */
@RunWith(JUnit4.class)
public class CredentialCacheTest {
    @Test
    public void getInstance() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        assertNotNull(cache);
        assertEquals(cache, CredentialCache.getInstance());
    }

    @Test
    public void setCredentials() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        assertEquals("tenant", cache.getTenant());
        assertEquals("deviceId", cache.getDeviceId());
        assertEquals("token", cache.getToken());
    }

    @Test
    public void getTenant() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        assertEquals("tenant", cache.getTenant());
    }

    @Test
    public void setTenant() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        cache.setTenant("newTenant");
        assertEquals("newTenant", cache.getTenant());
        assertEquals("deviceId", cache.getDeviceId());
        assertEquals("token", cache.getToken());
    }

    @Test
    public void getDeviceId() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        assertEquals("deviceId", cache.getDeviceId());

    }

    @Test
    public void setDeviceId() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        cache.setDeviceId("newDeviceId");
        assertEquals("tenant", cache.getTenant());
        assertEquals("newDeviceId", cache.getDeviceId());
        assertEquals("token", cache.getToken());

    }

    @Test
    public void getToken() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        assertEquals("token", cache.getToken());
    }

    @Test
    public void setToken() throws Exception {
        CredentialCache cache = CredentialCache.getInstance();
        cache.setCredentials("tenant", "deviceId", "token");
        cache.setToken("newToken");
        assertEquals("tenant", cache.getTenant());
        assertEquals("deviceId", cache.getDeviceId());
        assertEquals("newToken", cache.getToken());
    }

}