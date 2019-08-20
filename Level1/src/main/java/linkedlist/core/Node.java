package linkedlist.core;


public class Node<T> {
		private Node<T> next;
		private T data;
		
		public Node(T v) {
			this.data = v;
		}
		
		public Node<T> getNext() {
			return next;
		}
		public void setNext(Node<T> next) {
			this.next = next;
		}
		public T getData() {
			return data;
		}
		public void setData(T data) {
			this.data = data;
		}
		
		public String toString() {
			return data.toString();
		}
	}