package linkedlist;

import linkedlist.core.Node;
import linkedlist.core.SimpleList;

public class UnfoldLinkedList {
	
	public static void main(String[] args) {
		UnfoldLinkedList ul = new UnfoldLinkedList();
		
		SimpleList<Integer> l = ul.createList(1);
		ul.unfold(l);
		
		l = ul.createList(2);
		ul.unfold(l);
	}

	public void unfold(SimpleList<Integer> sl) {
		SimpleList<Integer> collector =  new SimpleList<Integer>();
		this._unfold(sl.getHead(), true, collector);
		
		Node<Integer> n = collector.getHead();
		while(n != null) {
			System.out.print(n + ",");
			n = n.getNext();
		}
		
		System.out.println("");
	}
	
	private void _unfold(Node<Integer> n, boolean odd, SimpleList<Integer> collector) {
		if(odd) {
			collector.add(n.getData());
		}
		
		if(n.getNext() != null && odd) {
			_unfold(n.getNext(), !odd, collector);
		} else if(n.getNext() != null && !odd){
			_unfold(n.getNext(), !odd, collector);
			collector.add(n.getData());
		} else {
			//For the last item in the list
			if(!odd)
				collector.add(n.getData());
		}
	}
	
	private SimpleList<Integer> createList(int setNumber) {
		SimpleList<Integer> sl = new SimpleList<>();
		
		if(setNumber == 1) {
			sl.add(1);
			sl.add(6);
			sl.add(2);
			sl.add(5);
			sl.add(3);
			sl.add(4);
		} else if(setNumber == 2) {
			sl.add(1);
			sl.add(5);
			sl.add(2);
			sl.add(4);
			sl.add(3);
		}
		
		return sl;
	}
	
	
	
	

}
