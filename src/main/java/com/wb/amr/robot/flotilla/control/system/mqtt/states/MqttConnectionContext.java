package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
@Getter(AccessLevel.PROTECTED)
public class MqttConnectionContext {
    private final String contextId;
    private final DisconnectedState disconnectedState;
    private final ConnectedState connectedState;
    private final ReconnectingState reconnectingState;
    private final FailedState failedState;
    private final ErrorState errorState;
    private final MqttClient client;
    private final MqttConnectionOptions options;
    private final Integer reconnectionAttempts;
    private MqttClientState currentState;
    private Boolean reconnected = false;
    @Setter(AccessLevel.PROTECTED)
    private String topic;

    public MqttConnectionContext(MqttClient client, MqttConnectionOptions options, Integer reconnectionAttempts) {
        this.client = client;
        this.options = options;
        this.reconnectionAttempts = reconnectionAttempts;
        this.contextId = "context_of_" + client.getClientId();
        this.disconnectedState = new DisconnectedState(this);
        this.connectedState = new ConnectedState(this);
        this.reconnectingState = new ReconnectingState(this);
        this.failedState = new FailedState(this);
        this.errorState = new ErrorState(this);
        this.currentState = disconnectedState;//primary state
    }

    protected CallbackHandler getCallbackHandler() {
        return new CallbackHandler(this, new CountDownLatch(1));
    }

    protected void setState(MqttClientState state) {
        this.currentState = state;
    }

    public void connect(String topic) {
        this.currentState.connected(topic);
    }

    public void publish(String message, int qos) {
        this.currentState.publishing(message, qos);
    }

    public void subscribe(String topic, int qos) {
        this.currentState.subscribe(topic, qos);
    }

    public void disconnect() {
        currentState.disconnected();
    }

    protected void reconnect(String topic) {
        this.currentState.reconnecting(topic);
    }

    protected Boolean isReconnected() {
        return this.reconnected;
    }

    protected void setIsReconnected(Boolean flag) {
        this.reconnected = flag;
    }

}