package interviews.aconex.gedcom.bnf;

class BNFLine {
	private int m_LineNumber;
	private String m_RuleExpression;
	
	public BNFLine(int m_LineNumber, String m_RuleExpression) {
		this.setLineNumber(m_LineNumber);
		this.setRuleExpression(m_RuleExpression);
	}

	public int getLineNumber() {
		return m_LineNumber;
	}

	public void setLineNumber(int m_LineNumber) {
		this.m_LineNumber = m_LineNumber;
	}

	public String getRuleExpression() {
		return m_RuleExpression;
	}

	public void setRuleExpression(String m_RuleExpression) {
		this.m_RuleExpression = m_RuleExpression;
	}
	
	@Override
	public String toString() {
		return "BNF rule text: Line => " + this.m_LineNumber + ", Expression => " + this.m_RuleExpression;
	}
}