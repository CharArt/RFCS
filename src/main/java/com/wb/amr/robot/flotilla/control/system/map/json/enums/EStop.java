package com.wb.amr.robot.flotilla.control.system.map.json.enums;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude
public enum EStop {
    AUTOACK,
    MANUAL,
    REMOTE,
    NONE
}
