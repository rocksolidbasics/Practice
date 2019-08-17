package btree.good;

import java.util.Comparator;

public class TreeNode<T> {

	private TreeNode<T> lNode;
	private TreeNode<T> rNode;
	private final T data;
	
	private Comparator<T> comparator;
	
	public TreeNode(T n, Comparator<T> c) {
		this.data = n;
		this.comparator = c;
	}
	
	public TreeNode<T> getlNode() {
		return lNode;
	}
	public void setlNode(TreeNode<T> lNode) {
		this.lNode = lNode;
	}
	public TreeNode<T> getrNode() {
		return rNode;
	}
	public void setrNode(TreeNode<T> rNode) {
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
