package io.cloudthing.sdk.device.connectivity.http;

import okhttp3.*;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kleptoman on 02.12.16.
 */
@RunWith(JUnit4.class)
public class DeviceRequestFactoryTest {

    private static final String TENANT = "tenant";
    private static final String DEVICE_ID = "deviceId";
    private static final String TOKEN = "token";

    @Before
    public void SetUp() {

    }

    @Test
    public void initListeners() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        requestFactory.listener = null;
        requestFactory.initListeners();

        assertNotNull(requestFactory.listener);
    }

    @Test
    public void generateHeaders() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        Headers headers = requestFactory.generateHeaders();
        assertEquals(2l, headers.size());
        assertNotNull(headers.get("Content-Type"));
        assertNotNull(headers.get("Authorization"));
        assertEquals("application/json", headers.get("Content-Type"));
        assertEquals("Basic ZGV2aWNlSWQ6dG9rZW4=", headers.get("Authorization"));
    }

    @Test
    public void getUrl() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        assertEquals("https://tenant.cloudthing.io:444/v1/deviceId/data", requestFactory.getUrl());
    }

    @Test
    public void setListener() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {

            }
        };
        requestFactory.setListener(callback);
        assertEquals(callback, requestFactory.listener);
    }

    @Test
    public void getRequestBody() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        requestFactory.putData("dataId", "dataValue");
        RequestBody body = requestFactory.getRequestBody();
        assertNotNull(body);
    }

    @Test(expected = IllegalStateException.class)
    public void clearData() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        requestFactory.putData("dataId", "dataValue");

        requestFactory.clearData();
        requestFactory.getRequestBody();
    }

    @Test
    public void putData() throws Exception {
        DeviceRequestFactory requestFactory = new ImplDeviceRequestFactory(DEVICE_ID, TOKEN, TENANT);
        requestFactory.putData("dataId", "dataValue");
        Map<String, String> data = requestFactory.getData();
        assertEquals(1l, data.size());
        assertTrue(data.containsKey("dataId"));
        assertEquals("dataValue", data.get("dataId"));

        requestFactory.putData("dataId", "newDataValue");
        assertEquals(1l, data.size());
        assertTrue(data.containsKey("dataId"));
        assertEquals("newDataValue", data.get("dataId"));

        requestFactory.putData("newDataId", "dataValue");
        assertEquals(2l, data.size());
        assertTrue(data.containsKey("dataId"));
        assertTrue(data.containsKey("newDataId"));
        assertEquals("dataValue", data.get("newDataId"));


    }

    private class ImplDeviceRequestFactory extends DeviceRequestFactory {

        protected ImplDeviceRequestFactory(String deviceId, String token, String tenant) {
            super(deviceId, token, tenant);
        }

        @Override
        public Request getRequest() {
            return null;
        }
    }

}