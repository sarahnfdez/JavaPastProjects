package assignment4Game;

import java.io.*;

public class Game {
 
 public static int play(InputStreamReader input){
  BufferedReader keyboard = new BufferedReader(input);
  Configuration c = new Configuration();
  int columnPlayed = 3; int player;
  
  // first move for player 1 (played by computer) : in the middle of the grid
  c.addDisk(firstMovePlayer1(), 1);
  int nbTurn = 1;
  
  while (nbTurn < 42){ // maximum of turns allowed by the size of the grid
   player = nbTurn %2 + 1;
   if (player == 2){
    columnPlayed = getNextMove(keyboard, c, 2);
   }
   if (player == 1){
    columnPlayed = movePlayer1(columnPlayed, c);
   }
   System.out.println(columnPlayed);
   c.addDisk(columnPlayed, player);
   if (c.isWinning(columnPlayed, player)){
    c.print();
    System.out.println("Congrats to player " + player + " !");
    return(player);
   }
   nbTurn++;
  }
  return -1;
 }
 
 public static int getNextMove(BufferedReader keyboard, Configuration c, int player){
  // ADD YOUR CODE HERE
   int result = 0;
   System.out.println("What column would you like to place your piece in?");
   try {
     String column = keyboard.readLine();
     int col = Integer.parseInt(column);
     if(col > 6 || col < 0){
       while(col > 6 || col < 0){
         System.out.println("Column input must be between 0-6. Please choose another column.");
         column = keyboard.readLine();
         col = Integer.parseInt(column);
         result = col;
       }
     }
     while(c.available[col] > 5){
       System.out.println("That column is full. Please choose another column.");
       column = keyboard.readLine();
       col = Integer.parseInt(column);
       result = col;
     }
     c.addDisk(col, player);
     c.print();
     return col;
   } catch (IOException e){
     System.out.println("Exception occurred");
     return -1;
   }
 }
 
 public static int firstMovePlayer1 (){
  return 3;
 }
 
 public static int movePlayer1 (int columnPlayed2, Configuration c){
  // ADD YOUR CODE HERE
   int last = columnPlayed2;  //easier to call
   int col = 0;  //column  to return
   if(c.canWinNextRound(1) != -1){  //if player1 can win the next round, play that column and return it
     col = c.canWinNextRound(1);
     c.addDisk(col, 1);
     return col;
   } else if (c.canWinTwoTurns(1) != -1){ //if player1 can win in two rounds, play that column and return it
     col = c.canWinTwoTurns(1);
     c.addDisk(col, 1);
     return col;
   } else if(c.available[columnPlayed2] != 6){ //if there is availability in the same column as last, play that column and return it
     c.addDisk(columnPlayed2, 1);
   } else {
     for(int i = 1; 1 < 4; i++){
       if(last-i > 0 && c.available[last-i] != 6){ //if there is availability the column to the left of last, play it and return it
         c.addDisk(last-i, 1);
         return last-i;
       } else if (last+i < 7 && c.available[last+i] != 6){ //else if there is availability in the column to the right of last, play it and return it
         c.addDisk(last+i, 1);
         return last+i;
       }
     }
   }
  return col; // DON'T FORGET TO CHANGE THE RETURN
 }
 
}
