package io.cloudthing.sdk.device.connectivity.http;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

/**
 * Created by kleptoman on 02.09.16.
 */
public class HttpRequestQueue {

    private static HttpRequestQueue instance;
    private OkHttpClient client;

    public static HttpRequestQueue getInstance() {
        synchronized (HttpRequestQueue.class) {
            if (instance == null) {
                instance = new HttpRequestQueue();
            }
        }
        return instance;
    }

    private HttpRequestQueue() {
        client = new OkHttpClient.Builder().build();
    }

    public OkHttpClient getClient() {
        return client;
    }

    public void setClient(OkHttpClient client) {
        this.client = client;
    }

    public void addToRequestQueue(Request req, Callback callback) {
        getClient().newCall(req).enqueue(callback);
    }
}
