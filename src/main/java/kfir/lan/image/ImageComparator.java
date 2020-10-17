package kfir.lan.image;

import java.awt.image.BufferedImage;

public interface ImageComparator {

    /**
     * @param approx an approximation of the original picture
     * @return double distance between 0 to 10
     */
    double distance(BufferedImage approx);
}
