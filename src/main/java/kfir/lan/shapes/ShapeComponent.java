package kfir.lan.shapes;

import javax.swing.*;
import java.awt.*;

public class ShapeComponent extends JComponent {

    private Shape shape;
    private Color color;
    private double angle;
    private final Object lock = new Object();

    public ShapeComponent() {
    }

    public ShapeComponent(Shape shape, Color color, double angle) {
        this.shape = shape;
        this.color = color;
        this.angle = angle;
    }

    @Override
    public void paint(Graphics g) {
        synchronized (lock) {
            if (shape == null || color == null) {
                return;
            }
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(color);
            Rectangle bounds = shape.getBounds();
            g2.rotate(angle, bounds.x + bounds.width /2.0, bounds.y + bounds.height / 2.0);
            g2.fill(shape);
        }
    }

    public void setShape(Shape shape) {
        synchronized (lock) {
            this.shape = shape;
        }
    }

    public void setColor(Color color) {
        synchronized (lock) {
            this.color = color;
        }
    }

    public void setAngle(double angle) {
        synchronized (lock) {
            this.angle = angle;
        }
    }
}
