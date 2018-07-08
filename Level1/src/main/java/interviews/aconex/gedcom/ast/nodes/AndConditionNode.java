package interviews.aconex.gedcom.ast.nodes;

public class AndConditionNode extends AbstractAstNode implements ASTConditionNode_I {
	
	public AndConditionNode() {
		this("&");
	}

	public AndConditionNode(Object value) {
		super(value);
	}

}
