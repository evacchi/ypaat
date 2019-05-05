package io.github.evacchi.bpmn.graph;

import java.util.List;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TProcess;
import io.github.evacchi.bpmn.TSubProcess;

public class GraphReader {

    public static GraphBuilder read(TDefinitions tdefs) {
        return read(findRootProcess(tdefs));
    }

    public static GraphBuilder read(TProcess root) {
        return read(root.getName(), root.getFlowElement());
    }

    public static GraphBuilder subProcess(TSubProcess root) {
        return read(root.getName(), root.getFlowElement());
    }

    private static GraphBuilder read(String name, List<JAXBElement<? extends TFlowElement>> elements) {
        GraphBuilder graph = new GraphBuilder(name);
        NodeCollector nodeCollector = new NodeCollector(graph);
        nodeCollector.visitFlowElements(elements);
        EdgeCollector edgeCollector = new EdgeCollector(graph);
        edgeCollector.visitFlowElements(elements);
        return graph;
    }

    private static TProcess findRootProcess(TDefinitions tdefs) {
        return tdefs.getRootElement().stream()
                .map(JAXBElement::getValue)
                .filter(r -> r instanceof TProcess)
                .map(r -> (TProcess) r)
                .findFirst().get();
    }
}
