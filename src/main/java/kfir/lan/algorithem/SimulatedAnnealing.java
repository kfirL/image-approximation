package kfir.lan.algorithem;

import kfir.lan.image.Visualizer;
import kfir.lan.image.BufferedCanvas;
import kfir.lan.heuristic.ImageComparator;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public class SimulatedAnnealing {

    private final Visualizer visualizer;
    private final BufferedCanvas bufferedCanvas;
    private final ImageComparator imageComparator;
    private final int maxWidth;
    private final int maxHeight;
    private final FeatureFactory featureFactory;

    public SimulatedAnnealing(BufferedImage image,
                              ImageComparator imageComparator,
                              int maxWidth,
                              int maxHeight,
                              FeatureFactory featureFactory) {
        this.visualizer = new Visualizer(image.getWidth(), image.getHeight());
        this.bufferedCanvas = new BufferedCanvas(image.getWidth(), image.getHeight());
        this.imageComparator = imageComparator;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.featureFactory = featureFactory;
    }

    public List<ShapeFeature> start(List<ShapeFeature> state, SimulatedAnnealingConfig config) {
        bufferedCanvas.paintAll(state);
        int iteration = 0;
        double currentEnergy = getEnergy();
        var minEnergy = currentEnergy;
        var bestState = new ArrayList<>(state);
        var temperature = config.getTemperature();
        var scheduledThreadPool = Executors.newScheduledThreadPool(1);
        var isTimeLeft = new AtomicBoolean(true);
        scheduledThreadPool.schedule(() -> isTimeLeft.set(false),
                config.getRunTime().getSeconds(), TimeUnit.SECONDS);
        while (isTimeLeft.get()) {
            double neighbourEnergy;
            if (isCreateNewShape(state.size(), config.getMaxShapes(),
                    config.getMaxAddShapeProbability(), currentEnergy)) {
                int addedIndex = state.size();
                ShapeFeature neighbourShape = featureFactory.generateShape();
                state.add(neighbourShape);
                bufferedCanvas.paintAll(state);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy, neighbourEnergy * config.getExtraShapePenalty(),
                        temperature, config.getEnergyFactor())) {
                    visualizer.showImage(bufferedCanvas.getBufferedImage());
                    currentEnergy = neighbourEnergy;
                } else {
                    state.remove(addedIndex);
                    bufferedCanvas.paintAll(state);
                }
            } else if (isRemoveShape(state.size(), config.getMaxShapes(), config.getMaxRemoveProbability(),
                    currentEnergy)) {
                int removedShapeIndex = ThreadLocalRandom.current().nextInt(state.size());
                ShapeFeature removedShape = state.get(removedShapeIndex);
                state.remove(removedShapeIndex);
                bufferedCanvas.paintAll(state);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy * config.getExtraShapePenalty(),
                        neighbourEnergy, temperature, config.getEnergyFactor())) {
                    visualizer.showImage(bufferedCanvas.getBufferedImage());
                    currentEnergy = neighbourEnergy;
                } else {
                    state.add(removedShapeIndex, removedShape);
                    bufferedCanvas.paintAll(state);
                }
            } else {
                int changedShapeIndex = ThreadLocalRandom.current().nextInt(state.size());
                ShapeFeature currentShapeState = state.get(changedShapeIndex);
                ShapeFeature neighbourShape = currentShapeState.copy().mutate(maxWidth, maxHeight);
                state.set(changedShapeIndex, neighbourShape);
                bufferedCanvas.paintAll(state);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy, neighbourEnergy, temperature, config.getEnergyFactor())) {
                    visualizer.showImage(bufferedCanvas.getBufferedImage());
                    currentEnergy = neighbourEnergy;
                } else {
                    state.set(changedShapeIndex, currentShapeState);
                    bufferedCanvas.paintAll(state);
                }
            }
            if (currentEnergy < minEnergy) {
                minEnergy = currentEnergy;
                bestState = new ArrayList<>(state);
            }
            iteration++;
            temperature *= config.getTemperatureGeometricFactor();
        }
        System.out.println("finished with energy: " + minEnergy + " after " + iteration + " iterations");
        scheduledThreadPool.shutdown();
        visualizer.showImage(bufferedCanvas.getBufferedImage());
        return bestState;
    }

    private double getEnergy() {
        return imageComparator.distance(bufferedCanvas.getBufferedImage());
    }

    private boolean isAcceptNeighbour(double oldEnergy, double newEnergy, double temperature, double energyFactor) {
        if (newEnergy < oldEnergy) {
            return true;
        }
        return Math.exp(energyFactor * (oldEnergy - newEnergy) / temperature) >=
                ThreadLocalRandom.current().nextDouble();
    }

    private boolean isCreateNewShape(double shapesNumber,
                                     double maxShapesNumber,
                                     double maxAddShapeProbability,
                                     double energy) {
        if (shapesNumber == maxShapesNumber) {
            return false;
        }
        double addShapeProbability = Math.min(energy, maxAddShapeProbability) *
                (1 - (shapesNumber / maxShapesNumber));
        return addShapeProbability >= ThreadLocalRandom.current().nextDouble();
    }

    private boolean isRemoveShape(double shapesNumber,
                                  double maxShapesNumber,
                                  double maxRemoveShapeProbability,
                                  double energy) {
        if (shapesNumber <= 1) {
            return false;
        }
        double removeShapeProbability = Math.min(energy, maxRemoveShapeProbability) *
                (shapesNumber / maxShapesNumber);
        return removeShapeProbability >= ThreadLocalRandom.current().nextDouble();
    }

}
