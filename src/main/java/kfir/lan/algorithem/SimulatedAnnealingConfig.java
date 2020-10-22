package kfir.lan.algorithem;

import java.time.Duration;

public class SimulatedAnnealingConfig {
    private final double temperature;
    private final double temperatureGeometricFactor;
    private final double maxAddShapeProbability;
    private final double maxRemoveProbability;
    private final double extraShapePenalty;
    private final int maxShapes;
    private final double energyFactor;
    private final Duration runTime;

    private SimulatedAnnealingConfig(double temperature,
                                     double temperatureGeometricFactor,
                                     double maxAddShapeProbability,
                                     double maxRemoveProbability,
                                     double extraShapePenalty,
                                     int maxShapes,
                                     double energyFactor,
                                     Duration runTime) {
        this.temperature = temperature;
        this.temperatureGeometricFactor = temperatureGeometricFactor;
        this.maxAddShapeProbability = maxAddShapeProbability;
        this.maxRemoveProbability = maxRemoveProbability;
        this.extraShapePenalty = extraShapePenalty;
        this.maxShapes = maxShapes;
        this.energyFactor = energyFactor;
        this.runTime = runTime;
    }

    public double getTemperature() {
        return temperature;
    }

    public double getTemperatureGeometricFactor() {
        return temperatureGeometricFactor;
    }

    public double getMaxAddShapeProbability() {
        return maxAddShapeProbability;
    }

    public double getMaxRemoveProbability() {
        return maxRemoveProbability;
    }

    public double getExtraShapePenalty() {
        return extraShapePenalty;
    }

    public int getMaxShapes() {
        return maxShapes;
    }

    public Duration getRunTime() {
        return runTime;
    }

    public double getEnergyFactor() {
        return energyFactor;
    }

    public static SimulatedAnnealingConfigBuilder builder() {
        return new SimulatedAnnealingConfigBuilder();
    }

    public static class SimulatedAnnealingConfigBuilder {
        private double temperature = 50;
        private double temperatureGeometricFactor = 0.999_5;
        private double maxAddShapeProbability = 0.01;
        private double maxRemoveProbability = 0.01;
        private double extraShapePenalty = 1.000_5;
        private int maxShapes = 100;
        private double energyFactor = 1e2;
        private Duration runTime = Duration.ofMinutes(2);

        public SimulatedAnnealingConfigBuilder setTemperature(double temperature) {
            this.temperature = temperature;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setTemperatureGeometricFactor(double temperatureGeometricFactor) {
            this.temperatureGeometricFactor = temperatureGeometricFactor;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setMaxAddShapeProbability(double maxAddShapeProbability) {
            this.maxAddShapeProbability = maxAddShapeProbability;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setMaxRemoveProbability(double maxRemoveProbability) {
            this.maxRemoveProbability = maxRemoveProbability;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setExtraShapePenalty(double extraShapePenalty) {
            this.extraShapePenalty = extraShapePenalty;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setMaxShapes(int maxShapes) {
            this.maxShapes = maxShapes;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setRunTime(Duration runTime) {
            this.runTime = runTime;
            return this;
        }

        public SimulatedAnnealingConfigBuilder setEnergyFactor(double energyFactor) {
            this.energyFactor = energyFactor;
            return this;
        }

        public SimulatedAnnealingConfig build() {
            return new SimulatedAnnealingConfig(temperature,
                    temperatureGeometricFactor,
                    maxAddShapeProbability,
                    maxRemoveProbability,
                    extraShapePenalty,
                    maxShapes,
                    energyFactor,
                    runTime);
        }


    }
}
