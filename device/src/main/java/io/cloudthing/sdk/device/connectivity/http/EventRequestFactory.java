package io.cloudthing.sdk.device.connectivity.http;

import io.cloudthing.sdk.device.data.EventPayload;

/**
 * Created by kleptoman on 21.12.16.
 */
public class EventRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/events/%s";

    protected EventRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        this.message = new EventPayload();
    }

    public String getPayload() {
        return ((EventPayload)message).getPayload();
    }

    public void setPayload(String payload) {
        ((EventPayload)message).setPayload(payload);
    }

    public String getEventId() {
        return ((EventPayload)message).getEventId();
    }

    public void setEventId(String eventId) {
        ((EventPayload)message).setEventId(eventId);
    }

    @Override
    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId, getEventId());
    }
}
