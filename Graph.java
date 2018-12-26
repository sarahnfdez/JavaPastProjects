package assignment4Graph;

public class Graph {
 
 boolean[][] adjacency;
 int nbNodes;
 
 public Graph (int nb){
  this.nbNodes = nb;
  this.adjacency = new boolean [nb][nb];
  for (int i = 0; i < nb; i++){
   for (int j = 0; j < nb; j++){
    this.adjacency[i][j] = false;
   }
  }
 }
 
 public void addEdge (int i, int j){
  // ADD YOUR CODE HERE
   adjacency[i][j] = true;
   adjacency[j][i] = true;
 }
 
 public void removeEdge (int i, int j){
  // ADD YOUR CODE HERE
   adjacency[i][j] = false;
   adjacency[j][i] = false;
 }
 
 public int nbEdges(){
  // ADD YOUR CODE HERE
   int nbEdges = 0;
   for(int i = 0; i < nbNodes;i++){
     for(int k = 0;k < nbNodes;k++){
       if(this.adjacency[i][k] == true){
         nbEdges++;
       }
     }
   }
   nbEdges = nbEdges/2;
  return nbEdges; // DON'T FORGET TO CHANGE THE RETURN
 }
 
 public boolean dfsCycle(int test, boolean visited[], int prev){
   visited[test] = true;  //marks the node as visited
   for(int i = 0; i <nbNodes; i++){  //runs through every possible edge with the tested node
     if(adjacency[test][i] == true && test != i && i != prev){   //if a node has an edge with another node, it is not a self loop, and it is not the edge with the previous node
       if(!visited[i]){  //if the node has not been visited, visit the node
         dfsCycle(i, visited, test);
       } else if (visited[i] == true){  //if the node has been visited, return true
         return true;
       }
     }
   }
   return false;
 }
 
 public boolean cycle(int start){
  // ADD YOUR CODE HERE
   boolean[] visited = new boolean[nbNodes];
   return dfsCycle(start, visited, -1); //uses depth first search to determine a cycle
 }
 
 public int bfsPath(int start, int end, boolean visited[], int distance[]){
   visited[start] = true;
   for(int i=0;i<nbNodes;i++){
     if(adjacency[start][i] == true){
       if(visited[i]){
         if(distance[i] > distance[start]+1){
           distance[i] = distance[start]+1;
         }
       }
       if(!visited[i]){
         distance[i] = distance[start]+1;
         bfsPath(i, end, visited, distance);
       } 
       
     }
   }
   if(distance[end] == 0){
     return nbNodes+1;
   } else {
     return distance[end];
   }
 }
 
 public int shortestPath(int start, int end){
  // ADD YOUR CODE HERE
  boolean visited[] = new boolean[nbNodes];
  int distance[] = new int[nbNodes];
  distance[start] = 0;
  return bfsPath(start, end, visited, distance);
 }
 
}
