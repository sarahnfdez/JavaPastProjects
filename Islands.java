import java.io.*;
import java.util.*;
import java.util.regex.*;
import java.math.*;

public class Islands {
  public int numProbs;
  public char[][][] maps;

  Islands(String file) throws RuntimeException {
  
    try {
            Scanner f = new Scanner(new File(file));
            String ln = f.nextLine(); /*first line is the number of problems*/
            this.numProbs = Integer.parseInt(ln);
            this.maps = new char[numProbs][100][100];
            int index = 0;
            while (f.hasNext()){
              String[] line = f.nextLine().split("\\s+");
              int xlim = Integer.parseInt(line[0]);
              int ylim = Integer.parseInt(line[1]);
              char[][] mapp = new char[xlim][ylim];
              for(int i = 0; i<xlim;i++){
                char[] mapline = f.nextLine().toCharArray();
                mapp[i] = mapline;
              }
              maps[index] = mapp;
              index++;
                }
            f.close();

        }
        catch (FileNotFoundException e){
            System.out.println("File not found!");
            System.exit(1);
        }
    
  }
  
  public static boolean isSafe(char[][] map, int row, int col, boolean visited[][]){
    int xmax = map.length; //gets the maximum x value
    int ymax = map[0].length; //gets the maximum y value
    return (row >= 0) && (row < xmax) && (col >= 0) && (col < ymax) && (map[row][col] == '-' && !visited[row][col]);
  //if the cell is within the min and max row and col values and is an island and is not visited, return true
  }
  
  public static void DFS(char[][] map, int row, int col, boolean[][] visited){
    //checks values of neighbors to the left/right and top/bottom
    int rowNbr[] = new int[] {-1, 0, 0, 1};
    int colNbr[] = new int[] {0, -1, 1, 0};
    visited[row][col] = true; //make the cell as visited
    for(int k = 0;k<4;++k){
      if(isSafe(map, row+rowNbr[k], col+colNbr[k], visited)){ //if the neighbor cell is safe to continue DFS
        DFS(map, row+rowNbr[k], col+colNbr[k], visited); //perform dfs on neighbor cell
      }
    }
  }
  
  public static int findIslands(char[][] map){
    int row = map.length;
    int col = map[0].length;
    boolean visited[][] = new boolean[row][col];
    int count = 0;
    for(int i = 0;i<row;++i){ //iterate through rows
      for(int j = 0; j<col;++j){ //iterate through columns
        if (map[i][j] == '-' && !visited[i][j]){ //if the cell is an island and is not visited
          DFS(map, i, j, visited); //perform dfs
          ++count; //increment island count
        }
      }
    }
    return count;
  }
    
  public static void main(String[] args){
    String file = "testIslands.txt";
    Islands problems = new Islands(file);
    int[] islands = new int[problems.numProbs];
    try{
      for(int i = 0;i<problems.numProbs;i++){
        int numIslands = findIslands(problems.maps[i]);
        islands[i] = numIslands;
      }
      String filename = "testIslands_solution.txt";
      PrintWriter outputStream = new PrintWriter(filename);
      for(int i = 0; i < islands.length;i++){
        outputStream.println(islands[i]);
      }
      outputStream.close();
    } catch(Exception e){
      e.printStackTrace();
    }
  }
  
  }
  