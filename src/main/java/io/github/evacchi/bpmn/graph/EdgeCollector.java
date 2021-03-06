package io.github.evacchi.bpmn.graph;

import java.util.List;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.BaseVisitor;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TSequenceFlow;

public class EdgeCollector extends BaseVisitor<Void, RuntimeException> {

    final GraphBuilder graphBuilder;

    public EdgeCollector(GraphBuilder graphBuilder) {
        this.graphBuilder = graphBuilder;
    }

    public Void visit(TSequenceFlow seq) {
        TFlowElement left = (TFlowElement) seq.getSourceRef();
        TFlowElement right = (TFlowElement) seq.getTargetRef();
        graphBuilder.addEdge(seq.getId(), seq, left.getId(), right.getId());
        return null;
    }

    public void visitFlowElements(List<JAXBElement<? extends TFlowElement>> flowElement) {
        flowElement.forEach(el -> el.getValue().accept(this));
    }
}
