package Experiments;

import java.util.HashMap;

import Binomial.BinomialTree;
import MyHeap.ClassicHeap;
import MyHeap.FibonacciHeap;
import Utils.MyPair;

public class DijkstraWithHeap<T extends Comparable<T>> {

	public void runTestFib(HashMap<Integer, Double>[] graph, int origin, FibonacciHeap<MyPair<Integer, Double>> heap){

		int[] prev = new int[graph.length];
		double[] dist = new double[graph.length];

		HashMap<Integer, BinomialTree<MyPair<Integer, Double>>> pointers = new HashMap<>();

		// Set initial values
		for(int i = 0; i < graph.length; i++){
			if(i == origin){
				pointers.put(i, heap.insert(new MyPair<Integer, Double>(i, 0.0)));
				dist[i] = 0.0;
			}else{
				pointers.put(i, heap.insert(new MyPair<Integer, Double>(i, Double.MAX_VALUE)));
				dist[i] = Double.MAX_VALUE;
			}
			prev[i] = -1;
		}

		MyPair<Integer,Double> min;

		while(!heap.isEmpty()){
			// Get the vertex i and weight
			min = heap.extractMin();

			// Remove ourselves from the pointers
			pointers.remove(min.getFirst());

			double newDist;
			int minIndex = min.getFirst();
			for(Integer v : graph[minIndex].keySet()){
				newDist = dist[minIndex] + graph[minIndex].get(v);
				if(newDist < dist[v]){
					dist[v] = newDist;
					prev[v] = minIndex;
					heap.decreaseKey(pointers.get(v), new MyPair<Integer, Double>(v, newDist));
				}
			}
		}
	}

	public void runTestHeap(HashMap<Integer, Double>[] graph, int origin, ClassicHeap<MyPair<Integer, Double>> heap){
		double dist[] = new double[graph.length];
		int prev[] = new int[graph.length];

//		HashMap<Integer, BinomialTree<MyPair<Integer, Double>>> pointers = new HashMap<>();

		// Set initial values
		for (int i = 0; i < graph.length; i++) {
			if (i == origin) {
//				pointers.put(i, heap.insert(new MyPair<Integer, Double>(i, 0.0)));
				dist[i] = 0.0;
			} else {
//				pointers.put(i, heap.insert(new MyPair<Integer, Double>(i, Double.MAX_VALUE)));
				dist[i] = Double.MAX_VALUE;
			}
			prev[i] = -1;
		}

		MyPair<Integer, Double> min;

		while (!heap.isEmpty()) {
			// Get the vertex i and weight
			min = heap.extractMin();

			// Remove ourselves from the pointers
//			pointers.remove(min.getFirst());

			double newDist;
			int minIndex = min.getFirst();
			for (Integer v : graph[minIndex].keySet()) {
				newDist = dist[minIndex] + graph[minIndex].get(v);
				if (newDist < dist[v]) {
					dist[v] = newDist;
					prev[v] = minIndex;
//					heap.decreaseKey(pointers.get(v), new MyPair<Integer, Double>(v, newDist));
				}
			}
		}

	}
}
