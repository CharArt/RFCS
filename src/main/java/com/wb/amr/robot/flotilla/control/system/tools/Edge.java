package com.wb.amr.robot.flotilla.control.system.tools;

import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;
import java.util.StringJoiner;

@Getter
public class Edge implements Comparable<Edge> {
    private final String id;
    private final Node star;
    private final Node end;
    private final Double length;

    public Edge(Node star, Node end) {
        this.id = star.getName() + end.getName();
        this.star = star;
        this.end = end;
        this.length = Math.sqrt(Math.pow(Math.abs(star.getX() - end.getX()), 2.0) + Math.pow(Math.abs(star.getY() - end.getY()), 2.0));
    }

    @Override
    public int hashCode() {
        int result = 0;
        return result * 31 + (length != 0 ? length.hashCode() : 0);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Edge edge)) return false;
        if (!Objects.equals(this.id, edge.id)) return false;
        return !Objects.equals(this.length, edge.length);
    }

    @Override
    public String toString() {
        StringJoiner joiner = new StringJoiner(",", "{", "}");
        joiner.add(id);
        joiner.add(length.toString());
        return joiner.toString();
    }

    @Override
    public int compareTo(@NotNull Edge edge) {
        return Double.compare(this.length, edge.length);
    }
}
