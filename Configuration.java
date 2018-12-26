package assignment4Game;

public class Configuration {
 
 public int[][] board;
 public int[] available;
 boolean spaceLeft;
 
 public Configuration(){
  board = new int[7][6];
  available = new int[7];
  spaceLeft = true;
 }
 
 public void print(){
  System.out.println("| 0 | 1 | 2 | 3 | 4 | 5 | 6 |");
  System.out.println("+---+---+---+---+---+---+---+");
  for (int i = 0; i < 6; i++){
   System.out.print("|");
   for (int j = 0; j < 7; j++){
    if (board[j][5-i] == 0){
     System.out.print("   |");
    }
    else{
     System.out.print(" "+ board[j][5-i]+" |");
    }
   }
   System.out.println();
  }
 }
 
 public void addDisk (int index, int player){
  // ADD YOUR CODE HERE
   int location = available[index];
   this.board[index][location] = player;
   available[index] = available[index]+1;
   for(int col = 0;col< 7;col++){
     for(int row = 0;row < 6;row++){
       if(this.board[col][row] == 0){
         spaceLeft = true;
         break;
       }
     }
   }
 }
 
 public boolean isWinning (int lastColumnPlayed, int player){
  // ADD YOUR CODE HERE
   int loc = available[lastColumnPlayed]-1;
   //HORIZONTAL
   if(lastColumnPlayed == 0){
     if(this.board[1][loc] == player && this.board[2][loc] == player && this.board[3][loc] == player){
       return true;
     }
   } else if(lastColumnPlayed == 1){
     if(this.board[0][loc] == player && this.board[2][loc] == player && this.board[3][loc] == player){
       return true;
     } else if(this.board[2][loc] == player && this.board[3][loc] == player && this.board[4][loc] == player) {
       return true;
     }
   } else if(lastColumnPlayed == 2){
     if(this.board[0][loc] == player && this.board[1][loc] == player && this.board[3][loc] == player){
       return true;
     } else if(this.board[1][loc] == player && this.board[3][loc] == player && this.board[4][loc] == player) {
       return true;
     } else if(this.board[3][loc] == player && this.board[4][loc] == player && this.board[5][loc] == player){
       return true;
     }
   } else if(lastColumnPlayed == 3){
     if(this.board[0][loc] == player && this.board[1][loc] == player && this.board[2][loc] == player){
       return true;
     } else if(this.board[1][loc] == player && this.board[2][loc] == player && this.board[4][loc] == player){
       return true;
     } else if(this.board[2][loc] == player && this.board[4][loc] == player && this.board[5][loc] == player){
       return true;
     } else if(this.board[4][loc] == player && this.board[5][loc] == player && this.board[6][loc] == player){
       return true;
     }
   } else if(lastColumnPlayed == 4){
     if(this.board[6][loc] == player && this.board[5][loc] == player && this.board[3][loc] == player){
       return true;
     } else if(this.board[5][loc] == player && this.board[3][loc] == player && this.board[2][loc] == player) {
       return true;
     } else if(this.board[3][loc] == player && this.board[2][loc] == player && this.board[1][loc] == player) {
       return true;
     }
   } else if(lastColumnPlayed == 5){
     if(this.board[6][loc] == player && this.board[4][loc] == player && this.board[3][loc] == player){
       return true;
     } else if (this.board[4][loc] == player && this.board[3][loc] == player && this.board[2][loc] == player) {
       return true;
     }
   } else if(lastColumnPlayed == 6){
     if(this.board[5][loc] == player && this.board[4][loc] == player && this.board[3][loc] == player){
       return true;
     }
   }
   //VERTICAL
   if(loc == 3){
     if(this.board[lastColumnPlayed][0] == player && this.board[lastColumnPlayed][1] == player && this.board[lastColumnPlayed][2] == player){
       return true;
     }
   } else if(loc == 4){
     if(this.board[lastColumnPlayed][5] == player && this.board[lastColumnPlayed][3] == player && this.board[lastColumnPlayed][2] == player){
       return true;
     } else if(this.board[lastColumnPlayed][3] == player && this.board[lastColumnPlayed][2] == player && this.board[lastColumnPlayed][1] == player){
       return true;
     }
   } else if(loc == 5){
     if(this.board[lastColumnPlayed][4] == player && this.board[lastColumnPlayed][3] == player && this.board[lastColumnPlayed][2] == player){
       return true;
     }
   }
   //DIAGONAL
   if(lastColumnPlayed == 0 || lastColumnPlayed == 1 ||lastColumnPlayed == 2){
     if(loc < 3){
       if(this.board[lastColumnPlayed+1][loc+1] == player && this.board[lastColumnPlayed+2][loc+2] == player && this.board[lastColumnPlayed+3][loc+3] == player){
         return true;
       }
     } else if(loc >= 3){
       if(this.board[lastColumnPlayed+1][loc-1] == player && this.board[lastColumnPlayed+2][loc-2] == player && this.board[lastColumnPlayed+3][loc-3] == player){
         return true;
       }
     }
   } else if(lastColumnPlayed == 3){
     if(loc < 3){
       if(this.board[lastColumnPlayed-1][loc+1] == player && this.board[lastColumnPlayed-2][loc+2] == player && this.board[lastColumnPlayed-3][loc+3] == player){
         return true;
       } else if(this.board[lastColumnPlayed+1][loc+1] == player && this.board[lastColumnPlayed+2][loc+2] == player && this.board[lastColumnPlayed+3][loc+3] == player){
         return true;
       }
     } else if (loc >= 3){
       if(this.board[lastColumnPlayed-1][loc-1] == player && this.board[lastColumnPlayed-2][loc-2] == player && this.board[lastColumnPlayed-3][loc-3] == player){
         return true;
       } else if(this.board[lastColumnPlayed+1][loc-1] == player && this.board[lastColumnPlayed+2][loc-2] == player && this.board[lastColumnPlayed+3][loc-3] == player){
         return true;
       }
     }
   } else if(lastColumnPlayed == 6 || lastColumnPlayed == 5 || lastColumnPlayed == 4){
     if(loc < 3){
       if(this.board[lastColumnPlayed-1][loc-1] == player && this.board[lastColumnPlayed-2][loc-2] == player && this.board[lastColumnPlayed-3][loc-3] == player){
         return true;
       }
     } else if(loc >= 3){
       if(this.board[lastColumnPlayed-1][loc+1] == player && this.board[lastColumnPlayed-2][loc+2] == player && this.board[lastColumnPlayed-3][loc+3] == player){
         return true;
       }
     }
   }
  return false; // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public int checkVertical1(int row, int col, int player){
   int[] col4 = {0, 0, 0, 0,};
   int counter = 0;
   int returnCol1 = 30;
   int returnCol2 = 30;
   if(row+1 < 6 && row+2 < 6 && row+3 < 6){
     col4[0] = board[col][row];
     col4[1] = board[col][row+1];
     col4[2] = board[col][row+2];
     col4[3] = board[col][row+3];
     for(int i = 0;i < col4.length;i++){ //loops through the array
       if(col4[i] == player){ //if the element is a player, increment the counter
         counter++;
       } else if(col4[i] ==0){ //if the element is a zero
         returnCol1 = i+col;
       } else{ //if the element is a rival, set counter to 0 and break
         counter = 0;
         break;
       }
     }
     if(counter != 3){ //if there are not the proper elements in the array
       returnCol1 = 30;
     }
   } 
   return 30;
 }
 
