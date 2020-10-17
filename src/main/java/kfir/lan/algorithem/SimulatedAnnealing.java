package kfir.lan.algorithem;

import kfir.lan.Visualizer;
import kfir.lan.image.ImageComparator;
import kfir.lan.shapes.ShapeFeature;

import java.util.concurrent.ThreadLocalRandom;

public class SimulatedAnnealing {

    private final Visualizer visualizer;
    private final ImageComparator imageComparator;
    private double temperature;
    private final int maxWidth;
    private final int maxHeight;
    private double temperatureGeometricFactor;
    private double minEnergy;

    public SimulatedAnnealing(Visualizer visualizer,
                              ImageComparator imageComparator,
                              double temperature,
                              int maxWidth,
                              int maxHeight,
                              double temperatureGeometricFactor) {
        this.visualizer = visualizer;
        this.imageComparator = imageComparator;
        this.temperature = temperature;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
        this.temperatureGeometricFactor = temperatureGeometricFactor;
    }

    public void start(ShapeFeature[] state, int iteration) {
        double currentEnergy = getEnergy();
        minEnergy = currentEnergy;
        while(iteration > 0) {
            int changedShapeIndex = ThreadLocalRandom.current().nextInt(state.length);
            ShapeFeature currentShapeState = state[changedShapeIndex];
            ShapeFeature neighbourShape = currentShapeState.copy().mutate(maxWidth, maxHeight);
            visualizer.updateShape(neighbourShape, changedShapeIndex);
            double neighbourEnergy = getEnergy();
            if (changeProbability(currentEnergy, neighbourEnergy, iteration) >= ThreadLocalRandom.current().nextDouble()) {
                state[changedShapeIndex] = neighbourShape;
                currentEnergy = neighbourEnergy;
            } else {
                visualizer.updateShape(currentShapeState, changedShapeIndex);
            }
            minEnergy = Math.min(minEnergy, currentEnergy);
            iteration--;
            temperature *= temperatureGeometricFactor;
        }
        System.out.println("finished");
    }

    private double getEnergy() {
        return imageComparator.distance(visualizer.getImage());
    }

    private double changeProbability(double oldEnergy, double newEnergy, int iteration) {
        if (newEnergy < oldEnergy) {
            return 1;
        }
        return Math.exp((oldEnergy - newEnergy) / temperature);
    }

}
