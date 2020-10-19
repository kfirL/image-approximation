package kfir.lan.algorithem;

import kfir.lan.Visualizer;
import kfir.lan.image.ImageComparator;
import kfir.lan.shapes.FeatureFactory;
import kfir.lan.shapes.ShapeFeature;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SimulatedAnnealing {

    private final Visualizer visualizer;
    private final ImageComparator imageComparator;
    private double temperature;
    private final int maxWidth;
    private final int maxHeight;
    private double temperatureGeometricFactor;
    private double minEnergy;
    private final FeatureFactory featureFactory;

    public SimulatedAnnealing(Visualizer visualizer,
                              ImageComparator imageComparator,
                              double temperature,
                              int maxWidth,
                              int maxHeight,
                              double temperatureGeometricFactor,
                              FeatureFactory featureFactory) {
        this.visualizer = visualizer;
        this.imageComparator = imageComparator;
        this.temperature = temperature;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.temperatureGeometricFactor = temperatureGeometricFactor;
        this.featureFactory = featureFactory;
    }

    public void start(List<ShapeFeature> state, int iteration, int maxShapes) {
        double currentEnergy = getEnergy();
        minEnergy = currentEnergy;
        while(iteration > 0) {
            double neighbourEnergy = Double.MAX_VALUE;
            if (isCreateNewShape(state.size(), maxShapes)) {
                int addedIndex = state.size();
                ShapeFeature neighbourShape = featureFactory.generateShape();
                visualizer.updateShape(neighbourShape, addedIndex);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy, neighbourEnergy  * 1.00005)) {
                    state.add(neighbourShape);
                    currentEnergy = neighbourEnergy;
                } else {
                    visualizer.removeShape(addedIndex);
                }
            } else if (isRemoveShape(state.size(), maxShapes)) {
                int changedShapeIndex = ThreadLocalRandom.current().nextInt(state.size());
                ShapeFeature currentShapeState = state.get(changedShapeIndex);
                visualizer.removeShape(changedShapeIndex);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy  * 1.00005, neighbourEnergy)) {
                    state.remove(changedShapeIndex);
                    currentEnergy = neighbourEnergy;
                } else {
                    visualizer.updateShape(currentShapeState, state.size() - 1);
                }
            } else {
                int changedShapeIndex = ThreadLocalRandom.current().nextInt(state.size());
                ShapeFeature currentShapeState = state.get(changedShapeIndex);
                ShapeFeature neighbourShape = currentShapeState.copy().mutate(maxWidth, maxHeight);
                visualizer.updateShape(neighbourShape, changedShapeIndex);
                neighbourEnergy = getEnergy();
                if (isAcceptNeighbour(currentEnergy, neighbourEnergy)) {
                    state.set(changedShapeIndex, neighbourShape);
                    currentEnergy = neighbourEnergy;
                } else {
                    visualizer.updateShape(currentShapeState, changedShapeIndex);
                }
            }

            minEnergy = Math.min(minEnergy, currentEnergy);
            iteration--;
            temperature *= temperatureGeometricFactor;
        }
        System.out.println("finished with energy: " + currentEnergy);
    }

    private double getEnergy() {
        return imageComparator.distance(visualizer.getImage());
    }

    private boolean isAcceptNeighbour(double oldEnergy, double newEnergy) {
        if (newEnergy < oldEnergy) {
            return true;
        }
        return Math.exp(1e2*(oldEnergy - newEnergy) / temperature)  >= ThreadLocalRandom.current().nextDouble();
    }

    private boolean isCreateNewShape(double shapesNumber, double maxShapesNumber) {
        if (shapesNumber == maxShapesNumber) {
            return false;
        }
        double addShapeProbability = 0.05 * (1 -(shapesNumber / maxShapesNumber));
        return addShapeProbability >= ThreadLocalRandom.current().nextDouble();
    }

    private boolean isRemoveShape(double shapesNumber, double maxShapesNumber) {
        if (shapesNumber <= 1) {
            return false;
        }
        double addShapeProbability = 0.05 * (shapesNumber / maxShapesNumber);
        return addShapeProbability >= ThreadLocalRandom.current().nextDouble();
    }

}