 public int checkHorizontal1(int row, int col, int player){
   int row4[] = {0, 0, 0, 0};  //array to hold potential row
   int counter = 0;
   int returnCol1 = 30;
   int returnCol2 = 30;
   if(col+1 < 7 && col+2 < 7 && col+3 < 7){
     row4[0] = board[col][row];
     row4[1] = board[col+1][row];
     row4[2] = board[col+2][row];
     row4[3] = board[col+3][row];
     for(int i = 0;i < row4.length;i++){ //loops through the array
       if(row4[i] == player){ //if the element is a player, increment the counter
         counter++;
       } else if(row4[i] ==0){ //if the element is a zero
         returnCol1 = i+col;
       } else{ //if the element is a rival, set counter to 0 and break
         counter = 0;
         break;
       }
     }
     if(counter != 3){ //if there are not the proper elements in the array
       returnCol1 = 30;
     }
     row4[0] = 0;
     row4[1] = 0;
     row4[2] = 0;
     row4[3] = 0;
     counter = 0;
   } 
   if(col-1 > 0 && col-2 > 0 && col-3 > 0){
     row4[3] = board[col][row];
     row4[2] = board[col-1][row];
     row4[1] = board[col-2][row];
     row4[0] = board[col-3][row];
     for(int i = 0;i < row4.length;i++){ //loops through the array
       if(row4[i] == player){ //if the element is a player, increment the counter
         counter++;
       } else if(row4[i] ==0){ //if the element is a zero
         returnCol2 = i+col;
       } else{ //if the element is a rival, set counter to 0 and break
         counter = 0;
         break;
       }
     }
     if(counter != 3){ //if there are not the proper elements in the array
       returnCol2 = 30;
     }   
   }
   if(returnCol1 == 30 && returnCol2 == 30){
     return 30;
   } else if(returnCol1 < returnCol2){
     return returnCol1;
   } else if(returnCol2 < returnCol1){
     return returnCol2;
   }
   return 30;
 }
 
