package io.github.evacchi.bpmn.draw;

public class Bounds {

    final double x, y, width, height;

    public Bounds(double x, double y, double width, double height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public double x() {
        return x;
    }

    public double y() {
        return y;
    }

    public double width() {
        return width;
    }

    public double height() {
        return height;
    }

    @Override
    public String toString() {
        return "Shape{" +
                "x=" + x +
                ", y=" + y +
                ", width=" + width +
                ", height=" + height +
                '}';
    }
}
