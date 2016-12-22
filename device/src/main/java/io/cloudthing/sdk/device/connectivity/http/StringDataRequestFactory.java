package io.cloudthing.sdk.device.connectivity.http;

import io.cloudthing.sdk.device.data.EventPayload;

/**
 * Created by kleptoman on 22.12.16.
 */
public class StringDataRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    public StringDataRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        message = new EventPayload();
    }

    public String getPayload() {
        return ((EventPayload)message).getPayload();
    }

    public void setPayload(String payload) {
        ((EventPayload)message).setPayload(payload);
    }

    @Override
    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId);
    }
}
