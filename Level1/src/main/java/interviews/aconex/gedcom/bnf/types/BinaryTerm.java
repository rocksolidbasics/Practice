package interviews.aconex.gedcom.bnf.types;

import java.util.LinkedList;
import java.util.List;

public class BinaryTerm extends AbstractTerm {

	public enum BINARY_OPERATOR {OR, AND};

	private List<Term_I> m_List = new LinkedList<Term_I>(); 
	
	public BinaryTerm(String expression) {
		super(expression);
	}
	
	public BINARY_OPERATOR getOperator() {
		if(this.getName().equals("&"))
			return BINARY_OPERATOR.AND;
		else
			return BINARY_OPERATOR.OR;
	}

	public void addTerm(Term_I term) {
		this.m_List.add(term);
	}
	
	public List<Term_I> getTerms() {
		return new LinkedList<Term_I>(this.m_List);
	}
	
}
