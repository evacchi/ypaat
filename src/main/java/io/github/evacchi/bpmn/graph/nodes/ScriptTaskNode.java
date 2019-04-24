package io.github.evacchi.bpmn.graph.nodes;

import io.github.evacchi.bpmn.TScriptTask;
import io.github.evacchi.bpmn.graph.GraphVisitor;

public class ScriptTaskNode extends FlowNode<TScriptTask> {

    public ScriptTaskNode(String id, TScriptTask el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
