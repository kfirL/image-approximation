package kfir.lan.image;

import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import java.awt.*;
import java.awt.geom.Area;
import java.awt.geom.Path2D;
import java.awt.image.BufferedImage;
import java.util.List;

public class BufferedCanvas {

    private BufferedImage bufferedImage;

    public BufferedCanvas(int imageWidth, int imageHeight) {
        bufferedImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_ARGB);
    }

    public void paintAll(List<ShapeFeature> shapeFeatures) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setComposite(AlphaComposite.Clear);
        graphics.fillRect(0, 0, bufferedImage.getWidth(), bufferedImage.getHeight());
        graphics.dispose();
        shapeFeatures.forEach(shapeFeature ->
                drawShape(shapeFeature.getShape(), shapeFeature.getColor(), shapeFeature.getAngle()));
    }

    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    private void drawShape(Shape shape, Color color, double angle) {
        Graphics2D graphics = bufferedImage.createGraphics();
        graphics.setColor(color);
        Rectangle bounds = shape.getBounds();
        graphics.rotate(angle, bounds.x + bounds.width / 2.0, bounds.y + bounds.height / 2.0);
        graphics.fill(shape);
        graphics.dispose();
    }

    private Shape getIntersectionShape(ShapeFeature shapeFeature, Area changedArea) {
        Area intersection = new Area(shapeFeature.getShape());
        intersection.intersect(changedArea);
        intersection.getBounds();
        return intersection;
    }

}
