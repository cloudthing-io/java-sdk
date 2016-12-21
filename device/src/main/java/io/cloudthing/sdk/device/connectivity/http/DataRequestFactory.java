package io.cloudthing.sdk.device.connectivity.http;

import okhttp3.Request;
import okhttp3.RequestBody;
import org.json.JSONException;

/**
 * Created by kleptoman on 02.09.16.
 */
public class DataRequestFactory extends DeviceRequestFactory {

    public DataRequestFactory(String deviceId, String token, String tenant) {
        super(deviceId, token, tenant);
    }

    @Override
    public Request getRequest() {
        return new Request.Builder()
                .url(getUrl())
                .headers(generateHeaders())
                .post(getRequestBody())
                .build();
    }

}
