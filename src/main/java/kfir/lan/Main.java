package kfir.lan;

import kfir.lan.algorithem.SimulatedAnnealing;
import kfir.lan.image.ImageComparator;
import kfir.lan.image.ImageSimpleComparator;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws IOException {
        BufferedImage origImage = ImageIO.read(new File("./car.jpg"));
        BufferedImage scaledDownImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = scaledDownImage.createGraphics();
        graphics.drawImage(origImage.getScaledInstance(50,50, Image.SCALE_DEFAULT), 0, 0, null);
        graphics.dispose();
        var scaledDownSolution = approxImage(scaledDownImage, null).stream()
                .map(shapeFeature -> shapeFeature.scale(origImage.getWidth() / 50.0,
                        origImage.getHeight() / 50.0))
                .collect(Collectors.toList());
        approxImage(origImage, scaledDownSolution);
    }

    private static List<ShapeFeature> approxImage(BufferedImage image, List<ShapeFeature> initialState) {
        var featureFactory = new FeatureFactory(FeatureFactory.FeatureType.Ellipse, image.getWidth(), image.getHeight());
        ImageComparator imageComparator = new ImageSimpleComparator(image);
        List<ShapeFeature> shapes;
        if (initialState != null) {
            shapes = initialState;
        } else {
            shapes = new ArrayList<>();
            for (int i=0; i < 1; ++i) {
                shapes.add(featureFactory.generateShape());
            }
        }
        var visualizer = new Visualizer(image.getWidth(),
                image.getHeight(),
                FeatureFactory.FeatureType.Ellipse,
                shapes,
                100);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(visualizer,
                imageComparator,
                initialState == null ? 50 : 5,
                image.getWidth(),
                image.getHeight(),
                0.995,
                featureFactory);
        var start = Instant.now();
        var res = simulatedAnnealing.start(shapes, 100_000, 100);
        System.out.println(Duration.between(start, Instant.now()).getSeconds());
        return res;
    }

}
