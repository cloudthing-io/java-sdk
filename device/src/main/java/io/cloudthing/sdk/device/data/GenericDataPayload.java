package io.cloudthing.sdk.device.data;

import io.cloudthing.sdk.device.data.convert.IPayloadConverter;

import java.util.*;

/**
 * Created by kleptoman on 22.12.16.
 */
public class GenericDataPayload implements ICloudThingMessage, IDataPayload {

    private Map<String, DataChunk> dataMap = new LinkedHashMap<>();
    private IPayloadConverter converter;

    @Override
    public String add(String key, String value) {
        UUID id = UUID.randomUUID();
        dataMap.put(id.toString(), new DataChunk(key, value));
        return id.toString();
    }

    @Override
    public String add(String key, String value, Date date) {
        UUID id = UUID.randomUUID();
        dataMap.put(id.toString(), new DataChunk(key, value, date));
        return id.toString();
    }

    @Override
    public String add(String key, String value, long timeIncrement) {
        UUID id = UUID.randomUUID();
        dataMap.put(id.toString(), new DataChunk(key, value, timeIncrement));
        return id.toString();
    }

    @Override
    public String add(DataChunk data) {
        UUID id = UUID.randomUUID();
        dataMap.put(id.toString(), data);
        return id.toString();
    }

    @Override
    public boolean remove(String id) {
        return dataMap.remove(id) != null;
    }

    @Override
    public Collection<DataChunk> getDataChunks() {
        return dataMap.values();
    }

    @Override
    public void setConverter(IPayloadConverter converter) {
        this.converter = converter;
    }

    @Override
    public ContentType getContentType() {
        return converter.getContentType();
    }

    @Override
    public byte[] toBytes() throws Exception {
        return converter.convert(this);
    }

    @Override
    public String toString() {
        return "GenericDataPayload{" +
                "dataMap=" + dataMap +
                '}';
    }
}
