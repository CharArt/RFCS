package com.wb.amr.robot.flotilla.control.system.map.dxf;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Objects;

@Data
@EqualsAndHashCode(callSuper = false)
public class NeighborPoint {
    private Long pointId;
    private Double lengthForNeighbor;

    public NeighborPoint(Long pointId, Double lengthForNeighbor) {
        this.pointId = pointId;
        this.lengthForNeighbor = lengthForNeighbor;
    }

    public NeighborPoint(Long pointId) {
        this.pointId = pointId;
    }

    public NeighborPoint() {
    }


    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof NeighborPoint neighborPoint)) return false;
        if (!(Objects.equals(this.pointId, neighborPoint.pointId))) return false;
        return Objects.equals(this.lengthForNeighbor, neighborPoint.lengthForNeighbor);
    }

    @Override
    public int hashCode() {
        int result = 17;
        result = 31 * result + (pointId != 0 ? pointId.hashCode() : 0);
        result = 31 * result + (lengthForNeighbor != 0 ? lengthForNeighbor.hashCode() : 0);
        return result;
    }
}
