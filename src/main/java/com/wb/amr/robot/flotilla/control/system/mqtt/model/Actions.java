package com.wb.amr.robot.flotilla.control.system.mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Data
@JsonInclude
@EqualsAndHashCode(callSuper = false)
public class Actions extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Actions.class.getName());

    @JsonProperty("actionType")
    private String actionType = "";

    @JsonProperty("actionId")
    private String actionId = "";

    @JsonProperty("actionDescription")
    private String actionDescription = "";

    @JsonProperty("blockingType")
    private String blockingType = ""; // Enum BlockingType

    @JsonProperty("actionParameters")
    private List<ActionParameter> actionParameters;

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
            throw new IllegalArgumentException("Empty Actions");
        }
    }
}
