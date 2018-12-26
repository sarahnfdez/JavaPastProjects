package A2;
import java.util.*;

class Assignment implements Comparator<Assignment>{
 int number;
 int weight;
 int deadline;
 
 
 protected Assignment() {
 }
 
 protected Assignment(int number, int weight, int deadline) {
  this.number = number;
  this.weight = weight;
  this.deadline = deadline;
 }
 
 
 
 /**
  * This method is used to sort to compare assignment objects for sorting. 
  * The way you implement this method will define which order the assignments appear in when you sort.
  * Return -1 if a1 should appear after a2
  * Return 1 if a1 should appear before a2
  * Return 0 if a1 and a2 are equivalent 
  */
 @Override
 public int compare(Assignment a1, Assignment a2) {
  //YOUR CODE GOES HERE, DONT FORGET TO EDIT THE RETURN STATEMENT
   if(a2.weight > a1.weight){//if a2 weighs more than a1, a2 should come before a1
     return 1;
   } else if (a2.weight < a1.weight){ //if a1 weighs more than a2, a1 should come before a2
       return -1;
   } else {//if both weights are the same
    if (a2.deadline > a1.deadline){ //if a2's deadline is later than a1's, a2 should come after a1
       return -1;
     } else if (a2.deadline < a1.deadline){//if a1's deadline is later than a2's, then a1 should come after a2
       return 1;
     } else {//if both deadlines and both weights are the same return 0
       return 0;
     }
   }
 }
}

public class HW_Sched {
 ArrayList<Assignment> Assignments = new ArrayList<Assignment>();
 int m;
 int lastDeadline = 0;
 
 protected HW_Sched(int[] weights, int[] deadlines, int size) {
  for (int i=0; i<size; i++) {
   Assignment homework = new Assignment(i, weights[i], deadlines[i]);
   this.Assignments.add(homework);
   if (homework.deadline > lastDeadline) {
    lastDeadline = homework.deadline;
   }
  }
  m =size;
 }
 
 
 /**
  * 
  * @return Array where output[i] corresponds to when assignment #i will be completed. output[i] is 0 if assignment #i is never completed.
  * The homework you complete first will be given an output of 1, the second, 2, etc.
  */
 public int[] SelectAssignments() {
  //Use the following command to sort your Assignments: 
  //Collections.sort(Assignments, new Assignment());
  //This will re-order your assignments. The resulting order will depend on how the compare function is implemented
  Collections.sort(Assignments, new Assignment());
  
  //Initializes the homeworkPlan, which you must fill out and output
  int[] homeworkPlan = new int[Assignments.size()];
  //YOUR CODE GOES HERE
  int dmax = 0;//maximum deadline value
  for(int i = 0;i<Assignments.size();i++){//creates the maximum deadline value by looking through all the deadlines
    Assignment temp = Assignments.get(i);
    int d = temp.deadline;
    if(d > dmax){
      dmax = d;
    }
  }
  int[] hwp = new int[dmax];//creates a temp homeworkplan where index is the hour to do hw and value is the assignment number
  for(int i = 0;i<dmax;i++){ //initializes all values in hwp to -1 for clarity
    hwp[i] = -1;
  }
  for(int i = 0;i<Assignments.size();i++){ //runs through assignments
    Assignment temp = Assignments.get(i); //gets assignment
    int tempDead = temp.deadline; //gets assignment deadline
    int k;
    if (dmax < tempDead){ //if deadline > max deadline
      k = dmax; //k is max
    } else { //if deadline < max deadline
      k = tempDead; //k is deadline
    }
    if(k >= 1 && hwp[k-1] == -1){ //if k>=1 and the hour is available to do homework
      hwp[k-1] = temp.number;//add the assignment number to the hour slot
    }
  }
  for(int i=0;i<dmax;i++){
    int assignmentNumber = hwp[i]; //gets the assignment number
    homeworkPlan[assignmentNumber] = i+1; //places the hour in the assignment number
  }
  
  return homeworkPlan;
 }
}
 



