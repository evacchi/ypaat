package io.github.evacchi.bpmn.example;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.draw.Canvas;
import io.github.evacchi.bpmn.draw.LayoutExtractor;
import io.github.evacchi.bpmn.draw.LayoutIndex;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphBuilder;

public class LayoutProcessor {
    void process(TDefinitions tdefs, GraphBuilder graphBuilder) {
        LayoutExtractor extractor = new LayoutExtractor();
        extractor.visit(tdefs);
        LayoutIndex index = extractor.index();
        EngineGraph graph = EngineGraph.of(graphBuilder);
        Canvas canvas = new Canvas(graph, index);
        canvas.eval();
    }
}
