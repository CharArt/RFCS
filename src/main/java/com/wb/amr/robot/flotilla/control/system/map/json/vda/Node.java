package com.wb.amr.robot.flotilla.control.system.map.json;

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
public class Node extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(ControlPoint.class.getName());

    @JsonProperty("nodeId")
    private String nodeId = "";

    @JsonProperty("sequenceId")
    private Integer sequenceId = 0;

    @JsonProperty("nodeDescription")
    private String nodeDescription = "";

    @JsonProperty("released")
    private Boolean released = false;

    @JsonProperty("nodePosition")
    private NodePosition nodePosition;

    @JsonProperty("actions")
    private List<Actions> actions;

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
            throw new IllegalArgumentException("Empty Node");
        }
    }
}
