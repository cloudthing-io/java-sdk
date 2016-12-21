package io.cloudthing.sdk.device.connectivity.mqtt;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by kleptoman on 14.09.16.
 */
public class ClientFactory {

    private static final String BROKER_TEMPLATE = "tcp://%s.cloudthing.io:1883";

    private static ClientFactory instance;

    public static ClientFactory getInstance() {
        if (instance == null) {
            instance = new ClientFactory();
        }
        return instance;
    }

    private ClientFactory() {

    }

    public MqttClient getClient(String tenant) {
        MemoryPersistence persistence = new MemoryPersistence();
        MqttClient client = null;
        try {
            client = new MqttClient(String.format(BROKER_TEMPLATE, tenant), MqttClient.generateClientId(), persistence);
        } catch (MqttException e) {
            e.printStackTrace();
        }
        return client;
    }
}
