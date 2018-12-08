package MyHeap;

import java.util.ArrayList;
import javafx.util.Pair;
import Binomial.BinomialTree;

/**
 * 
 * @author dario_local
 *
 * @param <T>
 */
public class FibonacciHeap<T extends Comparable<T>> implements AHeap<T> {
	private Pair<T, Integer> min; // Value and index
	private ArrayList<BinomialTree<T>> nodes;
	private long count;

	public FibonacciHeap() {
		nodes = new ArrayList<BinomialTree<T>>();
		count = 0L;
	}

	/**
	 * Returns the reference so it can be stored somewhere else 
	 * to help the decreaseKey operation
	 * @param value
	 * @return ptr to the new binomialTree Node
	 */
	public BinomialTree<T> insert(T value){
		BinomialTree<T> ptr = new BinomialTree<T>(value);
		nodes.add(ptr);
		// Update min
		if (count == 0) {
			min = new Pair<T, Integer>(value, nodes.size() - 1);
		} else if (min.getKey().compareTo(value) > 0) {
			min = new Pair<T, Integer>(value, nodes.size() - 1);
		}
		count++;
		return ptr;
	}

	@Override
	public T peekMin() {
		return min.getKey();
	}

	@Override
	public T extractMin() {
		// Extract min
		BinomialTree<T> minBinT = nodes.get(min.getValue());

		count--;

		int index = min.getValue();
		if (minBinT.getDegree() == 1) {
			// Easy case
			nodes.remove(index);
		} else {
			// Get children
			BinomialTree<T>[] minChildren = minBinT.getChildren();
			nodes.remove(index);
			// Append Children
			for (int i = 0; i < minChildren.length; i++) {
				// Reset parent pointer
				minChildren[i].setParentRef(null);
				nodes.add(minChildren[i]);
			}
		}
		// Sort this mess
		consolidate();
		// Find next min
		if (nodes.size() == 0) {
			return null;
		} else {
			updateMin();
			return minBinT.getValue();
		}
	}

	/**
	 * This sorts my life, I mean the complexity!
	 */
	private void consolidate() {
		// Temp array to consolidate the nodes
		ArrayList<BinomialTree<T>> newNodeArrangement = new ArrayList<>();

		// While we have not re arranged every binomial tree
		while (!nodes.isEmpty()) {
			BinomialTree<T> tmp = nodes.remove(0);

			// Expand the newArrangement if necessary
			while (newNodeArrangement.size() <= tmp.getDegree()) {
				newNodeArrangement.add(null);
			}

			BinomialTree<T> nnaTmp = newNodeArrangement.get(tmp.getDegree() - 1);
			while (nnaTmp != null) {
				// This operation sets the parent reference if successful
				int res = tmp.joinBinomialTree(nnaTmp);
				// If it merged with nnaTmp as the root
				if (res == 0) {
					tmp = nnaTmp;
				}
				nnaTmp = newNodeArrangement.get(tmp.getDegree() - 1);
				// Delete previous position
				newNodeArrangement.set(tmp.getDegree() - 2, null);
			}

			// Expand if the degree increased
			while (newNodeArrangement.size() <= tmp.getDegree()) {
				newNodeArrangement.add(null);
			}

			newNodeArrangement.set(tmp.getDegree() - 1, tmp);
		}

		// We have the new nodes!
		// Let's get rid of null nodes
		nodes = new ArrayList<BinomialTree<T>>();
		for (BinomialTree<T> consolidatedNode : newNodeArrangement) {
			if (consolidatedNode != null) {
				nodes.add(consolidatedNode);
			}
		}

		// Nodes consolidated!
	}

	/**
	 * @param value
	 *            to update
	 * @param newKey
	 *            to put on value's position
	 */
	public void decreaseKey(BinomialTree<T> value, T newKey) {
		// Update key
		value.setValue(newKey);
		
		// It's the root!
		if(value.getParentRef() == null){ return ; }

		// Check if parent is bigger (heap does not hold)
		if(value.getParentRef().getValue().compareTo(value.getValue()) > 0){
			// Call cut
			ArrayList<BinomialTree<T>> cutted = value.cut();
			for(BinomialTree<T> bt : cutted){
				nodes.add(bt);
			}
		}
		
		updateMin();
	}
	
	private void updateMin(){
		// Value of the first node
		T newMin = nodes.get(0).getValue();
		int newMinIndex = 0;
		for (int i = 1; i < nodes.size(); i++) {
			if (nodes.get(i).getValue().compareTo(newMin) < 0) {
				newMin = nodes.get(i).getValue();
				newMinIndex = i;
			}
		}
		// Update value
		min = new Pair<T, Integer>(newMin, newMinIndex);
	}

	public void print() {
		for (BinomialTree<T> node : nodes) {
			node.print();
			System.out.println(" - ");
		}
	}

}
