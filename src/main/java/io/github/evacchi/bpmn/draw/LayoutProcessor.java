package io.github.evacchi.bpmn.draw;

import java.awt.image.BufferedImage;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.Graph;
import io.github.evacchi.bpmn.graph.GraphReader;

public class LayoutProcessor {

    private final TDefinitions tdefs;
    private final EngineGraph graph;

    public static LayoutProcessor of(TDefinitions tdefs) {
        GraphReader processVisitor = GraphReader.of(tdefs);
        Graph graphBuilder = processVisitor.read();
        EngineGraph graph = EngineGraph.of(graphBuilder);
        return new LayoutProcessor(tdefs, graph);
    }

    public LayoutProcessor(TDefinitions tdefs, EngineGraph graph) {
        this.tdefs = tdefs;
        this.graph = graph;
    }

    public BufferedImage process() {
        LayoutExtractor extractor = new LayoutExtractor();
        extractor.visit(tdefs);
        LayoutIndex index = extractor.index();
        Canvas canvas = new Canvas(graph, index);
        canvas.eval();
        return canvas.bufferedImage();
    }
}
