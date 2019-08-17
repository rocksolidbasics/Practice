package btree.good;

public class Tree<T> {
	
	private TreeNode<T> rootNode;

	public void addNode(TreeNode<T> tn) throws Exception {
		if(tn == null)
			throw new Exception("Null node provided");
		
		if(rootNode == null)
			rootNode = tn;
		else {
		}
	}
	
	public TreeNode<T> findNode(T value) {
		return this._findNode(rootNode, value);
	}
	
	//TODO: Path match
	public TreeNode<T> getLCAPathMatch(T val1, T val2) {
		return null;
	}
	
	//Bubble up method
	public TreeNode<T> getLCA(T val1, T val2) {
		return this._getLCA(rootNode, val1, val2);
	}
	
	private TreeNode<T> _getLCA(TreeNode<T> node, T val1, T val2) {
		if(node == null || node.getData() == null)
			return null;
		
		if(node.compare(node.getData(), val1) == 0 ||
				node.compare(node.getData(), val2) == 0) {
			return node;
		}
		
		TreeNode<T> lNode = _getLCA(node.getlNode(), val1, val2);
		TreeNode<T> rNode = _getLCA(node.getrNode(), val1, val2);
		
		if(lNode != null && rNode != null)
			return node;
		else if(lNode != null)
			return lNode;
		else if(rNode != null)
			return rNode;
		else
			return null;
	}
	
	public TreePath<T> getPathTo(T value, T stopVal) {
		TreePath<T> path = new TreePath<>();
		
		this._tracePath(rootNode, value, path, stopVal);
		return path;
	}
	
	public TreePath<T> getPathTo(T value) {
		TreePath<T> path = new TreePath<>();
		
		this._tracePath(rootNode, value, path, null);
		return path;
	}
	
	private boolean _tracePath(TreeNode<T> node, T findValue, TreePath<T> path, T stopValue) {
		if(node == null || node.getData() == null)
			return false;
		
		if(node.compare(node.getData(), findValue) == 0) {
			path.addNode(node);
			return true;
		}
		
		if(node.compare(node.getData(), findValue) == -1) {
			if(_tracePath(node.getlNode(), findValue, path, stopValue)) {
				path.addNode(node);
				
				if(stopValue != null) {
					if(node.getData() == stopValue)
						return false;
				}
				
				return true;
			}
			return false;
		} else {
			if(_tracePath(node.getrNode(), findValue, path, stopValue)) {
				path.addNode(node);
				
				if(stopValue != null) {
					if(node.getData() == stopValue)
						return false;
				}
				
				return true;
			}
			return false;
		}
	}
	
	private TreeNode<T> _findNode(TreeNode<T> node, T value) {
		if(node == null || node.getData() == null)
			return null;
		
		if(node.compare(node.getData(), value) == 0)
			return node;
		
		if(node.compare(node.getData(), value) == -1) {
			return _findNode(node.getlNode(), value);
		} else {
			return _findNode(node.getrNode(), value);
		}
	}

	public void populate(java.util.function.Supplier<TreeNode<T>> supplier) {
		TreeNode<T> tn = supplier.get();
		rootNode = tn;
	}
	
	public String toString() {
		return "";
	}

}
