import java.util.*;

public class BellmanFord{

 
 /**
  * Utility class. Don't use.
  */
 public class BellmanFordException extends Exception{
  private static final long serialVersionUID = -4302041380938489291L;
  public BellmanFordException() {super();}
  public BellmanFordException(String message) {
   super(message);
  }
 }
 
 /**
  * Custom exception class for BellmanFord algorithm
  * 
  * Use this to specify a negative cycle has been found 
  */
 public class NegativeWeightException extends BellmanFordException{
  private static final long serialVersionUID = -7144618211100573822L;
  public NegativeWeightException() {super();}
  public NegativeWeightException(String message) {
   super(message);
  }
 }
 
 /**
  * Custom exception class for BellmanFord algorithm
  *
  * Use this to specify that a path does not exist
  */
 public class PathDoesNotExistException extends BellmanFordException{
  private static final long serialVersionUID = 547323414762935276L;
  public PathDoesNotExistException() { super();} 
  public PathDoesNotExistException(String message) {
   super(message);
  }
 }
 
    private int[] distances = null;
    private int[] predecessors = null;
    private int source;

    BellmanFord(WGraph g, int source) throws BellmanFordException{
        /* Constructor, input a graph and a source
         * Computes the Bellman Ford algorithm to populate the
         * attributes 
         *  distances - at position "n" the distance of node "n" to the source is kept
         *  predecessors - at position "n" the predecessor of node "n" on the path
         *                 to the source is kept
         *  source - the source node
         *
         *  If the node is not reachable from the source, the
         *  distance value must be Integer.MAX_VALUE
         *  
         *  When throwing an exception, choose an appropriate one from the ones given above
         */
        
        /* YOUR CODE GOES HERE */
      ArrayList<Edge> edges = g.getEdges();
      int[] dist = new int[g.getNbNodes()]; //create the private array of integers for distance
      int[] pre = new int[g.getNbNodes()]; //create the private array of integers for predecessors
      for(int i = 0; i < g.getNbNodes();i++){ //initialize distances/predecessors to vertices as infinite
        dist[i] = Integer.MAX_VALUE;
        pre[i] = Integer.MAX_VALUE;
      }
      dist[source] = 0; //set the source distance as 0
      pre[0] = 0;
      
      for(int i = 1;i<g.getNbNodes()-1;i++){ //cycles through the graph V-1 times
        for(int j = 0; j<edges.size();j++){ //cycles through all the edges of the graph
          int u = edges.get(j).nodes[0]; //gets first node of edge
          int v = edges.get(j).nodes[1]; //gets second node of edge
          int weight = edges.get(j).weight; //gets weight of edge
          if(dist[u] != Integer.MAX_VALUE && dist[u]+weight < dist[v]){ //if u is a previously reached node and the distance at v < distance of u + weight 
            dist[v] = dist[u] + weight; //distance at v is the distance at u + weight
            pre[v] = u; //u is the predecessor of v
          }
        }
      }
      
      for(int j = 0; j< edges.size();j++){ //goes through all edges
        int u = edges.get(j).nodes[0]; //node 1 of edge
        int v = edges.get(j).nodes[1]; //node 2 of edge
        int weight = edges.get(j).weight; //weight of edge
        if(dist[u] != Integer.MAX_VALUE && dist[u]+weight < dist[v]){ //checks if there is a negative weight cycle and throws exception
          throw new NegativeWeightException("The graph contains a negative weight cycle; therefore, a BellmanFord cannot be created.");
        }
      }
      this.predecessors = pre; //sets predecessors
      this.distances = dist; //sets distances
      this.source = source; //sets source
    }

    public int[] shortestPath(int destination) throws BellmanFordException{
        /*Returns the list of nodes along the shortest path from 
         * the object source to the input destination
         * If not path exists an Exception is thrown
         * Choose appropriate Exception from the ones given 
         */

        /* YOUR CODE GOES HERE (update the return statement as well!) */
        int predecessor = this.predecessors[destination]; //gets the predecessor to the dest
        if(predecessor == Integer.MAX_VALUE){ //if the predecessor is MAX_VALUE, it does not ever have a path from source; throw exception
          throw new PathDoesNotExistException("There is no path from the source to this node.");
        }
        int[] tempList = new int[predecessors.length]; //creates a temporary list to hold the predecessors in order
        for(int i = 0; i < tempList.length;i++){ //arbitrarily fills the temp list with inf
          tempList[i] = Integer.MAX_VALUE;
        }
        int i = 0; //counter
        while(predecessor != this.source){ //while the predecessor is not the source
          tempList[i] = predecessor; //add the predecessor to the temp list
          predecessor = this.predecessors[predecessor]; //new predecessor is predecessor to the predecessor
          if(predecessor == Integer.MAX_VALUE){ //if the predecessor is MAX_VALUE, it does not ever have a path from source; throw exception
            throw new PathDoesNotExistException("There is no path from the source to this node.");
          }
          i++; //increment the counter
        }
        tempList[i] = predecessor; //adds source to predecessor list
        int size = 0; //creates size variable
        for(int j = 0;j<tempList.length;j++){ //finds the amount of predecessors in the array
          if(tempList[j] != Integer.MAX_VALUE){
            size++;
          }
        }
        int[] path = new int[size+1];//create a path variable to hold the predecessors
        i = 0;//reset i to 0
        for(int k = size-1;k>=0;k--){//moves backwards through the temp list, filling the path list forwards
          path[i] = tempList[k]; //inputs predecessors
          i++; //increment i
        }
        path[i] = destination;//adds destination to the path
        return path; //return the path
    }

    public void printPath(int destination){
        /*Print the path in the format s->n1->n2->destination
         *if the path exists, else catch the Error and 
         *prints it
         */
        try {
            int[] path = this.shortestPath(destination);
            for (int i = 0; i < path.length; i++){
                int next = path[i];
                if (next == destination){
                    System.out.println(destination);
                }
                else {
                    System.out.print(next + "-->");
                }
            }
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }
    }

    public static void main(String[] args){

        String file = args[0];
      //String file = "bf4.txt";
        WGraph g = new WGraph(file);
        try{
            BellmanFord bf = new BellmanFord(g, g.getSource());
            bf.printPath(g.getDestination());
        }
        catch (BellmanFordException e){
            System.out.println(e);
        }

   } 
}
