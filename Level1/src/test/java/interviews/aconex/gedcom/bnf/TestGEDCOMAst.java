/**
 * 
 */
package interviews.aconex.gedcom.bnf;

import java.io.File;

import org.junit.Before;
import org.junit.Test;

import interviews.aconex.gedcom.ast.GedcomAST;
import interviews.aconex.gedcom.ast.GedcomParser;
import interviews.aconex.gedcom.ast.exceptions.GedcomASTException;
import interviews.aconex.gedcom.objectmodel.GedcomRecord;

public class TestGEDCOMAst {

	@Before
	public void setUp() throws Exception {
		String filePath = new File(".").getAbsolutePath() + "/src/main/resources/gedcom.bnf.rules.txt";
		System.setProperty("gedcom.bnf.rules.file", filePath);
	}

	@Test
	public void test() {
		try {
			GedcomParser parser = GedcomAST.newParser();
			//GedcomRecord rec = parser.parseRecord("0 @I1@ INDI");
			GedcomRecord rec = parser.parseRecord("0 SEX MA");
			System.out.println(rec);
		} catch (GedcomASTException e) {
			e.printStackTrace();
		}
	}

}
