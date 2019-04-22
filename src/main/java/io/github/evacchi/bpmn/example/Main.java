package io.github.evacchi.bpmn.example;

import javax.xml.bind.JAXB;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.engine.Engine;
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

        GraphBuilder result = new ProcessVisitor().process(tdefs);

        Engine engine = new Engine(result);
        engine.eval();

    }

}

