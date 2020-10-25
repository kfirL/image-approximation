package kfir.lan.shapes;

import java.util.concurrent.ThreadLocalRandom;

public class FeatureFactory {

    public static final int COLOR_BOUND = 256;
    public static final int ALPHA_BOUND = 56;

    public enum FeatureType {
        Ellipse
    }

    private FeatureType featureType;
    private int maxWidth;
    private int maxHeight;

    public FeatureFactory(FeatureType featureType, int maxWidth, int maxHeight) {
        this.featureType = featureType;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    public ShapeFeature generateShape() {
        if (featureType == FeatureType.Ellipse) {
            int x = ThreadLocalRandom.current().nextInt(maxWidth / 2) + maxWidth / 4;
            int width = ThreadLocalRandom.current().nextInt(maxWidth / 4);
            int y = ThreadLocalRandom.current().nextInt(maxHeight / 2) + maxHeight / 4;
            int height = ThreadLocalRandom.current().nextInt(maxHeight / 4);
            int red = ThreadLocalRandom.current().nextInt(COLOR_BOUND);
            int green = ThreadLocalRandom.current().nextInt(COLOR_BOUND);
            int blue = ThreadLocalRandom.current().nextInt(COLOR_BOUND);
            int alpha = ThreadLocalRandom.current().nextInt(ALPHA_BOUND) + COLOR_BOUND - ALPHA_BOUND;
            double angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
            return new EllipseFeature(red, green, blue, alpha, angle, x, y, width, height);
        }
        return null;
    }


}
