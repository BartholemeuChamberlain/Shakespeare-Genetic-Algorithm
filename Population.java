//Author: Relly Valentine
//Date Created: 05/30/19
//Date Completed: 06/01/19


package valentine;

import java.util.ArrayList;
import java.util.Random;

public class Population {

    Random rand = new Random();

    //characteristics
    float mutationRate;     // Mutation Rate
    DNA[] population;       // Array to hold the current population
    ArrayList<DNA> matingPool; // ArrayList which we use for the "mating pool"
    String target;          // Target phrase
    int generations;        // Number of Generations
    boolean finished;       // Are we finished?
    int perfectScore;


    Population(String p, float m, int num){
        target = p;
        mutationRate = m;
        population = new DNA[num];
        for(int i = 0; i < population.length; i++){
            population[i]  = new DNA(target.length());
        }
        calcFitness();
        matingPool = new ArrayList<DNA>();
        finished = false;
        generations = 0;

        perfectScore = 1;
    }


    //Fill our fitness array with a value for every member of the population
    void calcFitness() {
        for (int i = 0; i < population.length; i++) {
            population[i].fitness(target);
        }
    }

    //Generate a Mating Pool
    public void naturalSelection(){
        //Clear the ArrayList for the NEW generation of population
        matingPool.clear();


        float maxFitness = 0;
        //Every time the fitness increases in a generation, the max fitness will be replaced
        for(int i = 0; i < population.length; i++){
            if(population[i].fitness > maxFitness){
                maxFitness = population[i].fitness;
            }
        }

        //Based on fitness, each member will get added to the mating pool a certain number of times
        //A higher fitness = more entries to the mating pool = more likely to be picked as a parent
        //A lower fitness = fewer entries to the mating pool = less likely to be picked as a parent

        for(int i = 0; i < population.length; i++){

            float fitness = (float)(1 / (1+Math.exp(-this.population[i].fitness))); //Sigmoid function to make the fitness a number between 0 and 1

            float n = fitness*100;//This will be the number of times the parent will be placed in the mating pool

                for (int j = 0; j < n; j++) {
                    matingPool.add(population[i]);
                }

        }
    }

    //Create a new Generation
    public void generate(){
        //Refill the population with children from the mating pool
        for(int i = 0; i < population.length; i++){
            int a = rand.nextInt(matingPool.size());
            int b = rand.nextInt(matingPool.size());
            DNA partnerA = matingPool.get(a);
            DNA partnerB = matingPool.get(b);

            //Applying the crossover (Creating the "child")
            DNA child = partnerA.crossover(partnerB);
            child.mutate(mutationRate, target);
            population[i] = child;
        }
        generations++;
    }

    //Compute the "most fit" member of the population
    public String getBest(){
        float worldRecord = 0.0f;
        int index = 0;
        for(int i = 0; i < population.length; i++){
            if(population[i].fitness > worldRecord){
                index = i;
                worldRecord = population[i].fitness;
            }
        }
        if(worldRecord == perfectScore){
            finished = true;
        }
        return population[index].getPhrase();
    }

    //Getters
    public boolean getFinished(){
        return finished;
    }
    public int getGenerations(){
        return generations;
    }

    //Compute average fitness for the population
    float getAverageFitness() {
        float total = 0;
        for (int i = 0; i < population.length; i++) {
            total += population[i].fitness;
        }
        return total / (population.length);
    }

    public String allPhrases(){
        String everything = "";
        int displayLimit = Math.min(population.length, 50);

        for(int i = 0; i < displayLimit; i++){
            everything += population[i].getPhrase() + "\n";
        }
        return everything;
    }

}
