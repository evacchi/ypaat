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
    }

    final List<Waypoint> points;

    public LayoutEdge(Waypoint... points) {
        this.points = Arrays.asList(points);
    }

    public LayoutEdge(List<Waypoint> points) {
        this.points = points;
    }
}
