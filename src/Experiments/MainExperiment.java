package Experiments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import Experiments.DijkstraNaive;
import Experiments.DijkstraWithHeap;
import Utils.MyPair;

public class MainExperiment {

	public static int maxAttemps = 1000; 
	
	public static HashMap<Integer, Double>[] makeGraph(int v, long e){
		
		HashMap<Integer, Double>[] graph = new HashMap[v];
		
		// Create all Vertices
		for(int i = 0; i < v - 1; i++){
			// Connect them to the next one
			HashMap<Integer,Double> edgeList = new HashMap<>();
			edgeList.put(i+1,1.0);
			graph[i] = edgeList;
		}
		graph[v-1] =  new HashMap<>(v/2);
		
		for(int i = v - 1; i > 0; i--){
			// Connect them to the previous one
			HashMap<Integer,Double> edgeList = graph[i];
			edgeList.put(i-1,1.0);
			graph[i] = edgeList;
		}
		
		System.out.println(" - Created vertices");
		
		long limit = e - v + 1;
		if(limit < 0){
			System.err.println("Alert overflow!");
			System.exit(1);
		}
		// Set random seed
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		
		// Add e -v + 1 random edges
		int connectionIndex;
		int vertexIndex;
		int Attemps;
		double weight;
		for(long i = 0; i < limit; i++){
			vertexIndex = rn.nextInt(v);
			connectionIndex = rn.nextInt(v);
			Attemps = 1;
			while(graph[vertexIndex].containsKey(connectionIndex)){
				if(Attemps%(maxAttemps) == 0){
					vertexIndex = rn.nextInt(v);
				}
				connectionIndex = rn.nextInt(v);
				Attemps++;
			}
			// Put the values on both lists
			weight = rn.nextDouble() + 0.000001;
			graph[vertexIndex].put(connectionIndex, weight);
			graph[connectionIndex].put(vertexIndex, weight);
		}
		System.out.println(" - Added vertices");
		
		return graph;
	}
	
	public static void main(String[] args){
		
		System.out.println("Starting tests");
		
		HashMap<Integer, Double>[] graph;
		int vertices = 100000;
		int[] graphEdges = {10, 100}; //, 1000};
		
		for (int i = 0; i < graphEdges.length; i++) {
			System.out.println("Building graph with "+graphEdges[i]+" edges");
			graph = makeGraph(vertices, vertices*graphEdges[i]);
				
		}
		
		System.out.println("Finished");
	}
}
