package ga;

import queue.Product;

import java.util.List;

public interface FitnessFunction {
    /**
     * Returns the fitness of a particle given its position.
     *
     * @param products
     *            the position of the particle
     * @return the fitness of the particle
     */
    int calculateFitness(List<Product> products);
    void minimum(int min, List<Product> products);
    void bestFitness(int seq, int min);
}
