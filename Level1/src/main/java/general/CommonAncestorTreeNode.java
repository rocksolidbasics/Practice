package general;

import java.util.LinkedList;
import java.util.List;

/**
 * Search and find command ancestor on a given non-binary tree.
 * 
 * Find the common ancestor for a given node
 * > First search for similar nodes in the tree
 * > After the first node found search for similar node, while keeping track of a common ancestor
 * ! What if both the similar nodes are found on the same branch
 *
 */

public class CommonAncestorTreeNode {

	public static void main(String[] args) {
		CommonAncestorTreeNode ctn = new CommonAncestorTreeNode();
		Node rootNodeA = ctn.createTreeA();
		Node rootNodeB = ctn.createTreeB();
		
		Node searchNode = new Node(8);
		int matchCount = ctn.countNodes(rootNodeA, searchNode);
		System.out.println("Tree A: Node count matching - " + searchNode + " = " + matchCount);
		
		Node sNode = new Node(6);
		matchCount = ctn.countNodes(rootNodeB, sNode);
		System.out.println("Tree B: Node count matching - " + sNode + " = " + matchCount);
		
		//System.out.println(ctn.getLCA(rootNodeA, sNode));
		sNode = new Node(8);
		System.out.println(ctn.getLCA(rootNodeB, sNode));
	}
	
	private int countNodes(Node rootNodeA, Node searchNode) {
		int count = 0;
		
		if(rootNodeA.equals(searchNode)) {
			count = 1;
		}	
		
		if(!rootNodeA.hasChildNodes())
			return count;

		for(int i=0; i<rootNodeA.getChildCount(); i++) {
			count += countNodes(rootNodeA.getNode(i), searchNode);
		}
		
		return count;
	}
	
	/**
	 * Get lowest common ancestor
	 * 
	 * @param rootNode
	 * @param searchNode
	 * @return
	 * @throws RuntimeException
	 */
	
	private Node getLCA(Node rootNode, Node searchNode) throws RuntimeException {
		Node matchedNode = (rootNode.equals(searchNode)) ? searchNode : null;

		Node retNode = null;
		boolean allMatch = false;
		
		for(int i=0; i<rootNode.getChildCount(); i++) {
			Node tmpNode = getLCA(rootNode.getNode(i), searchNode);
			allMatch = allMatch && (tmpNode!=null);
			
			if(tmpNode != null) {
				retNode = tmpNode;
			}
		}
		
		if(allMatch)
			return rootNode;
		else if(retNode != null && !retNode.equals(searchNode))
			return searchNode;
		else if(retNode != null && retNode.equals(searchNode))
			return retNode;
		else
			return matchedNode;
	}

	/**
	 * Tree structure
	 * 
	 * 			(5)					 (5)
	 * 			/  \				/   \
	 * 		  (12) (6)			  (12)   (6*)
	 * 		  /    / \			  /  \    |  \
	 * 		(7)   (2) (8^)		 (7) (8^) (2) (6*)
	 * 		/  \				/   \
	 * 	   (8^) (9)			   (8^) (9)
	 * 
	 * @return
	 */
	
	private Node createTreeB() {
		Node n8a = new Node(8);
		Node n9 = new Node(9);
		Node n7 = new Node(7);
		n7.addChildNode(n8a).addChildNode(n9);
		
		Node n8b = new Node(8);
		Node n12 = new Node(12);
		n12.addChildNode(n7).addChildNode(n8b);
		
		Node n6a = new Node(6);
		Node n2 = new Node(2);
		Node n6b = new Node(6);
		n6a.addChildNode(n2).addChildNode(n6b);
		
		Node n5 = new Node(5);
		n5.addChildNode(n12).addChildNode(n6a);
		
		return n5;
	}
	
	private Node createTreeA() {
		Node n8a = new Node(8);
		Node n9 = new Node(9);
		Node n7 = new Node(7);
		n7.addChildNode(n8a).addChildNode(n9);
		
		Node n12 = new Node(12);
		n12.addChildNode(n7);
		
		Node n2 = new Node(2);
		Node n8b = new Node(8);
		Node n6 = new Node(6);
		n6.addChildNode(n2).addChildNode(n8b);
		
		Node n5 = new Node(5);
		n5.addChildNode(n12).addChildNode(n6);
				
		return n5;
	}
	
	/**
	 * Tree structure definition
	 *
	 */
	
	private static class Node {
		private int value;
		private List<Node> childNodes = new LinkedList<Node>();
		
		public Node(int v) {
			this.setValue(v);
		}
		
		public Node addChildNode(Node n) {
			childNodes.add(n);
			return this;
		}
		
		public int getChildCount() {
			return this.childNodes.size();
		}
		
		public boolean hasChildNodes() {
			return (childNodes != null && childNodes.size() > 0);
		}
		
		public Node getNode(int index) {
			if(!hasChildNodes() || index > childNodes.size()-1)
				return null;
			
			return childNodes.get(index);
		}
		
		@Override
		public boolean equals(Object that) {
			if(that == null)
				return false;
			
			if(!(that instanceof Node))
				return false;
			
			if(this == that)
				return true;
			
			return (this.getValue() == ((Node)that).getValue());
		}
		
		@Override
		public String toString() {
			return "Node[" + this.value + "]";
		}

		public int getValue() {
			return value;
		}

		public void setValue(int value) {
			this.value = value;
		}
	}

}
