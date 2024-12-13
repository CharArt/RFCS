package com.wb.amr.robot.flotilla.control.system.mqtt;

public interface MQTTConnectionState {

    void disconnected();

    void connected();

    void connecting();

    void reconnecting();

    void errorOccurred();

}