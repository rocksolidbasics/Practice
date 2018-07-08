package interviews.aconex.gedcom.ast.nodes;

import java.util.List;

public interface ASTNode_I {

	public List<ASTNode_I> getChildNodes();
	
	public void appendChildNode(ASTNode_I childNode);
	
	public Object getValue();
	
	public boolean isLeaf();
	
}
