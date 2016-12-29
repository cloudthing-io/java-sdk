package io.cloudthing.sdk.device.connectivity.http;

import com.google.common.io.BaseEncoding;
import okhttp3.Headers;
import okhttp3.Request;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleptoman on 02.09.16.
 */
public class ValidationRequestFactory {

    private static final String URL_TEMPLATE = "https://%s.cloudthing.io:444/v1/%s/data";

    protected String tenant;
    protected String deviceId;
    protected String token;

    public ValidationRequestFactory(String tenant, String deviceId, String token) {
        this.tenant = tenant;
        this.deviceId = deviceId;
        this.token = token;
    }

    public Request getRequest() {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders())
                .get()
                .build();
    }

    private Headers generateHeaders() {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", "application/json");
        result.put("Authorization", getAuthorization());
        return Headers.of(result);
    }

    private String getAuthorization() {
        String auth = deviceId + ":" + token;
        byte[] data = new byte[0];
        try {
            data = auth.getBytes("UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        String base64 = BaseEncoding.base64().encode(data);
        return "Basic " + base64;
    }

    protected String getUrl() {
        return String.format(URL_TEMPLATE, tenant, deviceId);
    }
}
