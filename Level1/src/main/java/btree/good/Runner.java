package btree.good;

import java.util.Comparator;

public class Runner {

	public static void main(String[] args) {
		Runner r = new Runner();
		Tr<Integer> testTr = r.generateTree();
		
		//Node search
		Tn<Integer> resNode = testTr.findNode(10);
		System.out.println("Node found: " + resNode.toString());
		
		//Node full path extraction
		TnPath<Integer> path = testTr.getPathTo(10);
		System.out.println("Full path (10): " + path.getPath());
		
		//Get sub-path
		path = testTr.getPathTo(10, 7);
		System.out.println("Sub-path (10, 7): " + path.getPath());
		
		//Get sub-path
		path = testTr.getPathTo(9, 7);
		System.out.println("Sub-path (9, 7): " + path.getPath());
		
		//LCA(n1, n2) - Dual path extraction and get intersection
		TnPath<Integer> pathA = testTr.getPathTo(10);
		TnPath<Integer> pathB = testTr.getPathTo(6);
		
		//LCA(n1, n2) - Bubble up LCA
		Tn<Integer> commonNode = testTr.getLCA(10, 6);
		System.out.println("Common parent: " + commonNode.getData());
	}

	private Tr<Integer> generateTree() {
		Comparator<Integer> c = new Comparator<Integer>() {
			@Override
			public int compare(Integer o1, Integer o2) {
				if(o1 == o2)
					return 0;
				
				return (o1.intValue() > o2.intValue()) ? 1 : -1;
			}
		};
		
		Tr<Integer> tr = new Tr<>();
		tr.populate(() -> {
			Tn<Integer> node = new Tn<>(5, c);
			Tn<Integer> n7 = new Tn<>(7, c);
			Tn<Integer> n3 = new Tn<>(3, c);
			node.setlNode(n7);
			node.setrNode(n3);
			Tn<Integer> n6 = new Tn<>(6, c);
			Tn<Integer> n9 = new Tn<>(9, c);
			n7.setlNode(n9);
			n7.setrNode(n6);
			Tn<Integer> n8 = new Tn<>(8, c);
			Tn<Integer> n10 = new Tn<>(10, c);
			n9.setlNode(n10);
			n9.setrNode(n8);
			
			Tn<Integer> n4 = new Tn<>(4, c);
			Tn<Integer> n2 = new Tn<>(2, c);
			n3.setlNode(n4);
			n3.setrNode(n2);
			Tn<Integer> n1 = new Tn<>(1, c);
			n2.setrNode(n1);
			
			return node;
		});
		
		return tr;
	}
}
