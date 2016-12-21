package io.cloudthing.sdk.device.data;

import java.nio.charset.Charset;

/**
 * Created by kleptoman on 18.12.16.
 */

public class EventPayload implements ICloudThingMessage {

    private String payload = "";

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
    }

    @Override
    public String toString() {
        return payload;
    }

    @Override
    public byte[] toBytes() {
        return this.toString().getBytes(Charset.forName("UTF-8"));
    }
}
