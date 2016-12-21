package io.cloudthing.sdk.device.connectivity.http;

import okhttp3.Request;

/**
 * Created by kleptoman on 02.09.16.
 */
public class ValidationRequestFactory extends DeviceRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    public ValidationRequestFactory(String tenant, String deviceId, String token) {
        super(deviceId, token, tenant);
    }

    @Override
    public Request getRequest() {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders())
                .get()
                .build();
    }

    @Override
    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId);
    }
}
