package io.github.evacchi.bpmn.draw;

import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.Edge;
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
        graph.nodes().forEach(this::visit);
        graph.edges().forEach(this::draw);
    }

    private void draw(Edge edge) {
        LayoutEdge layout = index.edge(edge.id());
        logger.info("Edge '{}' for {} -> {} with waypoints {}", edge.id(), edge.left().id(), edge.right().id(), layout.points());
    }

    @Override
    public void visit(StartEventNode node) {
        Bounds shape = index.shape(node.id());
        logger.info("StartEvent '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
    }

    @Override
    public void visit(EndEventNode node) {
        Bounds shape = index.shape(node.id());
        logger.info("EndEvent '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
    }

    @Override
    public void visit(ScriptTaskNode node) {
        Bounds shape = index.shape(node.id());
        logger.info("ScriptTask '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(SubProcessNode node) {
        Bounds shape = index.shape(node.id());
        logger.info("SubProcess '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
        Canvas nestedEngine = new Canvas(node.element(), index);
        nestedEngine.eval();
        graph.outgoing(node).forEach(this::visit);
    }

    public void visit(Node<?> node) {
        node.accept(this);
    }
}
