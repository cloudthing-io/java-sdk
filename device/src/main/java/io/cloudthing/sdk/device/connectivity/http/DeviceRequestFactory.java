package io.cloudthing.sdk.device.connectivity.http;

import com.google.common.collect.ImmutableMap;
import com.google.common.io.BaseEncoding;
import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.ICloudThingMessage;
import okhttp3.*;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleptoman on 02.09.16.
 */
public abstract class DeviceRequestFactory {

    private static final Map<ContentType, String> CONTENT_TYPE_MAP;

    protected String tenant;
    protected String deviceId;
    protected String token;

    protected Callback listener;

    protected ICloudThingMessage message;

    protected DeviceRequestFactory() {
        initListeners();
    }

    protected DeviceRequestFactory(String deviceId, String token, String tenant) {
        this();
        this.deviceId = deviceId;
        this.token = token;
        this.tenant = tenant;
    }

    @Deprecated
    public Request getRequest() {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders())
                .post(getRequestBody())
                .build();
    }

    protected abstract Request getRequest(ICloudThingMessage message, ContentType contentType) throws Exception;

    protected void initListeners() {
        listener = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);

                Headers responseHeaders = response.headers();
                for (int i = 0, size = responseHeaders.size(); i < size; i++) {
                    System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
                }

                System.out.println(response.body().string());
            }
        };
    }

    @Deprecated
    protected Headers generateHeaders() {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", "application/json");
        result.put("Authorization", getAuthorization());
        return Headers.of(result);
    }

    protected Headers generateHeaders(ContentType contentType) {
        Map<String, String> result = new HashMap<>();
        result.put("Content-Type", CONTENT_TYPE_MAP.get(contentType));
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

    protected abstract String getUrl();

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    @Deprecated
    protected RequestBody getRequestBody() {
        try {
            return RequestBody.create(MediaType.parse("application/json"), message.toBytes());
        } catch (Exception e) {
            e.printStackTrace();
            return RequestBody.create(MediaType.parse("application/json"), "Error");
        }
    }

    protected RequestBody getRequestBody(ICloudThingMessage message, ContentType contentType) throws Exception {
        return RequestBody.create(MediaType.parse( CONTENT_TYPE_MAP.get(contentType)), message.toBytes());
    }
    public String getTenant() {
        return tenant;
    }

    public void setTenant(String tenant) {
        this.tenant = tenant;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    static {
        ImmutableMap.Builder<ContentType, String> builder = ImmutableMap.builder();
        builder.put(ContentType.CBOR, "application/cbor");
        builder.put(ContentType.JSON, "application/json");
        builder.put(ContentType.CUSTOM, "application/binary");
        CONTENT_TYPE_MAP = builder.build();
    }
}
