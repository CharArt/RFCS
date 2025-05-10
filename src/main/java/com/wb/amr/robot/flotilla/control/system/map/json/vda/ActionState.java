package com.wb.amr.robot.flotilla.control.system.map.json.vda;

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
public class ActionState extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(ActionState.class.getName());

    @JsonProperty("actionId")
    private String actionId = "";

    @JsonProperty("actionType")
    private String actionType = "";

    @JsonProperty("actionStatus")
    private String actionStatus = "";

    @JsonProperty("resultDescription")
    private String resultDescription = "";

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
            throw new IllegalArgumentException("Empty ActionState");
        }
    }
}
