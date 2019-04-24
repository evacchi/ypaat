package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.graph.nodes.EndEventNode;
import io.github.evacchi.bpmn.graph.nodes.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.nodes.StartEventNode;
import io.github.evacchi.bpmn.graph.nodes.SubProcessNode;

public interface GraphVisitor {

    void visit(StartEventNode node);

    void visit(EndEventNode node);

    void visit(ScriptTaskNode node);

    void visit(SubProcessNode node);

    void visit(Node<?> tNode);
}
