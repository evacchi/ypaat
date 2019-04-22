package io.github.evacchi.bpmn.example;

import io.github.evacchi.bpmn.TProcess;
import io.github.evacchi.bpmn.TSubProcess;

public class ProcessVisitor {
    public GraphBuilder process(TProcess root) {
        var nodeCollector = new NodeCollector();
        nodeCollector.visitFlowElements(root.getFlowElement());
        var edgeCollector = new EdgeCollector(nodeCollector.nodes());
        edgeCollector.visitFlowElements(root.getFlowElement());

        return edgeCollector.graphBuilder();
    }

    public GraphBuilder subProcess(TSubProcess root) {
        var nodeCollector = new NodeCollector();
        nodeCollector.visitFlowElements(root.getFlowElement());
        var edgeCollector = new EdgeCollector(nodeCollector.nodes());
        edgeCollector.visitFlowElements(root.getFlowElement());

        return edgeCollector.graphBuilder();
    }
}
