package interviews.aconex.gedcom.objectmodel.generator;

import java.io.BufferedReader;

import interviews.aconex.gedcom.ast.GedcomAST;
import interviews.aconex.gedcom.ast.GedcomParser;
import interviews.aconex.gedcom.ast.exceptions.GedcomASTException;
import interviews.aconex.gedcom.objectmodel.GedcomEntity;
import interviews.aconex.gedcom.objectmodel.GedcomRecord;
import interviews.aconex.gedcom.objectmodel.GedcomTree;
import interviews.aconex.gedcom.objectmodel.generator.exceptions.GedcomParserException;

/**
 * Class responsibilities
 * - Loads the GEDCOM format data via the properties specified
 * - Creates the parser
 * - Generates the GEDCOM tree
 */

public class GedcomTreeBuilder {

	private GedcomParser m_Parser;
	private GedcomReader m_Reader;
	
	public GedcomTreeBuilder() throws GedcomParserException {
		try {
			m_Parser = GedcomAST.newParser();
		} catch (GedcomASTException e) {
			throw new GedcomParserException("Error instantiating Gedcom parser", e);
		}
	}
	
	public GedcomTree parse(BufferedReader reader) throws GedcomParserException {
		m_Reader = new GedcomReader(reader);
		
		String line = null;
		GedcomTree tree = new GedcomTree();
		GedcomEntity entity = null;
		
		while((line = m_Reader.readNext()) != null) {
			GedcomRecord record = this.m_Parser.parseRecord(line);
			if(record.getRecordClassifier() == GedcomRecord.CLASSIFIER.ID) {
				entity = new GedcomEntity(record);
				tree.append(entity);
				continue;
			}
			
			entity.appendData(record);
		}
		
		tree.append(entity);
		return tree;
	}

}
