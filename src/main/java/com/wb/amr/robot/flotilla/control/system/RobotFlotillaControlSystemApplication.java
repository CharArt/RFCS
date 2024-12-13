package com.wb.amr.robot.flotilla.control.system;

import io.github.cdimascio.dotenv.Dotenv;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class RobotFlotillaControlSystemApplication {

    public static void main(String[] args) throws MqttException, InterruptedException {
        Dotenv dotenv = Dotenv.load();
        SpringApplication.run(RobotFlotillaControlSystemApplication.class, args);
/*
        MQTTConfig config = new MQTTConfig();
        MqttClient client = config.getClient();
        MqttConnectionOptions options = config.getOptions();
        client.setCallback(new MQTTCallbackHandler(client, options));
        client.connect(options);

        String topic = "topic/test";
        int subQos = 1;
        int pubQos = 1;

        client.subscribe(topic, subQos);

        for (int i = 0; i < 10; i++) {
            String msg = "Hello MQTT " + i;
            MqttMessage mqttMessage = new MqttMessage(msg.getBytes());
            System.out.println("connection: " + client.isConnected());
            client.publish(topic, mqttMessage.getPayload(), pubQos, false);
            Thread.sleep(1000);
        }

        client.disconnect();
        client.close();

 */
    }
}