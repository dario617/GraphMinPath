package Binomial;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;

/**
 * 
 * @author dario_local
 *
 * @param <T>
 */
public class BinomialTree<T extends Comparable<T>> {
	private T value;
	private int degree;
	private boolean marked;
	private BinomialTree<T>[] children;
	private BinomialTree<T> parentRef;

	public BinomialTree(T val) {
		this.value = val;
		this.degree = 1;
		this.marked = false;
		this.parentRef = null;
	}

	/**
	 * @param bt
	 *            Binomial tree to be added to current
	 * @return 1 0 or -1, 1 if the current node added the new one, 0 if it was
	 *         appended to the node or -1 if the nodes had different degree
	 */
	public int joinBinomialTree(BinomialTree<T> bt) {
		if (bt == null) {
			return 1;
		}

		if (degree != bt.getDegree()) {
			return -1;
		} else {
			// Add to this binomial tree
			if (value.compareTo(bt.getValue()) <= 0) {
				// Reshape children array and degree
				degree++;
				BinomialTree<T>[] newAllocation = new BinomialTree[degree - 1];
				
				// Reference this
				bt.setParentRef(this);
				
				// The first node to be inserted
				if (degree == 2) {
					children = newAllocation;
					children[0] = bt;
					return 1;
				} else {
					// Copy the references to this array
					// Parents remain the same but the last index
					for (int i = 0; i < degree - 2; i++) {
						newAllocation[i] = children[i];
					}
					children = newAllocation;
					children[degree - 2] = bt;
				}

				return 1;
			} else {
				bt.joinBinomialTree(this);
				return 0;
			}
		}
	}

	// TODO 
	public void print() {
		print(0);
	}

	// TODO
	public void print(int level) {
		System.out.print("level: " + level + " val: " + value.toString());
		for (BinomialTree<T> n : children) {
			if(n != null ){
				n.print(level + 1);
			}
		}
	}

	/**
	 * Cuts the binomialTree until no more marked bt are found
	 * or we reach the root
	 * @return the cutted elements
	 */
	public ArrayList<BinomialTree<T>> cut() {
		
		ArrayList<BinomialTree<T>> ans = new ArrayList<>();
		
		// Local reference
		BinomialTree<T> current = this;
		BinomialTree<T> parent = current.getParentRef();
		
		while(parent != null){
			
			// Delete parent reference
			current.setParentRef(null);
			// Find the parent's reference and cut it
			BinomialTree<T>[] ch = parent.getChildren();
			for(int i = 0; i < ch.length; i++){
				if(ch[i] != null && ch[i] == current){
					ch[i] = null;
					break;
				}
			}
			// Decrease the degree accordingly
			parent.checkDegree();
			
			// Add the current node to the ans
			ans.add(current);
			
			// If we find a cool parent
			if(!parent.isMarked()){
				// Mark it
				parent.setMarked(true);
				break;
			}
			// If it is marked we have to cut it anyway
			parent.setMarked(false);

			// Update loop references
			current = parent;
			parent = current.getParentRef();
		}
		
		if(ans.size() == 0){
			return null;
		}else{
			return ans;
		}
	}
	
	/**
	 * Check that the degree holds:
	 * there is one child with degree equal to this.degree - 1
	 */
	private void checkDegree(){
		int maxDegreeOnChildren = 0;
		for(int i = 0; i < children.length; i++){
			if(children[i] != null && children[i].getDegree() > maxDegreeOnChildren){
				maxDegreeOnChildren = children[i].getDegree();
			}
		}
		// If the property no longer holds
		if(maxDegreeOnChildren < degree - 1){
			degree = maxDegreeOnChildren + 1;
			// Shuffle elements
			this.shuffleChildren();
		}
	}
	
	/**
	 * Rearrange the pointers to fit the updated degree
	 * If the degree is the same nothing will happen 
	 */
	private void shuffleChildren(){
		if(degree == 0){
			degree = 1;
			children = null;
			return;
		}
		BinomialTree<T>[] newChildrenArray = new BinomialTree[degree - 1];
		int ncaIndex = 0;
		for(int i = 0; i < children.length; i++){
			if(children[i] != null){
				newChildrenArray[ncaIndex] = children[i];
				ncaIndex++;
			}
		}
		children = newChildrenArray;
	}
	
	public T getValue() {
		return value;
	}

	public void setValue(T value) {
		this.value = value;
	}

	public int getDegree() {
		return degree;
	}

	public BinomialTree<T>[] getChildren() {
		return children;
	}

	public boolean isMarked() {
		return marked;
	}

	public void setMarked(boolean marked) {
		this.marked = marked;
	}

	public BinomialTree<T> getParentRef() {
		return parentRef;
	}

	public void setParentRef(BinomialTree<T> parentRef) {
		this.parentRef = parentRef;
	}
}
