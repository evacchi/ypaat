package io.github.evacchi.bpmn.draw;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.List;

import io.github.evacchi.bpmn.TEndEvent;
import io.github.evacchi.bpmn.TScriptTask;
import io.github.evacchi.bpmn.TStartEvent;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.Edge;
import io.github.evacchi.bpmn.graph.GraphVisitor;
import io.github.evacchi.bpmn.graph.Node;
import io.github.evacchi.bpmn.graph.nodes.EndEventNode;
import io.github.evacchi.bpmn.graph.nodes.ScriptTaskNode;
import io.github.evacchi.bpmn.graph.nodes.StartEventNode;
import io.github.evacchi.bpmn.graph.nodes.SubProcessNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Canvas implements GraphVisitor {

    private static final Logger logger = LoggerFactory.getLogger(Canvas.class);
    private final EngineGraph graph;
    private final LayoutIndex index;

    private final Bounds bounds;
    private final BufferedImage bufferedImage;
    private final Graphics2D graphics2D;

    public Canvas(EngineGraph graph, LayoutIndex index) {
        this.graph = graph;
        this.index = index;

        int canvasW = 20 + maxX();
        int canvasH = 20 + maxY();

        this.bounds = new Bounds(0, 0, canvasW, canvasH);

        this.bufferedImage = new BufferedImage(
                bounds.width,
                bounds.height,
                BufferedImage.TYPE_INT_RGB);
        Graphics graphics = this.bufferedImage.getGraphics();
        graphics2D = (Graphics2D) graphics;
        graphics2D.setBackground(Color.WHITE);
        graphics2D.clearRect(0,
                             0,
                             bounds.width,
                             bounds.height);
        initGraphicContext(graphics2D);
    }

    public Canvas(EngineGraph graph, LayoutIndex index, BufferedImage bufferedImage, Bounds bounds) {
        this.graph = graph;
        this.index = index;
        this.bounds = bounds;

        this.bufferedImage = bufferedImage;
        Graphics graphics = bufferedImage.getGraphics();
        graphics2D = (Graphics2D) graphics;
        initGraphicContext(graphics2D);
    }

    private void initGraphicContext(Graphics2D graphics2D) {
        graphics2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING,
                                    RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                                    RenderingHints.VALUE_ANTIALIAS_ON);
        graphics2D.setStroke(new BasicStroke(3));
    }

    public void eval() {
        graph.edges().forEach(this::draw);
        graph.nodes().forEach(this::visit);
    }

    public BufferedImage bufferedImage() {
        return bufferedImage;
    }

    private void draw(Edge edge) {
        LayoutEdge layout = index.edge(edge.id());
        logger.info("Edge '{}' for {} -> {} with waypoints {}", edge.id(), edge.left().id(), edge.right().id(), layout.points());
        List<LayoutEdge.Waypoint> pts = layout.points();
        graphics2D.setPaint(Color.BLACK);
        LayoutEdge.Waypoint left = pts.get(0);
        for (int i = 1; i < pts.size(); i++) {
            LayoutEdge.Waypoint right = pts.get(i);
            graphics2D.drawLine(
                    left.x + this.bounds.x,
                    left.y + this.bounds.y,
                    right.x + this.bounds.x,
                    right.y + this.bounds.y);
            left = right;
        }
    }

    @Override
    public void visit(StartEventNode node) {
        Bounds shape = shapeOf(node);
        logger.info("StartEvent '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
        Ellipse2D.Double s = new Ellipse2D.Double(shape.x, shape.y, shape.width, shape.height);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.draw(s);
        graphics2D.setPaint(Color.GREEN);
        graphics2D.fill(s);

        graphics2D.setPaint(Color.BLACK);
        TStartEvent element = node.element();
        graphics2D.drawString(element.getName(), shape.x, shape.y - 10);
    }

    @Override
    public void visit(EndEventNode node) {
        Bounds shape = shapeOf(node);
        logger.info("EndEvent '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
        Ellipse2D.Double s = new Ellipse2D.Double(shape.x, shape.y, shape.width, shape.height);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.draw(s);
        graphics2D.setPaint(Color.RED);
        graphics2D.fill(s);

        graphics2D.setPaint(Color.BLACK);
        TEndEvent element = node.element();
        graphics2D.drawString(element.getName(), shape.x, shape.y - 10);
    }

    @Override
    public void visit(ScriptTaskNode node) {
        Bounds shape = shapeOf(node);
        logger.info("ScriptTask '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);

        Rectangle2D.Double s = new Rectangle2D.Double(shape.x, shape.y, shape.width, shape.height);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.draw(s);
        graphics2D.setPaint(new Color(240, 240, 240));
        graphics2D.fill(s);

        graphics2D.setPaint(Color.BLACK);
        TScriptTask element = node.element();
        graphics2D.drawString(element.getName(), shape.x + 20, shape.y + 20);
    }

    @Override
    public void visit(SubProcessNode node) {
        Bounds shape = shapeOf(node);
        logger.info("SubProcess '{}' at ({},{}) size {}×{}", node.id(), shape.x, shape.y, shape.width, shape.height);
        Rectangle2D.Double s = new Rectangle2D.Double(shape.x, shape.y, shape.width, shape.height);
        graphics2D.setPaint(Color.BLACK);
        graphics2D.draw(s);
        graphics2D.setPaint(Color.WHITE);
        graphics2D.fill(s);

        graphics2D.setPaint(Color.BLACK);
        EngineGraph element = node.element();
        graphics2D.drawString(element.name(), shape.x + 5, shape.y - 5);

        Canvas nestedEngine = new Canvas(node.element(), index, bufferedImage, shape);
        nestedEngine.eval();
    }

    public void visit(Node<?> node) {
        node.accept(this);
    }

    private int maxY() {
        return graph.nodes().stream().map(this::shapeOf).mapToInt(s -> s.y + s.height).max().getAsInt();
    }

    private int maxX() {
        return graph.nodes().stream().map(this::shapeOf).mapToInt(s -> s.x + s.width).max().getAsInt();
    }

    private Bounds shapeOf(Node<?> n) {
        return index.shape(n.id());
    }
}
