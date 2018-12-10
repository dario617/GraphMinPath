package Experiments;

import java.util.HashMap;
import java.util.Map.Entry;

public class DijkstraNaive {

	public void runTest(HashMap<Integer, Double>[] graph, int origin){

		int[] prev = new int[graph.length];
		double[] dist = new double[graph.length];
		boolean[] marked = new boolean[graph.length];
		
		// Initialize
		for(int i = 0; i < graph.length; i++){
			if(i == origin){
				dist[i] = 0.0;
			}else{
				dist[i] = Double.MAX_VALUE;
			}
			
			marked[i] = false;
			prev[i] = -1;
		}
		
		double minDist, bufdist;
		int minNode, u, v;
		
		for(int i = 0; i < graph.length; i++){
			
			minDist = Double.MAX_VALUE;
			minNode = -1;
			for(int j = 0; j < graph.length; j++){
				if(!marked[j] && dist[j] < minDist){
					minDist = dist[j];
					minNode = j;
				}
			}
			
			u = minNode;
			marked[u] = true;
			
			
			// Update distances
			for(Entry<Integer,Double> vEntry : graph[u].entrySet()){
				v = vEntry.getKey();
				bufdist = dist[u] + graph[u].get(v);
				if(dist[v] > bufdist){
					dist[v] = bufdist;
					prev[v] = u;
				}
			}
			
		}
		
	}
}
