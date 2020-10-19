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
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.TemporalField;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage image = ImageIO.read(new File("./car.jpg"));
        ImageComparator imageComparator = new ImageSimpleComparator(image);
        var featureFactory = new FeatureFactory(FeatureFactory.FeatureType.Ellipse, image.getWidth(), image.getHeight());
        List<ShapeFeature> shapes = new ArrayList<>();
        for (int i=0; i < 1; ++i) {
            shapes.add(featureFactory.generateShape());
        }
        var visualizer = new Visualizer(image.getWidth(),
                image.getHeight(),
                FeatureFactory.FeatureType.Ellipse,
                shapes,
                100);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(visualizer,
                imageComparator,
                50,
                image.getWidth(),
                image.getHeight(),
                0.000_005,
                featureFactory);
        var start = Instant.now();
        simulatedAnnealing.start(shapes, 100_000, 100);
        System.out.println(Duration.between(start, Instant.now()).getSeconds());
    }

}
