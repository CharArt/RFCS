package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public interface MqttClientState {

    void disconnected();

    void connected(String topic);

    void reconnecting(String topic);

    void publishing(String message, Integer qos);

    void subscribe(String topic, Integer qos);
}