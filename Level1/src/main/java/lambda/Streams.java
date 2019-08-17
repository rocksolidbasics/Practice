package lambda;

import java.util.Arrays;
import java.util.stream.Stream;

public class Streams {

	public static void main(String[] args) {
		Streams st = new Streams();
		st.count();
	}

	private void count() {
		Stream<Integer> stream = Arrays.asList(1, 2, 5, 6).stream();
	}
}
