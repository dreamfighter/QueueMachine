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
    }

    public List<Product> mutate(List<Product> candidate){
        Collections.shuffle(candidate);;
        return candidate;
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

    public List<Product> crossover(List<Product> indv1,List<Product> indv2){
        //List<List<Product>> newIndv = new ArrayList<List<Product>>();
        Map<String, List<Product>> map = new HashMap<String, List<Product>>();
        for(Product p:indv1){
            List<Product> nP = map.get(p.getName());
            if(nP==null){
                nP = new ArrayList<Product>();
                map.put(p.getName(),nP);
            }
            nP.add(p);
        }

        //newIndv.add(new ArrayList<Product>(map.get("p1")));
        //newIndv.add(new ArrayList<Product>(map.get("p2")));

        int index = 0;
        Iterator<Product> iterator = indv2.iterator();
        while(iterator.hasNext()){
            Product p = iterator.next();
            if(p.getName().equals("p2")){
                List<Product> pl = map.get("p2");
                if(index<pl.size()) {
                    p.setNo(pl.get(index).getNo());

                }else{
                    iterator.remove();
                }
                index++;
            }
        }

        //newIndv.get(0).addAll(map.get("p2"));
        //newIndv.get(1).addAll(map.get("p1"));

        return indv2;
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

    public void setFitnessFunction(FitnessFunction fitnessFunction) {
        this.fitnessFunction = fitnessFunction;
    }

    public void setIndividuals(List<List<Product>> individuals) {
        this.individuals = individuals;
    }

    public List<List<Product>> getIndividuals() {
        return individuals;
    }
}
