package io.cloudthing.sdk.device.data.convert;

import com.google.common.base.Strings;
import io.cloudthing.sdk.device.data.ContentType;
import io.cloudthing.sdk.device.data.DataChunk;
import io.cloudthing.sdk.device.data.IDataPayload;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleptoman on 28.12.16.
 */
public class JsonPayloadConverter implements IPayloadConverter {

    private Map<String, Integer> keys = new HashMap<>();

    @Override
    public ContentType getContentType() {
        return ContentType.JSON;
    }

    @Override
    public byte[] convert(IDataPayload payload) throws Exception {
        keys.clear();
        JSONObject result = new JSONObject();
        result.put("i", indexKeys(payload.getDataChunks()));
        result.put("r", getRecords(payload.getDataChunks()));
        return result.toString().getBytes("utf-8");
    }

    private JSONArray indexKeys(Collection<DataChunk> data) {
        int iter = 0;
        JSONArray array = new JSONArray();
        for (DataChunk chunk: data) {
            if (Strings.isNullOrEmpty(chunk.getKey())) {
                throw new IllegalStateException("Data chunks must have keys set!");
            }
            if (keys.containsKey(chunk.getKey())) {
                continue;
            }
            keys.put(chunk.getKey(), iter);
            array.put(iter++, chunk.getKey());
        }
        return array;
    }

    private JSONArray getRecords(Collection<DataChunk> data) {
        JSONArray array = new JSONArray();
        for (DataChunk chunk: data) {
            array.put(toJson(chunk));
        }
        return array;
    }

    private JSONObject toJson(DataChunk chunk) {
        JSONObject result = new JSONObject();
        result.put("k", keys.get(chunk.getKey()));
        if (chunk.getValue()==null || Strings.isNullOrEmpty(chunk.getValue().toString())) {
            throw new IllegalStateException("Data chunks must have values set!");
        }
        result.put("v", chunk.getValue());
        if (chunk.hasTime()) {
            if (chunk.getDate() != null) {
                result.put("t", chunk.getDate().getTime()/1000);
            } else {
                result.put("t", chunk.getTimeIncrement());
            }
        }
        if (chunk.hasGeo()) {
            result.put("g", new JSONObject(chunk.getGeo()));
        }
        return result;
    }
}
