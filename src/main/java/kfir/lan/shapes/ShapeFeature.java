package kfir.lan.shapes;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public abstract class ShapeFeature {

    private static final int ALPHA_CHANGE = 30;

    private Color color;
    private double angle;

    public ShapeFeature(int red, int green, int blue, int alpha, double angle) {
        this.color = new Color(red, green, blue, alpha);
        this.angle = angle;
    }

    public ShapeFeature(Color color, double angle) {
        this.color = color;
        this.angle = angle;
    }

    public abstract ShapeFeature mutate(int maxWidth, int maxHeight);

    public abstract ShapeFeature copy();

    public abstract Shape getShape();

    public abstract ShapeFeature scale(double xScale, double yScale);

    protected void mutateAngle() {
        this.angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
    }

    protected void mutateColor() {
        int red = color.getRed();
        int green = color.getGreen();
        int blue = color.getBlue();
        int alpha = color.getAlpha();
        int colorChange = ThreadLocalRandom.current().nextInt(ALPHA_CHANGE) + 1;
        colorChange = ThreadLocalRandom.current().nextBoolean() ? colorChange : -colorChange;
        int colorToChange = ThreadLocalRandom.current().nextInt(4);
        switch (colorToChange) {
            case 0 -> red = Math.min(255, Math.max(0, colorChange + red));
            case 1 -> green = Math.min(255, Math.max(0, colorChange + green));
            case 2 -> blue = Math.min(255, Math.max(0, colorChange + blue));
            default -> alpha = Math.min(255, Math.max(0, colorChange + alpha));
        }
        color = new Color(red, green, blue, alpha);
    }

    public Color getColor() {
        return color;
    }

    public double getAngle() {
        return angle;
    }

}
