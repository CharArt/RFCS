package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;

public class DisconnectedState implements ConnectionState {

    private final ConnectionContext context;

    public DisconnectedState(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {
    }

    @Override
    public void connected() {
        MqttClient client = context.getClient();
        MqttConnectionOptions options = context.getOptions();
        try {
            if (!client.isConnected()) {
                client.connect(options);
                if (client.isConnected()) {
                    client.setCallback(new CallbackHandler(this.context));
                    context.setState(context.getConnectedState());
                    context.getCurrentState().connected();
                } else {
                    context.setState(context.getReconnectingState());
                    context.getCurrentState().reconnecting(3);
                }
            } else {
                System.out.println("Connection had connected already");
            }
        } catch (MqttException mqttException) {
            System.out.println(mqttException.getMessage());
            context.setState(context.getErrorState());
            context.getCurrentState().reconnecting(3);
        }
    }

    @Override
    public void reconnecting(int attempts) {
    }

    @Override
    public void failed() {

    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retaine) {

    }
}
