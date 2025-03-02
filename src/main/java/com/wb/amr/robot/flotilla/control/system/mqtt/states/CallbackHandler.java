package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;

import java.util.concurrent.CountDownLatch;

@Component
public class CallbackHandler implements MqttCallback {
    private static final Logger LOGGER = LogManager.getLogger(CallbackHandler.class.getName());
    private final MqttConnectionContext context;
    private final CountDownLatch latch;

    public CallbackHandler(MqttConnectionContext context, CountDownLatch latch) {
        this.latch = latch;
        this.context = context;
    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
        MqttClient client = context.getClient();
        if (mqttDisconnectResponse.getReturnCode() == 0) {
            LOGGER.error("{} disconnected from {} successfully", client.getClientId(), client.getServerURI());
        } else {
            LOGGER.error("{} disconnected from {} unsuccessfully with cause {}",
                    client.getClientId(), client.getServerURI(), mqttDisconnectResponse.getReasonString());
        }
    }

    @Override
    public void mqttErrorOccurred(MqttException e) {
        MqttClient client = context.getClient();
        if (!client.isConnected() && context.isReconnected()) {
            context.setState(context.getFailedState());
            LOGGER.error("{} can't connect to {}, {}", client.getClientId(), client.getServerURI(), e);
        } else if (!client.isConnected() && !context.isReconnected()) {
            context.setState(context.getReconnectingState());
            LOGGER.error("{} try reconnect to {}", client.getClientId(), client.getServerURI());
            context.reconnect(context.getTopic());
        } else {
            context.setState(context.getErrorState());
            LOGGER.error("{} get error {}", client.getClientId(), e);
        }

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {

    }

    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {
        LOGGER.info("{} message arrived", context.getClient().getClientId());
    }

    @Override
    public void connectComplete(boolean reconnect, String serverURI) {
        MqttClient client = context.getClient();
        if (client.isConnected() && !context.isReconnected()) {
            context.setState(context.getConnectedState());
            latch.countDown();
            LOGGER.info("{} connected to {} successfully", client.getClientId(), serverURI);
        } else if (client.isConnected() && context.isReconnected()) {
            context.setState(context.getConnectedState());
            LOGGER.info("{} reconnected to {} successfully", client.getClientId(), serverURI);
        } else {
            context.setState(context.getErrorState());
        }
    }

    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {

    }

}