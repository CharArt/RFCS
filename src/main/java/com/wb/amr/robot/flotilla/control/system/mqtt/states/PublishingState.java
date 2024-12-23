package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

public class PublishingState implements ConnectionState {

    private final ConnectionContext context;

    public PublishingState(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {
        MqttClient client = context.getClient();
        if (client.isConnected()) {
            try {
                client.disconnect();
                client.close();
                context.setState(context.getDisconnectedState());
                System.out.println("Connection close");
            } catch (MqttException mqttException) {
                System.out.println(mqttException.getMessage());
                context.setState(context.getErrorState());
            }
        } else {
            System.out.println("Connection have disconnected already");
        }
    }

    @Override
    public void connected() {

    }

    @Override
    public void reconnecting(int attempts) {

    }

    @Override
    public void failed() {

    }

    @Override
    public void publishing(String topic, byte[] payload, int qos, boolean retain) {
        MqttClient client = context.getClient();
        try {
            client.publish(topic, payload, qos, retain);
        } catch (MqttException mqttException) {
            System.out.println(mqttException.getMessage());
            context.setState(context.getErrorState());
        }

    }
}
