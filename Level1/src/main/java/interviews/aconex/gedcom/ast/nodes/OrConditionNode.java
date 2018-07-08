package interviews.aconex.gedcom.ast.nodes;

public class OrConditionNode extends AbstractAstNode implements ASTConditionNode_I {
	
	private boolean isRepeating = false;
	
	public OrConditionNode() {
		this("|");
	}

	public OrConditionNode(Object value) {
		super(value);
	}

	public boolean isRepeating() {
		return isRepeating;
	}

	public void setRepeating(boolean isRepeating) {
		this.isRepeating = isRepeating;
	}

}
