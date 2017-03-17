package io.cloudthing.sdk.device.connectivity.mqtt;

import io.cloudthing.sdk.device.data.ICloudThingMessage;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttException;

/**
 * Created by Krzysztof Drozynski on 17.03.17.
 */
public interface IMqttCloudthingClient {
    void connect() throws MqttException;

    void disconnect() throws MqttException;

    boolean isConnected();

    void subscribe(String topic) throws MqttException;

    void unsubscribe(String topic) throws MqttException;

    void subscribe() throws MqttException;

    void unsubscribe() throws MqttException;

    MqttDeliveryToken publish(String topic, ICloudThingMessage message) throws MqttException;

    MqttDeliveryToken publish(String topic, ICloudThingMessage message, int qos) throws MqttException;

    MqttDeliveryToken publish(ICloudThingMessage message) throws MqttException;

    MqttDeliveryToken publish(ICloudThingMessage message, int qos) throws MqttException;

    MqttCallback getCallback();

    void setCallback(MqttCallback callback);
}
