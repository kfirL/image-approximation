package kfir.lan.shapes;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Ellipse2D;
import java.util.concurrent.ThreadLocalRandom;

public class EllipseFeature extends ShapeFeature {

    private Ellipse2D ellipse;

    public EllipseFeature(int red,
                          int green,
                          int blue,
                          int alpha,
                          double angle,
                          double x,
                          double y,
                          double width,
                          double height) {
        super(red, green, blue, alpha, angle);
        this.ellipse = new Ellipse2D.Double(x,y,width,height);
    }

    public EllipseFeature(Color color, double angle, Ellipse2D ellipse) {
        super(color, angle);
        this.ellipse = ellipse;
    }

    @Override
    public ShapeFeature mutate(int maxWidth, int maxHeight) {
        int feature = ThreadLocalRandom.current().nextInt(2);
        if (feature == 0) {
            mutateColor();
        } else {
            mutateLocation(maxWidth, maxHeight);
        }
        return this;
    }

    @Override
    public ShapeFeature copy() {
        return new EllipseFeature(getColor(), getAngle(), ellipse);
    }

    @Override
    public Shape getShape() {
        return ellipse;
    }

    @Override
    public ShapeFeature scale(double xScale, double yScale) {
        var affineTransform = new AffineTransform();
        affineTransform.scale(xScale, yScale);
        Shape scaledShape = affineTransform.createTransformedShape(getShape());
        var bounds = scaledShape.getBounds();
        return new EllipseFeature(getColor(), getAngle(),
                new Ellipse2D.Double(bounds.getX(), bounds.getY(), bounds.getWidth(), bounds.getHeight()));
    }

    private void mutateLocation(double maxWidth, double maxHeight) {
        int feature = ThreadLocalRandom.current().nextInt(3);
        double x = ellipse.getX();
        double y = ellipse.getY();
        double width = ellipse.getWidth();
        double height = ellipse.getHeight();
        if (feature == 0) {
            x = locationChange(x, maxWidth, -width);
            y = locationChange(y, maxHeight, -height);
            ellipse = new Ellipse2D.Double(x,y,width,height);
        } else if (feature == 1) {
            width = locationChange(width, maxWidth, 0);
            height = locationChange(height, maxHeight, 0);
            ellipse = new Ellipse2D.Double(x,y,width,height);
        } else {
            mutateAngle();
        }
    }

    private double locationChange(double val, double maxVal, double minVal) {
        double change = ThreadLocalRandom.current().nextDouble(maxVal * 0.1);
        change = ThreadLocalRandom.current().nextBoolean() ? change : -change;
        return Math.min(maxVal, Math.max(minVal, val+ change));
    }
}
