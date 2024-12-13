package com.wb.amr.robot.flotilla.control.system.mqtt;

import com.wb.amr.robot.flotilla.control.system.mqtt.states.*;
import lombok.Getter;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;

@Getter
public class MQTTStateMachine {

    private final MQTTDisconnectedState disconnectedState;
    private final MQTTConnectingState connectingState;
    private final MQTTConnectedState connectedState;
    private final MQTTReconnectingState reconnectingState;
    private final MQTTFailedState failedState;
    private final MQTTErrorState errorState;

    private MQTTConnectionState currentState;

    private MqttClient client;
    private MqttConnectionOptions options;

    public MQTTStateMachine(MqttClient client, MqttConnectionOptions options) {
        this.client = client;
        this.options = options;

        this.disconnectedState = new MQTTDisconnectedState(this);
        this.connectingState = new MQTTConnectingState(this);
        this.connectedState = new MQTTConnectedState(this);
        this.reconnectingState = new MQTTReconnectingState(this);
        this.failedState = new MQTTFailedState(this);
        this.errorState = new MQTTErrorState(this);

        this.currentState = disconnectedState;
    }

    public void setState(MQTTConnectionState state) {
        this.currentState = state;
    }
}
