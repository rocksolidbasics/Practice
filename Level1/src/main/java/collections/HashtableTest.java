package collections;

import static org.junit.Assert.*;

import java.util.Hashtable;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

public class HashtableTest {

	public static void main(String[] args) {
		Result runClasses = JUnitCore.runClasses(HashtableTest.class);
		for(Failure failure : runClasses.getFailures()) {
			System.out.println(failure.toString());
		}
	}
	
	@Before
	public void beforeTest() {
		//None
	}
	
	@Test
	public void testTwosComplement() {
		int bucketSize = 5;
		Integer key = new Integer(-10);
		int hash = key.hashCode();
		int twosC = (hash & 0x7FFFFFFF);
        int index = twosC % bucketSize;
        
        System.out.println("With bucket size of " + bucketSize + 
        		"\n\t key = " + key +
        		"\n\t hash = " + hash +
        		"\n\t Two's complement = " + twosC +
        		"\n\t Calculated bucket index = " + index);
        
        assertTrue(key == -10);
        assertTrue(twosC == 2147483638);
        assertTrue(index == 3);
	}
	
	@Test
	public void testReHash() {
		int oldCapacity = 11;
		int loc = (oldCapacity << 1);
		assertTrue(loc == 22);
		
		int newCapacity = (oldCapacity << 1) + 1;
		assertTrue(newCapacity == 23);
		
		System.out.println("Old capacity = " + oldCapacity + 
				"\n\t Left shift '<<' old capacity = " + loc +
				"\n\t New capacity = " + newCapacity);
	}
	
	@Test(expected = NullPointerException.class)
	public void testHashtableKeyNonNull() {
		Hashtable ht = new Hashtable();
		ht.put(null, "Test");
	}
	
	@Test(expected = NullPointerException.class)
	public void testHashtableValueNonNull() {
		Hashtable ht = new Hashtable();
		ht.put("One", null);
	}
	
	@Test
	public void test() {
		assertTrue("Test message", true);
	}
}
