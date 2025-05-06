package com.wb.amr.robot.flotilla.control.system.tools.parser;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.wb.amr.robot.flotilla.control.system.map.xml.Neighbor;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.List;

public class NeighborSerializer extends StdSerializer<List<Neighbor>> {
    private static final Logger LOGGER = LogManager.getLogger(NeighborSerializer.class.getName());

    public NeighborSerializer() {
        super((Class<List<Neighbor>>) (Class<?>) List.class);
    }

    @Override
    public void serialize(List<Neighbor> neighborList,
                          JsonGenerator jsonGenerator,
                          SerializerProvider serializerProvider) {
        try {
            ToXmlGenerator xmlGenerator = (ToXmlGenerator) jsonGenerator;
//            xmlGenerator.writeStartObject();
//            xmlGenerator.writeRaw("<NeighbInfo>");
            for (Neighbor neighbor : neighborList) {
                xmlGenerator.writeRaw("\n<id>");
                xmlGenerator.writeRaw(neighbor.getId().toString());
                xmlGenerator.writeRaw("</id>\n");
                xmlGenerator.writeRaw("<distance>");
                xmlGenerator.writeRaw(String.valueOf(neighbor.getDistance()));
                xmlGenerator.writeRaw("</distance>\n");
//                xmlGenerator.writeNumberField("id", neighbor.getId());
//                xmlGenerator.writeNumberField("distance", neighbor.getDistance());
//                xmlGenerator.writeNumberField("Rever", neighbor.getRever());
//                xmlGenerator.writeNumberField("EmptyCantRun", neighbor.getEmptyCantRun());
//                xmlGenerator.writeNumberField("Speed", neighbor.getSpeed());
//                xmlGenerator.writeStringField("Ultrasonic", neighbor.getUltrasonic());
//                xmlGenerator.writeNumberField("LeftWidth", neighbor.getLeftWidth());
//                xmlGenerator.writeNumberField("RightWidth", neighbor.getRightWidth());
//                xmlGenerator.writeNumberField("RoadLeftWidth", neighbor.getRoadLeftWidth());
//                xmlGenerator.writeNumberField("RoadRightWidth", neighbor.getRoadRightWidth());
//                xmlGenerator.writeStringField("robottype", neighbor.getRobottype());
//                xmlGenerator.writeNumberField("PodDir", neighbor.getPodDir());
//                xmlGenerator.writeStringField("AlarmVoice", neighbor.getAlarmVoice());
//                xmlGenerator.writeNumberField("PreForkLift", neighbor.getPreForkLift());
//                xmlGenerator.writeNumberField("PreForkLiftLoad", neighbor.getPreForkLiftLoad());
//                xmlGenerator.writeNumberField("AutoBackRoad", neighbor.getAutoBackRoad());
//                xmlGenerator.writeNumberField("LaserType", neighbor.getLaserType());
//                xmlGenerator.writeNumberField("PodLaserType", neighbor.getPodLaserType());
//                xmlGenerator.writeNumberField("SensorSwitch", neighbor.getSensorSwitch());
//                xmlGenerator.writeStringField("carryType", neighbor.getCarryType());
//                xmlGenerator.writeStringField("ctnrtype", neighbor.getCtnrtype());
//                xmlGenerator.writeNumberField("isLine", neighbor.getIsLine());
//                xmlGenerator.writeNumberField("RotFreeRun", neighbor.getRotFreeRun());
//                xmlGenerator.writeStringField("ActLimitHight", neighbor.getActLimitHight());
//                xmlGenerator.writeStringField("EntryDir", neighbor.getEntryDir());
//                xmlGenerator.writeNumberField("VirtualLine", neighbor.getVirtualLine());
//                xmlGenerator.writeNumberField("CtrlPoint", neighbor.getCtrlPoint());
            }
//            xmlGenerator.writeRaw("<NeighbInfo>");
//            xmlGenerator.writeEndObject();
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
            System.out.println(exception.getMessage());
            exception.printStackTrace();
        }
    }
}
