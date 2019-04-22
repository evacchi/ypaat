package io.github.evacchi.bpmn.example;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.BaseVisitor;
import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.TEndEvent;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TProcess;
import io.github.evacchi.bpmn.TScriptTask;
import io.github.evacchi.bpmn.TSequenceFlow;
import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.TSubProcess;

public class NodeCollector extends BaseVisitor<Void, RuntimeException> {

    final List<Node<?>> nodes;

    public NodeCollector() {
        nodes = new ArrayList<>();
    }

    public List<Node<?>> nodes() {
        return nodes;
    }

    public void visitFlowElements(List<JAXBElement<? extends TFlowElement>> flowElement) {
        flowElement.forEach(el -> el.getValue().accept(this));
    }

    public Void visit(TSubProcess p) {
        GraphBuilder result = new ProcessVisitor().subProcess(p);
        nodes.add(new SubGraph(p.getId(), result));
        return null;
    }

    public Void visit(TStartEvent evt) {
        nodes.add(new FlowNode(evt.getId(), evt));
        return null;
    }

    public Void visit(TEndEvent evt) {
        nodes.add(new FlowNode(evt.getId(), evt));
        return null;
    }

    public Void visit(TScriptTask task) {
        nodes.add(new FlowNode(task.getId(), task));
        return null;
    }

    public Void visit(TSequenceFlow seq) {
        return null;
    }
}
