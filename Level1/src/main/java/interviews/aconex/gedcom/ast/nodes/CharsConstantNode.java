package interviews.aconex.gedcom.ast.nodes;

public class CharsConstantNode extends AbstractAstNode implements ASTConstantNode_I {

	public CharsConstantNode() {
		this(null);
	}
	
	public CharsConstantNode(Object value) {
		super(value);
	}
	
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String[] v = (String[])this.getValue();
		
		for(int i=0; i<v.length; i++) {
			sb.append(v[i] + ", ");
		}
		
		return sb.toString();
	}

}
