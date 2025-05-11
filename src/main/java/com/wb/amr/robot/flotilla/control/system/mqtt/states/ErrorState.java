package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public class ErrorState implements MqttClientState {

    private final MqttConnectionContext context;

    public ErrorState(MqttConnectionContext context) {
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
    public void publishing(String message, int qos) {

    }

    @Override
    public void subscribe(String topic, int qos) {

    }
}
