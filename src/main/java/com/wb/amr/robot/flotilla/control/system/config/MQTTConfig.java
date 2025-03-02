package com.wb.amr.robot.flotilla.control.system.config;

import com.wb.amr.robot.flotilla.control.system.mqtt.states.CallbackHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;
import org.eclipse.paho.mqttv5.client.persist.MemoryPersistence;
import org.eclipse.paho.mqttv5.common.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MQTTConfig {
    private static final Logger LOGGER = LogManager.getLogger(MQTTConfig.class.getName());

    @Bean
    public MqttConnectionOptions getMQTTOptions() {
        MqttConnectionOptions options = new MqttConnectionOptions();
        options.setCleanStart(true);
        options.setKeepAliveInterval(60);
        return options;
    }

    private final String BROKER = "tcp://localhost:1883";
    private final String CLIENT_ID_PUB = "publisher";
    private final String CLIENT_ID_SUB = "subscriber";

    @Bean
    public MqttClient getMQTTClient() {
        try {
            MemoryPersistence persistence = new MemoryPersistence();
            MqttClient client = new MqttClient(BROKER, CLIENT_ID_PUB, persistence);
            return client;
        } catch (MqttException e) {
            LOGGER.error(e);
            throw new RuntimeException("MQTT Client doesn't work");
        }
    }
}