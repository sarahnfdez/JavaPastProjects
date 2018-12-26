package assignment2;

public class Warehouse{

 protected Shelf[] storage;
 protected int nbShelves;
 public Box toShip;
 public UrgentBox toShipUrgently;
 static String problem = "problem encountered while performing the operation";
 static String noProblem = "operation was successfully carried out";
 
 public Warehouse(int n, int[] heights, int[] lengths){
  this.nbShelves = n;
  this.storage = new Shelf[n];
  for (int i = 0; i < n; i++){
   this.storage[i]= new Shelf(heights[i], lengths[i]);
  }
  this.toShip = null;
  this.toShipUrgently = null;
 }
 
 public String printShipping(){
  Box b = toShip;
  String result = "not urgent : ";
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n" + "should be already gone : ";
  b = toShipUrgently;
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n";
  return result;
 }
 
  public String print(){
   String result = "";
  for (int i = 0; i < nbShelves; i++){
   result += i + "-th shelf " + storage[i].print();
  }
  return result;
 }
 
  public void clear(){
   toShip = null;
   toShipUrgently = null;
   for (int i = 0; i < nbShelves ; i++){
    storage[i].clear();
   }
  }
  
  /**
   * initiate the merge sort algorithm
   */
 public void sort(){
  mergeSort(0, nbShelves -1);
 }
 
 /**
  * performs the induction step of the merge sort algorithm
  * @param start
  * @param end
  */
 protected void mergeSort(int start, int end){
  //ADD YOUR CODE HERE
   int mid;
   if(start < end){
     mid = (start+end)/2;
     mergeSort(start, mid);
     mergeSort(mid+1, end);
     merge(start, mid, end);
   }
 }
 
 /**
  * performs the merge part of the merge sort algorithm
  * @param start
  * @param mid
  * @param end
  */
 protected void merge(int start, int mid, int end){
  //ADD YOUR CODE HERE
   int i = 0;
   int j = 0;
   int k = 0;
   int n1 = mid-start+1;
   int n2 = end-mid;
   int [] left = new int[n1+1];
   int[] right = new int[n2+1];
   for(i=0;i< n1; i++){
     left[i] = this.storage[i+start].height;
   }
   for(j = 0;j<n2;j++){
     right[j] = this.storage[j+mid+1].height;
   }
   right[j] = 1000;
   left[i] = 1000;
   k = i = j = 0;
   for(k = start; k <= end;k++){
     if(left[i] <= right[j]){
       this.storage[k].height = left[i];
       i++;
     } else {
       this.storage[k].height = right[j];
       j++;
     }
   }
 }
 
 /**
  * Adds a box is the smallest possible shelf where there is room available.
  * Here we assume that there is at least one shelf (i.e. nbShelves >0)
  * @param b
  * @return problem or noProblem
  */
 public String addBox (Box b){
  //ADD YOUR CODE HERE
   int shelf;
   int length = b.length;
   int height = b.height;
   for(int i = 0;i< storage.length;i++){
     if(length <= storage[i].availableLength && height <= storage[i].height){
       shelf = i;
       length = storage[i].availableLength;
       height = storage[i].height;
       for(int k=i+1; k<nbShelves;k++){
         if(height == storage[k].height && length > storage[k].availableLength && storage[k].availableLength >= b.length){
           shelf = k;
           length = storage[k].availableLength;
           height = storage[k].height;
         }
       }
       storage[shelf].addBox(b);
       return noProblem;
     }
   }
  return problem;
 }
 
 /**
  * Adds a box to its corresponding shipping list and updates all the fields
  * @param b
  * @return problem or noProblem
  */
 public String addToShip (Box b){
  //ADD YOUR CODE HERE
   if(b instanceof UrgentBox){
     if(toShipUrgently != null){
       b.next = toShipUrgently;
       toShipUrgently = (UrgentBox) b;
       return noProblem;
     } else {
       toShipUrgently = (UrgentBox) b;
       return noProblem;
     }
   } else if (b instanceof Box){
     if(toShip != null){
       b.next = toShip;
       toShip = b;
       return noProblem;
     } else {
       toShip = b;
       return noProblem;
     }
   } else {
     return problem;
   }
 }
 
 /**
  * Find a box with the identifier (if it exists)
  * Remove the box from its corresponding shelf
  * Add it to its corresponding shipping list
  * @param identifier
  * @return problem or noProblem
  */
 public String shipBox (String identifier){
  //ADD YOUR CODE HERE
   for(int i=0;i<nbShelves;i++){
     Box ship = storage[i].removeBox(identifier);
     if(ship != null){
       addToShip(ship);
       return noProblem;
     }
   }
  return problem;
 }
 
 /**
  * if there is a better shelf for the box, moves the box to the optimal shelf.
  * If there are none, do not do anything
  * @param b
  * @param position
  */
 public void moveOneBox (Box b, int position){
  //ADD YOUR CODE HERE
   int shelf = position;
   int height = storage[position].height;
   for(int i=0;i<nbShelves;i++){
     int heighti = storage[i].height;
     int lengthi = storage[i].availableLength;
     if(i != position){
       if(b.length <= lengthi){
         if(storage[position].height > heighti && heighti >= b.height){
           if(heighti < height){
             shelf = i;
             height = heighti;
           }
         }
       }
     }
   }
   if(shelf != position){
     Box move = storage[position].removeBox(b.id);
     storage[shelf].addBox(move);
   }
 }
 
 /**
  * reorganize the entire warehouse : start with smaller shelves and first box on each shelf.
  */
 public void reorganize (){
  //ADD YOUR CODE HERE
   Box b;
   for(int i =0;i<nbShelves;i++){
     if(storage[i].firstBox != null){
       b = storage[i].firstBox;
       while(b != null){
         Box n = b.next;
         moveOneBox(b, i);
         b = n;
       }
     }
   }
 }
}