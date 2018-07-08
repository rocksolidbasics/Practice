package interviews.aconex.gedcom.bnf;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;

public class TestBNFDocumentBuilder {

	private File m_BnfFile;
	
	@Before
	public void setUp() throws Exception {
		//TODO: Fetch file name from a property value
		String basePath = new File(".").getAbsolutePath() + "/src/main/resources/";
		this.m_BnfFile = new File(basePath + "/gedcom.bnf.rules.txt");
	}

	@Test
	public void testBNFDocumentBuilder() {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(this.m_BnfFile);
			BNFDocumentBuilder builder = new BNFDocumentBuilder(fis);
			BNFDocument doc = builder.build();
			System.out.println(doc);
			
			assertTrue("Builder created", builder != null);
		} catch (BNFDocumentBuilderException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if(fis != null) {
				try {
					fis.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

}
