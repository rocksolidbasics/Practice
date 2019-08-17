package threads.forkjoin1;

import java.util.List;
import java.util.concurrent.RecursiveAction;

public class ForkJoinTask extends RecursiveAction {

	private static final long serialVersionUID = 1L;

	private List<Integer> intList;

	private int first;

	private int last;

	private int increment;
	
	public ForkJoinTask(List<Integer> list, int first, int last,
			int increment) {
		this.intList = list;
		this.first = first;
		this.last = last;
		this.increment = increment;
	}
	
	@Override
	protected void compute() {
		if(last - first < 10) {
			updateValue();
		} else {
			int middle = (last + first)/2;
			ForkJoinTask tLeft = new ForkJoinTask(intList, first, 
					middle + 1, increment);
			ForkJoinTask tRight = new ForkJoinTask(intList, middle+1, 
					last, increment);
			invokeAll(tLeft, tRight);
		}
	}

	private void updateValue() {
		for(int i=first; i<last; i++) {
			intList.set(i, intList.get(i) + increment);
		}
	}
	
}
