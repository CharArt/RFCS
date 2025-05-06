package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.wb.amr.robot.flotilla.control.system.map.xml.Neighbor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class NeighborDeserializer extends StdDeserializer<List<Neighbor>> {

    private static final Logger LOGGER = LogManager.getLogger(NeighborDeserializer.class.getName());

    public NeighborDeserializer() {
        super((Class<List<Neighbor>>) (Class<?>) List.class);
    }

    @JsonDeserialize(using = NeighborDeserializer.class)
    @Override
    public List<Neighbor> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            XmlMapper xmlMapper = (XmlMapper) jsonParser.getCodec();
            JsonNode xmlRoot = xmlMapper.readTree(jsonParser);

            List<JsonNode> ids = toList(xmlRoot.get("id"));
            List<JsonNode> distances = toList(xmlRoot.get("distance"));
            List<JsonNode> revers = toList(xmlRoot.get("Rever"));
            List<JsonNode> empties = toList(xmlRoot.get("EmptyCantRun"));
            List<JsonNode> speeds = toList(xmlRoot.get("Speed"));
            List<JsonNode> ultrasonics = toList(xmlRoot.get("Ultrasonic"));
            List<JsonNode> leftWidths = toList(xmlRoot.get("LeftWidth"));
            List<JsonNode> rightWidths = toList(xmlRoot.get("RightWidth"));
            List<JsonNode> roadLeftWidths = toList(xmlRoot.get("RoadLeftWidth"));
            List<JsonNode> roadRightWidths = toList(xmlRoot.get("RoadRightWidth"));
            List<JsonNode> robottypes = toList(xmlRoot.get("robottype"));
            List<JsonNode> podDirs = toList(xmlRoot.get("PodDir"));
            List<JsonNode> startDirs = toList(xmlRoot.get("StartDir"));
            List<JsonNode> alarmVoices = toList(xmlRoot.get("AlarmVoice"));
            List<JsonNode> preForkLifts = toList(xmlRoot.get("PreForkLift"));
            List<JsonNode> preForkLiftLoads = toList(xmlRoot.get("PreForkLiftLoad"));
            List<JsonNode> autoBackRoads = toList(xmlRoot.get("AutoBackRoad"));
            List<JsonNode> laserTypes = toList(xmlRoot.get("LaserType"));
            List<JsonNode> podLaserTypes = toList(xmlRoot.get("PodLaserType"));
            List<JsonNode> sensorSwitchs = toList(xmlRoot.get("SensorSwitch"));
            List<JsonNode> carryTypes = toList(xmlRoot.get("carryType"));
            List<JsonNode> isLines = toList(xmlRoot.get("isLine"));
            List<JsonNode> rotFreeRuns = toList(xmlRoot.get("RotFreeRun"));
            List<JsonNode> actLimitHights = toList(xmlRoot.get("ActLimitHight"));
            List<JsonNode> entryDirs = toList(xmlRoot.get("EntryDir"));
            List<JsonNode> virtualLines = toList(xmlRoot.get("VirtualLine"));
            List<JsonNode> ctrlPoints = toList(xmlRoot.get("CtrlPoint"));

            List<Neighbor> list = new ArrayList<>(ids.size());
            for (int i = 0; i < ids.size(); i++) {
                Neighbor neighbor = new Neighbor();
                neighbor.setId(ids.get(i).asInt());
                neighbor.setDistance(distances.get(i).asDouble());
                neighbor.setRever(revers.get(i).asInt());
                neighbor.setEmptyCantRun(empties.get(i).asInt());
                neighbor.setSpeed(speeds.get(i).asDouble());
                neighbor.setUltrasonic(ultrasonics.get(i).asText());
                neighbor.setLeftWidth(leftWidths.get(i).asInt());
                neighbor.setRightWidth(rightWidths.get(i).asInt());
                neighbor.setRoadLeftWidth(roadLeftWidths.get(i).asInt());
                neighbor.setRoadRightWidth(roadRightWidths.get(i).asInt());
                neighbor.setRobottype(robottypes.get(i).asText());
                neighbor.setPodDir(podDirs.get(i).asInt());
                neighbor.setStartDir(startDirs.get(i).asInt());
                neighbor.setAlarmVoice(alarmVoices.get(i).asText());
                neighbor.setPreForkLift(preForkLifts.get(i).asInt());
                neighbor.setPreForkLiftLoad(preForkLiftLoads.get(i).asInt());
                neighbor.setAutoBackRoad(autoBackRoads.get(i).asInt());
                neighbor.setLaserType(laserTypes.get(i).asInt());
                neighbor.setPodLaserType(podLaserTypes.get(i).asInt());
                neighbor.setSensorSwitch(sensorSwitchs.get(i).asInt());
                neighbor.setCarryType(carryTypes.get(i).asText());
                neighbor.setIsLine(isLines.get(i).asInt());
                neighbor.setRotFreeRun(rotFreeRuns.get(i).asInt());
                neighbor.setActLimitHight(actLimitHights.get(i).asText());
                neighbor.setEntryDir(entryDirs.get(i).asText());
                neighbor.setVirtualLine(virtualLines.get(i).asInt());
                neighbor.setCtrlPoint(ctrlPoints.get(i).asInt());
                list.add(neighbor);
            }
            return list;
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
            System.out.println(exception.getMessage());
        }
        return List.of();
    }

    private List<JsonNode> toList(JsonNode node) {
        if (node == null) {
            return Collections.emptyList();
        } else if (node.isArray()) {
            ArrayNode arr = (ArrayNode) node;
            List<JsonNode> result = new ArrayList<>(arr.size());
            arr.forEach(result::add);
            return result;
        } else {
            return Collections.singletonList(node);
        }
    }
}
