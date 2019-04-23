package io.github.evacchi.bpmn.draw;

import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphVisitor;
import io.github.evacchi.bpmn.graph.Node;
import io.github.evacchi.bpmn.graph.bpmn.EndEventNode;
import io.github.evacchi.bpmn.graph.bpmn.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.bpmn.StartEventNode;
import io.github.evacchi.bpmn.graph.bpmn.SubProcessNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canvas implements GraphVisitor {

    private static final Logger logger = LoggerFactory.getLogger(Canvas.class);
    private final EngineGraph graph;
    private final LayoutIndex index;

    public Canvas(EngineGraph graph, LayoutIndex index) {
        this.graph = graph;
        this.index = index;
    }

    public void eval() {
        visit(graph.start());
    }

    @Override
    public void visit(StartEventNode node) {
        draw(node);
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(EndEventNode node) {
        draw(node);
        // no outgoing edges
    }

    @Override
    public void visit(ScriptTaskNode node) {
        draw(node);
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(SubProcessNode node) {
        Canvas nestedEngine = new Canvas(node.element(), index);
        nestedEngine.eval();
        graph.outgoing(node).forEach(this::visit);
    }

    public void visit(Node<?> node) {
        node.accept(this);
    }

    private void draw(Node<?> node) {
        Bounds shape = index.shape(node.id());
        logger.info("Draw node {} at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
    }
}
