package interviews.aconex.gedcom.ast;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import interviews.aconex.gedcom.ast.nodes.ASTConditionNode_I;
import interviews.aconex.gedcom.ast.nodes.ASTConstantNode_I;
import interviews.aconex.gedcom.ast.nodes.ASTNode_I;
import interviews.aconex.gedcom.ast.nodes.AndConditionNode;
import interviews.aconex.gedcom.ast.nodes.CharsConstantNode;
import interviews.aconex.gedcom.ast.nodes.DigitConstantNode;
import interviews.aconex.gedcom.ast.nodes.OrConditionNode;
import interviews.aconex.gedcom.ast.nodes.StringConstantNode;
import interviews.aconex.gedcom.ast.nodes.TypeNode;
import interviews.aconex.gedcom.objectmodel.GedcomRecord;

public class GedcomParser {

	private ASTNode_I m_AstRoot;
	private HashMap<String,String> m_ParsedMap = new HashMap<>();
	
	public GedcomParser(ASTNode_I astNode) {
		this.m_AstRoot = astNode;
	}
	
	public GedcomRecord parseRecord(String record) {
		this._parse(this.m_AstRoot, record+"_ ", 0);
		return createRecord();
	}
	
	private GedcomRecord createRecord() {
		GedcomRecord gRecord = new GedcomRecord();

		if(this.m_ParsedMap.get("ID") != null && !this.m_ParsedMap.get("ID").equals("")) {
			gRecord.setRecordClassifier(GedcomRecord.CLASSIFIER.ID);
			gRecord.setRecordType(this.m_ParsedMap.get("DATA"));
			gRecord.setRecordData(this.m_ParsedMap.get("ID"));
			gRecord.setLevel(Integer.parseInt(this.m_ParsedMap.get("LEVEL")));
		} else if(this.m_ParsedMap.get("TAG") != null && !this.m_ParsedMap.get("TAG").equals("")) {
			gRecord.setRecordClassifier(GedcomRecord.CLASSIFIER.INFO);
			gRecord.setRecordType(this.m_ParsedMap.get("TAG"));
			gRecord.setRecordData(this.m_ParsedMap.get("DATA"));
			gRecord.setLevel(Integer.parseInt(this.m_ParsedMap.get("LEVEL")));
		}
		
		return gRecord;
	}

	private int _parse(ASTNode_I node, String value, int curIndex) {
		List<ASTNode_I> childNodes = node.getChildNodes();
		
		Iterator<ASTNode_I> astIter = childNodes.iterator();
		while(astIter.hasNext()) {
			ASTNode_I lnode = astIter.next();
			int newIndex = this._parse(lnode, value, curIndex);
			newIndex = skipSpace(value, newIndex);
			
			if(node instanceof TypeNode) {
				this.m_ParsedMap.put(node.getValue().toString(), value.substring(curIndex, newIndex).trim());
				curIndex = newIndex;
			} else if(node instanceof ASTConditionNode_I) {
				if(newIndex > curIndex && node instanceof OrConditionNode && ((OrConditionNode)node).isRepeating()) {
					astIter = childNodes.iterator();
				} else if(newIndex == curIndex && node instanceof AndConditionNode) {
					return curIndex;
				}
				curIndex = newIndex;
			}
		}
		
		if(node instanceof ASTConstantNode_I) {
			int newIndex = ConstantMatcher.match(node, value, curIndex);
			if(newIndex > curIndex) {
				curIndex = newIndex;
				return curIndex;
			}
		}
		
		return curIndex;
	}
	
	private int skipSpace(String value, int index) {
		while(value.charAt(index) == ' ')
			index++;
		
		return index;
	}

	private static class ConstantMatcher {

		public static int match(ASTNode_I node, String value, int curIndex) {
			int i = curIndex;
			if(node instanceof DigitConstantNode) {
				while(i<value.length()-1 && node.getValue().toString().indexOf(value.charAt(i)) != -1)
					i++;
				return i;
			} else if(node instanceof StringConstantNode) {
				if(curIndex+node.getValue().toString().length() < value.length()-1 && 
						node.getValue().equals(value.substring(curIndex, curIndex+node.getValue().toString().length())))
					return curIndex + node.getValue().toString().length();
			} else if(node instanceof CharsConstantNode) {
				while(i<value.length()-1 && node.getValue().toString().indexOf(value.charAt(i)) != -1)
					i++;
				return i;
			}
			
			return curIndex;
		}
		
	}

}
