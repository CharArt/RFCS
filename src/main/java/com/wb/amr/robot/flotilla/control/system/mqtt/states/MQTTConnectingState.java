package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTConnectionState;
import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTStateMachine;
import org.eclipse.paho.mqttv5.common.MqttException;

public class MQTTConnectingState implements MQTTConnectionState {

    MQTTStateMachine stateMachine;

    public MQTTConnectingState(MQTTStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }


    @Override
    public void disconnected() {

    }

    @Override
    public void connected() {

    }

    @Override
    public void connecting() {
        try {
            stateMachine.getClient().connect(stateMachine.getOptions());
            if (stateMachine.getClient().isConnected()) {
                stateMachine.setState(stateMachine.getConnectedState());
            } else {
                stateMachine.setState(stateMachine.getReconnectingState());
            }
        } catch (MqttException mqttException) {
            System.out.println(mqttException.getMessage());
            stateMachine.setState(stateMachine.getErrorState());
        }
    }

    @Override
    public void reconnecting() {

    }

    @Override
    public void errorOccurred() {

    }

}
