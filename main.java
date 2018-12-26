package A1;

import A1.Chaining.*;
import A1.Open_Addressing.*;
import java.io.*;
import java.util.*;

public class main {

    /**
     * Calculate 2^w
     */
    public static int power2(int w) {
        return (int) Math.pow(2, w);
    }

    /**
     * Uniformly generate a random integer between min and max, excluding both
     */
    public static int generateRandom(int min, int max, int seed) {
        Random generator = new Random();
        //if the seed is equal or above 0, we use the input seed, otherwise not.
        if (seed >= 0) {
            generator.setSeed(seed);
        }
        int i = generator.nextInt(max - min - 1);
        return i + min + 1;
    }

    /**
     * export CSV file
     */
    public static void generateCSVOutputFile(String filePathName, ArrayList<Double> alphaList, ArrayList<Double> avColListChain, ArrayList<Double> avColListProbe) {
        File file = new File(filePathName);
        FileWriter fw;
        try {
            fw = new FileWriter(file);
            int len = alphaList.size();
            fw.append("Alpha");
            for (int i = 0; i < len; i++) {
                fw.append("," + alphaList.get(i));
            }
            fw.append('\n');
            fw.append("Chain");
            for (int i = 0; i < len; i++) {
                fw.append("," + avColListChain.get(i));
            }
            fw.append('\n');
            fw.append("Open Addressing");
            for (int i = 0; i < len; i++) {
                fw.append(", " + avColListProbe.get(i));
            }
            fw.append('\n');
            fw.flush();
            fw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {

        /*===========PART 1 : Experimenting with n===================*/
        //Initializing the three arraylists that will go into the output 
        ArrayList<Double> alphaList = new ArrayList<Double>();
        ArrayList<Double> avColListChain = new ArrayList<Double>();
        ArrayList<Double> avColListProbe = new ArrayList<Double>();

        //Keys to insert into both hash tables
        int[] keysToInsert = {164, 127, 481, 132, 467, 160, 205, 186, 107, 179,
            955, 533, 858, 906, 207, 810, 110, 159, 484, 62, 387, 436, 761, 507,
            832, 881, 181, 784, 84, 133, 458, 36};

        //values of n to test for in the experiment
        int[] nList = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 22, 24, 26, 28, 30, 32};
        //value of w to use for the experiment on n
        int w = 10;

            //initializing two hash tables with a seed
            Chaining MyChainTable = new Chaining(w, 137);
            Open_Addressing MyProbeTable = new Open_Addressing(w, 137);

            /*Use the hash tables to compute the average number of 
                        collisions over keys keysToInsert, for each value of n. 
                        The format of the three arraylists to fillis as follows:
                        
                        alphaList = arraylist of all tested alphas 
                                   (corresponding to each tested n)
                        avColListChain = average number of collisions for each
                                         Chain experiment 
                                         (make sure the order matches alphaList)
                        avColListProbe =  average number of collisions for each
                                         Linear Probe experiment
                                           (make sure the order matches)
                        The CSV file will output the result which you can visualize
             */
            //ADD YOUR CODE HERE
            int chosenN; //index through nList
            double alpha; //alphas of chaining/probe table
            double collisionsChain; //stores collisions of chain table
            double collisionsProbe; //stores collisions of probe table
            for (int n = 0; n < nList.length; n++){ //indexing through nList
              chosenN = nList[n]; //stores n value
              double chosenND = (double) chosenN; // int -> double n
              double m = (double) MyChainTable.m; // stores number of slots; int -> double
              alpha = chosenND/m; // creates alpha n/m
              alphaList.add(alpha); //adds alpha to alphalist
              for (int test = 0; test < chosenN-1;test++) { //cycles through keysToInsert list 0 to n-1
                collisionsChain = (double) MyChainTable.insertKey(keysToInsert[test]); //calculates collisions of chain table
                collisionsProbe = (double) MyProbeTable.insertKey(keysToInsert[test]); //calculates collisions of probe table
                avColListChain.add(collisionsChain); //adds collisionsChain to collision chain list
                avColListProbe.add(collisionsProbe); //adds collisionsProbe to collision probe list
              }
            }
            
        generateCSVOutputFile("n_comparison.csv", alphaList, avColListChain, avColListProbe);

        /*===========    PART 2 : Test removeKey  ===================*/
 /* In this exercise, you apply your removeKey method on an example.
        Make sure you use the same seed, 137, as you did in part 1. You will
        be penalized if you don't use the same seed.
         */
        //Please not the output CSV will be slightly wrong; ignore the labels.
        ArrayList<Double> removeCollisions = new ArrayList<Double>();
        ArrayList<Double> removeIndex = new ArrayList<Double>();
        int[] keysToRemove = {6, 8, 164, 180, 127, 3, 481, 132, 4, 467, 5, 160,
            205, 186, 107, 179};

        //ADD YOUR CODE HERE
        Open_Addressing removeProbe = new Open_Addressing(w, 137); //creates new probe table
        for(int i = 0;i<16;i++){ //from 0 to 15
          removeProbe.insertKey(keysToInsert[i]); //inserts 16 keys
        }
        for(int index = 0;index<16;index++){ // 0 to 15
          double indexD = (double) index; //stores the index as a double
          removeIndex.add(indexD); //adds the index to  the removeIndex list
          double colRemove = (double) removeProbe.removeKey(keysToRemove[index]); //calculates collisions in removeKey
          removeCollisions.add(colRemove); //stores collisions
        }
        
        generateCSVOutputFile("remove_collisions.csv", removeIndex, removeCollisions, removeCollisions);

        /*===========PART 3 : Experimenting with w===================*/

 /*In this exercise, the hash tables are random with no seed. You choose 
                values for the constant, then vary w and observe your results.
         */
        //generating random hash tables with no seed can be done by sending -1
        //as the seed. You can read the generateRandom method for detail.
        //Lists to fill for the output CSV, exactly the same as in Task 1.
        ArrayList<Double> alphaList2 = new ArrayList<Double>();
        ArrayList<Double> avColListChain2 = new ArrayList<Double>();
        ArrayList<Double> avColListProbe2 = new ArrayList<Double>();

        //ADD YOUR CODE HERE
        int randomNumber; //stores the generated random numbers
        ArrayList<Integer> ranKeys = new ArrayList<Integer>(); //creates the arraylist to hold the randomized keys
        for (int j = 0;j<10;j++){ //cycles through 10 times
          randomNumber = generateRandom(0,55,-1); //generates a random number
          while (ranKeys.contains(randomNumber)){ //if the arraylist already contains this random number
            randomNumber = generateRandom(0,55,-1); //generate a new random number
          }
          ranKeys.add(randomNumber); //add the random number to the key list
        }
        int[] wList = {4, 8, 12, 16, 20}; //creates list of w variables
        double wChainCols; //variable to store values for chain collisions
        double wProbeCols; //variable to store values for probe collisions
        double wAlpha; //variable to store values for alpha
        double chainCol = 0; //stores chain collisions for average
        double probeCol = 0; //stores probe collisions for average
        for(int j = 0; j<wList.length;j++){ //cycles through wlist
          Chaining MyChainTablew = new Chaining(wList[j], -1); //creates new chain table with w variable
          Open_Addressing MyProbeTablew = new Open_Addressing(wList[j], -1); //creates new probe table with w variable
          double m = (double) MyChainTablew.m; // stores number of slots; int -> double
          wAlpha = 10.0/m; // creates alpha n/m
          alphaList2.add(wAlpha); //adds alpha to alphalist2
          for(int l = 0; l < 10;l++){ //cycles through ranKeys
            wChainCols = (double) MyChainTablew.insertKey(ranKeys.get(l)); //calculates collisions of chain table
            wProbeCols = (double) MyProbeTablew.insertKey(ranKeys.get(l)); //calculates collisions of probe table
            chainCol = chainCol+wChainCols; //sums all chain collisions under value w
            probeCol = probeCol+wProbeCols; //sums all probe collisions under value w
          }
          chainCol = chainCol/10.0; //average collision = collisions/# of keys
          probeCol = probeCol/10.0; //average collision = collisions/# of keys
          avColListChain2.add(chainCol); //adds wChainCols to collision chain list
          avColListProbe2.add(probeCol); //adds wProbeCols to collision probe list
        }
        
        generateCSVOutputFile("w_comparison.csv", alphaList2, avColListChain2, avColListProbe2);

    }

}
