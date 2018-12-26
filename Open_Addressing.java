package A1;

import static A1.main.*;

public class Open_Addressing {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public int[] Table;

    //Constructor for the class. sets up the data structure for you
    protected Open_Addressing(int w, int seed) {

        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
        this.Table = new int[m];
        //empty slots are initalized as -1, since all keys are positive
        for (int i = 0; i < m; i++) {
            Table[i] = -1;
        }

    }

    /**
     * Implements the hash function g(k)
     */
    public int probe(int key, int i) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        int h = ((this.A*key) % power2(this.w)) >> (this.w-this.r);
        return (h+i) % power2(this.r);
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table[hashValue] == -1;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
      int collisions = 0; //stores collisions
      int i; //indexing through table
      int hashValue; //stores hashValue
      for(i = 0; i < m-1; i++){ //moves through all values of m until finding an empty slot or i=m
        hashValue = probe(key, i); //creates hashValue
        if (isSlotEmpty(hashValue) != true){ //if the slot is full
          if(Table[hashValue] == key){
            return collisions;
          }
          collisions++; //increment collisions
        } else { //if slot is empty
          Table[hashValue] = key; //input value key at index hashValue
          return collisions; //return collisions
        }
      }
      return collisions; //if all slots are full, return all collisions
    }

    /**
     * Removes key k from hash table. Returns the number of collisions
     * encountered
     */
    public int removeKey(int key) {
        //ADD YOUR CODE HERE (CHANGE THE RETURN STATEMENT)
        int collisions = 0; //stores collisions
        int i; //stores index through table
        int hashValue; //stores hashValue
        for (i = 0; i < m-1; i++){ //moves through values of m until finding the key or i=m
          hashValue = probe(key, i); //creates hashValue
          if (Table[hashValue] != key){ //if the value at that slot is not key
            collisions++; //increment collisions
          } else { //if the value at the slot is the key
            Table[hashValue] = -1; //remove the key by changing the value to -1
            return collisions; //return collisions
          }
        }
        return collisions; //if the key is not in the table, return all collisions
    }
}
