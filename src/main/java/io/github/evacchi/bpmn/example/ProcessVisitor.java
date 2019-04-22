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
        return process(tProcess);
    }

    private TProcess findRootProcess(TDefinitions tdefs) {
        return tdefs.getRootElement().stream()
                .map(JAXBElement::getValue)
                .filter(r -> r instanceof TProcess)
                .map(r -> (TProcess) r)
                .findFirst().get();
    }

    public GraphBuilder process(TProcess root) {
        return process(root::getFlowElement).withName(root.getName());
    }

    public GraphBuilder subProcess(TSubProcess root) {
        return process(root::getFlowElement).withName(root.getName());
    }

    public GraphBuilder process(Supplier<List<JAXBElement<? extends TFlowElement>>> elementSupplier) {
        GraphBuilder graphBuilder = new GraphBuilder();
        NodeCollector nodeCollector = new NodeCollector(graphBuilder);
        nodeCollector.visitFlowElements(elementSupplier.get());
        EdgeCollector edgeCollector = new EdgeCollector(graphBuilder);
        edgeCollector.visitFlowElements(elementSupplier.get());

        return edgeCollector.graphBuilder();
    }
}
