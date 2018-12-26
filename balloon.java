import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;

public class balloon {

  public int numProbs;
  public int[] numBalloons;
  public ArrayList<ArrayList<Integer>> balloons;
  
    balloon(String file) throws RuntimeException {
  
     try {
            Scanner f = new Scanner(new File(file));
            String ln = f.nextLine(); /*first line is the number of problems*/
            this.numProbs = Integer.parseInt(ln); //sets the number of problems
            this.numBalloons = new int[numProbs]; //makes an array for the number of balloons in each problem
            String[] ln2 = f.nextLine().split("\\s+"); /*second line is the number of balloons per problem*/
            for (int i = 0;i<numProbs;i++){ //creates the array of the number of balloons in each problem
              this.numBalloons[i] = Integer.parseInt(ln2[i]);  //fills in numBalloons
            }
            this.balloons = new ArrayList<ArrayList<Integer>>(); //makes an arraylist of arraylist of ints to hold the balloon fields
            while (f.hasNext()){
                String[] line = f.nextLine().split("\\s+");
                ArrayList<Integer> temp = new ArrayList<Integer>(); 
                for(int i = 0;i<line.length;i++){
                  int t = Integer.parseInt(line[i]); //gets the height of the balloon
                  temp.add(t); //adds the balloon to the space
                }
                this.balloons.add(temp); //adds the balloon field to the list of fields
            }
            f.close();

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
    
  }
  
  public static int minArrows(int numBalloons, ArrayList<Integer> balloons){
    int arrows =0;
    while (!balloons.isEmpty()){ //while there are still balloons
      int max = 0;
      int index = 0;
        for(int i = 0;i<balloons.size();i++){ //finds the tallest height of the balloons
          int level = balloons.get(i);
          if (level > max){
            max = level; //max = tallest height
            index = i; //index = index of the tallest balloon
          }
        }
        arrows = arrows+1; //increment arrows
        balloons.remove(index); //pop the balloon!
        max = max-1; //decrease in height
        for(int k = index; k< balloons.size();k++){ //for all the balloons after the tallest balloon
          int balloon = balloons.get(k); //get the height
          if (balloon == max){ //if the height is at the level of the arrow
            balloons.remove(k); //pop the balloon!
            max = max-1; //decrease the height
            k = k-1; //move back the index
          }
        }
    }
    return arrows;
  }
  
  public static void main(String[] args) {
    String file = "testBalloons.txt";
    balloon fields = new balloon(file);
    int[] arrows = new int[fields.numProbs];
    try {
      for(int i = 0;i< fields.numProbs;i++){
        int numBalloon = fields.numBalloons[i];
        ArrayList<Integer> field = fields.balloons.get(i);
        int arrow = minArrows(numBalloon, field);
        arrows[i] = arrow;
      }
      String filename = "testBalloons_solution.txt";
      PrintWriter outputStream = new PrintWriter(filename);
      for(int i = 0; i < arrows.length;i++){
        outputStream.println(arrows[i]);
      }
      outputStream.close();
  } catch (Exception e){
      e.printStackTrace();
    }
  }

}