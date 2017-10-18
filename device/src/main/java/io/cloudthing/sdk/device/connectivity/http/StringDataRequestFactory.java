package io.cloudthing.sdk.device.connectivity.http;

import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.EventPayload;
import io.cloudthing.sdk.device.data.ICloudThingMessage;
import okhttp3.Request;

/**
 * Created by kleptoman on 22.12.16.
 */
@Deprecated
public class StringDataRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    public StringDataRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        message = new EventPayload();
    }

    @Override
    protected Request getRequest(ICloudThingMessage message, ContentType contentType) {
        return getRequest();
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
