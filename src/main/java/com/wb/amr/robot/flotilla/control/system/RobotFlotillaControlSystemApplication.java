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
    }
}