package kfir.lan.heuristic;

import java.awt.image.BufferedImage;

public interface ImageComparator {

    /**
     * @param approx an approximation of the original picture
     * @return double distance between 0 to 1
     */
    double distance(BufferedImage approx);
}
