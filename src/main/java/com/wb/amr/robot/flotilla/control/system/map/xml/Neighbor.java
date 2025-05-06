package com.wb.amr.robot.flotilla.control.system.map.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class Neighbor {

    private static final Logger LOGGER = LogManager.getLogger(Neighbor.class.getName());

    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JacksonXmlProperty(localName = "distance")
    private Double distance;

    @JacksonXmlProperty(localName = "Rever")
    private Integer rever;

    @JacksonXmlProperty(localName = "EmptyCantRun")
    private Integer emptyCantRun;

    @JacksonXmlProperty(localName = "Speed")
    private Double speed;

    @JacksonXmlProperty(localName = "Ultrasonic")
    private String ultrasonic;

    @JacksonXmlProperty(localName = "LeftWidth")
    private Integer leftWidth;

    @JacksonXmlProperty(localName = "RightWidth")
    private Integer rightWidth;

    @JacksonXmlProperty(localName = "RoadLeftWidth")
    private Integer roadLeftWidth;

    @JacksonXmlProperty(localName = "RoadRightWidth")
    private Integer roadRightWidth;

    @JacksonXmlProperty(localName = "robottype")
    private String robottype;

    @JacksonXmlProperty(localName = "PodDir")
    private Integer podDir;

    @JacksonXmlProperty(localName = "StartDir")
    private Integer startDir;

    @JacksonXmlProperty(localName = "AlarmVoice")
    private String alarmVoice;

    @JacksonXmlProperty(localName = "PreForkLift")
    private Integer preForkLift;

    @JacksonXmlProperty(localName = "PreForkLiftLoad")
    private Integer preForkLiftLoad;

    @JacksonXmlProperty(localName = "AutoBackRoad")
    private Integer AutoBackRoad;

    @JacksonXmlProperty(localName = "LaserType")
    private Integer laserType;

    @JacksonXmlProperty(localName = "PodLaserType")
    private Integer podLaserType;

    @JacksonXmlProperty(localName = "SensorSwitch")
    private Integer sensorSwitch;

    @JacksonXmlProperty(localName = "carryType")
    private String carryType;

    @JacksonXmlProperty(localName = "ctnrtype")
    private String ctnrtype;

    @JacksonXmlProperty(localName = "isLine")
    private Integer isLine;

    @JacksonXmlProperty(localName = "RotFreeRun")
    private Integer rotFreeRun;

    @JacksonXmlProperty(localName = "ActLimitHight")
    private String actLimitHight;

    @JacksonXmlProperty(localName = "entryDir")
    private String entryDir;

    @JacksonXmlProperty(localName = "VirtualLine")
    private Integer virtualLine;

    @JacksonXmlProperty(localName = "CtrlPoint")
    private Integer ctrlPoint;

    @Override
    public String toString() {
        try {
            XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            LOGGER.error(e.toString());
            throw new IllegalArgumentException("Empty MapCfg");
        }
    }
}