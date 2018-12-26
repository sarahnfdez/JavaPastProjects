import java.io.*;
import java.util.*;




public class FordFulkerson {
 
  
 public static ArrayList<Integer> pathDFS(Integer source, Integer destination, WGraph graph){
  ArrayList<Integer> Stack = new ArrayList<Integer>();
  /* YOUR CODE GOES HERE */
  ArrayList<Integer> path = new ArrayList<Integer>(); //creates an arraylist for the path
  int nbNodes = graph.getNbNodes(); //number of vertices
  boolean[] visited = new boolean[nbNodes];//creates a boolean to check if the node was visited
  Stack.add(source); //adds source to stack
  while(!Stack.isEmpty()){ //while stack is not empty
    int current = Stack.remove(Stack.size()-1); //pop vertex off stack
    if(visited[current] == false){ //if the vertex has not been visited
      visited[current] = true; //mark as visited
      if(current != source && graph.getEdge(path.get(path.size()-1),current) == null){ //if the vertex is not the source AND it DOES NOT have an edge with the previous node in the stack
        path.remove(path.size()-1); //remove the previous node in the stack
      }
      path.add(current); //add the vertex to the path
      if(current == destination){ //if the vertex is destination, break the loop
        break;
      }
      int counter = 0; //counter to see if there are any adjacent vertices
      for(Edge edge: graph.listOfEdgesSorted()){ //loops through all edges in graph
        if(edge.nodes[0] == current && edge.weight > 0 && visited[edge.nodes[1]] == false){ //if the from vertex is the current vertex and the weight is greater than 0 and it does not go to a previously visited node
          Stack.add(edge.nodes[1]); //add the connected vertex to the stack
          counter++; //increnent adjecent vertex counter
        }
      }
      if (counter == 0){ //if there are no adjacent vertices remove the current from the path
        path.remove(path.size()-1);
      }
    }
  }
  if(path.contains(destination)){ //if the path contains the destination return the path
    return path;
  } else { //else clear the path and return an empty arraylist
    path.clear();
    return path;
  }
 }
 
 
 
 public static void fordfulkerson(Integer source, Integer destination, WGraph graph, String filePath){
  String answer="";
  String myMcGillID = "260663523"; //Please initialize this variable with your McGill ID
  int maxFlow = 0;
  
    /* YOUR CODE GOES HERE */
  try{
  WGraph residual = new WGraph(graph); //creates the residual graph
  WGraph g = new WGraph(graph); //clones the original graph
  ArrayList<Edge> gEdges = g.listOfEdgesSorted(); //creates an arraylist of edges
  for(int i = 0;i<gEdges.size();i++){ //goes through each edge and sets the flow to 0
    Edge temp = gEdges.get(i); //selects edge
    int node1 = temp.nodes[0]; //start node
    int node2 = temp.nodes[1]; //finish node
    g.setEdge(node1, node2, 0);//sets the weight of the edge to 0
  }
  while(!pathDFS(source, destination, residual).isEmpty()){ //while there is an augmenting path
    ArrayList<Integer> path = pathDFS(source, destination, residual); //get the augmenting path
    int minCf = Integer.MAX_VALUE; //set variable for the smallest capacity on the path
    for(int i = 0;i<path.size()-1;i++){//checks all edges in the path for the smallest capacity
      int node1 = path.get(i); //vertex 1
      int node2 = path.get(i+1);//vertex 2
      Edge temp = residual.getEdge(node1, node2); //gets edge
      if(minCf > temp.weight){ //if the edge weight is smaller than the min capacity
        minCf = temp.weight; //the min capacity is the weight
      }
    }
    for(int i = 0;i<path.size()-1;i++){ //edits the flows of the return graph
      int u = path.get(i); //node1
      int v = path.get(i+1); //node2
      if(g.getEdge(u,v) != null){ //if the edge is in the graph
        Edge temp = g.getEdge(u,v); //get the edge 
        g.setEdge(u,v, temp.weight+minCf); // f(u,v) = f(u,v)+minCf
      } else { //if the edge does not exist, then it is a backward edge in the residual graph
        Edge temp = g.getEdge(v,u); //get the edge in the graph
        g.setEdge(v,u, temp.weight-minCf); //subtract the minCf from the weight
      }
      
    }
    maxFlow = maxFlow+minCf; //adds the min capacity to the flow
    for(int i = 0;i<path.size()-1;i++){ //edit the residual graph
      int u = path.get(i); //node1
      int v = path.get(i+1); //node2
      Edge temp1 = residual.getEdge(u, v); //get edge (u,v)
      residual.setEdge(u,v,temp1.weight-minCf); //Cf(u,v) = cf(u,v)-f(u,v)
      if(residual.getEdge(v,u) == null){ //if (v,u) is not in Gf
        Edge e = new Edge(v,u, 0); // make edge (v,u) 
      residual.addEdge(e); // add (v,u) to Gf
      }
      Edge temp2 = residual.getEdge(v, u); //get edge (v,u)
      residual.setEdge(v,u,temp2.weight+minCf); //Cf(v,u) = cf(v,u)+f(v,u)
    }
  }
  
  
  
  answer += maxFlow + "\n" + g.toString(); 
  writeAnswer(filePath+myMcGillID+".txt",answer);
  System.out.println(answer);
 } catch (Exception e){
   maxFlow = -1;
 }
}
 public static void writeAnswer(String path, String line){
  BufferedReader br = null;
  File file = new File(path);
  // if file doesnt exists, then create it
  
  try {
  if (!file.exists()) {
   file.createNewFile();
  }
  FileWriter fw = new FileWriter(file.getAbsoluteFile(),true);
  BufferedWriter bw = new BufferedWriter(fw);
  bw.write(line+"\n"); 
  bw.close();
  } catch (IOException e) {
   e.printStackTrace();
  } finally {
   try {
    if (br != null)br.close();
   } catch (IOException ex) {
    ex.printStackTrace();
   }
  }
 }
 
  public static void main(String[] args){
   String file = args[0];
    //String file = "ff2.txt";
   File f = new File(file);
   WGraph g = new WGraph(file);
   fordfulkerson(g.getSource(),g.getDestination(),g,f.getAbsolutePath().replace(".txt",""));
  }
}
