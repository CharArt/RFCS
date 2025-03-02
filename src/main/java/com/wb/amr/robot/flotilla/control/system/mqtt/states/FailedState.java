package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public class FailedState implements MqttClientState {

    private final MqttConnectionContext context;

    public FailedState(MqttConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {

    }

    @Override
    public void connected(String topic) {

    }

    @Override
    public void reconnecting(String topic) {

    }

    @Override
    public void publishing(String message, Integer qos) {
    }

    @Override
    public void subscribe(String topic, Integer qos) {

    }
}
