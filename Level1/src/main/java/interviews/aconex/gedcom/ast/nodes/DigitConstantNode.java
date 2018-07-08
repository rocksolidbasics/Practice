package interviews.aconex.gedcom.ast.nodes;

public class DigitConstantNode extends AbstractAstNode implements ASTConstantNode_I {

	public DigitConstantNode() {
		this(null);
	}
	
	public DigitConstantNode(Object value) {
		super(value);
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String[] v = (String[])this.getValue();
		
		for(int i=0; i<v.length; i++) {
			sb.append(v[i] + ", ");
		}
		
		return sb.toString();
	}

}
