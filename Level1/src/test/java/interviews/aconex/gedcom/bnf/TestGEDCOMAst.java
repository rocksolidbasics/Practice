/**
 * 
 */
package interviews.aconex.gedcom.bnf;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;

import org.junit.Before;
import org.junit.Test;

import interviews.aconex.gedcom.adapter.GedcomTreeVisitor;
import interviews.aconex.gedcom.adapter.Visitable_I;
import interviews.aconex.gedcom.ast.GedcomAST;
import interviews.aconex.gedcom.ast.GedcomParser;
import interviews.aconex.gedcom.ast.exceptions.GedcomASTException;
import interviews.aconex.gedcom.objectmodel.GedcomRecord;
import interviews.aconex.gedcom.objectmodel.GedcomTree;
import interviews.aconex.gedcom.objectmodel.generator.GedcomTreeBuilder;
import interviews.aconex.gedcom.objectmodel.generator.exceptions.GedcomParserException;

public class TestGEDCOMAst {

	private String gedcomSampleFile;

	@Before
	public void setUp() throws Exception {
		String filePath = new File(".").getAbsolutePath() + "/src/main/resources/gedcom.bnf.rules.txt";
		System.setProperty("gedcom.bnf.rules.file", filePath);

		gedcomSampleFile = new File(".").getAbsolutePath() + "/src/main/resources/GEDCOM.sample.data.txt";
	}

	public void testParser() {
		GedcomParser parser;
		try {
			parser = GedcomAST.newParser();
		} catch (GedcomASTException e) {
			e.printStackTrace();
			return;
		}
		
		GedcomRecord parseRecord = parser.parseRecord("1 BIRT");
		System.out.println(parseRecord);
		parseRecord = parser.parseRecord("0 @I0001@ INDI");
		System.out.println(parseRecord);
		parseRecord = parser.parseRecord("1 NAME Elizabeth Alexandra Mary /Windsor/");
		System.out.println(parseRecord);
	}
	
	@Test
	public void testTree() {
		BufferedReader bis;
		GedcomTreeBuilder builder;
		
		try {
			bis = new BufferedReader(
					new InputStreamReader(new FileInputStream(new File(gedcomSampleFile))));
			builder = new GedcomTreeBuilder();
			GedcomTree tree = builder.parse(bis);
			tree.accept(new GedcomTreeVisitor());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (GedcomParserException e) {
			e.printStackTrace();
		}
	}

}
