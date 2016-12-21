package io.cloudthing.sdk.device.connectivity.http;

import io.cloudthing.sdk.device.data.DataPayload;

import java.util.Map;

/**
 * Created by kleptoman on 02.09.16.
 */
public class DataRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    public DataRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        this.message = new DataPayload();
    }

    public void clearData() {
        ((DataPayload)message).clearData();
    }

    public void putData(String dataId, String dataValue) {
        ((DataPayload)message).putData(dataId, dataValue);
    }

    public Map<String, String> getData() {
        return ((DataPayload)message).getData();
    }

    @Override
    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId);
    }
}
