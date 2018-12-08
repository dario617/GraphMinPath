package Utils;

public class MyPair<T1 extends Comparable<T1>, T2 extends Comparable<T2>> implements Comparable<MyPair<T1,T2>>{

	private T1 first;
	private T2 second;
	
	public MyPair(T1 first, T2 second) {
		super();
		this.first = first;
		this.second = second;
	}
	
	@Override
	public int compareTo(MyPair<T1,T2> o) {
		return second.compareTo(o.getSecond());
	}
	
	public T1 getFirst() {
		return first;
	}

	public void setFirst(T1 first) {
		this.first = first;
	}

	public T2 getSecond() {
		return second;
	}

	public void setSecond(T2 second) {
		this.second = second;
	}
}
