package io.cloudthing.sdk.device.data;

import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleptoman on 29.11.16.
 */

public final class DataPayload implements ICloudThingMessage {

    private static final String BODY_TEMPLATE = "{\"r\":[%s]}";
    private static final String DATA_OBJ_TEMPLATE = "{\"k\":\"%s\",\"v\":%s}";

    private Map<String, String> data = new HashMap<>();

    public void clearData() {
        data.clear();
    }

    public boolean hasData() {
        return !data.isEmpty();
    }

    public void putData(String dataId, String dataValue) {
        data.put(dataId, dataValue);
    }

    public Map<String, String> getData() {
        return Collections.unmodifiableMap(data);
    }

    @Override
    public String toString() {
        if (data.isEmpty()) {
            throw new IllegalStateException("There's nothing to be sent!");
        }
        StringBuilder sBuilder = new StringBuilder();
        int iter = 0;
        for (Map.Entry<String, String> dataEntry: data.entrySet()) {
            if (iter != 0) {
                sBuilder.append(',');
            }
            sBuilder.append(String.format(DATA_OBJ_TEMPLATE, dataEntry.getKey(), getFormattedValue(dataEntry.getValue())));
            iter++;
        }

        return String.format(BODY_TEMPLATE, sBuilder.toString());
    }

    public byte[] toBytes() {
        return this.toString().getBytes(Charset.forName("UTF-8"));
    }

    private String getFormattedValue(String value) {
        if (isNumeric(value)) {
            return value;
        }
        return String.format("\"%s\"", value);
    }

    private boolean isNumeric(String s) {
        return s.matches("[-+]?\\d*\\.?\\d*");
    }
}
