package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public class FailedState implements ConnectionState {

    private final ConnectionContext context;

    public FailedState(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {

    }

    @Override
    public void connected() {

    }

    @Override
    public void reconnecting(int attempts) {

    }

    @Override
    public void failed() {
        System.out.println("We could connect to broker to URL:" + context.getClient().getServerURI());
    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retaine) {
    }
}
