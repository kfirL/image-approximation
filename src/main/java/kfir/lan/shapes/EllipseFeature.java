package kfir.lan.shapes;

import java.util.concurrent.ThreadLocalRandom;

public class EllipseFeature extends ShapeFeature {

    private int x;
    private int y;
    private int width;
    private int height;

    public EllipseFeature(int red,
                          int green,
                          int blue,
                          int alpha,
                          double angle,
                          int x,
                          int y,
                          int width,
                          int height) {
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

    private void mutateLocation(int maxWidth, int maxHeight) {
        int feature = ThreadLocalRandom.current().nextInt(3);
        if (feature == 0) {
            x = locationChange(x, maxWidth);
            y = locationChange(y, maxHeight);
        } else if (feature == 1) {
            width = locationChange(width, maxWidth);
            height = locationChange(height, maxHeight);
        } else {
            mutateAngle();
        }
    }

    private int locationChange(int val, int maxVal) {
        int change = ThreadLocalRandom.current().nextInt(maxVal / 4);
        change = ThreadLocalRandom.current().nextBoolean() ? change : -change;
        return Math.min(maxVal, Math.max(0, val+ change));
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
