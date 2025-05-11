package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;

public class ConnectedState implements MqttClientState {
    private static final Logger LOGGER = LogManager.getLogger(ConnectedState.class.getName());
    private final MqttConnectionContext context;

    public ConnectedState(MqttConnectionContext context) {
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
                LOGGER.warn("{} Connection had disconnected already", client.getClientId());
            }
        } catch (MqttException mqttException) {
            context.setState(context.getErrorState());
            LOGGER.error("{} exception: {}", client.getClientId(), mqttException);
        }
    }

    @Override
    public void connected(String topic) {

    }

    @Override
    public void reconnecting(String topic) {

    }


    @Override
    public void publishing(String message, int qos) {
        System.out.println("connected state");
        MqttClient client = context.getClient();
        MqttMessage mqttMessage = new MqttMessage(message.getBytes());
        mqttMessage.setQos(qos);
        try {
            client.publish(context.getTopic(), mqttMessage);
        } catch (MqttException e) {
            LOGGER.error("{} attempt to publish failed, in reason: {} ", client.getClientId(), e.getMessage());
            context.setState(context.getErrorState());
        }
    }

    @Override
    public void subscribe(String topic, int qos) {
        MqttClient client = context.getClient();
        try {
            client.subscribe(topic, qos);
        } catch (MqttException e) {
            LOGGER.error("{} get exception on subscribe {} ", client.getClientId(), e.getMessage());
            context.setState(context.getErrorState());
        }
    }
}
