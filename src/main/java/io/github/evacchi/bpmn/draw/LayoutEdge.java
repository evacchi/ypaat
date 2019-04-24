package io.github.evacchi.bpmn.draw;

import java.util.Arrays;
import java.util.List;

public class LayoutEdge {

    public static class Waypoint {

        final double x, y;

        public Waypoint(double x, double y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public String toString() {
            return String.format("(%.2f, %.2f)", x, y);
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
