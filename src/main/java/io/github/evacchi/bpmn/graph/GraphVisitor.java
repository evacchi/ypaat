package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.graph.bpmn.EndEventNode;
import io.github.evacchi.bpmn.graph.bpmn.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.bpmn.StartEventNode;
import io.github.evacchi.bpmn.graph.bpmn.SubProcessNode;

public interface GraphVisitor {

    void visit(StartEventNode node);

    void visit(EndEventNode node);

    void visit(ScriptTaskNode node);

    void visit(SubProcessNode node);

    void visit(Node<?> tNode);
}
