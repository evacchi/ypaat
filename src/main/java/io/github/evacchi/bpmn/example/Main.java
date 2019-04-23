package io.github.evacchi.bpmn.example;

import javax.xml.bind.JAXB;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.engine.Engine;
import io.github.evacchi.bpmn.engine.EngineGraph;
import io.github.evacchi.bpmn.graph.GraphBuilder;

public class Main {

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        TDefinitions tdefs = JAXB.unmarshal(
                Main.class.getResourceAsStream("/BPMN2-SubProcess.bpmn2"),
//                Main.class.getResourceAsStream("/helloWorld.bpmn"),
                TDefinitions.class);

        GraphBuilder graphBuilder =
                new ProcessVisitor().process(tdefs);
        EngineGraph result = EngineGraph.of(graphBuilder);
        Engine engine = new Engine(result);
        engine.eval();

        LayoutProcessor layout = new LayoutProcessor();
        layout.process(tdefs, graphBuilder);
    }
}

