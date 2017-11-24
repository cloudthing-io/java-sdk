package io.cloudthing.sdk.device.connectivity.mqtt;

import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

/**
 * Created by Krzysztof Drozynski on 17.03.17.
 */
public class MqttCloudthingClientBuilder {

    private static final String BROKER_TEMPLATE = "%s://%s:%d";
    private static final String URI_PREFIX = "tcp";
    private static final String URI_PREFIX_SECURE = "ssl";
    private static final long DEFAULT_PORT = 1883;
    private static final long DEFAULT_PORT_SECURE = 1884;

    private String tenant;
    private String deviceId;
    private String token;
    private boolean secure = false;
    private boolean cleanSession = true;
    private int qos = 0;
    private String defaultTopic;
    private String serverTemplate = "{tenant}.cloudthing.io";
    private long serverPort = DEFAULT_PORT;
    private MqttCallback mqttCallback;

    public static MqttCloudthingClientBuilder getBuilder() {
        return new MqttCloudthingClientBuilder();
    }

    public IMqttCloudthingClient build() throws MqttException {
        validate();
        MqttClient mqttClient = new MqttClient(getBrokerUri(), MqttClient.generateClientId(), new MemoryPersistence());
        MqttCloudthingClient cloudthingClient = new MqttCloudthingClient(mqttClient, generateOptions());
        cloudthingClient.setQos(qos);
        if (defaultTopic != null && !"".equals(defaultTopic)) {
            cloudthingClient.setDefaultTopic(defaultTopic);
        }
        if (mqttCallback != null) {
            cloudthingClient.setCallback(mqttCallback);
        }
        return cloudthingClient;
    }

    public MqttCloudthingClientBuilder setTenant(String tenant) {
        this.tenant = tenant;
        return this;
    }

    public MqttCloudthingClientBuilder setDeviceId(String deviceId) {
        this.deviceId = deviceId;
        return this;
    }

    public MqttCloudthingClientBuilder setToken(String token) {
        this.token = token;
        return this;
    }

    public MqttCloudthingClientBuilder setSecure(boolean secure) {
        this.secure = secure;
        this.serverPort = secure ? DEFAULT_PORT_SECURE : DEFAULT_PORT;
        return this;
    }

    public MqttCloudthingClientBuilder setQos(int qos) {
        this.qos = qos;
        return this;
    }

    public MqttCloudthingClientBuilder setDefaultTopic(String defaultTopic) {
        this.defaultTopic = defaultTopic;
        return this;
    }

    public MqttCloudthingClientBuilder setServerTemplate(String serverTemplate) {
        this.serverTemplate = serverTemplate;
        return this;
    }

    public MqttCloudthingClientBuilder setServerPort(long serverPort) {
        this.serverPort = serverPort;
        return this;
    }

    public MqttCloudthingClientBuilder setMqttCallback(MqttCallback mqttCallback) {
        this.mqttCallback = mqttCallback;
        return this;
    }

    public MqttCloudthingClientBuilder setCleanSession(boolean cleanSession) {
        this.cleanSession = cleanSession;
        return this;
    }

    private String getBrokerUri() {
        String prefix = secure ? URI_PREFIX_SECURE : URI_PREFIX;
        return String.format(BROKER_TEMPLATE, prefix, getServerName(), serverPort);
    }

    private String getServerName() {
        return serverTemplate.replaceAll("\\{tenant}", tenant);
    }

    private MqttConnectOptions generateOptions() {
        MqttConnectOptions connectOptions = new MqttConnectOptions();
        connectOptions.setUserName(this.tenant + ":" + deviceId);
        connectOptions.setPassword(token.toCharArray());
        connectOptions.setCleanSession(cleanSession);
        connectOptions.setConnectionTimeout(30);
        return connectOptions;
    }

    private void validate() {
        if (tenant == null || "".equals(tenant)
                || deviceId == null || "".equals(deviceId)
                || token == null || "".equals(token)) {
            throw new IllegalStateException("Tenant, deviceId and token have to be set!");
        }
    }
}
