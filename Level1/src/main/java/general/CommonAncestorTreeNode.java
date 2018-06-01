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
		
		sNode = new Node(8);
		System.out.println("LCA for Tree A for node - " + sNode + " => " + ctn.getLCA(rootNodeA, sNode));
		System.out.println("LCA for Tree B for node - " + sNode + " => " + ctn.getLCA(rootNodeB, sNode));
		sNode = new Node(6);
		
		//TODO: Fix this LCA scenario, where two search nodes are in parent child relation to each other
		System.out.println("LCA for Tree B for node - " + sNode + " => " + ctn.getLCA(rootNodeB, sNode));
		
	}
	
	/**
	 * Count number of nodes of the given type in the tree
	 * @param rootNodeA
	 * @param searchNode
	 * @return
	 */
	
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
	 * Get (LCA) lowest common ancestor
	 * 
	 * @param rootNode
	 * @param searchNode
	 * @return
	 * @throws RuntimeException
	 */
	
	private Node getLCA(Node rootNode, Node searchNode) throws RuntimeException {
		//Check if the current node matches, else matchedNode is null
		Node matchedNode = (rootNode.equals(searchNode)) ? searchNode : null;

		Node retNode = null;
		
		//If the child nodes are only 1 or below, then there is no point in determining
		//an 'allMatch'. We will set allMatch to 'true' only if there is more than one to
		//match, it will be set to false if any one of the returns in the child iteration
		//loop returns null - that is that path had no matches
		boolean allMatch = (rootNode.getChildCount() > 1) ? true : false;
		
		//Iterate the children
		for(int i=0; i<rootNode.getChildCount(); i++) {
			//Enter the new path, and try to find a match
			Node tmpNode = getLCA(rootNode.getNode(i), searchNode);
			//If a match was found, do an and with the previous find result
			//If the previous path had a find, then allMatch will be true,
			//else if it had null, allMatch will be false
			allMatch = allMatch && (tmpNode!=null);
			
			//As we assume that the nodes returned from below will be either a
			//"matched node" or a "parent of the matched node", lets set it
			if(tmpNode != null) {
				retNode = tmpNode;
			}
		}
		
		//If this node did an allMatch it is the LCA, so pass this node up
		//till the parent
		if(allMatch)
			return rootNode;
		//If this node received a node from a path below and it doesn't match
		//the node being searched, then its the LCA, so pass it up
		else if(retNode != null && !retNode.equals(searchNode))
			return retNode;
		//If this node received a node from the path below and it matches the
		//node being searched, then pass it up
		else if(retNode != null && retNode.equals(searchNode))
			return searchNode;
		else
		//If none of the above send back the value of matchedNode which can be
		//null or a match
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
