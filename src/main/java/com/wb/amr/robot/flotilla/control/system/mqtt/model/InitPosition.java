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
public class InitPosition extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(InitPosition.class.getName());

    @JsonProperty("x")
    private Double x = 0.0;

    @JsonProperty("y")
    private Double y = 0.0;

    @JsonProperty("then")
    private Double then = 0.0;

    @JsonProperty("mapId")
    private String mapId = "";

    @JsonProperty("lastNodeId")
    private String lastNodeId = "";

    public void setValue(String key, Object value) {
        super.checkSetValue(this, key, value);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.getMessage());
            throw new IllegalArgumentException("Empty InitPosition");
        }
    }
}