package io.cloudthing.sdk.device.data;

/**
 * Created by kleptoman on 29.12.16.
 */
public interface IEventPayload {

    void setBytes(byte[] bytes);

    String getEventId();
}
