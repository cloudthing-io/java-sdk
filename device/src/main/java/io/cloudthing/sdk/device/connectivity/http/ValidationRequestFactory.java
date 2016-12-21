package io.cloudthing.sdk.device.connectivity.http;

import okhttp3.Request;

import java.util.Map;

/**
 * Created by kleptoman on 02.09.16.
 */
public class ValidationRequestFactory extends DeviceRequestFactory {

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

}
