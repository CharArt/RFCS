package com.wb.amr.robot.flotilla.control.system.map.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wb.amr.robot.flotilla.control.system.map.json.enums.OrientationType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

@Data
@JsonInclude
@EqualsAndHashCode(callSuper = false)
public class Edge extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Edge.class.getName());

    @JsonProperty("edgeId")
    private String edgeId = "";

    @JsonProperty("sequenceId")
    private Integer sequenceId = 0;

    @JsonProperty("edgeDescription")
    private String edgeDescription = "";

    @JsonProperty("released")
    private Boolean released = false;

    @JsonProperty("startNodeId")
    private String startNodeId = "";

    @JsonProperty("endNodeId")
    private String endNodeId = "";

    @JsonProperty("maxSpeed")
    private Double maxSpeed = 0.0;

    @JsonProperty("maxHeight")
    private Double maxHeight = 0.0;

    @JsonProperty("minHeight")
    private Double minHeight = 0.0;

    @JsonProperty("orientation")
    private Double orientation = 0.0;

    @JsonProperty("orientationType")
    private OrientationType orientationType;

    @JsonProperty("direction")
    private String direction = "";

    @JsonProperty("rotationAllowed")
    private Boolean rotationAllowed = false;

    @JsonProperty("maxRotationSpeed")
    private Double maxRotationSpeed = 0.0;

    @JsonProperty("trajectory")
    private List<Trajectory> trajectory;

    @JsonProperty("length")
    private Double length = 0.0;

    @JsonProperty("corridor")
    private Corridor corridor;

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
            throw new IllegalArgumentException("Empty ControlPoint");
        }
    }
}
