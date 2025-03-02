package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.common.MqttException;

public class ReconnectingState implements MqttClientState, Runnable {
    private final static Logger LOGGER = LogManager.getLogger(ReconnectingState.class.getName());
    private final MqttConnectionContext context;

    public ReconnectingState(MqttConnectionContext context) {
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
        System.out.println("reconnected state");
        Thread thread = new Thread(this);
        thread.start();
    }

    @Override
    public void publishing(String message, Integer qos) {

    }

    @Override
    public void subscribe(String topic, Integer qos) {

    }

    @Override
    public void run() {
        context.setIsReconnected(true);
        int counter = 0;
        int attempts = context.getReconnectionAttempts();
        MqttClient client = context.getClient();
        while (counter < attempts) {
            try {
                LOGGER.info("Attempt connection {} of {}", counter + 1, client.getClientId());
                if (!client.isConnected()) {
                    client.reconnect();
                } else {
                    break;
                }
                ++counter;
                Thread.sleep(6000L);
            } catch (MqttException | InterruptedException ex) {
                LOGGER.error("{} get exception {} while try reconnected with reason: {}",
                        client.getClientId(), ex, ex.getMessage());
                context.setState(context.getErrorState());
            }
        }
    }
}
