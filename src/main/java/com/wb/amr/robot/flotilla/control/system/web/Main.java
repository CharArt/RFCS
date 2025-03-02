package com.wb.amr.robot.flotilla.control.system.web;

import com.wb.amr.robot.flotilla.control.system.config.MQTTConfig;
import com.wb.amr.robot.flotilla.control.system.mqtt.states.MqttConnectionContext;
import org.eclipse.paho.mqttv5.client.MqttClient;
import org.eclipse.paho.mqttv5.client.MqttConnectionOptions;

public class Main {
    public static void main(String[] args) {
        MQTTConfig config = new MQTTConfig();
        MqttClient client = config.getMQTTClient();
        MqttConnectionOptions options = config.getMQTTOptions();
        MqttConnectionContext context = new MqttConnectionContext(client, options, 3);

        context.connect("topic/test");

        context.publish("test", 1);

        context.disconnect();


//        Parser parser = new Parser();
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            InputStream rawOrder = new ClassPathResource("json/OrderExample.json").getInputStream();
//            InputStream rawStatus = new ClassPathResource("json/StatusExample.json").getInputStream();
//            Order order = parser.getOrder(rawOrder);
//            System.out.println(order.toString());
//            Order order1 = mapper.readValue(rawOrder, Order.class);
//            System.out.println(order1.toString());
//            Status status = parser.getStatus(rawStatus);
//            System.out.println(status1.toString());
//            Status status1 = mapper.readValue(rawStatus, Status.class);
//            System.out.println(status1.toString());
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
    }
}