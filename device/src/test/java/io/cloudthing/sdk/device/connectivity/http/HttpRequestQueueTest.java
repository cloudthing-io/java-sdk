package io.cloudthing.sdk.device.connectivity.http;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by kleptoman on 02.12.16.
 */
@RunWith(JUnit4.class)
public class HttpRequestQueueTest {


    @Before
    public void SetUp() {

    }

    @Test
    public void getInstance() throws Exception {
        HttpRequestQueue queue = HttpRequestQueue.getInstance();
        assertNotNull(queue);

        assertEquals(queue, HttpRequestQueue.getInstance());
    }

    //TODO need to do better implementation of HttpRequestQueue, so we can mock Network for Volley
    @Test
    public void getRequestQueue() throws Exception {

    }

    //TODO ditto
    @Test
    public void setRequestQueue() throws Exception {

    }

    //TODO ditto
    @Test
    public void addToRequestQueue() throws Exception {

    }

}