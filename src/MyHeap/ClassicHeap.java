package MyHeap;

import java.util.ArrayList;

import Binomial.BinomialTree;
import Utils.MyPair;

public class ClassicHeap<T extends Comparable<T>> implements AHeap<T> {
	
	private ArrayList<T> nodes;	
	
	public ClassicHeap(int cap) {
		nodes= new ArrayList<T>();
		nodes.ensureCapacity(cap);
		nodes.add(0, null);
	}

	@Override
	public T peekMin() {
		if(this.isEmpty()) {
			return nodes.get(0);
		}else {
			return nodes.get(1);
		}
	}

	@Override
	public T extractMin() {
		if(this.isEmpty()) {
			return nodes.get(0);
		}
		
		T min = nodes.get(1);
		int lastIndex = nodes.size();
		T newMin = nodes.get(lastIndex);
		nodes.remove(lastIndex);		
		nodes.set(1, newMin);
		this.heapify(1);
		
		return min;
	}

	private void heapify(int index) {
		if(!isLeaf(index)) {
			int leftChild = this.leftChild(index);
			int rightChild = this.rightChild(index);
			
			if(nodes.get(index).compareTo(nodes.get(leftChild)) > 0  || nodes.get(index).compareTo(nodes.get(rightChild)) > 0) {
				if(nodes.get(leftChild).compareTo(nodes.get(rightChild)) < 0) {
					this.swap(index, leftChild);
					this.heapify(leftChild);
				}else {
					this.swap(index, rightChild);
					this.heapify(rightChild);
				}
			}
		}
	}
	
	public void heapify() {
        for (int index = (this.size() / 2); index >= 1; index--) {
            heapify(index);
        }
    }

	private void swap(int index, int index2) {
		T aNode = nodes.get(index);
		nodes.set(index, nodes.get(index2));
		nodes.set(index2, aNode);		
	}

	private int rightChild(int index) {		
		return (2 * index) + 1;
	}

	private int leftChild(int index) {		
		return 2 * index;
	}

	private boolean isLeaf(int index) {
		if(index > (this.size() / 2) && index <= this.size()) {
			return true;
		}
		return false;
	}

	public T insert(T node) {
		int newIndex = this.size() + 1;
		nodes.add(newIndex, node);
		if(newIndex > 1) {
			int parentIndex = this.parent(newIndex);
			while(nodes.get(newIndex).compareTo(nodes.get(parentIndex)) < 1) {
				this.swap(newIndex, parentIndex);
				newIndex = parentIndex;
			}			
		}
		return node;
	}

	private int parent(int index) {
		return index / 2;
	}

	public boolean isEmpty() {		
		return nodes.size() <= 1;
	}
	
	public int size() {
		return nodes.size() - 1;
	}

	public void decreaseKey(MyPair<Integer, Double> node, MyPair<Integer, Double> myPair) {
		node.setSecond(myPair.getSecond());		
	}
}
