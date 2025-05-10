package com.wb.amr.robot.flotilla.control.system.map.json.vda;

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
public class Trajectory extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Trajectory.class.getName());

    @JsonProperty("degree")
    private Double degree = 0.0;

    @JsonProperty("knowVector")
    private Double knowVector = 0.0;

    @JsonProperty("controlPoints")
    private List<ControlPoint> controlPoints;


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
            throw new IllegalArgumentException("Empty Trajectory");
        }
    }
}
