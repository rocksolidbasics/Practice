package interviews.aconex.gedcom.bnf;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import interviews.aconex.gedcom.ast.GEDCOMSyntaxTree;
import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;

public class BNFDocumentBuilder {

	private BNFReader m_Reader;
	private Map<String, BNFRule> m_BnfRuleMap = new HashMap<>();
	
	public BNFDocumentBuilder(InputStream inStream) throws BNFDocumentBuilderException {
		if(inStream == null)
			throw new BNFDocumentBuilderException("Input BNF stream does not exist");
		
		try {
			LineNumberReader inReader = new LineNumberReader(new InputStreamReader(inStream, "UTF-8"));
			m_Reader = new BNFReader(inReader);
		} catch (UnsupportedEncodingException e) {
			throw new BNFDocumentBuilderException("Input BNF file encoding incorrect", e);
		}
	}
	
	public BNFDocument build() throws BNFDocumentBuilderException {
		BNFLine bnfLine = null;
		BNFRuleParser parser = new BNFRuleParser();

		bnfLine = m_Reader.readNext();
		BNFRule bnfRule = parser.parse(bnfLine);
		m_BnfRuleMap.put(BNFDocument.BNF_ROOT, bnfRule);
		
		while((bnfLine = m_Reader.readNext()) != null) {
			bnfRule = parser.parse(bnfLine);
			m_BnfRuleMap.put(bnfRule.getRuleTerm(), bnfRule);
		}
		
		BNFDocument doc = new BNFDocument(m_BnfRuleMap);
		doc.validate();
		
		return doc;
	}
	
}
