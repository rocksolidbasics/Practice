package btree.good;

import java.util.LinkedList;
import java.util.Queue;

public class TreePath<T> {

	Queue<TreeNode<T>> path = new LinkedList<TreeNode<T>>();
	
	public void addNode(TreeNode<T> node) {
		this.path.offer(node);
	}
	
	public Queue<TreeNode<T>> getPath() {
		return path;
	}
}
