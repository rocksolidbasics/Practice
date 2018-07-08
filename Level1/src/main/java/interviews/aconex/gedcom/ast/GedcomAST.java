package interviews.aconex.gedcom.ast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import interviews.aconex.gedcom.ast.exceptions.GedcomASTException;
import interviews.aconex.gedcom.ast.nodes.ASTNode_I;
import interviews.aconex.gedcom.ast.nodes.AndConditionNode;
import interviews.aconex.gedcom.ast.nodes.CharsConstantNode;
import interviews.aconex.gedcom.ast.nodes.DigitConstantNode;
import interviews.aconex.gedcom.ast.nodes.OrConditionNode;
import interviews.aconex.gedcom.ast.nodes.StringConstantNode;
import interviews.aconex.gedcom.ast.nodes.TypeNode;
import interviews.aconex.gedcom.bnf.BNFDocument;
import interviews.aconex.gedcom.bnf.BNFDocumentBuilder;
import interviews.aconex.gedcom.bnf.BNFRule;
import interviews.aconex.gedcom.bnf.exceptions.BNFDocumentBuilderException;
import interviews.aconex.gedcom.bnf.types.BinaryTerm;
import interviews.aconex.gedcom.bnf.types.ConstantTerm;
import interviews.aconex.gedcom.bnf.types.SimpleTerm;
import interviews.aconex.gedcom.bnf.types.Term_I;

public class GedcomAST {
	
	private static GedcomAST m_Instance;
	private BNFDocument m_BnfDoc;
	private ASTNode_I m_AstRoot;
	
	static {
		 try {
			m_Instance = new GedcomAST();
		} catch (GedcomASTException e) {
			e.printStackTrace();
		}
	}
	
	private GedcomAST() throws GedcomASTException {
		try {
			_initBNFBuilder(this._getBNFFilePath());
		} catch (FileNotFoundException e) {
			throw new GedcomASTException("GEDCOM BNF rules file not specified. "
					+ "Use the 'gedcom.bnf.rules.file property to set the file path");
		} catch (BNFDocumentBuilderException e) {
			throw new GedcomASTException("Error initializing GEDCOM AST", e);
		}
	}
	
	public void build() throws GedcomASTException {
		if(!shouldRebuild())
			return;
		
		this._build();
	}
	
	public static GedcomParser newParser() throws GedcomASTException {
		m_Instance.build();
		return new GedcomParser(m_Instance.m_AstRoot);
	}
	
	private void _build() throws GedcomASTException {
		BNFRule rootRule = m_BnfDoc.getRootRule();
		m_AstRoot = _createNode(rootRule.getRootTerm(), rootRule.getRuleName());
		this._appendChildNodes(m_AstRoot, rootRule.getRootTerm());
	}
	
	private void _appendChildNodes(ASTNode_I targetNode, Term_I lTerm) throws GedcomASTException {
		if(!(lTerm instanceof ConstantTerm)) {
			if(lTerm instanceof BinaryTerm) {
				List<Term_I> childTerms = ((BinaryTerm)lTerm).getTerms();
				Iterator<Term_I> termIter = childTerms.iterator();
				
				while(termIter.hasNext()) {
					ASTNode_I childNode = null;
					Term_I term = termIter.next();
					
					if(term instanceof BinaryTerm) {
						childNode = ((((BinaryTerm)term).getOperator() == BinaryTerm.BINARY_OPERATOR.OR)) ? new OrConditionNode() : new AndConditionNode();
						if(childNode instanceof OrConditionNode && term.isRepeating())
							((OrConditionNode)childNode).setRepeating(true);
						
						this._appendChildNodes(childNode, term);
					} else if(term instanceof ConstantTerm) {
						if(term.getName().equals("DIGIT")) {
							BNFRule digitRule = this.m_BnfDoc.getRule(term.getName());
							childNode = new DigitConstantNode(getStringFromBNFConstants(digitRule.getRuleExpression()));
						} else if(term.getName().equals("CHARS")) {
							BNFRule charRule = this.m_BnfDoc.getRule(term.getName());
							childNode = new CharsConstantNode(getStringFromBNFConstants(charRule.getRuleExpression()));
						} else
							childNode = new StringConstantNode(term.getName());
					} else {
						childNode = _createNode(term, term.getName());
						this._appendChildNodes(childNode, term);
					}
					
					targetNode.appendChildNode(childNode);
				}
			} else if(lTerm instanceof SimpleTerm) {
				ASTNode_I childNode = null;
				BNFRule childRule = this.m_BnfDoc.getRule(lTerm.getName());
				Term_I term = childRule.getRootTerm();
				
				//If Simple Term is of type DIGIT/ CHARS create a node Composite node object
				if(term.getName().equals("DIGIT")) {
					BNFRule digitRule = this.m_BnfDoc.getRule(term.getName());
					childNode = new DigitConstantNode(getStringFromBNFConstants(digitRule.getRuleExpression()));
				} else if(term.getName().equals("CHARS")) {
					BNFRule charRule = this.m_BnfDoc.getRule(term.getName());
					childNode = new CharsConstantNode(getStringFromBNFConstants(charRule.getRuleExpression()));
				} else {
					childNode = ((((BinaryTerm)term).getOperator() == BinaryTerm.BINARY_OPERATOR.OR)) ? new OrConditionNode() : new AndConditionNode();
					if(childNode instanceof OrConditionNode && term.isRepeating())
						((OrConditionNode)childNode).setRepeating(true);
				
					this._appendChildNodes(childNode, term);
				}
				
				targetNode.appendChildNode(childNode);
			}
		}

	}
	
	private String getStringFromBNFConstants(String bnfConstString) {
		if(bnfConstString == null)
			return "";
		
		bnfConstString = bnfConstString.replaceAll("'", "");
		bnfConstString = bnfConstString.replaceAll("\"", "");
		return bnfConstString.replaceAll("\\|", "");
	}

	private ASTNode_I _createNode(Term_I rootTerm, String typeName) throws GedcomASTException {
		if(rootTerm instanceof BinaryTerm && ((BinaryTerm)rootTerm).getOperator() == BinaryTerm.BINARY_OPERATOR.OR) {
			return new OrConditionNode();
		} else if(rootTerm instanceof BinaryTerm && ((BinaryTerm)rootTerm).getOperator() == BinaryTerm.BINARY_OPERATOR.AND) {
			return new AndConditionNode();
		} else if(rootTerm instanceof SimpleTerm) {
			return new TypeNode(typeName);
		} else if(rootTerm instanceof ConstantTerm) {
			return new StringConstantNode(typeName);
		} else
			throw new GedcomASTException("Invalid BNF term encountered while building AST");
	}

	private boolean shouldRebuild() {
		// TODO Check serialized AST tree file and BNF file's modified time and decide
		return true;
	}

	private String _getBNFFilePath() throws GedcomASTException {
		String path = System.getProperty("gedcom.bnf.rules.file");
		if(path == null || path.trim().equals(""))
			throw new GedcomASTException("GEDCOM BNF rules file not specified. "
					+ "Use the 'gedcom.bnf.rules.file property to set the file path");
		
		return path;
	}
	
	private void _initBNFBuilder(String bnfFilePath) throws FileNotFoundException, BNFDocumentBuilderException {
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(new File(bnfFilePath));
			BNFDocumentBuilder builder = new BNFDocumentBuilder(fis);
			this.m_BnfDoc = builder.build();
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
