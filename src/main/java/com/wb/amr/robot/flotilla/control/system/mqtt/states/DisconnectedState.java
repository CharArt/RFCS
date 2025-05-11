package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class DisconnectedState implements MqttClientState {
    private static final Logger LOGGER = LogManager.getLogger(DisconnectedState.class.getName());
    private final MqttConnectionContext context;

    public DisconnectedState(MqttConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected() {
    }

    @Override
    public void connected(String topic) {
        context.setTopic(topic);
        MqttClient client = context.getClient();
        MqttConnectionOptions options = context.getOptions();
        CountDownLatch latch = new CountDownLatch(1);
        client.setCallback(new CallbackHandler(context, latch));

        if (topic != null && !topic.isEmpty()) {
            try {
                LOGGER.info("{} try to connect", client.getClientId());
                client.connect(options);
                if(!latch.await(1, TimeUnit.MILLISECONDS)){
                    throw new MqttException(new Throwable("Connection timeout"));
                }
            } catch (MqttException | InterruptedException e) {
                LOGGER.error(e);
                context.setState(context.getReconnectingState());
                context.reconnect(context.getTopic());
            }
        } else {
            LOGGER.error("Topic can't be empty or null");
            throw new IllegalArgumentException("Topic can't be empty or null");
        }
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
