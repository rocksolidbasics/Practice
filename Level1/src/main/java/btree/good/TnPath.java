package btree.good;

import java.util.LinkedList;
import java.util.Queue;

public class TnPath<T> {

	Queue<Tn<T>> path = new LinkedList<Tn<T>>();
	
	public void addNode(Tn<T> node) {
		this.path.offer(node);
	}
	
	public Queue<Tn<T>> getPath() {
		return path;
	}
}
