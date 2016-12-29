package io.cloudthing.sdk.device.data.convert;

import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.IDataPayload;

/**
 * Created by kleptoman on 28.12.16.
 */
public interface IPayloadConverter {

    ContentType getContentType();

    byte[] convert(IDataPayload payload) throws Exception;

}
