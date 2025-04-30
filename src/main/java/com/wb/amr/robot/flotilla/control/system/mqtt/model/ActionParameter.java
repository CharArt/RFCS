package com.wb.amr.robot.flotilla.control.system.mqtt.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;

@Data
@JsonInclude
@EqualsAndHashCode(callSuper = false)
public class ActionParameter extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(ActionParameter.class.getName());

    @JsonProperty("key")
    private String key = "";

    @JsonProperty("value")
    private Object value;

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
            throw new IllegalArgumentException("Empty ActionParameter");
        }
    }
}
