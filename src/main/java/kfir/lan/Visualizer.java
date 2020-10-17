package kfir.lan;

import kfir.lan.shapes.ColoredShape;
import kfir.lan.shapes.EllipseFeature;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;

public class Visualizer {

    private final int maxWidth;
    private final int maxHeight;
    private final FeatureFactory.FeatureType featureType;
    private JFrame window = new JFrame();
    private ColoredShape[] coloredShapes;

    public Visualizer(int maxWidth, int maxHeight,
                      FeatureFactory.FeatureType featureType,
                      ShapeFeature... features) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.featureType = featureType;
        window.setLayout(new OverlayLayout(window.getContentPane()));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setPreferredSize(new Dimension(maxWidth, maxHeight));
        window.pack();
        window.setLocationRelativeTo(null);
        coloredShapes = new ColoredShape[features.length];
        for (int i = 0; i < coloredShapes.length; ++i) {
            coloredShapes[i] = featureToComponent(features[i]);
            window.getContentPane().add(coloredShapes[i]);
        }
        window.setVisible(true);
    }

    public BufferedImage getImage() {
        BufferedImage img = new BufferedImage(maxWidth, maxHeight, BufferedImage.TYPE_INT_ARGB);
        window.getContentPane().paintAll(img.createGraphics());
        return img;
    }

    public void updateShape(ShapeFeature shapeFeature, int index) {
        var color = getColorFromFeature(shapeFeature);
        var shape = getShapeFromFeature(shapeFeature);
        var angle = shapeFeature.getAngle();
        var coloredShape = coloredShapes[index];
        coloredShape.setColor(color);
        coloredShape.setShape(shape);
        coloredShape.setAngle(angle);
        coloredShape.repaint();
    }

    private ColoredShape featureToComponent(ShapeFeature shapeFeature) {
        var color = getColorFromFeature(shapeFeature);
        var shape = getShapeFromFeature(shapeFeature);
        return new ColoredShape(shape, color, shapeFeature.getAngle());
    }

    private Shape getShapeFromFeature(ShapeFeature shapeFeature) {
        if (featureType == FeatureFactory.FeatureType.Ellipse) {
            var ellipseFeature = (EllipseFeature) shapeFeature;
            return new Ellipse2D.Double(ellipseFeature.getX(),
                    ellipseFeature.getY(),
                    ellipseFeature.getWidth(),
                    ellipseFeature.getHeight());
        }
        return null;
    }

    private Color getColorFromFeature(ShapeFeature shapeFeature) {
        return new Color(shapeFeature.getRed(),
                shapeFeature.getGreen(),
                shapeFeature.getBlue(),
                shapeFeature.getAlpha());
    }

}
