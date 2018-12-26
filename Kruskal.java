package A2;
import java.util.*;

public class Kruskal{

    public static WGraph kruskal(WGraph g){

        /* Fill this method (The statement return null is here only to compile) */
      WGraph minSpan = new WGraph(); //creates the empty min spanning tree
      DisjointSets sets = new DisjointSets(g.getNbNodes()); //creates the disjoint sets
      int count = 0;//creates a count to check if we have the minimmum number of edges
      int minEdges = g.getNbNodes()-1; //variable to check if we have the minimum number of edges
      ArrayList<Edge> sortedEdges = g.listOfEdgesSorted();//edges sorted by weight
      for(int i =0;i< sortedEdges.size();i++){//loops through sortedEdges
        Edge temp = sortedEdges.get(i);//edge we focus on
        int tempNode1 = temp.nodes[0];//node 1 of edge
        int tempNode2 = temp.nodes[1];//node 2 of edge
        if(IsSafe(sets, temp)){//if the edge is safe
          sets.union(tempNode1, tempNode2); //union of node 1 and 2 in the set 
          count++;//increment count
          minSpan.addEdge(temp);//add the edge to the min spanning tree
        }
        if (count == minEdges){//if count == the number of edges that should be in a min spanning tree
          break;//break the for loop
        }
      }
        return minSpan; //return the min spanning tree
    }

    public static Boolean IsSafe(DisjointSets p, Edge e){

        /* Fill this method (The statement return 0 is here only to compile) */
      int node1 = e.nodes[0];//node 1
      int node2 = e.nodes[1];// node 2
      if(p.find(node1) != p.find(node2)){//if node 1 and node 2 are in disjoint sets (reps are not the same)
        return true; //return true
      }
        return false; //if the nodes are in a component set (reps are the same), return false
    }

    public static void main(String[] args){
       System.out.println("Working Directory = " + System.getProperty("user.dir"));
        String file = "g1.txt";
        WGraph g = new WGraph(file);
        WGraph t = kruskal(g);
        System.out.println(t);

   } 
}
