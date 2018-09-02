package ga;

import queue.Product;

import java.util.*;

public class GeneticAlgorithms {

    /* GA parameters */
    private static final double uniformRate = 0.5;
    private static final double mutationRate = 0.015;
    private static final int tournamentSize = 5;
    private static final boolean elitism = true;
    private List<Product> bestCandidate = new ArrayList<Product>();

    private FitnessFunction fitnessFunction;
    private List<List<Product>> population = new ArrayList<List<Product>>();

    public void init(Map<String,List<List<Product>>> source,int minThreshold){
        Population myPop = new Population();
        myPop.initialization(source,20);
        myPop.setFitnessFunction(fitnessFunction);

        // Evolve our population until we reach an optimum solution
        int generationCount = 0;
        int localMin = Integer.MAX_VALUE;
        List<Product> candidate = myPop.getIndividuals().get(0);
        while (minThreshold < localMin && generationCount<10) {
            candidate = myPop.getBestCandidate();
            localMin = myPop.getMinFitness();
            generationCount++;
            fitnessFunction.bestFitness(generationCount, localMin);
            System.out.println("Generation: " + generationCount + " Fittest: " + localMin);
            if(localMin < minThreshold){
                break;
            }
            myPop = evolvePopulation(myPop);
        }
        System.out.println("Solution found!");
        System.out.println("Generation: " + generationCount);
        System.out.println("Genes:" + candidate);
        System.out.println("Best Fitness:" + localMin);

        fitnessFunction.minimum(localMin, candidate);

        /*
        Random rand = new Random();
        List<Product> firstParticle = new ArrayList<Product>();
        List<List<Product>> secondCandidate = new ArrayList<List<Product>>();
        for(List<List<Product>> list :source.values()){
            int  n = rand.nextInt(list.size());
            firstParticle.addAll(list.get(n));
            secondCandidate.add(list.get(n));
        }
        List<Product> secondParticle = generateSecondParticle(secondCandidate);

        population.add(firstParticle);
        population.add(secondParticle);
        */
        /*
        int min = Integer.MAX_VALUE;
        List<Product> finalParticle = new ArrayList<Product>();
        for(List<Product> products:population){
            int fitness = evaluation.calculateFitness(products);
            if(fitness<min){
                min = fitness;
                finalParticle = products;
            }
        }
        evaluation.minimum(min, finalParticle);
        */
    }

    public Population evolvePopulation(Population pop){
        Population newPopulation = new Population();
        newPopulation.setFitnessFunction(fitnessFunction);

        // Keep our best individual
        if (elitism) {
            bestCandidate = newPopulation.getBestCandidate();
            //newPopulation.saveIndividual(0, pop.getBestCandidate());
        }

        // Crossover population
        int elitismOffset;
        if (elitism) {
            elitismOffset = 1;
        } else {
            elitismOffset = 0;
        }
        // Loop over the population size and create new individuals with
        // crossover

        for (int i = 0; i < pop.getIndividuals().size(); i++) {
            List<Product> indiv1 = tournamentSelection(pop.getIndividuals());
            List<Product> indiv2 = tournamentSelection(pop.getIndividuals());
            List<Product> newIndiv = crossover(indiv1, indiv2);
            newPopulation.getIndividuals().add(newIndiv);
        }

        // Mutate population
        for (int i = elitismOffset; i < newPopulation.getIndividuals().size(); i++) {
            mutate(newPopulation.getIndividuals().get(i));
        }

        return newPopulation;
    }

    public List<Product> mutate(List<Product> candidate){
        Collections.shuffle(candidate);
        return candidate;
    }

    public List<Product> crossover(List<Product> indv1,List<Product> indv2){
        System.out.println("Cross Over");
        List<Product> newIndv = new ArrayList<Product>();
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        for(Product p:indv1){
            System.out.println("old[" + p.getName() + "]=>" + p.getCount());
            List<Product> nP = map.get(p.getName());
            if(nP==null){
                nP = new ArrayList<Product>();
                map.put(p.getName(),nP);
            }
            if(p.getName().equals("p1")){
                newIndv.add(p);
            }
            nP.add(p);
        }

        for(Product p:indv2){
            if(p.getName().equals("p2")){
                //List<Product> pl = map.get("p1");
                newIndv.add(p);
            }
        }

        //newIndv.add(new ArrayList<Product>(map.get("p1")));
        //newIndv.add(new ArrayList<Product>(map.get("p2")));

        for(Product p:map.get("p2")){
            System.out.println("p2=" + p.getCount());
        }

        /*
        int index = 0;
        Iterator<Product> iterator = indv2.iterator();
        while(iterator.hasNext()){
            Product p = iterator.next();
            if(p.getName().equals("p2")){
                List<Product> pl = map.get("p2");
                if(index<pl.size()) {
                    p.setCount(pl.get(index).getCount());
                    index++;
                }else{
                    iterator.remove();
                }
            }
        }
        List<Product> pl = map.get("p2");
        index++;
        while(index<pl.size()){
            indv2.add(pl.get(index));
            index++;
        }
        */

        //newIndv.get(0).addAll(map.get("p2"));
        //newIndv.get(1).addAll(map.get("p1"));

        //return indv2;
        return newIndv;
    }

    // Select individuals for crossover
    private List<Product> tournamentSelection(List<List<Product>> pop) {
        int tournamentSize = 5;
        // Create a tournament population
        List<List<Product>> tournament = new ArrayList<List<Product>>();
        // For each place in the tournament get a random individual
        for (int i = 0; i < tournamentSize; i++) {
            int randomId = (int) (Math.random() * pop.size());
            tournament.add(i, pop.get(randomId));
        }
        // Get the fittest
        List<Product> indv = tournament.get(0);
        int min = Integer.MAX_VALUE;
        for(List<Product> pl:tournament){
            int fitness = fitnessFunction.calculateFitness(pl);
            if(fitness<min){
                min = fitness;
                indv = pl;
            }
        }
        return indv;
    }

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }
}
