package interviews.aconex.gedcom.ast.nodes;

import java.util.LinkedList;
import java.util.List;

public class AbstractAstNode implements ASTNode_I {

	private Object m_NodeValue;
	private List<ASTNode_I> m_ChildNodes = new LinkedList<ASTNode_I>();
	
	public AbstractAstNode(Object value) {
		this.m_NodeValue = value;
	}
	
	@Override
	public List<ASTNode_I> getChildNodes() {
		return this.m_ChildNodes;
	}

	@Override
	public Object getValue() {
		return this.m_NodeValue;
	}

	@Override
	public boolean isLeaf() {
		return this.m_ChildNodes.size() == 0;
	}

	@Override
	public void appendChildNode(ASTNode_I childNode) {
		this.m_ChildNodes.add(childNode);
	}
	
	@Override
	public String toString() {
		return this.m_NodeValue.toString();
	}

}
