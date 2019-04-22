package io.github.evacchi.bpmn.graph;

public interface GraphVisitor {

    void visit(StartEventNode node);

    void visit(EndEventNode node);

    void visit(ScriptTaskNode node);

    void visit(SubProcessNode node);

    void visit(Node<?> tNode);
}
