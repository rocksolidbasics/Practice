package interviews.aconex.gedcom.bnf;

import interviews.aconex.gedcom.bnf.types.BinaryTerm;
import interviews.aconex.gedcom.bnf.types.Term_I;

public class BNFRule {
	private int m_RuleId;
	private String m_RuleName;
	private String m_RuleExpr;
	
	private Term_I m_RootTerm;
	
	public BNFRule(int ruleId, String ruleName, String ruleExpr) {
		this.m_RuleId = ruleId;
		this.m_RuleName = ruleName;
		this.m_RuleExpr = ruleExpr;
	}
	
	public int getRuleId() {
		return m_RuleId;
	}
	
	public String getRuleName() {
		return m_RuleName;
	}
	
	public String getRuleExpression() {
		return m_RuleExpr;
	}
	
	public Term_I getRootTerm() {
		return m_RootTerm;
	}

	public void setRootTerm(Term_I term) {
		this.m_RootTerm = term;
	}
	
	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		out.append("BNFRule => ID = " + this.m_RuleId + "\n" + 
				"\t Rule name => " + this.m_RuleName + "\n" +
				"\t Rule Expr => " + this.m_RuleExpr + "\n" +
				"\t Constituent terms [\n" +
				"\t\t Term type => " + this.m_RootTerm.getClass().getName() + "\n" +
				"\t\t Is Repeating => " + this.m_RootTerm.isRepeating() + "\n");
		if(this.m_RootTerm instanceof BinaryTerm)
			out.append("\t\t Child term count => " + ((BinaryTerm)this.m_RootTerm).getTerms().size());
		
		return out.toString();
	}
}