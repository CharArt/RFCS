package com.wb.amr.robot.flotilla.control.system.mqtt.states;

public interface ConnectionState {

    void disconnected();

    void connected();

    void reconnecting(int attempts);

    void failed();

    void publishing(String topic, byte[] payload, int qos, boolean retain);

}