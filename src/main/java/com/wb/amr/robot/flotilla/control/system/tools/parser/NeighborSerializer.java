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
import java.util.Objects;

public class NeighborSerializer extends StdSerializer<List<Neighbor>> {
    private static final Logger LOGGER = LogManager.getLogger(NeighborSerializer.class.getName());

    public NeighborSerializer() {
        super((Class<List<Neighbor>>) (Class<?>) List.class);
    }

    @Override
    public void serialize(List<Neighbor> neighborList, JsonGenerator jsonGenerator, SerializerProvider serializerProvider) {
        try {
            ToXmlGenerator xmlGenerator = (ToXmlGenerator) jsonGenerator;
            xmlGenerator.writeRaw("\n");
            for (Neighbor neighbor : neighborList) {
                writeTag(xmlGenerator, "id", neighbor.getId());
                writeTag(xmlGenerator, "distance", neighbor.getDistance());
                writeTag(xmlGenerator, "Rever", neighbor.getDistance());
                writeTag(xmlGenerator, "EmptyCantRun", neighbor.getEmptyCantRun());
                writeTag(xmlGenerator, "Speed", neighbor.getSpeed());
                writeTag(xmlGenerator, "Ultrasonic", neighbor.getUltrasonic());
                writeTag(xmlGenerator, "LeftWidth", neighbor.getLeftWidth());
                writeTag(xmlGenerator, "RightWidth", neighbor.getRightWidth());
                writeTag(xmlGenerator, "RoadLeftWidth", neighbor.getRoadLeftWidth());
                writeTag(xmlGenerator, "RoadRightWidth", neighbor.getRoadRightWidth());
                writeTag(xmlGenerator, "robottype", neighbor.getRobottype());
                writeTag(xmlGenerator, "PodDir", neighbor.getPodDir());
                writeTag(xmlGenerator, "AlarmVoice", neighbor.getAlarmVoice());
                writeTag(xmlGenerator, "PreForkLift", neighbor.getPreForkLift());
                writeTag(xmlGenerator, "PreForkLiftLoad", neighbor.getPreForkLiftLoad());
                writeTag(xmlGenerator, "AutoBackRoad", neighbor.getAutoBackRoad());
                writeTag(xmlGenerator, "LaserType", neighbor.getLaserType());
                writeTag(xmlGenerator, "PodLaserType", neighbor.getPodLaserType());
                writeTag(xmlGenerator, "SensorSwitch", neighbor.getSensorSwitch());
                writeTag(xmlGenerator, "carryType", neighbor.getCarryType());
                writeTag(xmlGenerator, "ctnrtype", neighbor.getCtnrtype());
                writeTag(xmlGenerator, "isLine", neighbor.getIsLine());
                writeTag(xmlGenerator, "RotFreeRun", neighbor.getRotFreeRun());
                writeTag(xmlGenerator, "ActLimitHight", neighbor.getActLimitHight());
                writeTag(xmlGenerator, "EntryDir", neighbor.getEntryDir());
                writeTag(xmlGenerator, "VirtualLine", neighbor.getVirtualLine());
                writeTag(xmlGenerator, "CtrlPoint", neighbor.getCtrlPoint());
            }
        } catch (IOException exception) {
            LOGGER.error(exception.getMessage());
        }
    }

    private void writeTag(ToXmlGenerator xmlGenerator, String tagName, Object value) {
        try {
            if (value != null) {
                xmlGenerator.writeRaw("\t<" + tagName + ">");
                xmlGenerator.writeRaw(Objects.toString(value));
                xmlGenerator.writeRaw("</" + tagName + ">\n");
            } else {
                xmlGenerator.writeRaw("\t</" + tagName + ">\n");
            }
        } catch (IOException exception) {
            LOGGER.error("Couldn't write XML tag <{}>", tagName, exception);
        }
    }
}
