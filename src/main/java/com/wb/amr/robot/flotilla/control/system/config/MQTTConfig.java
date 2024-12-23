package com.wb.amr.robot.flotilla.control.system.config;

import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfig {

    @Bean
    public MqttConnectionOptions getOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(true);
        options.setKeepAliveInterval(60);
        options.setConnectionTimeout(30);
        options.setAutomaticReconnect(true);
        return options;
    }

    @Bean
    public MqttClient mqttClient() throws MqttException {
        return new MqttClient("tcp://localhost:1883", "publisher");
    }
}