package ga;

import queue.Product;

import java.util.*;

/**
 * p1 p1 p1 p1 p1
 * p2 p2 p2 p2 p2
 *
 * p1 p1 p1 p1 p1
 * p2 p2 p2 p2 p2
 **/
public class Population {
    private List<List<Product>> individuals = new ArrayList<List<Product>>();
    private Random rand = new Random();
    private int minFitness = Integer.MAX_VALUE;
    private FitnessFunction fitnessFunction;

    public void initialization(Map<String,List<List<Product>>> source, int populationSize){
        List<Product> firstCandidate = new ArrayList<Product>();
        List<List<Product>> secondCandidate = new ArrayList<List<Product>>();
        for(List<List<Product>> list :source.values()){
            int  n = rand.nextInt(list.size());
            firstCandidate.addAll(list.get(n));
            secondCandidate.add(list.get(n));
        }
        List<Product> secondParticle = generateSecondIndividu(secondCandidate);

        individuals.add(firstCandidate);
        individuals.add(secondParticle);

        for(int i=0;i<populationSize;i++){
            individuals.add(generateIndividu(source));
        }

        for(List<Product> l:individuals){
            for (Product p:l){
                System.out.println("new["+p.getName()+"]=>" + p.getCount());
            }
            System.out.println();
        }
    }

    public List<Product> getBestCandidate() {
        List<Product> bestCandidate = new ArrayList<Product>();
        // Loop through individuals to find fittest
        minFitness = Integer.MAX_VALUE;
        for (int i = 0; i < individuals.size(); i++) {
            int fitness = fitnessFunction.calculateFitness(individuals.get(i));
            if (fitness < minFitness) {
                minFitness = fitness;
                bestCandidate = individuals.get(i);
            }
        }
        return bestCandidate;
    }

    public int getMinFitness() {
        return minFitness;
    }

    public List<Product> generateIndividu(Map<String,List<List<Product>>> source){
        List<Product> candidate = new ArrayList<Product>();

        for(List<List<Product>> list :source.values()){
            int  n = rand.nextInt(list.size());
            candidate.addAll(list.get(n));
        }

        Collections.shuffle(candidate);

        return candidate;
    }

    private List<Product> generateSecondIndividu(List<List<Product>> secondCandidate){
        List<Product> particle = new ArrayList<Product>();
        int index = 0;
        boolean loop = true;
        while(loop) {
            int count = 0;
            for (List<Product> products : secondCandidate) {
                if(index<products.size()) {
                    Product p = products.get(index);

                    particle.add(p);
                    count++;
                }
            }
            if(count==0){
                loop = false;
            }
            index++;
        }

        return particle;
    }

    public void setIndividuals(List<List<Product>> individuals) {
        this.individuals = individuals;
    }

    public List<List<Product>> getIndividuals() {
        return individuals;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
