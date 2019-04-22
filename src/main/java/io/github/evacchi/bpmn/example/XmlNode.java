package io.github.evacchi.bpmn.example;

import io.github.evacchi.bpmn.TEndEvent;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TScriptTask;
import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.graph.FlowNode;

public class XmlNode {

    static FlowNode of(TFlowElement el) {
        if (el instanceof TStartEvent) {
            return new FlowNode(el.getId(), el);
        }
        if (el instanceof TEndEvent) {
            return new FlowNode(el.getId(), el);
        }
        if (el instanceof TScriptTask) {
            return new FlowNode(el.getId(), el);
        }
        throw new UnsupportedOperationException(el.getId());
    }
}
