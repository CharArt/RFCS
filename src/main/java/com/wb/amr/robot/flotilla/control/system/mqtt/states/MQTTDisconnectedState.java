package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTConnectionState;
import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTStateMachine;

public class MQTTDisconnectedState implements MQTTConnectionState {

    private MQTTStateMachine stateMachine;

    public MQTTDisconnectedState(MQTTStateMachine stateMachine) {
        this.stateMachine = stateMachine;
    }

    @Override
    public void disconnected() {
        if (!stateMachine.getClient().isConnected()) {
            stateMachine.setState(stateMachine.getConnectingState());
        } else {
            stateMachine.setState(stateMachine.getConnectedState());
        }
    }

    @Override
    public void connected() {

    }

    @Override
    public void connecting() {

    }

    @Override
    public void reconnecting() {

    }

    @Override
    public void errorOccurred() {

    }
}
