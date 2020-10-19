package kfir.lan.shapes;

import java.util.concurrent.ThreadLocalRandom;

public abstract class ShapeFeature {

    private static final int ALPHA_CHANGE = 30;

    private int red;
    private int green;
    private int blue;
    private int alpha;
    private double angle;

    public ShapeFeature(int red, int green, int blue, int alpha, double angle) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.alpha = alpha;
        this.angle = angle;
    }

    public abstract ShapeFeature mutate(int maxWidth, int maxHeight);

    public abstract ShapeFeature copy();

    public abstract ShapeFeature scale(double xRation, double yRatio);

    protected void mutateAngle() {
        this.angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
    }

    protected void mutateColor() {
        int colorChange = ThreadLocalRandom.current().nextInt(ALPHA_CHANGE) + 1;
        colorChange = ThreadLocalRandom.current().nextBoolean() ? colorChange : -colorChange;
        int color = ThreadLocalRandom.current().nextInt(4);
        switch (color) {
            case 0 -> red = Math.min(255, Math.max(0, colorChange + red));
            case 1 -> green  = Math.min(255, Math.max(0, colorChange + green));
            case 2 -> blue  = Math.min(255, Math.max(0, colorChange + blue));
            default -> alpha = Math.min(255, Math.max(0, colorChange + alpha));
        }
    }

    public int getRed() {
        return red;
    }

    public int getGreen() {
        return green;
    }

    public int getBlue() {
        return blue;
    }

    public int getAlpha() {
        return alpha;
    }

    public double getAngle() {
        return angle;
    }

}
