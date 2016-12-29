package io.cloudthing.sdk.device.data;

/**
 * Created by kleptoman on 05.12.16.
 */

public interface ICloudThingMessage {

    byte[] toBytes() throws Exception;

}
