package io.cloudthing.sdk.device.data;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by kleptoman on 22.12.16.
 */
public class DataChunk {

    private String key;
    private String value;
    private Date date;
    private Long timeIncrement;
    private Double latitude;
    private Double longitude;

    public DataChunk(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public DataChunk(String key, String value, Date date) {
        this.key = key;
        this.value = value;
        this.date = date;
    }

    public DataChunk(String key, String value, Long timeIncrement) {
        this.key = key;
        this.value = value;
        this.timeIncrement = timeIncrement;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
        this.timeIncrement = null;
    }

    public Long getTimeIncrement() {
        return timeIncrement;
    }

    public void setTimeIncrement(Long timeIncrement) {
        this.timeIncrement = timeIncrement;
        this.date = null;
    }

    public boolean hasTime() {
        return date != null || timeIncrement != null;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setGeo(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Map<String, Double> getGeo() {
        Map<String, Double> result = new HashMap<>();
        result.put("lt", latitude);
        result.put("ln", longitude);
        return result;
    }

    public boolean hasGeo() {
        return latitude != null && longitude != null;
    }
}
