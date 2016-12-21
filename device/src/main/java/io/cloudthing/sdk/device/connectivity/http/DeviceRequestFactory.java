package io.cloudthing.sdk.device.connectivity.http;

import com.google.common.io.BaseEncoding;
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

    protected final String tenant;
    protected final String deviceId;
    protected final String token;
    protected Callback listener;

    protected ICloudThingMessage message;

    protected DeviceRequestFactory(String deviceId, String token, String tenant) {
        this.deviceId = deviceId;
        this.token = token;
        this.tenant = tenant;
        initListeners();
    }

    public Request getRequest() {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders())
                .post(getRequestBody())
                .build();
    }

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

    protected Headers generateHeaders() {
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

    protected abstract String getUrl();

    public Callback getListener() {
        return listener;
    }

    public void setListener(Callback listener) {
        this.listener = listener;
    }

    protected RequestBody getRequestBody() {
        return RequestBody.create(MediaType.parse("application/json"), message.toBytes());
    }

}
