package interviews.aconex.gedcom.bnf.types;

public abstract class AbstractTerm implements Term_I {

	private String m_Name;
	private boolean m_IsRepeating;
	
	public AbstractTerm(String expression) {
		this.m_Name = expression;
	}
	
	public boolean isRepeating() {
		return this.m_IsRepeating;
	}
	
	public void setRepeating(boolean repeat) {
		this.m_IsRepeating = repeat;
	}

	@Override
	public String getName() {
		return this.m_Name;
	}

}
