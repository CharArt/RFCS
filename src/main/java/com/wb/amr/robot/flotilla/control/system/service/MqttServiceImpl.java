package com.wb.amr.robot.flotilla.control.system.service;

import com.wb.amr.robot.flotilla.control.system.mqtt.states.ConnectionContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

public class MqttServiceImpl {

    @Autowired
    private ConnectionContext context;

    public void push() {
        context.connect();
    }

}
