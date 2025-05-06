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
public class Order extends BasicEntity {
    private static final Logger LOGGER = LogManager.getLogger(Order.class.getName());

    @JsonProperty("headerId")
    private Long headerId = 0L;

    @JsonProperty("orderId")
    private String orderId = "";

    @JsonProperty("timestamp")
    private String timestamp = ""; // ISO 8601, UTC), YYYY-MM-DDThh:mm:ssZ, "2025-01-14T08:33:15Z".

    @JsonProperty("version")
    private String version = ""; // Protocol version [Major].[Minor].[Patch] (2.0.0)

    @JsonProperty("manufacturer")
    private String manufacturer = ""; // Manufacture (kuka) or (hik)

    @JsonProperty("serialNumber")
    private String serialNumber = ""; // Serial number of AGV (7567）

    @JsonProperty("zoneSetId")
    private String zoneSetId = "";

    @JsonProperty("orderUpdateId")
    private Integer orderUpdateId = 0;

    @JsonProperty("nodes")
    private List<Node> nodes;

    @JsonProperty("edges")
    private List<Edge> edges;

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
            throw new IllegalArgumentException("Empty Order");
        }
    }
}