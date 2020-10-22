package kfir.lan;

import kfir.lan.shapes.ShapeFeature;

import java.awt.image.BufferedImage;
import java.util.List;

public interface ImageApproximator {

    List<ShapeFeature> approximate(BufferedImage image);
}
