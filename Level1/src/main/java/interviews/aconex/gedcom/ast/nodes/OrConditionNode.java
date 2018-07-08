package interviews.aconex.gedcom.ast.nodes;

public class OrConditionNode extends AbstractAstNode {
	
	public OrConditionNode() {
		this("|");
	}

	public OrConditionNode(Object value) {
		super(value);
	}

}
