package A1;

import java.util.*;
import static A1.main.*;

public class Chaining {

    public int m; // number of SLOTS AVAILABLE
    public int A; // the default random number
    int w;
    int r;
    public ArrayList<ArrayList<Integer>> Table;

    //Constructor for the class. sets up the data structure for you
    protected Chaining(int w, int seed) {
        this.w = w;
        this.r = (int) (w - 1) / 2 + 1;
        this.m = power2(r);
        this.Table = new ArrayList<ArrayList<Integer>>(m);
        for (int i = 0; i < m; i++) {
            Table.add(new ArrayList<Integer>());
        }
        this.A = generateRandom((int) power2(w - 1), (int) power2(w), seed);
    }

    /**
     * Implements the hash function h(k)
     */
    public int chain(int key) {
        //ADD YOUR CODE HERE (change return statement)
        int mod = (this.A*key) % power2(this.w);
        return (mod >> (this.w - this.r));
    }

    /**
     * Checks if slot n is empty
     */
    public boolean isSlotEmpty(int hashValue) {
        return Table.get(hashValue).size() == 0;
    }

    /**
     * Inserts key k into hash table. Returns the number of collisions
     * encountered
     */
    public int insertKey(int key) {
        //ADD YOUR CODE HERE (chane return statement)
      int hashValue = chain(key);
      if (isSlotEmpty(hashValue)) { //if the slot is empty
        this.Table.get(hashValue).add(key); //set the value at index hashValue to value key
        return 0; //return 0 collisions
      } else { //if the slot is not empty
        if (this.Table.get(hashValue).contains(key)){  //if the key is already in the table return -1 
          return -1;
        }
        int collisions = this.Table.get(hashValue).size(); //checks how many other elements are in the slot (collisions)
        if (collisions >= m) { //if the slot is full, add value key at index hashValue and resize arraylist
          this.Table.get(hashValue).add(key);
          return collisions;
        } else { //if the slot is not full
          this.Table.get(hashValue).add(key); //set the key at the index hashValue to value key
          return collisions; // return the total collisions
        }
      }
    }

}
