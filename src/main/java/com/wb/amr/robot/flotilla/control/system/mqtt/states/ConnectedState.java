package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

public class ConnectedState implements ConnectionState {

    private final ConnectionContext context;

    public ConnectedState(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {
        MqttClient client = context.getClient();
        try {
            if (client.isConnected()) {
                client.disconnect();
                client.close();
            } else {
                System.out.println("Connection had disconnected already");
            }
        } catch (MqttException mqttException) {
            System.out.println(mqttException.getMessage());
            context.setState(context.getErrorState());
        }
    }

    @Override
    public void connected() {
        System.out.println("We ready to send messages");
        context.setState(context.getPublishingState());
    }

    @Override
    public void reconnecting(int attempts) {

    }

    @Override
    public void failed() {

    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retain) {
    }
}
