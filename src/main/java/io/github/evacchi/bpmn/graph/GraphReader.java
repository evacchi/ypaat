package io.github.evacchi.bpmn.graph;

import java.util.List;
import java.util.function.Supplier;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TProcess;
import io.github.evacchi.bpmn.TSubProcess;

public class GraphReader {

    public static GraphReader of(TDefinitions tdefs) {
        return new GraphReader(findRootProcess(tdefs));
    }

    private final TProcess rootProcess;

    public GraphReader(TProcess rootProcess) {
        this.rootProcess = rootProcess;
    }

    public Graph read() {
        return read(rootProcess);
    }

    public static Graph read(TProcess root) {
        return read(root.getName(), root::getFlowElement);
    }

    public static Graph subProcess(TSubProcess root) {
        return read(root.getName(), root::getFlowElement);
    }

    private static Graph read(String name, Supplier<List<JAXBElement<? extends TFlowElement>>> elementSupplier) {
        List<JAXBElement<? extends TFlowElement>> elements = elementSupplier.get();
        Graph graphBuilder = new Graph(name);
        NodeCollector nodeCollector = new NodeCollector(graphBuilder);
        nodeCollector.visitFlowElements(elements);
        EdgeCollector edgeCollector = new EdgeCollector(graphBuilder);
        edgeCollector.visitFlowElements(elements);
        return edgeCollector.graphBuilder();
    }

    private static TProcess findRootProcess(TDefinitions tdefs) {
        return tdefs.getRootElement().stream()
                .map(JAXBElement::getValue)
                .filter(r -> r instanceof TProcess)
                .map(r -> (TProcess) r)
                .findFirst().get();
    }
}
