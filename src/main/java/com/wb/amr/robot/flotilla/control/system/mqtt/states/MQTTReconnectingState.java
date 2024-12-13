package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTConnectionState;
import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTStateMachine;
import org.eclipse.paho.mqttv5.common.MqttException;

public class MQTTReconnectingState implements MQTTConnectionState {

    MQTTStateMachine stateMachine;

    public MQTTReconnectingState(MQTTStateMachine stateMachine) {
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

    }

    @Override
    public void reconnecting() {
        int attempts = 3;
        int counter = 0;
        while (stateMachine.getClient().isConnected() || counter < attempts) {
            try {
                stateMachine.getClient().reconnect();
                if (stateMachine.getClient().isConnected()) {
                    stateMachine.setState(stateMachine.getConnectedState());
                } else {
                    System.out.println("Failed reconnection attempt, attempt:" + counter);
                    counter++;
                }
            } catch (MqttException mqttException) {
                System.out.println(mqttException.getMessage());
                stateMachine.setState(stateMachine.getErrorState());
            }
        }
        if(!stateMachine.getClient().isConnected()){
            stateMachine.setState(stateMachine.getFailedState());
        }
    }

    @Override
    public void errorOccurred() {

    }

}
