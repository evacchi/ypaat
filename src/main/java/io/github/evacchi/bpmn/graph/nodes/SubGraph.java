package io.github.evacchi.bpmn.graph.nodes;

import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.Node;

public class SubGraph extends Node<EngineGraph> {

    public SubGraph(String id, EngineGraph el) {
        super(id, el);
    }
}
