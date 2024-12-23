package com.wb.amr.robot.flotilla.control.system.mqtt.states;

import org.eclipse.paho.mqttv5.client.*;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.eclipse.paho.mqttv5.common.MqttMessage;
import org.eclipse.paho.mqttv5.common.packet.MqttProperties;
import org.springframework.stereotype.Component;

@Component
public class CallbackHandler implements MqttCallback {

    private final ConnectionContext context;

    public CallbackHandler(ConnectionContext context) {
        this.context = context;
    }

    @Override
    public void disconnected(MqttDisconnectResponse mqttDisconnectResponse) {
        context.setState(context.getDisconnectedState());
        context.getCurrentState().reconnecting(3);
        System.out.println(mqttDisconnectResponse.getReasonString());
        System.out.println(mqttDisconnectResponse.getException().getMessage());
        //Here we have to write reason, number of exception time stamp in log (4jlog)
    }

    @Override
    public void mqttErrorOccurred(MqttException e) {
        context.setState(context.getErrorState());
        context.getCurrentState().reconnecting(3);
    }

    @Override
    public void messageArrived(String topic, MqttMessage mqttMessage) throws Exception {
        System.out.println("Message in topic:" + topic + "[" + mqttMessage + "]" + "-arrived");
        //Here we have to write reason, number of exception time stamp in log (4jlog)
    }

    /*TO_DO*/
    @Override
    public void deliveryComplete(IMqttToken iMqttToken) {
    }

    @Override
    public void connectComplete(boolean reconnect, String s) {
        context.setState(context.getConnectedState());
        if(reconnect){
            System.out.println("We reconnected");
            //Here we have to write reason, number of exception time stamp in log (4jlog)
        }
    }

    /*TO_DO*/
    @Override
    public void authPacketArrived(int i, MqttProperties mqttProperties) {

    }

}