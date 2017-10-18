package io.cloudthing.sdk.device.connectivity.mqtt;

import io.cloudthing.sdk.device.data.ICloudThingMessage;
import org.eclipse.paho.client.mqttv3.*;

/**
 * Created by Krzysztof Drozynski on 17.03.17.
 */
public class MqttCloudthingClient implements IMqttCloudthingClient {

    private MqttConnectOptions connectOptions;
    private IMqttClient client;
    private MqttTopic topic;
    private MqttCallback callback;
    private String defaultTopic;
    private int qos;
    private boolean retain = false;

    MqttCloudthingClient(IMqttClient client, MqttConnectOptions connectOptions) {
        this.client = client;
        this.connectOptions = connectOptions;
    }

    @Override
    public void connect() throws MqttException {
        this.client.connect(this.connectOptions);
    }

    @Override
    public void disconnect() throws MqttException {
        this.client.disconnect();
    }

    @Override
    public boolean isConnected() {
        return this.client.isConnected();
    }

    @Override
    public void subscribe(String topic) throws MqttException {
        this.client.subscribe(topic);
    }

    @Override
    public void unsubscribe(String topic) throws MqttException {
        this.client.unsubscribe(topic);
    }

    @Override
    public void subscribe() throws MqttException {
        if (defaultTopic == null) {
            throw new IllegalStateException("Default topic was not set!");
        }
        this.client.subscribe(defaultTopic);
    }

    @Override
    public void unsubscribe() throws MqttException {
        if (defaultTopic == null) {
            throw new IllegalStateException("Default topic was not set!");
        }
        this.client.unsubscribe(defaultTopic);
    }

    @Override
    public MqttDeliveryToken publish(String topic, ICloudThingMessage message) throws MqttException {
        try {
            return this.client.getTopic(topic).publish(message.toBytes(), qos, retain);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    @Override
    public MqttDeliveryToken publish(String topic, ICloudThingMessage message, int qos) throws MqttException {
        try {
            return this.client.getTopic(topic).publish(message.toBytes(), qos, retain);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    @Override
    public MqttDeliveryToken publish(ICloudThingMessage message) throws MqttException {
        if (topic == null) {
            throw new IllegalStateException("Default topic was not set!");
        }
        try {
            return this.topic.publish(message.toBytes(), qos, retain);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    @Override
    public MqttDeliveryToken publish(ICloudThingMessage message, int qos) throws MqttException {
        if (topic == null) {
            throw new IllegalStateException("Default topic was not set!");
        }
        try {
            return this.topic.publish(message.toBytes(), qos, retain);
        } catch (Exception e) {
            throw new MqttException(e);
        }
    }

    @Override
    public MqttCallback getCallback() {
        return callback;
    }

    @Override
    public void setCallback(MqttCallback callback) {
        this.callback = callback;
        this.client.setCallback(callback);
    }

    public void setDefaultTopic(String topic) {
        this.defaultTopic = topic;
        this.topic = client.getTopic(topic);
    }

    public String getDefaultTopic() {
        return defaultTopic;
    }

    public void setQos(int qos) {
        this.qos = qos;
    }

    public int getQos() {
        return qos;
    }

    public boolean isRetain() {
        return retain;
    }

    public void setRetain(boolean retain) {
        this.retain = retain;
    }
}
