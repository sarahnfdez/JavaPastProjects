package assignment2;

public class Shelf {
 
 protected int height;
 protected int availableLength;
 protected int totalLength;
 protected Box firstBox;
 protected Box lastBox;

 public Shelf(int height, int totalLength){
  this.height = height;
  this.availableLength = totalLength;
  this.totalLength = totalLength;
  this.firstBox = null;
  this.lastBox = null;
 }
 
 protected void clear(){
  availableLength = totalLength;
  firstBox = null;
  lastBox = null;
 }
 
 public String print(){
  String result = "( " + height + " - " + availableLength + " ) : ";
  Box b = firstBox;
  while(b != null){
   result += b.id + ", ";
   b = b.next;
  }
  result += "\n";
  return result;
 }
 
 /**
  * Adds a box on the shelf. Here we assume that the box fits in height and length on the shelf.
  * @param b
  */
 public void addBox(Box b){
  //ADD YOUR CODE HERE
   if(this.firstBox == null){
     this.firstBox = b;
     b.previous = null;
   }
   this.availableLength = this.availableLength - b.length;
   if(b != this.firstBox){
     Box temp = this.lastBox;
     b.previous = temp;
     this.lastBox.next = b;
   }
   this.lastBox = b;
 }
 
 /**
  * If the box with the identifier is on the shelf, remove the box from the shelf and return that box.
  * If not, do not do anything to the Shelf and return null.
  * @param identifier
  * @return
  */
 public Box removeBox(String identifier){
  //ADD YOUR CODE HERE
   Box b = this.firstBox;
   if(this.firstBox == null){
     return null;
   }
   if(identifier.equals(this.firstBox.id)){
     this.firstBox = b.next;
     this.availableLength = availableLength+b.length;
     b.next = b.previous = null;
     if(this.firstBox != null){
       this.firstBox.previous = null;
       if(this.firstBox.next == null){
         this.lastBox = this.firstBox;
       }
     }
     if(this.firstBox == null){
       this.lastBox = this.firstBox;
     }
     return b;
   }
   while(b.next != null){
     b = b.next;
     if(identifier.equals(this.lastBox.id)){
       b = this.lastBox;
       this.availableLength = availableLength+b.length;
       this.lastBox = this.lastBox.previous;
       this.lastBox.next = null;
       return b;
     }
     if(identifier.equals(b.id) == true){
       this.availableLength = availableLength+b.length;
       b.next.previous = b.previous;
       b.previous.next = b.next;
       b.previous = b.next = null;
       return b;
     }
   }
   return null;
 }  
 
}