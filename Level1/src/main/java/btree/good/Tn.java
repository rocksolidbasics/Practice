package btree.good;

import java.util.Comparator;

public class Tn<T> {

	private Tn<T> lNode;
	private Tn<T> rNode;
	private final T data;
	
	private Comparator<T> comparator;
	
	public Tn(T n, Comparator<T> c) {
		this.data = n;
		this.comparator = c;
	}
	
	public Tn<T> getlNode() {
		return lNode;
	}
	public void setlNode(Tn<T> lNode) {
		this.lNode = lNode;
	}
	public Tn<T> getrNode() {
		return rNode;
	}
	public void setrNode(Tn<T> rNode) {
		this.rNode = rNode;
	}
	public T getData() {
		return data;
	}
	
	public int hashCode() {
		int hashCode = 31 + (lNode.hashCode());
		hashCode = hashCode * (31 + rNode.hashCode());
		
		return hashCode;
	}
	
	public String toString() {
		return data.toString();
	}

	public int compare(T o1, T o2) {
		return this.comparator.compare(o1, o2);
	}
}
