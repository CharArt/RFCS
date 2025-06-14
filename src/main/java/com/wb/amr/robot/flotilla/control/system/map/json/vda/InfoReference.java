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
public class InfoReference extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(ErrorReferences.class.getName());

    @JsonProperty("referenceKey")
    private String referenceKey = "";

    @JsonProperty("referenceValue")
    private Object referenceValue;

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
            throw new IllegalArgumentException("Empty InfoReference");
        }
    }

}
