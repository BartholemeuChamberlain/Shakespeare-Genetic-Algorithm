//Author: Relly Valentine
//Date Created: 05/30/19
//Date Completed: 06/01/19

//DNA Class

package valentine;
import java.util.Random;

public class DNA {
    // The Nature of Code
// Daniel Shiffman
// http://natureofcode.com

// Genetic Algorithm, Evolving Shakespeare

// A class to describe a psuedo-DNA, i.e. genotype
//   Here, a virtual organism's DNA is an array of character.
//   Functionality:
//      -- convert DNA into a string
//      -- calculate DNA's "fitness"
//      -- mate DNA with another set of DNA
//      -- mutate DNA

    private Random rand = new Random();
    //the genetic sequence
    private char[] genes;
     float fitness;
    private String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz .,1234567890-+=[]{}':;/?!@#$%^&*() n";    // String that holds all the letters
    private int N = alphabet.length();

    //constructor - will make random DNA

    DNA(int num){
        genes = new char[num];
        for(int i = 0; i < genes.length; i++){
            //pick from a range of characters
            genes[i] = (char) alphabet.charAt(rand.nextInt(N));

        }
    }

    //Convert the char array to a String
    public String getPhrase(){
        return new String(genes);
    }

    //The Fitness function - will return a floating point % of "correct" characters
    public void fitness(String target){
        int score = 0;
        for(int i = 0; i < genes.length; i++){
            if(genes[i] == target.charAt(i)){
                score++;    //Every time a character from the random genes variable is correct, the score for that parent will increase
            }
        }

         fitness = (float)score / (float)target.length();    //this is the (1/27)^39 probability
    }


    //Crossover Method
    public DNA crossover(DNA partner){
        //A new child
        DNA child = new DNA(genes.length);

        int midpoint = rand.nextInt(genes.length); //Pick a midpoint

        //Half from one, Half from another
        for(int i = 0; i < genes.length; i++){
            if(i > midpoint){
                child.genes[i] = this.genes[i];
            }else{
                child.genes[i] = partner.genes[i];
            }
        }
        return child;
    }


    //Based on mutation probability, this method picks a new random character
    public void mutate(float mutationRate, String target){
        for(int i = 0; i < genes.length; i++){
            if(genes[i] == target.charAt(i)){
                genes[i] = target.charAt(i);
            }
            else if(rand.nextInt(1)< mutationRate){
                genes[i] = (char) alphabet.charAt(rand.nextInt(N));
            }
        }
    }

}
