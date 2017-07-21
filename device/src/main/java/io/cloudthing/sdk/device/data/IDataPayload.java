package io.cloudthing.sdk.device.data;

import io.cloudthing.sdk.device.data.convert.IPayloadConverter;

import java.util.Collection;
import java.util.Date;

/**
 * Created by kleptoman on 22.12.16.
 */
public interface IDataPayload {

    String add(String key, Object value);

    String add(String key, Object value, Date date);

    String add(String key, Object value, long timeIncrement);

    String add(DataChunk data);

    boolean remove(String id);

    Collection<DataChunk> getDataChunks();

    void setConverter(IPayloadConverter converter);

    ContentType getContentType();

}
