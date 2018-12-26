package assignment1;
public class Message {
 
 public String message;
 public int lengthOfMessage;

 public Message (String m){
  message = m;
  lengthOfMessage = m.length();
  this.makeValid();
 }
 
 public Message (String m, boolean b){
  message = m;
  lengthOfMessage = m.length();
 }
 
 public static void main(String args[]){

 }
 
 /**
  * makeValid modifies message to remove any character that is not a letter and turn Upper Case into Lower Case
  */
 public void makeValid(){
  //INSERT YOUR CODE HERE
   String str = this.message;
   int stringLength = str.length();
   for(int position=0; position < stringLength; position++){
     char a = str.charAt(position);
     if((a >= 65) && (a <= 90)){  //takes any uppercase letter, turns it lower case and adds it back to the string
         a = (char) ( (int) a + 32);
         String addition = String.valueOf(a);
         str = str.substring(0, position)+addition+str.substring(position+1);
       } else if((a < 65) || (a > 122) || a > 90 && a < 97){  //takes any symbol, removes it from the string and updates the string length 
       str = str.substring(0, position)+str.substring(position+1);
       stringLength = str.length();
       position--;
       } else {
         continue;
       }
   } 
   this.message = str;
   this.lengthOfMessage = str.length();
 }
 
 /**
  * prints the string message
  */
 public void print(){
  System.out.println(message);
 }
 
 /**
  * tests if two Messages are equal
  */
 public boolean equals(Message m){
  if (message.equals(m.message) && lengthOfMessage == m.lengthOfMessage){
   return true;
  }
  return false;
 }
 
 /**
  * caesarCipher implements the Caesar cipher : it shifts all letter by the number 'key' given as a parameter.
  * @param key
  */
 public void caesarCipher(int key){
  // INSERT YOUR CODE HERE
   key = key%26; //takes any number in the key and reduces it to a letter from 1 to 26
   String str = this.message;
   for(int pos = 0; pos < this.lengthOfMessage; pos++){ //loops through the message
     char a = str.charAt(pos);
     a = (char) (a+key); //shifts the character
     if(a > 122){ //checks to see if it's not over the ASCII space for lowercase letters; if so, it wraps it around to the beginning
       int difference = (int) (a-122)-1;
       a = (char) ('a'+difference);
     } 
     if (a < 97){ //checks to see if it's not under the ASCII space for lowercase letters; it so, it wraps it around to the end
       int difference = (int) ('a' - a)-1;
       a = (char) ('z'-difference);
     }
     String addition = String.valueOf(a);
     str = str.substring(0, pos)+addition+str.substring(pos+1);  //concatenates the strings 
   }
   this.message = str;
 }
 
 public void caesarDecipher(int key){
  this.caesarCipher(- key);
 }
 
 /**
  * caesarAnalysis breaks the Caesar cipher
  * you will implement the following algorithm :
  * - compute how often each letter appear in the message
  * - compute a shift (key) such that the letter that happens the most was originally an 'e'
  * - decipher the message using the key you have just computed
  */
 public void caesarAnalysis(){
  // INSERT YOUR CODE HERE
   String str = this.message;
   int stringLength = this.lengthOfMessage;
   int counter = 0;
   int charPosition = 0;
   int arr[] = new int[stringLength];
   for(int pos = 0; pos < stringLength;pos++){ //goes through the message changing the compared character after it compares with all the other characters in the message
     for(int i = 0; i < stringLength-1;i++){ //goes through the message with one character comparing to see if it matches; if it does, a counter increments
       char a = str.charAt(pos);
       if(a == str.charAt(i)){
       counter++;
       }
     }
     arr[pos] = counter; //that counter is held in an int array at the position of the later it is comparing
     counter = 0; //counter is reset
   }
   for(int i = 0; i < arr.length;i++){ //moves through the int array to find the position in the message with the highest instances and records it's location
     if(arr[i] > counter){
       counter = arr[i];
       charPosition = i;
     }
   }
   char shiftKey = str.charAt(charPosition); 
   int difference = (int) (shiftKey - 'e'); //the difference is calculated between the highest used character and 'e'; this will be added to all other characters
   StringBuilder sb = new StringBuilder();
   for(int i=0;i<stringLength;i++){ //builds the decoded message by moving through the message and adding the difference
     char cc = (char) (str.charAt(i)-difference);
     if(cc > 122){ //wraps the character back to 'a' if it goes beyond the 'z' in ASCII
       int diff = (int) (cc-122);
       cc = (char) (96+diff); 
     }
     if(cc < 97){ //wraps the character back to 'z' if it goes before the 'a' in ASCII
       int diff = (int) (97-cc);
       cc = (char) (123-diff);
     }
     sb.append(cc); //concatenates the characters
   }
   this.message = sb.toString();
   this.lengthOfMessage = sb.length();
 }
 
