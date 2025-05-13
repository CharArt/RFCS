package com.wb.amr.robot.flotilla.control.system.map.dxf;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
public class PointXYZ {
    private Double x;
    private Double y;
    private Double z;

    public PointXYZ(Double x, Double y, Double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (z != 0 ? z.hashCode() : 0);
        result = 31 * result + (y != 0 ? y.hashCode() : 0);
        result = 31 * result + (x != 0 ? x.hashCode() : 0);
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof PointXYZ point)) return false;

        if (!Objects.equals(this.z, point.z)) return false;
        if (!Objects.equals(this.y, point.y)) return false;
        return Objects.equals(this.x, point.x);
    }

}
