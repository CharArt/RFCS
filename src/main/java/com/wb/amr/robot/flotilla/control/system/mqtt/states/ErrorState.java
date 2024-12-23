package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public class ErrorState implements ConnectionState {

    private final ConnectionContext context;

    public ErrorState(ConnectionContext context) {
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
        context.setState(context.getReconnectingState());
        context.getCurrentState().reconnecting(attempts);
    }

    @Override
    public void failed() {

    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retaine) {

    }
}
