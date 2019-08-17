package btree.good;

public class Tr<T> {
	
	private Tn<T> rootNode;

	public void addNode(Tn<T> tn) throws Exception {
		if(tn == null)
			throw new Exception("Null node provided");
		
		if(rootNode == null)
			rootNode = tn;
		else {
		}
	}
	
	public Tn<T> findNode(T value) {
		return this._findNode(rootNode, value);
	}
	
	//TODO: Path match
	public Tn<T> getLCAPathMatch(T val1, T val2) {
		return null;
	}
	
	//Bubble up method
	public Tn<T> getLCA(T val1, T val2) {
		return this._getLCA(rootNode, val1, val2);
	}
	
	private Tn<T> _getLCA(Tn<T> node, T val1, T val2) {
		if(node == null || node.getData() == null)
			return null;
		
		if(node.compare(node.getData(), val1) == 0 ||
				node.compare(node.getData(), val2) == 0) {
			return node;
		}
		
		Tn<T> lNode = _getLCA(node.getlNode(), val1, val2);
		Tn<T> rNode = _getLCA(node.getrNode(), val1, val2);
		
		if(lNode != null && rNode != null)
			return node;
		else if(lNode != null)
			return lNode;
		else if(rNode != null)
			return rNode;
		else
			return null;
	}
	
	public TnPath<T> getPathTo(T value, T stopVal) {
		TnPath<T> path = new TnPath<>();
		
		this._tracePath(rootNode, value, path, stopVal);
		return path;
	}
	
	public TnPath<T> getPathTo(T value) {
		TnPath<T> path = new TnPath<>();
		
		this._tracePath(rootNode, value, path, null);
		return path;
	}
	
	private boolean _tracePath(Tn<T> node, T findValue, TnPath<T> path, T stopValue) {
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
	
	private Tn<T> _findNode(Tn<T> node, T value) {
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

	public void populate(java.util.function.Supplier<Tn<T>> supplier) {
		Tn<T> tn = supplier.get();
		rootNode = tn;
	}
	
	public String toString() {
		return "";
	}

}
