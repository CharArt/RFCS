package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.eclipse.paho.mqttv5.common.MqttException;

public class ReconnectingState implements ConnectionState {

    private final ConnectionContext context;

    public ReconnectingState(ConnectionContext context) {
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
        int counter = 0;
        while (context.getClient().isConnected() || counter < attempts) {
            try {
                context.getClient().reconnect();
                if (context.getClient().isConnected()) {
                    context.setState(context.getConnectedState());
                } else {
                    System.out.println("Failed reconnection attempt, attempt:" + counter);
                    counter++;
                }
            } catch (MqttException mqttException) {
                System.out.println(mqttException.getMessage());
                context.setState(context.getErrorState());
            }
        }
        if(!context.getClient().isConnected()){
            context.setState(context.getFailedState());
            context.getCurrentState().failed();
        }
    }

    @Override
    public void failed() {

    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retaine) {

    }
}
