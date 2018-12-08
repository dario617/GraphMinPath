package MyHeap;

public interface AHeap<T extends Comparable<T>>{
	
	public T peekMin();
	
	public T extractMin();
}
