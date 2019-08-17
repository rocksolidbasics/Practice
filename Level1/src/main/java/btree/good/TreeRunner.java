package btree.good;

import java.util.Comparator;

public class TreeRunner {

	public static void main(String[] args) {
		TreeRunner r = new TreeRunner();
		Tree<Integer> testTr = r.generateTree();
		
		//Node search
		TreeNode<Integer> resNode = testTr.findNode(10);
		System.out.println("Node found: " + resNode.toString());
		
		//Node full path extraction
		TreePath<Integer> path = testTr.getPathTo(10);
		System.out.println("Full path (10): " + path.getPath());
		
		//Get sub-path
		path = testTr.getPathTo(10, 7);
		System.out.println("Sub-path (10, 7): " + path.getPath());
		
		//Get sub-path
		path = testTr.getPathTo(9, 7);
		System.out.println("Sub-path (9, 7): " + path.getPath());
		
		//LCA(n1, n2) - Dual path extraction and get intersection
		TreePath<Integer> pathA = testTr.getPathTo(10);
		TreePath<Integer> pathB = testTr.getPathTo(6);
		
		//LCA(n1, n2) - Bubble up LCA
		TreeNode<Integer> commonNode = testTr.getLCA(10, 6);
		System.out.println("Common parent: " + commonNode.getData());
	}

	private Tree<Integer> generateTree() {
		Comparator<Integer> c = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1 == o2)
					return 0;
				
				return (o1.intValue() > o2.intValue()) ? 1 : -1;
			}
		};
		
		Tree<Integer> tr = new Tree<>();
		tr.populate(() -> {
			TreeNode<Integer> node = new TreeNode<>(5, c);
			TreeNode<Integer> n7 = new TreeNode<>(7, c);
			TreeNode<Integer> n3 = new TreeNode<>(3, c);
			node.setlNode(n7);
			node.setrNode(n3);
			TreeNode<Integer> n6 = new TreeNode<>(6, c);
			TreeNode<Integer> n9 = new TreeNode<>(9, c);
			n7.setlNode(n9);
			n7.setrNode(n6);
			TreeNode<Integer> n8 = new TreeNode<>(8, c);
			TreeNode<Integer> n10 = new TreeNode<>(10, c);
			n9.setlNode(n10);
			n9.setrNode(n8);
			
			TreeNode<Integer> n4 = new TreeNode<>(4, c);
			TreeNode<Integer> n2 = new TreeNode<>(2, c);
			n3.setlNode(n4);
			n3.setrNode(n2);
			TreeNode<Integer> n1 = new TreeNode<>(1, c);
			n2.setrNode(n1);
			
			return node;
		});
		
		return tr;
	}
}
