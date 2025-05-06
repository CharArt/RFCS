package com.wb.amr.robot.flotilla.control.system.map.xml;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.ser.ToXmlGenerator;
import com.wb.amr.robot.flotilla.control.system.tools.parser.NeighborDeserializer;
import com.wb.amr.robot.flotilla.control.system.tools.parser.NeighborSerializer;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

    @JsonPropertyOrder({
            "id","xpos","ypos","width","height","value","RoadProperty","allDir",
            "EleDir","agvInDir","ElePre","RotRad","robottype","RotUnderPod",
            "rotMech","evit","rotByRobotType","rotForPodType","rotForCtnrType",
            "sensorswitchPoint","RotarySwitch","PalletRecognition","RotBarrierArea",
            "IsUpdateMap","TransMachineDir","CtuStance","TurnableCtrl","VirtualPoint",
            "TranZoneType","ForkWaitPos","CheckMapCode","StorageType", "NeighbInfo"
    })
@Data
@EqualsAndHashCode(callSuper = false)
@JsonIgnoreProperties(ignoreUnknown = true)
public class PointInfo {

    private static final Logger LOGGER = LogManager.getLogger(PointInfo.class.getName());

    @JacksonXmlProperty(localName = "id")
    private Integer id;

    @JacksonXmlProperty(localName = "xpos")
    private Double xpos;

    @JacksonXmlProperty(localName = "ypos")
    private Double ypos;

    @JacksonXmlProperty(localName = "width")
    private Double width;

    @JacksonXmlProperty(localName = "height")
    private Double height;

    @JacksonXmlProperty(localName = "value")
    private Value value;

    @JacksonXmlProperty(localName = "RoadProperty")
    private Integer roadProperty;

    @JacksonXmlProperty(localName = "allDir")
    private Integer allDir;

    @JacksonXmlProperty(localName = "EleDir")
    private Integer eleDir;

    @JacksonXmlProperty(localName = "agvInDir")
    private Integer agvInDir;

    @JacksonXmlProperty(localName = "ElePre")
    private Integer elePre;

    @JacksonXmlProperty(localName = "RotRad")
    private Integer rotRad;

    @JacksonXmlProperty(localName = "robottype")
    private String robottype;

    @JacksonXmlProperty(localName = "RotUnderPod")
    private Integer rotUnderPod;

    @JacksonXmlProperty(localName = "rotMech")
    private Integer rotMech;

    @JacksonXmlProperty(localName = "evit")
    private Integer evit;

    @JacksonXmlProperty(localName = "rotByRobotType")
    private String rotByRobotType;

    @JacksonXmlProperty(localName = "rotForPodType")
    private String rotForPodType;

    @JacksonXmlProperty(localName = "rotForCtnrType")
    private String rotForCtnrType;

    @JacksonXmlProperty(localName = "sensorswitchPoint")
    private Integer sensorswitchPoint;

    @JacksonXmlProperty(localName = "RotarySwitch")
    private Integer RotarySwitch;

    @JacksonXmlProperty(localName = "PalletRecognition")
    private Integer palletRecognition;

    @JacksonXmlProperty(localName = "RotBarrierArea")
    private Integer rotBarrierArea;

    @JacksonXmlProperty(localName = "IsUpdateMap")
    private Integer isUpdateMap;

    @JacksonXmlProperty(localName = "TransMachineDir")
    private Integer transMachineDir;

    @JacksonXmlProperty(localName = "CtuStance")
    private Integer ctuStance;

    @JacksonXmlProperty(localName = "TurnableCtrl")
    private Integer turnableCtrl;

    @JacksonXmlProperty(localName = "VirtualPoint")
    private Integer virtualPoint;

    @JacksonXmlProperty(localName = "TranZoneType")
    private Integer TranZoneType;

    @JacksonXmlProperty(localName = "ForkWaitPos")
    private Integer ForkWaitPos;

    @JacksonXmlProperty(localName = "CheckMapCode")
    private Integer checkMapCode;


    @JacksonXmlProperty(localName = "StorageType")
    private Integer storageType;


    @JsonDeserialize(using = NeighborDeserializer.class)
    @JsonSerialize(using = NeighborSerializer.class)
    @JacksonXmlElementWrapper(localName = "NeighbInfo")
    private List<Neighbor> neighbInfo;

    @Override
    public String toString() {
        try {
            XmlMapper mapper = new XmlMapper();
            mapper.enable(SerializationFeature.INDENT_OUTPUT);
            mapper.configure(ToXmlGenerator.Feature.WRITE_XML_DECLARATION, true);
            return mapper.writeValueAsString(this);
        } catch (Exception e) {
            LOGGER.error(e.getMessage());
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return "";
    }
}