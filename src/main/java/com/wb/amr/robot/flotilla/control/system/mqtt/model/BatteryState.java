package com.wb.amr.robot.flotilla.control.system.mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@JsonInclude
@EqualsAndHashCode(callSuper = false)
public class BatteryState extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(BatteryState.class.getName());

    @JsonProperty("batteryCharge")
    private Double batteryCharge = 0.0;

    @JsonProperty("batteryVoltage")
    private Double batteryVoltage = 0.0;

    @JsonProperty("charging")
    private Boolean charging = false;

    public void setValue(String key, Object value) {
        super.checkSetValue(this, key, value);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e);
            throw new IllegalArgumentException("Empty BatteryState");
        }
    }
}
