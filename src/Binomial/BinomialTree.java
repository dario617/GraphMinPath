package Binomial;

public class BinomialTree<T extends Comparable<T>> {
	private T value;
	private int degree;
	private boolean marked;
	private BinomialTree<T>[] children;
	
	public BinomialTree(T val){
		this.value = val;
		this.degree = 1;
		this.marked = false;
	}

	/**
	 * @param bt Binomial tree to be added to current
	 * @return 1 0 or -1, 1 if the current node added the new one,
	 * 0 if it was appended to the node or -1 if the nodes had different degree
	 */
	public int joinBinomialTree(BinomialTree<T> bt){
		if(bt == null){
			return 1;
		}
		
		if(degree != bt.getDegree()){
			// Add to this binomial tree
			return -1;
		}else{
			if(value.compareTo(bt.getValue()) < 0){
				// Reshape children array and degree
				degree++;
				BinomialTree<T>[] newAllocation = new BinomialTree[degree - 1]; 
				// The first node to be inserted
				if(degree == 2){
					children = newAllocation;
					children[0] = bt;
					return 1;
				}else{
					for(int i = 0; i < degree - 2; i++){
						newAllocation[i] = children[i];
					}
					children = newAllocation;
					children[degree - 2] = bt;
				}
				
				return 1;
			}else{
				bt.joinBinomialTree(this);
				return 0;
			}
		}
	}
	
	public void print(){
		print(0);
	}
	
	public void print(int level){
		System.out.print("level: "+level+" val: "+value.toString());
		for(BinomialTree<T> n : children){
			n.print(level+1);
		}
	}
	
	// TODO!
	public void cut(int val){
		
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

	public void setDegree(int degree) {
		this.degree = degree;
	}

	public BinomialTree<T>[] getChildren() {
		return children;
	}

}
