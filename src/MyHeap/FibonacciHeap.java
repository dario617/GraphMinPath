package MyHeap;

import java.util.ArrayList;
import javafx.util.Pair;
import Binomial.BinomialTree;

public class FibonacciHeap<T extends Comparable<T>> {
	private Pair<T, Integer> min; // Value and index
	private ArrayList<BinomialTree<T>> nodes;
	private long count;
	
	public FibonacciHeap() {
		nodes = new ArrayList<BinomialTree<T>>();
		count = 0L;
	}
	
	public void insert(T value){
		nodes.add(new BinomialTree<T>(value));
		// Update min
		if(count == 0){
			min = new Pair<T, Integer>(value,nodes.size() - 1); 
		}else if(min.getKey().compareTo(value) > 0){
			min = new Pair<T, Integer>(value,nodes.size() - 1);
		}
		count++;
	}
	
	public T peekMin(){
		return min.getKey();
	}
	
	public T extractMin(){
		//Extract min
		BinomialTree<T> minBinT = nodes.get(min.getValue());
		
		count--;

		int index = min.getValue();
		if(minBinT.getDegree() == 1){
			// Easy case
			nodes.remove(index);
		}else{
			// Get children
			BinomialTree<T>[] minChildren = minBinT.getChildren();
			nodes.remove(index);
			// Append Children
			for(int i = 0; i < minChildren.length; i++){
				nodes.add(minChildren[i]);
			}
			// Sort this mess
			consolidate();
		}
		// Find next min
		if(nodes.size() == 0){
			return null;
		}else{
			// Value of the first node
			T newMin = nodes.get(0).getValue();
			int newMinIndex = 0;
			for(int i = 1; i < nodes.size(); i++){
				if(nodes.get(i).getValue().compareTo(newMin) < 0){
					newMin = nodes.get(i).getValue();
					newMinIndex = i;
				}
			}
			// Update value
			min = new Pair<T,Integer>(newMin,newMinIndex);
			return minBinT.getValue();
		}
	}
	
	private void consolidate(){
		// Temp array to consolidate the nodes
		ArrayList<BinomialTree<T>> newNodeArrangement = new ArrayList<>();
		
		// While we have not re arranged every binomial tree
		while(!nodes.isEmpty()){
			BinomialTree<T> tmp = nodes.remove(0);
			
			// Expand the newArrangement if necessary
			while(newNodeArrangement.size() <= tmp.getDegree()){
				newNodeArrangement.add(null);
			}
			
			BinomialTree<T> nnaTmp = newNodeArrangement.get(tmp.getDegree() - 1); 
			while( nnaTmp != null){
				int res = tmp.joinBinomialTree(nnaTmp);
				// If it merged with nnaTmp as the root
				if(res == 0){
					tmp = nnaTmp;
				}
				nnaTmp = newNodeArrangement.get(tmp.getDegree() - 1);
			}
			
			// Expand if the degree increased
			while(newNodeArrangement.size() <= tmp.getDegree()){
				newNodeArrangement.add(null);
			}
			
			newNodeArrangement.set(tmp.getDegree() - 1, tmp);
		}
		
		// We have the new nodes!
		// Let's get rid of null nodes
		nodes = new ArrayList<BinomialTree<T>>();
		for(BinomialTree<T> consolidatedNode : newNodeArrangement){
			if(consolidatedNode != null){
				nodes.add(consolidatedNode);
			}
		}
		
		// Nodes consolidated!
	}
	
	public void print(){
		for(BinomialTree<T> node : nodes){
			node.print();
			System.out.println(" - ");
		}
	}
	
}
