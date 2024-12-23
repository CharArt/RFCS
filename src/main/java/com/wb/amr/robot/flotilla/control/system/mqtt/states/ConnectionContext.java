package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import lombok.AccessLevel;
import lombok.Getter;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.springframework.stereotype.Component;

@Getter(AccessLevel.PROTECTED)
@Component
public class ConnectionContext {

    private final DisconnectedState disconnectedState;
    private final ConnectedState connectedState;
    private final PublishingState publishingState;
    private final ReconnectingState reconnectingState;
    private final FailedState failedState;
    private final ErrorState errorState;
    private final MqttClient client;
    private final MqttConnectionOptions options;
    private ConnectionState currentState;

    public ConnectionContext(MqttClient client, MqttConnectionOptions options) {
        this.client = client;
        this.options = options;
        this.disconnectedState = new DisconnectedState(this);
        this.connectedState = new ConnectedState(this);
        this.publishingState = new PublishingState(this);
        this.reconnectingState = new ReconnectingState(this);
        this.failedState = new FailedState(this);
        this.errorState = new ErrorState(this);
        this.currentState = disconnectedState;
    }

    protected void setState(ConnectionState state) {
        this.currentState = state;
    }

    public void connect() {
        currentState.connected();
    }

    public void publish(String topic, String massage) {
        int qos = 2;
        boolean retain = true;
        byte[] payload = massage.getBytes();
        currentState.publishing(topic, payload, qos, retain);
    }

    public void disconnect() {
        currentState.disconnected();
    }

    protected CallbackHandler getCallbackHandler() {
        return new CallbackHandler(this);
    }
}