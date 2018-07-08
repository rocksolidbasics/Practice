package interviews.aconex.gedcom.bnf;

import java.io.IOException;
import java.io.LineNumberReader;

import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;

class BNFReader {
	
	private int lineNumber = 0;
	private LineNumberReader m_InReader;
	
	public BNFReader(LineNumberReader reader) throws BNFDocumentBuilderException {
		try {
			if(!reader.ready())
				throw new BNFDocumentBuilderException("Input stream not ready");
		} catch(IOException ex) {
			throw new BNFDocumentBuilderException("Input stream not readable");
		}
		
		this.m_InReader = reader;
	}
	
	public BNFLine readNext() throws BNFDocumentBuilderException {
		try {
			String ruleText = m_InReader.readLine();
			if(ruleText == null)
				return null;
			
			lineNumber = m_InReader.getLineNumber();
			
			if(ruleText.trim().equals("") || ruleText.trim().startsWith("#"))
				return readNext();
			
			return new BNFLine(lineNumber, this.cleanString(ruleText));
		} catch (IOException e) {
			throw new BNFDocumentBuilderException("Error while reading BNF document - Line: " + (lineNumber+1));
		}
	}
	
	public String cleanString(String line) {
		while(line.contains("\t"))
			line = line.replace("\t", " ");

		while(line.contains("  "))
			line = line.replace("  ", " ");
		
		return line.trim();
	}
}