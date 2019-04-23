package io.github.evacchi.bpmn.draw;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.BPMNDiagram;
import io.github.evacchi.bpmn.BPMNEdge;
import io.github.evacchi.bpmn.BPMNPlane;
import io.github.evacchi.bpmn.BPMNShape;
import io.github.evacchi.bpmn.BaseVisitor;
import io.github.evacchi.bpmn.TDefinitions;

public class LayoutExtractor extends BaseVisitor<Void, RuntimeException> {

    private LayoutIndex index = new LayoutIndex();

    public LayoutIndex index() {
        return index;
    }

    @Override
    public Void visit(TDefinitions defs) {
        defs.getBPMNDiagram().forEach(this::visit);
        return null;
    }

    @Override
    public Void visit(BPMNDiagram diag) {
        return visit(diag.getBPMNPlane());
    }

    @Override
    public Void visit(BPMNPlane plane) {
        plane.getDiagramElement().stream().map(JAXBElement::getValue).forEach(n -> n.accept(this));
        return null;
    }

    @Override
    public Void visit(BPMNShape shape) {
        String id = shape.getBpmnElement().getLocalPart();
        index.put(id, shape);
        return null;
    }

    @Override
    public Void visit(BPMNEdge e) {
        String id = e.getBpmnElement().getLocalPart();
        index.put(id, e);
        return null;
    }
}
