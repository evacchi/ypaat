package io.github.evacchi.bpmn.draw;

import java.util.Arrays;
import java.util.List;

public class LayoutEdge {

    public static class Waypoint {

        final int x, y;

        public Waypoint(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%d, %d)", x, y);
        }
    }

    private final List<Waypoint> points;

    public LayoutEdge(Waypoint... points) {
        this.points = Arrays.asList(points);
    }

    public LayoutEdge(List<Waypoint> points) {
        this.points = points;
    }

    public List<Waypoint> points() {
        return points;
    }
}
