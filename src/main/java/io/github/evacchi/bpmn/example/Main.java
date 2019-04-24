package io.github.evacchi.bpmn.example;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import javax.imageio.ImageIO;

import io.github.evacchi.bpmn.Bpmn;
import io.github.evacchi.bpmn.engine.Engine;

public class Main {

    public static void main(String[] args) throws IOException {

        InputStream resource =
                Main.class.getResourceAsStream(
                        "/BPMN2-SubProcess.bpmn2"
//                        "/helloWorld.bpmn"
                );

        Bpmn bpmn = Bpmn.of(resource);
        Engine engine = bpmn.engine();
        engine.eval();

        BufferedImage image = bpmn.draw();
        ImageIO.write(image, "png", new File("./output_image.png"));
    }
}

