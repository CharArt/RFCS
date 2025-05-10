package com.wb.amr.robot.flotilla.control.system.map.json.vda.enums;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public enum ActionStatus {
    WAITING,
    INITIALIZING,
    RUNNING,
    PAUSED,
    FINISHED,
    FAILED
}
