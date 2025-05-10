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
public class Error extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Error.class.getName());

    @JsonProperty("errorType")
    private String errorType = "";

    @JsonProperty("errorReferences")
    private List<ErrorReferences> errorReferences;

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
            LOGGER.error("Empty error in toString(), error");
            throw new IllegalArgumentException("Empty error");
        }
    }
}
