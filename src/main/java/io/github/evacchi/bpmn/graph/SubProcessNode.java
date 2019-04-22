package io.github.evacchi.bpmn.graph;

public class SubProcessNode extends SubGraph {

    public SubProcessNode(String id, GraphBuilder el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
