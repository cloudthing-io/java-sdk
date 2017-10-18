package io.cloudthing.sdk.device.connectivity.http;

import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.DataPayload;
import io.cloudthing.sdk.device.data.ICloudThingMessage;
import io.cloudthing.sdk.device.data.IDataPayload;
import okhttp3.Callback;
import okhttp3.Request;

import java.util.Map;

/**
 * Created by kleptoman on 02.09.16.
 */
public class DataRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    public static DataRequestFactoryBuilder builder() {
        return new DataRequestFactoryBuilder();
    }

    protected DataRequestFactory() {
        initListeners();
    }

    public <T extends IDataPayload & ICloudThingMessage> Request getRequest(T dataPayload) throws Exception {
        return getRequest(dataPayload, dataPayload.getContentType());
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
        return String.format(URL_TEMPLATE, tenant, deviceId);
    }

    public static class DataRequestFactoryBuilder {

        private String tenant;
        private String deviceId;
        private String token;

        private Callback listener;

        private DataRequestFactoryBuilder() {
        }

        public DataRequestFactory build() {
            DataRequestFactory result = new DataRequestFactory();
            result.setTenant(tenant);
            result.setDeviceId(deviceId);
            result.setToken(token);
            if (listener != null) {
                result.setListener(listener);
            }

            return result;
        }

        public DataRequestFactoryBuilder setTenant(String tenant) {
            this.tenant = tenant;
            return this;
        }

        public DataRequestFactoryBuilder setDeviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public DataRequestFactoryBuilder setToken(String token) {
            this.token = token;
            return this;
        }

        public DataRequestFactoryBuilder setListener(Callback listener) {
            this.listener = listener;
            return this;
        }
    }


    @Deprecated
    public DataRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
        this.message = new DataPayload();
    }

    @Deprecated
    public void clearData() {
        ((DataPayload)message).clearData();
    }

    @Deprecated
    public void putData(String dataId, String dataValue) {
        ((DataPayload)message).putData(dataId, dataValue);
    }

    @Deprecated
    public Map<String, String> getData() {
        return ((DataPayload)message).getData();
    }

}
