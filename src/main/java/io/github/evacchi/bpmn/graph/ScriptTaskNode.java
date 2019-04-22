package io.github.evacchi.bpmn.graph;

import io.github.evacchi.bpmn.TScriptTask;

public class ScriptTaskNode extends FlowNode<TScriptTask> {

    public ScriptTaskNode(String id, TScriptTask el) {
        super(id, el);
    }

    public void accept(GraphVisitor visitor) {
        visitor.visit(this);
    }

}
