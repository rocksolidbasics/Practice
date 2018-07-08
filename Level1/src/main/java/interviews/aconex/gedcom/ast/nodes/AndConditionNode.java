package interviews.aconex.gedcom.ast.nodes;

public class AndConditionNode extends AbstractAstNode {
	
	public AndConditionNode() {
		this("&");
	}

	public AndConditionNode(Object value) {
		super(value);
	}

}