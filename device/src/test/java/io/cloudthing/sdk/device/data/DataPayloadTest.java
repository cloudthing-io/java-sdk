package io.cloudthing.sdk.device.data;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

/**
 * Created by kleptoman on 01.12.16.
 */
@RunWith(JUnit4.class)
public class DataPayloadTest {

    @Test
    public void hasData() throws Exception {
        DataPayload data = new DataPayload();
        assertFalse(data.hasData());
        data.putData("dataId", "dataValue");
        assertTrue(data.hasData());
        data.putData("newDataId", "dataValue");
        assertTrue(data.hasData());
    }

    @Test
    public void clearData() throws Exception {
        DataPayload data = new DataPayload();
        data.putData("dataId", "dataValue");
        data.clearData();
        assertFalse(data.hasData());
    }

    @Test
    public void putData() throws Exception {
        DataPayload data = new DataPayload();
        data.putData("dataId", "dataValue");
        assertTrue(data.hasData());
        assertEquals(1L, data.getData().size());
        assertTrue(data.getData().containsKey("dataId"));
        assertTrue(data.getData().containsValue("dataValue"));

        data.putData("newDataId", "dataValue");
        assertEquals(2L, data.getData().size());

        data.putData("dataId", "newDataValue");
        assertEquals(2L, data.getData().size());
        assertEquals("newDataValue", data.getData().get("dataId"));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void getData() throws Exception {
        DataPayload data = new DataPayload();
        data.putData("dataId", "dataValue");

        Map<String, String> dataMap = data.getData();
        assertNotNull(dataMap);
        assertEquals(1L, dataMap.size());
        assertTrue(data.getData().containsKey("dataId"));
        assertTrue(data.getData().containsValue("dataValue"));

        data.putData("newDataId", "dataValue");
        assertEquals(2L, dataMap.size());

        dataMap.put("invalid", "operation");
    }

    @Test(expected = IllegalStateException.class)
    public void toStringTest() throws Exception {
        String EXPECTED_ONE = "{\"r\":[{\"k\":\"dataId\",\"v\":\"dataValue\"}]}";
        DataPayload data = new DataPayload();
        data.putData("dataId", "dataValue");
        assertEquals(EXPECTED_ONE, data.toString());

        data.clearData();
        data.putData("dataId", "12");
        data.putData("dataId1", "12.1");
        data.putData("dataId2", "12.");
        data.putData("dataId4", ".2");
        data.putData("dataId3", "0.1");
        data.putData("newDataId", "dataValue");
        String output = data.toString();
        assertTrue(output.contains("{\"k\":\"dataId\",\"v\":12}"));
        assertTrue(output.contains("{\"k\":\"dataId1\",\"v\":12.1}"));
        assertTrue(output.contains("{\"k\":\"dataId2\",\"v\":12.}"));
        assertTrue(output.contains("{\"k\":\"dataId3\",\"v\":0.1}"));
        assertTrue(output.contains("{\"k\":\"dataId4\",\"v\":.2}"));
        assertTrue(output.contains("{\"k\":\"newDataId\",\"v\":\"dataValue\"}"));
        assertTrue(output.matches("\\{\\\"r\\\":\\[(\\{\\\"k\\\":\\\"\\w+\\\",\\\"v\\\":([0-9\\.]+|\\\"\\w+\\\")\\},?)+\\]\\}"));

        data.clearData();
        data.toString();
    }
}