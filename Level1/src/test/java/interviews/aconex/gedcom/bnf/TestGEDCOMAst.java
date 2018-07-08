/**
 * 
 */
package interviews.aconex.gedcom.bnf;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import interviews.aconex.gedcom.ast.GEDCOMSyntaxTree;
import interviews.aconex.gedcom.ast.exceptions.GedcomASTException;

public class TestGEDCOMAst {

	@Before
	public void setUp() throws Exception {
		String filePath = new File(".").getAbsolutePath() + "/src/main/resources/gedcom.bnf.rules.txt";
		System.setProperty("gedcom.bnf.rules.file", filePath);
	}

	@Test
	public void test() {
		try {
			GEDCOMSyntaxTree astTree = new GEDCOMSyntaxTree();
			astTree.build();
		} catch (GedcomASTException e) {
			e.printStackTrace();
		}
	}

}
