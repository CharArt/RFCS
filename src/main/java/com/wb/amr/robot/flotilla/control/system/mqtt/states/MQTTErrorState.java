package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTConnectionState;
import com.wb.amr.robot.flotilla.control.system.mqtt.MQTTStateMachine;

public class MQTTErrorState implements MQTTConnectionState {

    MQTTStateMachine stateMachine;

    public MQTTErrorState(MQTTStateMachine stateMachine) {
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

    }

    @Override
    public void errorOccurred() {

    }

    @Override
    public void messageArrived() {

    }

    @Override
    public void deliveryComplete() {

    }
}
