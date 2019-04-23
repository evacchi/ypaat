package io.github.evacchi.bpmn.example;

import java.util.List;
import java.util.function.Supplier;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TProcess;
import io.github.evacchi.bpmn.TSubProcess;
import io.github.evacchi.bpmn.graph.GraphBuilder;

public class ProcessVisitor {

    public GraphBuilder process(TDefinitions tdefs) {
        TProcess tProcess = findRootProcess(tdefs);
        GraphBuilder graphBuilder = process(tProcess);
        return graphBuilder;
    }

    private TProcess findRootProcess(TDefinitions tdefs) {
        return tdefs.getRootElement().stream()
                .map(JAXBElement::getValue)
                .filter(r -> r instanceof TProcess)
                .map(r -> (TProcess) r)
                .findFirst().get();
    }

    public GraphBuilder process(TProcess root) {
        return process(root.getName(), root::getFlowElement);
    }

    public GraphBuilder subProcess(TSubProcess root) {
        return process(root.getName(), root::getFlowElement);
    }

    private GraphBuilder process(String name, Supplier<List<JAXBElement<? extends TFlowElement>>> elementSupplier) {
        List<JAXBElement<? extends TFlowElement>> elements = elementSupplier.get();
        GraphBuilder graphBuilder = new GraphBuilder(name);
        NodeCollector nodeCollector = new NodeCollector(graphBuilder);
        nodeCollector.visitFlowElements(elements);
        EdgeCollector edgeCollector = new EdgeCollector(graphBuilder);
        edgeCollector.visitFlowElements(elements);
        return edgeCollector.graphBuilder();
    }
}
