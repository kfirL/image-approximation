package kfir.lan;

import kfir.lan.algorithem.SimulatedAnnealing;
import kfir.lan.algorithem.SimulatedAnnealingConfig;
import kfir.lan.heuristic.ImageComparator;
import kfir.lan.heuristic.ImageSimpleComparator;
import kfir.lan.image.BufferedCanvas;
import kfir.lan.image.Visualizer;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class SimulatedAnnealingApproximator implements ImageApproximator {

    @Override
    public List<ShapeFeature> approximate(BufferedImage image, FeatureFactory.FeatureType featureType) {
        var scaledDownX = Math.min(50, image.getWidth());
        var scaledDownY = Math.min(50, image.getHeight());
        BufferedImage scaledDownImage = new BufferedImage(scaledDownX, scaledDownY, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = scaledDownImage.createGraphics();
        graphics.drawImage(image.getScaledInstance(scaledDownX, scaledDownY, Image.SCALE_DEFAULT), 0, 0, null);
        graphics.dispose();
        var scaledDownFeatureFactory = new FeatureFactory(featureType, scaledDownImage.getWidth(), scaledDownImage.getHeight());
        List<ShapeFeature> initialState = new ArrayList<>();
        initialState.add(scaledDownFeatureFactory.generateShape());
        var scaledDownConfig = SimulatedAnnealingConfig.builder()
                .setRunTime(Duration.ofMinutes(3))
                .build();
        var scaledUpApproximation = approxImage(scaledDownImage, scaledDownFeatureFactory,
                initialState, scaledDownConfig).stream()
                .map(shapeFeature -> shapeFeature.scale(image.getWidth() / ((double) scaledDownX),
                        image.getHeight() / ((double) scaledDownY)))
                .collect(Collectors.toList());
        var featureFactory = new FeatureFactory(FeatureFactory.FeatureType.Ellipse,
                image.getWidth(), image.getHeight());
        var secondRunConfig = SimulatedAnnealingConfig.builder()
                .setTemperature(0.01)
                .setRunTime(Duration.ofMinutes(2))
                .build();
        return approxImage(image, featureFactory, scaledUpApproximation, secondRunConfig);
    }

    private List<ShapeFeature> approxImage(BufferedImage image,
                                           FeatureFactory featureFactory,
                                           List<ShapeFeature> initialState,
                                           SimulatedAnnealingConfig config) {
        ImageComparator imageComparator = new ImageSimpleComparator(image);
        SimulatedAnnealing simulatedAnnealing = new SimulatedAnnealing(image,
                imageComparator,
                image.getWidth(),
                image.getHeight(),
                featureFactory);
        return simulatedAnnealing.start(initialState, config);
    }
}
