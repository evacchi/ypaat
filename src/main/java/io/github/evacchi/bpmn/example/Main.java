package io.github.evacchi.bpmn.example;

import java.util.List;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBElement;

import io.github.evacchi.bpmn.TDefinitions;
import io.github.evacchi.bpmn.TFlowElement;
import io.github.evacchi.bpmn.TProcess;

public class Main {

    public static void main(String[] args) {
        new Main().main();
    }

    public void main() {
        var tdefs = JAXB.unmarshal(
                Main.class.getResourceAsStream("/BPMN2-SubProcess.bpmn2"),
//                Main.class.getResourceAsStream("/helloWorld.bpmn"),
                TDefinitions.class);

        var p = findProcess(tdefs);
        GraphBuilder result = new ProcessVisitor().process(p);
        System.out.println(result);
    }

    private TProcess findProcess(TDefinitions tdefs) {
        return tdefs.getRootElement().stream()
                .map(JAXBElement::getValue)
                .filter(r -> r instanceof TProcess)
                .map(r -> (TProcess) r)
                .findFirst().get();
    }
}

