package swarm;

public class Particle {
    private long[] position;
    private long[] speed;
    private double fitness;
    private long[] bestPosition;
    private double bestFitness = Double.NEGATIVE_INFINITY;

    public Particle(long[] initialPosition, long[] initialSpeed) {
        this.position = initialPosition;
        this.speed = initialSpeed;
    }

    public long[] getPosition() {
        return position;
    }

    public void setPosition(long[] position) {
        this.position = position;
    }

    public long[] getSpeed() {
        return speed;
    }

    public void setSpeed(long[] speed) {
        this.speed = speed;
    }

    public double getFitness() {
        return fitness;
    }

    public void setFitness(double fitness) {
        this.fitness = fitness;
    }

    public long[] getBestPosition() {
        return bestPosition;
    }

    public void setBestPosition(long[] bestPosition) {
        this.bestPosition = bestPosition;
    }

    public double getBestFitness() {
        return bestFitness;
    }

    public void setBestFitness(double bestFitness) {
        this.bestFitness = bestFitness;
    }
}
