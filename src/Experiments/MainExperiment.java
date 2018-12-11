package Experiments;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import Experiments.DijkstraNaive;
import Experiments.DijkstraWithHeap;
import MyHeap.ClassicHeap;
import MyHeap.FibonacciHeap;
import Utils.MyPair;

public class MainExperiment {

	public static int maxAttemps = 1000;

	public static HashMap<Integer, Double>[] makeGraph(int v, long e) {

		HashMap<Integer, Double>[] graph = new HashMap[v];

		// Set random seed
		Random rn = new Random();
		rn.setSeed(System.currentTimeMillis());
		double weight;

		// Create all Vertices
		for (int i = 0; i < v; i++) {
			graph[i] = new HashMap<>();
		}

		for (int i = v - 1; i > 0; i--) {
			weight = rn.nextDouble() + 0.00001;
			// Connect them to the previous one
			graph[i].put(i - 1, weight);
			// Connect them to the next one
			graph[i - 1].put(i, weight);
		}

		System.out.println(" - Created vertices");

		long limit = e - v + 1;
		if (limit < 0) {
			System.err.println("Alert overflow!");
			System.exit(1);
		}

		// Add e -v + 1 random edges
		int connectionIndex;
		int vertexIndex;
		int Attemps;
		for (long i = 0; i < limit; i++) {
			vertexIndex = rn.nextInt(v);
			connectionIndex = rn.nextInt(v);
			Attemps = 1;
			while (graph[vertexIndex].containsKey(connectionIndex) || vertexIndex == connectionIndex) {
				if (Attemps % (maxAttemps) == 0) {
					vertexIndex = rn.nextInt(v);
				}
				connectionIndex = rn.nextInt(v);
				Attemps++;
			}
			// Put the values on both lists
			weight = rn.nextDouble() + 0.00001;
			graph[vertexIndex].put(connectionIndex, weight);
			graph[connectionIndex].put(vertexIndex, weight);
		}
		System.out.println(" - Added vertices");

		return graph;
	}

	public static void main(String[] args) {

		System.out.println("Starting tests");

		HashMap<Integer, Double>[] graph;
		int vertices = 100000;
		int[] graphEdges = {10, 100};// , 100}; //, 1000};

		long init, end;
		int rep = 6;

		int[] timeNaive = new int[rep * graphEdges.length];
		int[] timeHeap = new int[rep * graphEdges.length];
		int[] timeFib = new int[rep * graphEdges.length];

		for (int j = 0; j < rep; j++) {
			System.out.println("========== Repetition: " + (j + 1) + " ==========");
			for (int i = 0; i < graphEdges.length; i++) {
				System.out.println("Building graph with " + graphEdges[i] * vertices + " edges");
				graph = makeGraph(vertices, vertices * graphEdges[i]);
				System.out.println("Graph is ready!");
				{
					DijkstraNaive expNaive = new DijkstraNaive();
					init = System.currentTimeMillis();
					expNaive.runTest(graph, 0);
					end = System.currentTimeMillis();
					System.out.println("Ran Naive in " + (end - init));
					timeNaive[rep * i + j] = (int) (end - init);
				}
				{
					DijkstraWithHeap<MyPair<Integer, Double>> expHeap = new DijkstraWithHeap<>();
					init = System.currentTimeMillis();
					//expHeap.runTestHeap(graph, 0, new ClassicHeap<MyPair<Integer, Double>>(graph.length));
					end = System.currentTimeMillis();
					System.out.println("Ran with Heap in " + (end - init));
					timeHeap[rep * i + j] = (int) (end - init);
				}
				{
					DijkstraWithHeap<MyPair<Integer, Double>> expHeap = new DijkstraWithHeap<>();
					init = System.currentTimeMillis();
					expHeap.runTestFib(graph, 0, new FibonacciHeap<MyPair<Integer, Double>>());
					end = System.currentTimeMillis();
					System.out.println("Ran Fibonacci Heap in" + (end - init));
					timeFib[rep * i + j] = (int) (end - init);
				}
			}
		}

		// Write results to csv
		File f = new File("results.csv");
		try {
			FileWriter fw = new FileWriter(f);

			for (int i = 0; i < timeNaive.length - 1; i++) {
				fw.write(timeNaive[i] + ",");
			}
			fw.write(timeNaive[timeNaive.length - 1] + "\n");
			fw.flush();

			for (int i = 0; i < timeHeap.length - 1; i++) {
				fw.write(timeHeap[i] + ",");
			}
			fw.write(timeHeap[timeHeap.length - 1] + "\n");
			fw.flush();

			for (int i = 0; i < timeFib.length - 1; i++) {
				fw.write(timeFib[i] + ",");
			}
			fw.write(timeFib[timeFib.length - 1] + "\n");
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
