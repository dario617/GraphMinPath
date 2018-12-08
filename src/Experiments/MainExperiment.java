package Experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;

import Experiments.DijkstraNaive;
import Experiments.DijkstraWithHeap;
import MyHeap.ClassicHeap;
import MyHeap.FibonacciHeap;
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
		int[] graphEdges = {10,100};//, 100}; //, 1000};
		
		long init, end;
		int rep = 10;
		
		int[] timeNaive = new int[rep * graphEdges.length];
		int[] timeHeap = new int[rep * graphEdges.length];
		int[] timeFib = new int[rep * graphEdges.length];
		
		for(int j = 0; j < rep; j++){
			System.out.println("========== Repetition: "+j+1+" ==========");
			for (int i = 0; i < graphEdges.length; i++) {
				System.out.println("Building graph with "+graphEdges[i]*vertices+" edges");
				graph = makeGraph(vertices, vertices*graphEdges[i]);
				
				{
					DijkstraNaive expNaive = new DijkstraNaive();
					init = System.currentTimeMillis();
					expNaive.runTest();
					end = System.currentTimeMillis();
					System.out.println("Ran in "+ (end - init));
					timeNaive[graphEdges.length * i + j] = (int) (end - init);
				}
				{
					DijkstraWithHeap<MyPair<Integer,Double>> expHeap = new DijkstraWithHeap<>();
					init = System.currentTimeMillis();
					expHeap.runTestHeap(graph, 0, new ClassicHeap<MyPair<Integer,Double>>());
					end = System.currentTimeMillis();
					System.out.println("Ran in "+ (end - init));
					timeHeap[graphEdges.length * i + j] = (int) (end - init);
				}
				{
					DijkstraWithHeap<MyPair<Integer,Double>> expHeap = new DijkstraWithHeap<>();
					init = System.currentTimeMillis();
					expHeap.runTestFib(graph, 0, new FibonacciHeap<MyPair<Integer,Double>>());
					end = System.currentTimeMillis();
					System.out.println("Ran in "+ (end - init));
					timeFib[graphEdges.length * i + j] = (int) (end - init);
				}
			}			
		}
		
		// Write results to csv
		File f = new File("results.csv");
		try {
			FileWriter fw = new FileWriter(f);
			
			for(int i = 0; i < timeNaive.length - 1; i++){
				fw.write(timeNaive[i]+",");	
			}
			fw.write(timeNaive[timeNaive.length - 1]+"\n");
			fw.flush();
			
			for(int i = 0; i < timeHeap.length - 1; i++){
				fw.write(timeHeap[i]+",");	
			}
			fw.write(timeHeap[timeHeap.length - 1]+"\n");
			fw.flush();
			
			for(int i = 0; i < timeFib.length - 1; i++){
				fw.write(timeFib[i]+",");	
			}
			fw.write(timeFib[timeFib.length - 1]+"\n");
			fw.flush();
			
			fw.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.err.println("Error Opennig the file!!");
			e.printStackTrace();
		}
		
		System.out.println("Finished");
	}
}
