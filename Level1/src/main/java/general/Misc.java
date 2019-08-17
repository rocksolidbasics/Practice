package general;

public class Misc {

	private static final String EXPORT_TYPE_SUFFIX = "export-type";
	private static final String PROC_OUT_PREFIX = "config.proc.out";
	
	public static void main(String[] args) {
		Misc m = new Misc();
		m.testSubstring1();
		m.testSubstring2();
	}

	private void testSubstring2() {
		String expr = "count(uid)";
		String operand = expr.trim().substring("count".length()+1, expr.length()-1);
		System.out.println(operand);
		
		String t = "(uid != NULL)";
		
		if(t.startsWith("("))
			t = t.substring(1);
		if(t.endsWith(")"))
			t = t.substring(0, t.length()-1);
		
		System.out.println(t);
	}

	private void testSubstring1() {
		String key = "config.proc.out.p1.export-type";
		
		if(((String)key).startsWith(PROC_OUT_PREFIX) && 
					((String)key).endsWith(EXPORT_TYPE_SUFFIX)) {
				String s = ((String)key).trim();
				s = s.substring(PROC_OUT_PREFIX.length() + 1, s.length()-EXPORT_TYPE_SUFFIX.length()-1);

				System.out.println(s);
		}
	}
}
