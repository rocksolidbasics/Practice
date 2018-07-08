package interviews.aconex.gedcom.bnf;

import interviews.aconex.gedcom.bnf.types.Term_I;

public class BNFRule {
	private int m_RuleId;
	private String m_RuleTerm;
	private String m_RuleExpr;
	
	private Term_I m_SubRules;
	
	public BNFRule(int ruleId, String ruleTerm, String ruleExpr) {
		this.m_RuleId = ruleId;
		this.m_RuleTerm = ruleTerm;
		this.m_RuleExpr = ruleExpr;
	}
	
	public int getRuleId() {
		return m_RuleId;
	}
	
	public String getRuleTerm() {
		return m_RuleTerm;
	}
	
	public String getRuleExpression() {
		return m_RuleExpr;
	}
	
	@Override
	public String toString() {
		return "ID => " + this.m_RuleId + ", Term => " + this.m_RuleTerm + ", Expression => " + this.m_RuleExpr;
	}

	public Term_I getSubRules() {
		return m_SubRules;
	}

	public void setSubRules(Term_I term) {
		this.m_SubRules = term;
	}
}