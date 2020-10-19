package kfir.lan.shapes;

import java.util.concurrent.ThreadLocalRandom;

public class FeatureFactory {

    public static enum FeatureType {
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
            int red = ThreadLocalRandom.current().nextInt(256);
            int green = ThreadLocalRandom.current().nextInt(256);
            int blue = ThreadLocalRandom.current().nextInt(256);
            int alpha = ThreadLocalRandom.current().nextInt(56) + 200;
            double angle = ThreadLocalRandom.current().nextDouble(Math.PI * 2);
            return new EllipseFeature(red, green, blue, alpha, angle, x, y, width, height);
        }
        return null;
    }


}
