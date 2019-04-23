package io.github.evacchi.bpmn.draw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import io.github.evacchi.bpmn.BPMNEdge;
import io.github.evacchi.bpmn.BPMNShape;

public class LayoutIndex {

    Map<String, Bounds> shapes = new HashMap<String, Bounds>();
    Map<String, LayoutEdge> edges = new HashMap<>();

    void put(String id, BPMNShape shape) {
        io.github.evacchi.bpmn.Bounds bounds = shape.getBounds();
        shapes.put(id, new Bounds(
                bounds.getX(),
                bounds.getY(),
                bounds.getWidth(),
                bounds.getHeight()
        ));
    }

    void put(String id, BPMNEdge edge) {
        List<LayoutEdge.Waypoint> pts = edge.getWaypoint().stream()
                .map(p -> new LayoutEdge.Waypoint(p.getX(), p.getY()))
                .collect(Collectors.toList());
        edges.put(id, new LayoutEdge(pts));
    }

    Bounds shape(String id) {
        return shapes.get(id);
    }

    LayoutEdge edge(String id) {
        return edges.get(id);
    }
}
