package interviews.aconex.gedcom.objectmodel.generator;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.LineNumberReader;

import interviews.aconex.gedcom.objectmodel.generator.exceptions.GedcomParserException;

public class GedcomReader {

	private LineNumberReader m_InReader;
	
	public GedcomReader(BufferedReader reader) throws GedcomParserException {
		try {
			if(!reader.ready())
				throw new GedcomParserException("Input stream not ready");
		} catch(IOException ex) {
			throw new GedcomParserException("Input stream not readable");
		}
		
		this.m_InReader = new LineNumberReader(reader);
	}
	
	public String readNext() throws GedcomParserException {
		try {
			String text = m_InReader.readLine();
			if(text == null)
				return null;
			
			if(text.trim().equals(""))
				return readNext();
			
			return text;
		} catch (IOException e) {
			throw new GedcomParserException("Error while reading Gedcom document");
		}
	}
}
