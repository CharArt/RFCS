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
public class EdgeStates extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(EdgeStates.class.getName());

    @JsonProperty("edgeId")
    private String edgeId = "";

    @JsonProperty("sequenceId")
    private Integer sequenceId = 0;

    @JsonProperty("edgeDescription")
    private String edgeDescription = "";

    @JsonProperty("trajectory")
    private Trajectory trajectory;


    public void setValue(String key, Object value) {
        super.checkSetValue(this, key, value);
    }

    @Override
    public String toString() {
        ObjectMapper mapper = new ObjectMapper();
        try {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
        } catch (JsonProcessingException e) {
            LOGGER.error(e.toString());
            throw new IllegalArgumentException("Empty EdgeStates");
        }
    }
}
