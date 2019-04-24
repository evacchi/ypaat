package io.github.evacchi.bpmn;

import java.awt.image.BufferedImage;
import java.io.InputStream;

import javax.xml.bind.JAXB;

import io.github.evacchi.bpmn.engine.Engine;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.draw.LayoutProcessor;
import io.github.evacchi.bpmn.graph.GraphReader;
import io.github.evacchi.bpmn.graph.Graph;

public class Bpmn {

    public static Bpmn of(InputStream resource) {
        TDefinitions tdefs = JAXB.unmarshal(
                resource,
                TDefinitions.class);
        return new Bpmn(tdefs);
    }

    private final TDefinitions tdefs;

    public Bpmn(TDefinitions tdefs) {
        this.tdefs = tdefs;
    }

    public Engine engine() {
        GraphReader graphReader = GraphReader.of(tdefs);
        Graph graph = graphReader.read();
        EngineGraph engineGraph = EngineGraph.of(graph);
        return new Engine(engineGraph);
    }

    public BufferedImage draw() {
        LayoutProcessor layout = LayoutProcessor.of(tdefs);
        return layout.process();
    }
}
