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
public class NodePosition extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(NodePosition.class.getName());

    @JsonProperty("x")
    private Double x = 0.0; // x-coordinate on the map value in meters [m]

    @JsonProperty("y")
    private Double y = 0.0; // y-coordinate on the map value in meters [m]

    @JsonProperty("theta")
    private Double theta = 0.0; // absolute angle in radians [-π, π]

    @JsonProperty("allowedDeviationXY")
    private Double allowedDeviationXY = 0.0; // Allowed deviation from node location, 0.05

    @JsonProperty("allowedDeviationTheta")
    private Double allowedDeviationTheta = 0.0; // Allowed deviation from angle location, 0.07

    @JsonProperty("mapId")
    private String mapId = "";

    @JsonProperty("mapDescription")
    private String mapDescription = "";

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
            throw new IllegalArgumentException("Empty NodePosition");
        }
    }
}
