package linkedlist.core;


public class SimpleList<T> {
		
		private Node<T> head = null;
		private Node<T> tail = null;
		
		public Node<T> getHead() {
			return head;
		}
		
		public void add(T value) {
			if(this.head == null) {
				this.head = new Node<>(value);
				this.tail = this.head;
			} else {
				Node<T> n = new Node<>(value);
				this.tail.setNext(n);
				this.tail = n;
			}
		}
		
		public String toString() {
			if(head != null)
				return head.toString();
			else
				return "";
		}
	}