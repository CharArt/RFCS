package com.wb.amr.robot.flotilla.control.system.mqtt.model.enums;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public enum BlockingType {
    NONE,
    SOFT,
    HARD
}