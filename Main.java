//Author: Relly Valentine
//Date Created: 05/30/19
//Date Completed: 06/01/19


package valentine;

// Genetic Algorithm, Evolving Shakespeare

// Demonstration of using a genetic algorithm to perform a search

// setup()
//  # Step 1: The Population
//    # Create an empty population (an array or ArrayList)
//    # Fill it with DNA encoded objects (pick random values to start)

// draw()
//  # Step 1: Selection
//    # Create an empty mating pool (an empty ArrayList)
//    # For every member of the population, evaluate its fitness based on some criteria / function,
//      and add it to the mating pool in a manner consistant with its fitness, i.e. the more fit it
//      is the more times it appears in the mating pool, in order to be more likely picked for reproduction.

//  # Step 2: Reproduction Create a new empty population
//    # Fill the new population by executing the following steps:
//       1. Pick two "parent" objects from the mating pool.
//       2. Crossover -- create a "child" object by mating these two parents.
//       3. Mutation -- mutate the child's DNA based on a given probability.
//       4. Add the child object to the new population.
//    # Replace the old population with the new population
//
//   # Rinse and repeat


public class Main{
    static String target;
    static int popmax;
    static float mutationRate;
    static Population population;

    public static void main(String[] args) {
        target = "to be or not to be.";
        popmax = 150;
        mutationRate = 0.01f;
        boolean finished;

        //Create a population with a target phrase, mutation rate, and population max
        population  = new Population(target, mutationRate, popmax);
        finished = population.getFinished();
        while(!finished){
            draw(population);
            if(population.getBest().equals(target)){
                System.out.println("The final answer is: "+population.getBest());
                finished = true;
            }
        }

    }

    private static void draw(Population p){

        //Generate a mating pool
        p.naturalSelection();

        //Create next generation
        p.generate();

        //Calculate fitness
        p.calcFitness();

        displayInfo();

    }

    private static void displayInfo(){
        //Display Current Status of population
        String answer = population.getBest();

        System.out.print("\t \t Best Phrase: "+answer);
        System.out.println();
        System.out.println();

        System.out.println("Total Generations: "+population.getGenerations()+"\n");
        System.out.println("Average Fitness: "+(population.getAverageFitness()*100)+"% \n");
        System.out.println("Total Population: "+popmax+"\n");
        System.out.println("Mutation Rate: "+(mutationRate*100)+"% \n");
        System.out.println();
        System.out.println("\t \t \t"+"All Phrases: \n"+population.allPhrases());

    }

}
