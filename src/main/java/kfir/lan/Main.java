package kfir.lan;

import kfir.lan.algorithem.SimulatedAnnealing;
import kfir.lan.image.ImageComparator;
import kfir.lan.image.ImageSimpleComparator;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("./car.jpg"));
        ImageComparator imageComparator = new ImageSimpleComparator(image);
        var featureFactory = new FeatureFactory(FeatureFactory.FeatureType.Ellipse, image.getWidth(), image.getHeight());
        ShapeFeature[] shapes = new ShapeFeature[10];
        for (int i=0; i < shapes.length; ++i) {
            shapes[i] = featureFactory.generateShape();
        }
        var visualizer = new Visualizer(image.getWidth(), image.getHeight(), FeatureFactory.FeatureType.Ellipse, shapes);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(visualizer,
                imageComparator,
                50,
                image.getWidth(),
                image.getHeight(),
                0.000_005);
        simulatedAnnealing.start(shapes, 200_000);
    }

}