 public int checkDiag1(int row, int col, int player){
   int diag4[] = {0, 0, 0, 0};  //array to hold potential row
   int counter = 0;
   int returnCol1 = 30;
   int returnCol2 = 30;
   if(row+1 < 6 && row+2 < 6 && row+3 < 6 && col+1 < 7 && col+2 < 7 && col+3 < 7){ //checks upward diagonal
     diag4[0] = board[col][row]; //loads the 4 values into the array
     diag4[1] = board[col+1][row+1];
     diag4[2] = board[col+2][row+2];
     diag4[3] = board[col+3][row+3];
     for(int i = 0;i < diag4.length;i++){ //loops through the array
       if(diag4[i] == player){ //if the element is a player, increment the counter
         counter++;
       } else if(diag4[i] == 0){ //if the element is zero, make returnCol that column
         returnCol1 = i+col;
       } else{ //if the element is a rival, set counter to 0 and break
         counter = 0;
         break;
       }
     }
     if(counter != 3){ //if there are not the proper elements in the array
       returnCol1 = 30;
     }
     diag4[0] = 0;
     diag4[1] = 0;
     diag4[2] = 0;
     diag4[3] = 0;
     counter = 0;
   } 
   if(row-1 > -1 && row-2 > -1 && row-3 > -1 && col+1 > -1 && col+2 > -1 && col+3 > -1){ //checks downward diagonal
     diag4[0] = board[col][row];
     diag4[1] = board[col+1][row-1];
     diag4[2] = board[col+2][row-2];
     diag4[3] = board[col+3][row-3];
     for(int i = 0;i < diag4.length;i++){
       if(diag4[i] == player){
         counter++;
       } else if(diag4[i] == 0){
         returnCol2 = i+col;
       } else{
         counter = 0;
         break;
       }
     }
     if(counter != 3){
       returnCol2 = 30;
     }
   }
   if(returnCol1 == 30 && returnCol2 == 30) {
     return 30;
   } else if(returnCol1 < returnCol2){
     return returnCol1;
   } else if(returnCol2 < returnCol1){
     return returnCol2;
   }
   return 30;
 }
 
 public int canWinNextRound (int player){
  // ADD YOUR CODE HERE
   int winCol = 30;
   for(int col = 0;col < 7;col++){
     for(int row = 0; row < 6;row++){
       if(this.board[col][row] == player){
         int hor = checkHorizontal1(row, col, player);
         int ver = checkVertical1(row, col, player);
         int diag = checkDiag1(row, col, player);
         if(hor == -1 && ver == -1 && diag == -1){
           continue;
         } else if(hor < diag && hor < ver && hor < winCol){
           winCol = hor;
         } else if(ver < hor && ver < diag && ver < winCol){
           winCol = ver;
         } else if(diag < hor && diag < ver && diag < winCol)
           winCol = diag;
       }
     }
   }
   if(winCol != 30){
     return winCol;
   } else {
     return -1;
   }
 }
 
 public int canWinTwoTurns (int player){
  // ADD YOUR CODE HERE
   int winCol = 30;
   for(int col = 0;col < 7;col++){
     if(available[col] != 6){
       addDisk(col, player);
     }
     for(int row = 0; row < 6;row++){
       if(this.board[col][row] == player){
         int hor = checkHorizontal1(row, col, player);
         int ver = checkVertical1(row, col, player);
         int diag = checkDiag1(row, col, player);
         if(hor == ver && hor != 30 && hor < winCol){
           return winCol = hor;
         }
         if(hor == diag && hor != 30 && hor < winCol){
           return winCol = hor;
         }
         if(ver == diag && ver != 30 && ver < winCol){
           return winCol = ver;
         }
       }
     }
     available[col] = available[col]-1;
     addDisk(col, 0);
     available[col] = available[col]-1;
   }
   if(winCol == 30){
     return -1;
   } else {
     return winCol;
   }
 }
 
}
