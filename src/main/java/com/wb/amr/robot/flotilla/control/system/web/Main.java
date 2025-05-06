package com.wb.amr.robot.flotilla.control.system.web;

import com.wb.amr.robot.flotilla.control.system.config.MQTTConfig;
import com.wb.amr.robot.flotilla.control.system.mqtt.states.MqttConnectionContext;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;

public class Main {
    public static void main(String[] args) {
        MQTTConfig config = new MQTTConfig();
        MqttClient client = config.getMQTTClient();
        MqttConnectionOptions options = config.getMQTTOptions();
        MqttConnectionContext context = new MqttConnectionContext(client, options, 3);

        context.connect("topic/test");

        context.publish("test", 1);

        context.disconnect();

    }
}