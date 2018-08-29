package swarm;

import queue.Product;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class SwarmAlgorithms {

    public interface Evaluation{
        int calculateFitness(List<Product> products);
        void minimum(int min, List<Product> products);

    }

    private Evaluation evaluation;
    private List<List<Product>> population = new ArrayList<List<Product>>();

    public void initialization(Map<String,List<List<Product>>> source){
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
    }

    private List<Product> generateSecondParticle(List<List<Product>> secondCandidate){
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

    public Evaluation getEvaluation() {
        return evaluation;
    }

    public void setEvaluation(Evaluation evaluation) {
        this.evaluation = evaluation;
    }
}
