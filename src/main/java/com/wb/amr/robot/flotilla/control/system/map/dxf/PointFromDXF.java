package com.wb.amr.robot.flotilla.control.system.map.dxf;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.text.DecimalFormat;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Data
@EqualsAndHashCode(callSuper = false)
public class PointFromDXF {

    private Long id;
    private String name;
    private Double x;
    private Double y;
    private Double z;
    private String type;
    private String businessName;
    private Set<NeighborPoint> neighbors;

    public PointFromDXF(Double x, Double y, Double z, String indexMap) {
        DecimalFormat format = new DecimalFormat("#");
        this.x = x;
        this.y = y;
        this.z = z;
        this.name = format.format(this.x) + indexMap + format.format(this.y);
        this.neighbors = new HashSet<>();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (this.z != 0 ? this.z.hashCode() : 0);
        result = 31 * result + (this.y != 0 ? this.y.hashCode() : 0);
        result = 31 * result + (this.x != 0 ? this.x.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PointFromDXF point)) return false;
        if (!Objects.equals(this.z, point.z)) return false;
        if (!Objects.equals(this.y, point.y)) return false;
        return Objects.equals(this.x, point.x);
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        DecimalFormat format = new DecimalFormat("#");
        builder.append(name + " Point id:" + id + " {x = " + format.format(this.x) + ", y = " + format.format(this.y) + "} "
                + "type: " + this.type + ", Neighbors:" + neighbors);
        return builder.toString();
    }
}