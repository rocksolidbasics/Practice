package interviews.aconex.gedcom.ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import interviews.aconex.gedcom.bnf.BNFDocument;
import interviews.aconex.gedcom.bnf.BNFDocumentBuilder;
import interviews.aconex.gedcom.bnf.BNFRule;
import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;

public class GEDCOMSyntaxTree {
	
	public GEDCOMSyntaxTree() { }
	
	public void init() {
		//TODO: Read properties file and load BNF
		
		String bnfFilePath = "";
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(bnfFilePath));
			BNFDocumentBuilder builder = new BNFDocumentBuilder(fis);
			BNFDocument doc = builder.build();
			System.out.println(doc);
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

	private void adaptRule(BNFRule bnfRule) {
		
	}

}
