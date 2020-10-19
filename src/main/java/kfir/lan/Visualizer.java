package kfir.lan;

import kfir.lan.shapes.ShapeComponent;
import kfir.lan.shapes.EllipseFeature;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import static java.awt.image.BufferedImage.TYPE_INT_ARGB;
import static java.awt.image.BufferedImage.TYPE_INT_RGB;

public class Visualizer {

    private final int maxWidth;
    private final int maxHeight;
    private final FeatureFactory.FeatureType featureType;
    private JFrame window = new JFrame();
    private List<ShapeComponent> shapeComponents;
    private BufferedImage image;

    public Visualizer(int maxWidth, int maxHeight,
                      FeatureFactory.FeatureType featureType,
                      List<ShapeFeature> shapesFeatures,
                      int maxShapes) {
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.featureType = featureType;
        window.setLayout(new OverlayLayout(window.getContentPane()));
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.getContentPane().setPreferredSize(new Dimension(maxWidth, maxHeight));
        window.pack();
        window.setLocationRelativeTo(null);
        shapeComponents = new ArrayList<>(maxShapes);
        for (ShapeFeature shapesFeature : shapesFeatures) {
            ShapeComponent shapeComponent = featureToComponent(shapesFeature);
            shapeComponents.add(shapeComponent);
            window.getContentPane().add(shapeComponent);
        }
        for (int i=shapesFeatures.size(); i < maxShapes; ++i) {
            ShapeComponent shapeComponent = new ShapeComponent();
            shapeComponents.add(shapeComponent);
            window.getContentPane().add(shapeComponent);
        }
        image = new BufferedImage(maxWidth, maxHeight, TYPE_INT_RGB);
        window.setVisible(true);
    }

    public BufferedImage getImage() {
        image.getGraphics().clearRect(0,0,maxWidth, maxHeight);
        window.getContentPane().print(image.getGraphics());
        return image;
    }

    public void updateShape(ShapeFeature shapeFeature, int index) {
        var color = getColorFromFeature(shapeFeature);
        var shape = getShapeFromFeature(shapeFeature);
        var angle = shapeFeature.getAngle();
        var shapeComponent = shapeComponents.get(index);
        shapeComponent.setColor(color);
        shapeComponent.setShape(shape);
        shapeComponent.setAngle(angle);
        shapeComponent.repaint();
    }

    public void removeShape(int index) {
        var shapeComponent = shapeComponents.get(index);
        shapeComponent.setShape(null);
        shapeComponent.setColor(null);
        shapeComponents.remove(index);
        shapeComponents.add(shapeComponent);
        shapeComponent.repaint();
    }

    private ShapeComponent featureToComponent(ShapeFeature shapeFeature) {
        if (shapeFeature == null) {
            return new ShapeComponent();
        }
        var color = getColorFromFeature(shapeFeature);
        var shape = getShapeFromFeature(shapeFeature);
        return new ShapeComponent(shape, color, shapeFeature.getAngle());
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
