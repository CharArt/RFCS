package com.wb.amr.robot.flotilla.control.system.map.xml;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class NeighborList extends ArrayList<Neighbor> {
    public NeighborList(int initialCapacity) {
        super(initialCapacity);
    }

    public NeighborList() {
    }

    public NeighborList(@NotNull Collection<? extends Neighbor> c) {
        super(c);
    }
}
