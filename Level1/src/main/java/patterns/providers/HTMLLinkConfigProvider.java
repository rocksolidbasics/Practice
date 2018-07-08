package patterns.providers;

public class HTMLLinkConfigProvider implements Provider_I {
	
	private String m_Val;
	
	public HTMLLinkConfigProvider() { }
	
	public HTMLLinkConfigProvider(String value) {
		this.m_Val = value;
	}

	public void print() {
		System.out.println("Link config proivder => " + m_Val);
	}
	
}
