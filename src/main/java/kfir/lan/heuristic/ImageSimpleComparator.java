package kfir.lan.heuristic;

import java.awt.image.BufferedImage;

public class ImageSimpleComparator implements ImageComparator{

    private static final int MAX_COLOR = 255;
    private final PixelColor[][] pixelColors;
    private final double normalizationFactor;

    public ImageSimpleComparator(BufferedImage bufferedImage) {
        pixelColors = new PixelColor[bufferedImage.getWidth()][bufferedImage.getHeight()];
        var colorModel = bufferedImage.getColorModel();
        double sumPixelDistances = 0;
        for (int x = 0; x < bufferedImage.getWidth(); ++x) {
            for (int y = 0; y < bufferedImage.getHeight(); ++y) {
                var dataElements = bufferedImage.getRaster().getDataElements(x, y, null);
                int red = colorModel.getRed(dataElements);
                int green = colorModel.getGreen(dataElements);
                int blue = colorModel.getBlue(dataElements);
                pixelColors[x][y] = new PixelColor(red, green, blue);
                sumPixelDistances += (Math.max(MAX_COLOR - red, red))
                        + (Math.max(MAX_COLOR - green, green))
                        + (Math.max(MAX_COLOR - blue, blue));
            }
        }
        normalizationFactor =  1 / sumPixelDistances;
    }

    public double distance(BufferedImage approx) {
        var colorModel = approx.getColorModel();
        double sumPixelDistances = 0;
        for (int x = 0; x < approx.getWidth(); ++x) {
            for (int y = 0; y < approx.getHeight(); ++y) {
                var dataElements = approx.getRaster().getDataElements(x, y, null);
                int red = colorModel.getRed(dataElements);
                int green = colorModel.getGreen(dataElements);
                int blue = colorModel.getBlue(dataElements);
                var origPixelColor = pixelColors[x][y];
                sumPixelDistances += Math.abs(origPixelColor.red - red)
                        + Math.abs(origPixelColor.green - green)
                        + Math.abs(origPixelColor.blue - blue);
            }
        }
        return sumPixelDistances * normalizationFactor;
    }

    private static class PixelColor {
        private int red;
        private int green;
        private int blue;

        public PixelColor(int red, int green, int blue) {
            this.red = red;
            this.green = green;
            this.blue = blue;
        }
    }
}
