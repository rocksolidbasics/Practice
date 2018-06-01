import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import btree.BinaryTree_I;

public class TestBTree {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testAddNode() {
		BinaryTree_I btree = new BinaryNodeTree();
		btree.addNode();
	}

}
