package com.wb.amr.robot.flotilla.control.system.dto.comand;

import com.wb.amr.robot.flotilla.control.system.tools.astar.Node;
import lombok.Getter;

import java.util.Collection;
import java.util.List;
import java.util.NavigableMap;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OrderPoint {
    @Getter
    private Integer x;
    @Getter
    private Integer y;
    @Getter
    private String name;
    @Getter
    private String businessName;

    public OrderPoint(String name) {
        if (Objects.nonNull(name)) {
            Pattern pattern = Pattern.compile("(\\d+)AA(\\d+)");
            Matcher matcher1 = pattern.matcher(name);
            if (matcher1.find()) {
                String xs = matcher1.group(1);
                String ys = matcher1.group(2);
                this.x = Integer.parseInt(xs);
                this.y = Integer.parseInt(ys);
                this.name = name;
            } else {
                this.businessName = name;
            }
        } else {
            throw new IllegalArgumentException("Name couldn't be empty");
        }
    }

    public OrderPoint(Double x, Double y, String index) {
        this.x = (int) Math.round(x);
        this.y = (int) Math.round(y);
        this.name = x.toString() + index + y.toString();
    }

    public Node getNode(NavigableMap<Integer, List<Node>> map) throws IllegalArgumentException {
        if (Objects.nonNull(this.x) && Objects.nonNull(this.y)) {
            return map.get(x).stream()
                    .filter(node -> this.y.equals((int) Math.round(node.getY())))
                    .findFirst().orElseThrow();
        } else if (Objects.isNull(this.x) && Objects.isNull(this.y) && Objects.nonNull(this.businessName)) {
            return map.values().stream().flatMap(Collection::stream)
                    .filter(node -> businessName.equals(node.getBusinessName()))
                    .findFirst().orElseThrow();
        } else {
            throw new IllegalArgumentException("Node doesn't exist");
        }
    }

    @Override
    public String toString() {
        return "OrderPoint{" +
                "x=" + x +
                ", y=" + y +
                ", name='" + name + '\'' +
                ", businessName='" + businessName + '\'' +
                '}';
    }
}