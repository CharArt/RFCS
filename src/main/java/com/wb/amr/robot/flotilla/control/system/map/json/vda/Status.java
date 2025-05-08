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
public class Status extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Status.class.getName());

    @JsonProperty("headerId")
    private Integer headerId = 0;

    @JsonProperty("orderId")
    private String orderId = "";

    @JsonProperty("timestamp")
    private String timestamp = ""; // (ISO 8601, UTC), YYYY-MM-DDThh:mm:ssZ, "2025-01-14T08:33:15Z"

    @JsonProperty("version")
    private String version = ""; // Protocol version [Major].[Minor].[Patch] (2.0.0)

    @JsonProperty("manufacturer")
    private String manufacturer = ""; // Manufacture (kuka) or (hik)

    @JsonProperty("serialNumber")
    private String serialNumber = ""; // Serial number of AGV (7567)

    @JsonProperty("zoneSetId")
    private String zoneSetId = "";

    @JsonProperty("orderUpdateId")
    private Integer orderUpdateId = 0;

    @JsonProperty("lastNodeId")
    private String lastNodeId = "";

    @JsonProperty("lastNodeSequenceId")
    private Integer lastNodeSequenceId = 0;

    @JsonProperty("nodeStates")
    private List<NodeState> nodeStates;

    @JsonProperty("edgeStates")
    private List<EdgeStates> edgeStates;

    @JsonProperty("agvPosition")
    private AGVPosition agvPosition;

    @JsonProperty("velocity")
    private Velocity velocity;

    @JsonProperty("loads")
    private List<Load> loads;

    @JsonProperty("driving")
    private Boolean driving;

    @JsonProperty("paused")
    private Boolean paused;

    @JsonProperty("actionStates")
    private List<ActionState> actionStates;

    @JsonProperty("batteryState")
    private BatteryState batteryState;

    @JsonProperty("operatingMode")
    private String operatingMode; // Enum OperatingMode

    @JsonProperty("errors")
    private List<Error> errors;

    @JsonProperty("information")
    private List<Information> information;

    @JsonProperty("safetyState")
    private SafetyState safetyState;

    @JsonProperty("newBaseRequest")
    private Boolean newBaseRequest = false;

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
            throw new IllegalArgumentException("Empty Status");
        }
    }
}