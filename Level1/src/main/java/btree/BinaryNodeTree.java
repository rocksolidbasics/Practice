package btree;

public class BinaryNodeTree implements BinaryTree_I {
	
	private Node root;

	@Override
	public void insert(Object value) {
		
	}
	
	@Override
	public boolean search(Object value) {
		
	}
	
	@Override
	public boolean delete(Object value) {
		
	}
	
	public void traverse(TreeVisitor_I v) {
		
	}
	
	private class Node<T> {
		private T nodeValue;
		private Node left;		//Less node
		private Node right;		//Higher node
		private Node parent;	//Parent node
		
		Node(T value) {
			this.nodeValue = value;
		}
		
		Node(T value, Node parent) {
			this.nodeValue = value;
			this.parent = parent;
		}
		
		T getValue() {
			return this.nodeValue;
		}
		
		Node getLeftValue() {
			return this.getLeftValue();
		}
		
		Node getRightValue() {
			return this.getRightValue();
		}
		
		boolean isLeaf() {
			return (left == null && right == null);
		}
	}
}
