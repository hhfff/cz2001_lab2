
import org.graphstream.graph.Graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;

public class MultiSourceBFS {
    //create the queue to do BFS, the hospital vertexes will be load the the list first
    private LinkedList<Integer> scrList;
    private  int n;//number of vertex in the graph
    //Use an adjacency list to represent the graph
    private ArrayList<ArrayList<Integer>> adjacencyList ;
    private long searchStartTime;
    public long search(String filName){
        searchStartTime=System.nanoTime();

        int dist[]= new int[n];//distance array
        int pred[]=new int[n];// store the BFS tree and later use it to find hospitalFile
        boolean visited[] = new boolean[n];
        //System.out.println(n);
        int i;

        //initialization
        for (i=0; i < n; i++) {
            visited[i]=false;
            dist[i]=Integer.MAX_VALUE;
            pred[i]=-1;
        }
        //hospital vertex are already in the queue, marked as visited and update array values
        for(i=0; i<scrList.size(); i++) {
            visited[scrList.get(i)]=true;
            dist[scrList.get(i)]=0;
            pred[scrList.get(i)]=-1;
        }

        //perform BFS with all the hospital vertexes already at the front of the queue

        while (!scrList.isEmpty()) {
            int u = scrList.remove();
            for (i = 0; i < adjacencyList.get(u).size(); i++) {
                if (visited[adjacencyList.get(u).get(i)]==false) {
                    visited[adjacencyList.get(u).get(i)]= true;
                    dist[adjacencyList.get(u).get(i)]= dist[u] + 1;
                    pred[adjacencyList.get(u).get(i)]= u;
                    scrList.add(adjacencyList.get(u).get(i));
                }
            }
        }
        long totalTime=System.nanoTime()-searchStartTime;
        FileHelper.writeToFile(dist, pred,n, filName);
        return totalTime;
    }

    public long searchRandomGraph(Graph graph, LinkedList<Integer> scrList) {
        adjacencyList= new ArrayList<ArrayList<Integer>>();
        this.scrList=scrList;

        n=graph.getNodeCount();
        //add node to adjancy list
        for (int i = 0; i < n; i++) {
            adjacencyList.add(new ArrayList<Integer>());
        }
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if(graph.getNode(i).hasEdgeBetween(j)) {
                    adjacencyList.get(i).add(j);
                    adjacencyList.get(j).add(i);
                }
            }
        }
        return search("Algo1-Random.txt");
    }
    public long searchRealGraph(ArrayList<ArrayList<Integer>> adjacencyList, LinkedList<Integer> scrList,int n) {
        this.n=n;
        this.adjacencyList=adjacencyList;
        this.scrList=scrList;
        return search("Algo1-RealNetwork.txt");
    }

}
