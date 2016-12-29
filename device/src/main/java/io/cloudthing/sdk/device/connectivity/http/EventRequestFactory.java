package io.cloudthing.sdk.device.connectivity.http;

import com.google.common.base.Strings;
import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.EventPayload;
import io.cloudthing.sdk.device.data.ICloudThingMessage;
import io.cloudthing.sdk.device.data.IEventPayload;
import okhttp3.Callback;
import okhttp3.Request;

/**
 * Created by kleptoman on 21.12.16.
 */
public class EventRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/events/%s";

    public static EventRequestFactoryBuilder builder() {
        return new EventRequestFactoryBuilder();
    }

    private String eventId;

    protected EventRequestFactory() {
    }

    public <T extends ICloudThingMessage & IEventPayload> Request getRequest(T payload) throws Exception {
        if (!Strings.isNullOrEmpty(payload.getEventId())) {
            eventId = payload.getEventId();
        }
        return getRequest(payload, ContentType.CUSTOM);
    }

    @Override
    protected Request getRequest(ICloudThingMessage message, ContentType contentType) throws Exception {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders(contentType))
                .post(getRequestBody(message, contentType))
                .build();
    }

    @Override
    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId, eventId);
    }

    @Deprecated
    public String getEventId() {
        return this.eventId;
    }

    @Deprecated
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public static class EventRequestFactoryBuilder {

        private String tenant;
        private String deviceId;
        private String token;
        private String eventId;

        private Callback listener;

        private EventRequestFactoryBuilder() {
        }

        public EventRequestFactory build() {
            EventRequestFactory result = new EventRequestFactory();
            result.setTenant(tenant);
            result.setDeviceId(deviceId);
            result.setToken(token);
            result.setEventId(eventId);
            if (listener != null) {
                result.setListener(listener);
            }

            return result;
        }

        public EventRequestFactoryBuilder setTenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public EventRequestFactoryBuilder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public EventRequestFactoryBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public EventRequestFactoryBuilder setListener(Callback listener) {
            this.listener = listener;
            return this;
        }

        public EventRequestFactoryBuilder setEventId(String eventId) {
            this.eventId = eventId;
            return this;
        }
    }

    @Deprecated
    public EventRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        this.message = new EventPayload();
    }

    @Deprecated
    public String getPayload() {
        return ((EventPayload)message).getPayload();
    }

    @Deprecated
    public void setPayload(String payload) {
        ((EventPayload)message).setPayload(payload);
    }
}
