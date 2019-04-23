package io.github.evacchi.bpmn.example;

import java.util.List;

import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.BaseVisitor;
import io.github.evacchi.bpmn.TEndEvent;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TScriptTask;
import io.github.evacchi.bpmn.TSequenceFlow;
import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.TSubProcess;
import io.github.evacchi.bpmn.graph.bpmn.EndEventNode;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphBuilder;
import io.github.evacchi.bpmn.graph.bpmn.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.bpmn.StartEventNode;
import io.github.evacchi.bpmn.graph.bpmn.SubProcessNode;

public class NodeCollector extends BaseVisitor<Void, RuntimeException> {

    final GraphBuilder nodes;

    public NodeCollector(GraphBuilder graphBuilder) {
        nodes = graphBuilder;
    }

    public void visitFlowElements(List<JAXBElement<? extends TFlowElement>> flowElement) {
        flowElement.forEach(el -> el.getValue().accept(this));
    }

    public Void visit(TSubProcess p) {
        GraphBuilder graphBuilder = new ProcessVisitor().subProcess(p);
        EngineGraph result = EngineGraph.of(graphBuilder);
        nodes.add(new SubProcessNode(p.getId(), result));
        return null;
    }

    public Void visit(TStartEvent evt) {
        nodes.add(new StartEventNode(evt.getId(), evt));
        return null;
    }

    public Void visit(TEndEvent evt) {
        nodes.add(new EndEventNode(evt.getId(), evt));
        return null;
    }

    public Void visit(TScriptTask task) {
        nodes.add(new ScriptTaskNode(task.getId(), task));
        return null;
    }

    public Void visit(TSequenceFlow seq) {
        return null;
    }
}
