package io.cloudthing.sdk.device.data;

import java.nio.charset.Charset;

/**
 * Created by kleptoman on 18.12.16.
 */

public class EventPayload implements ICloudThingMessage, IEventPayload {

    private String eventId;
    private String payload;
    private byte[] bytes;

    public String getPayload() {
        return payload;
    }

    public void setPayload(String payload) {
        this.payload = payload;
        this.bytes = null;
    }

    public byte[] getBytes() {
        return bytes;
    }

    @Override
    public void setBytes(byte[] bytes) {
        this.bytes = bytes;
        this.payload = null;
    }

    @Override
    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public boolean isPureBytes() {
        return bytes != null;
    }


    @Override
    public String toString() {
        return isPureBytes() ? new String(bytes, Charset.forName("UTF-8")) : payload;
    }

    @Override
    public byte[] toBytes() {
        return isPureBytes() ? bytes : payload.getBytes(Charset.forName("UTF-8"));
    }
}
