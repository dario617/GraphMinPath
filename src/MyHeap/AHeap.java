package MyHeap;

public interface AHeap<T extends Comparable<T>>{

	public void insert(T value);
	
	public T peekMin();
	
	public T extractMin();
	
	public void decreaseKey();
	
}
