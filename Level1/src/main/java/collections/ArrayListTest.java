package collections;

import java.io.FileOutputStream;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import static org.junit.Assert.*;

public class ArrayListTest {
	
	public static void main(String[] args) {
		Result runClasses = JUnitCore.runClasses(ArrayListTest.class);
		List<Failure> failures = runClasses.getFailures();
		
		for(Failure f : failures) {
			System.out.println(f.toString());
		}
	}
	
	private ArrayList<String> al = new ArrayList<String>();

	@Before
	public void setUp() throws Exception {
		al.add("Hello");
		al.add("Hi");
		al.add("Howdy");
	}
	
	@Test
	public void testIteration() {
		for(String s : al) {
			assertTrue("HelloHiHowdy".contains(s));
		}
	}

	@Test
	public void testListSerialization() {
		try {
			FileOutputStream fos = new FileOutputStream("./src/main/resources/myfile");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(al);
			oos.close();
			fos.close();
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

}
