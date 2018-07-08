package patterns.providers;

public class HTMLCSSConfigProvider implements Provider_I {

	private String m_Val;
	
	public HTMLCSSConfigProvider() { }
	
	public HTMLCSSConfigProvider(String val) {
		this.m_Val = val;
	}
	
	public void print() {
		System.out.println("CSS config provider => " + this.m_Val);
	}
}
