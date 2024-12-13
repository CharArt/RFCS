package com.wb.amr.robot.flotilla.control.system.mqtt;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MQTTCallbackHandler implements MqttCallback {

    @Autowired
    private final MQTTConnectionState state;

    public MQTTCallbackHandler(MQTTConnectionState state) {
        this.state = state;
    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {

    }

    @Override
    public void mqttErrorOccurred(MqttException e) {

    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
    }

    /*TO_DO*/
    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {
        //TODO
    }

    @Override
    public void connectComplete(boolean reconnect, String s) {

    }

    /*TO_DO*/
    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {
        //TODO
    }


}