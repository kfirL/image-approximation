package kfir.lan.shapes;

import java.util.concurrent.ThreadLocalRandom;

public class EllipseFeature extends ShapeFeature {

    private double x;
    private double y;
    private double width;
    private double height;

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
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
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
        return new EllipseFeature(getRed(), getGreen(), getBlue(), getAlpha(), getAngle(), x, y, width, height);
    }

    @Override
    public ShapeFeature scale(double xRatio, double yRatio) {
        x *= xRatio;
        width *= xRatio;
        y *= yRatio;
        height *= yRatio;
        return this;
    }

    private void mutateLocation(double maxWidth, double maxHeight) {
        int feature = ThreadLocalRandom.current().nextInt(3);
        if (feature == 0) {
            x = locationChange(x, maxWidth, -width);
            y = locationChange(y, maxHeight, -height);
        } else if (feature == 1) {
            width = locationChange(width, maxWidth, 0);
            height = locationChange(height, maxHeight, 0);
        } else {
            mutateAngle();
        }
    }

    private double locationChange(double val, double maxVal, double minVal) {
        double change = ThreadLocalRandom.current().nextDouble(maxVal / 4);
        change = ThreadLocalRandom.current().nextBoolean() ? change : -change;
        return Math.min(maxVal, Math.max(minVal, val+ change));
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }
}
