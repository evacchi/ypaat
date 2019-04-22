package io.github.evacchi.bpmn.engine;

import io.github.evacchi.bpmn.graph.EndEventNode;
import io.github.evacchi.bpmn.graph.GraphBuilder;
import io.github.evacchi.bpmn.graph.GraphVisitor;
import io.github.evacchi.bpmn.graph.Node;
import io.github.evacchi.bpmn.graph.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.StartEventNode;
import io.github.evacchi.bpmn.graph.SubProcessNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Engine implements GraphVisitor {

    private static final Logger logger = LoggerFactory.getLogger(Engine.class);
    private final GraphBuilder graph;

    public Engine(GraphBuilder graph) {
        this.graph = graph;
    }

    public void eval() {
        visit(graph.start());
    }

    @Override
    public void visit(StartEventNode node) {
        logger.info("Process '{}' started.", graph.name());
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(EndEventNode node) {
        logger.info("Process ended.");
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(ScriptTaskNode node) {
        logger.info("Evaluating script task: {}",
                    node.element().getScript().getContent());
        graph.outgoing(node).forEach(this::visit);
    }

    @Override
    public void visit(SubProcessNode node) {
        Engine nestedEngine = new Engine(node.element());
        logger.info("Evaluate SubProcess '{}'", node.element().name());
        nestedEngine.eval();
        logger.info("SubProcess Ended");
        graph.outgoing(node).forEach(this::visit);
    }

    public void visit(Node<?> node) {
        logger.info("Visiting node {}", node.id());
        node.accept(this);
    }
}