 /**
  * vigenereCipher implements the Vigenere Cipher : it shifts all letter from message by the corresponding shift in the 'key'
  * @param key
  */
 public void vigenereCipher (int[] key){
  // INSERT YOUR CODE HERE
   int stringLength = this.lengthOfMessage;
   int keyCounter = 0;
   String str = this.message;
   StringBuilder sb = new StringBuilder();
   for(int i =0;i<stringLength;i++){ //moves through the message and the int array and adds the value at the int array position to the character in the message position
     if(keyCounter > key.length-1){ //if the int array is less then the message length, this if statement restarts the position in the int array
       keyCounter = 0;
     }
     char cc = (char) (str.charAt(i)+key[keyCounter]); //adds the character and the key
     if(cc > 122){ //wraps the character back to 'a' if it goes beyond 'z'
       int difference = (int) (cc-122);
       cc = (char) (96+difference);
     }
     if(cc < 97){ //wraps the character back to 'z' if it goes before 'a'
       int difference = (int) (97-cc);
       cc = (char) (123-difference);
     }
     sb.append(cc); //concatenates the characters
     keyCounter++;
   }
   this.message = sb.toString();
 }

 /**
  * vigenereDecipher deciphers the message given the 'key' according to the Vigenere Cipher
  * @param key
  */
 public void vigenereDecipher (int[] key){
  // INSERT YOUR CODE HERE
   int stringLength = this.lengthOfMessage;
   int keyCounter = 0;
   String str = this.message;
   StringBuilder sb = new StringBuilder();
   for(int i =0;i<stringLength;i++){ //moves through the decoded message and the int array and adds the value at the int array position to the character in the message position
     if(keyCounter > key.length-1){ //restarts the position in the int array if the message is longer than the int array
       keyCounter = 0;
     }
     char cc = (char) (str.charAt(i)-key[keyCounter]); //adds the character and the key
     if(cc < 97){ //wraps the character back to 'z' if it goes before 'a'
       int difference = (int) (97-cc);
       cc = (char) (123-difference);
     }
     if(cc > 122){ //wraps the character back to 'a' if it goes beyond 'z'
       int difference = (int) (cc-122);
       cc = (char) (96+difference);
     }
     sb.append(cc); //concatenates the strings
     keyCounter++;
   }
   this.message = sb.toString();
 }
 
 /**
  * transpositionCipher performs the transition cipher on the message by reorganizing the letters and eventually adding characters
  * @param key
  */
 public void transpositionCipher (int key){
  // INSERT YOUR CODE HERE
   int stringLength = this.lengthOfMessage;
   String str = this.message;
   double dKey = (double) key; 
   double dRows = stringLength/dKey; 
   if(dRows != stringLength/key){ //if dRows != stringLength/key, then it means there needs to be extra '*' added to pad the row
     dRows++;
   }
   int rows = (int) dRows;
   char arr[] = new char[rows*key]; //char array to hold the characters from the message AND the stars '*'
   for(int i = 0;i < arr.length;i++){
     if(i >= stringLength){
       arr[i] = '*'; 
     } else {
       arr[i] = str.charAt(i); 
     }
   }
   StringBuilder sb = new StringBuilder();
   for(int j=0;j<key;j++){ //moves through each row of the char array
     for(int k=j;k<arr.length;k = k+key){ //moves through each column of the char array
       sb.append(arr[k]); //concatenates the characters
     }
   }
   this.message = sb.toString(); 
   this.lengthOfMessage = sb.length();
 }
 
 /**
  * transpositionDecipher deciphers the message given the 'key'  according to the transition cipher.
  * @param key
  */
 public void transpositionDecipher (int key){
  // INSERT YOUR CODE HERE
   int stringLength = this.lengthOfMessage;
   double dKey = (double) key;
   double dRows = stringLength/dKey;
   if(dRows != stringLength/key){ //if dRows != stringLength/key, then it means there are extra '*' added to pad the row
     dRows++;
   }
   int rows = (int) dRows;  
   stringLength = rows*key;
   String str = this.message;
   StringBuilder sb = new StringBuilder();
   for(int i = 0;i<rows;i++){ //moves through the rows of the decoded message
     for(int k=i;k<stringLength;k = k+rows){ //moves through the columns of the decoded message
       if(str.charAt(k) != '*'){
         sb.append(str.charAt(k)); //concatencates the characters
       }
     }
   }
   this.message = sb.toString(); 
   this.lengthOfMessage = sb.length();
 }
 
}
