package oops;

/**
 * Demonstrates
 *	- Non-static inner class
 *	- Static inner class
 *	- Method local inner class
 *	- Anonymous class
 *	- Member shadowing (data and method)
 */

public class InnerClassTypes {

	//Private variable accessible from non-static inner classes
	private String m_PrivVar;
	protected String m_ProtVar;
	
	private static String m_EnclosingStaticVar;
	private static String m_PrivStaticVar;
	
	//This is protected an static
	protected static String m_StaticProtVar;
	
	public static void main(String[] args) {
		InnerClassTypes ict = new InnerClassTypes();
		ict.m_PrivVar = "Enclosing";
		
		InnerClassTypes.NonStaticInner nsi = ict.new NonStaticInner("Inner");
		nsi.accessParam("Param");
		nsi.accessLocalVar("Local");
		nsi.accessLocalVar();
		nsi.accessInnerVar("");
		nsi.accessEnclosingVar("");
		
		//Can access non-static inner variables
		ict.accessNonStaticInnerPrivateVariable(nsi);
		
		m_EnclosingStaticVar = "Enclosing static";
		m_PrivStaticVar = "Enclosing private static";
		m_StaticProtVar = "Enclosing protected static";
		
		StaticInnerClass sic = new StaticInnerClass("Static Inner");
		sic.accessInnerVar();
		sic.accessShadowedInnerStaticVar();
		sic.accessShadowedEnclosingStaticVar();
		sic.accessEnclosingStaticVar();
		sic.accessEnclosingNonStaticVar();
		
		StaticInnerClass.m_StaticProtVar = "Inner static private";
		
		ict.testMethodLocalAnonymousClass();
		
		ict.m_Runnable.run();
		
		ict.testMethodLocalInnerClass();
	}
	
	public void testMethodLocalInnerClass() {
		String lVar = "Method local";
		String l2Var = "Non shadowed method local var";
		
		//Only non-static classes are allowed
		class MethodInner {
			
			private String lmVar = "Method Inner var";
			private String lVar = "Shadowed method local var";
			
			public void accessVars() {
				System.out.println("Method inner => method local inner: " + lmVar);
				System.out.println("Method inner => shadowed method local var: " + lVar);
				System.out.println("Method inner => method local var: " + l2Var);
			}
			
		}
		
		//Static inner classes in method not allowed - Only allowed 'final' and 'abstract'
		//static class MethodStaticInner { }
		
		MethodInner mi = new MethodInner();
		mi.accessVars();
	}
	
	/**
	 * Method local anonymous classes
	 */
	public void testMethodLocalAnonymousClass() {
		String lVar = "Local variable";
		String m_PrivVar = "Shadowed enclosing var";
		
		Runnable r = new Runnable() {

			@Override
			public void run() {
				System.out.println("Method local anon => shadowed enclosing private var: " + m_PrivVar);
				System.out.println("Method local anon => enclosing private var: " + InnerClassTypes.this.m_PrivVar);
				System.out.println("Method local anon => enclosing method local var: " + lVar);
			}
			
		};
		
		String lPrintVal = "Hello";
		ConstructorInput c = new ConstructorInput(lPrintVal) {
			public void printValue() {
				System.out.println("ACI print: " + this.v1);
			}
		};
		
		r.run();
		c.printValue();
	}
	
	public class ConstructorInput {
		protected String v1;
		
		public ConstructorInput(String v) {
			this.v1 = v;
		}
		
		public void printValue() {
			System.out.println("CI print: " + v1);
		}
	}
	
	/**
	 * Class anonymous classes
	 */
	private Runnable m_Runnable = new Runnable() {

		private String lVar = "Local variable";
		String m_PrivVar = "Shadowed enclosing var";
		
		@Override
		public void run() {
			System.out.println("Class field anon => shadowed enclosing private var: " + m_PrivVar);
			System.out.println("Method field anon => enclosing private var: " + InnerClassTypes.this.m_PrivVar);
			System.out.println("Method field anon => enclosing method local var: " + lVar);
		}
		
	};
	
	public void accessNonStaticInnerPrivateVariable(InnerClassTypes.NonStaticInner nsi) {
		System.out.println("Access non-static inner's variable from enclosing: " + nsi.m_PrivVar);
	}
	
	//A static inner class
	public static class StaticInnerClass {
		
		private String m_PrivVar;
		
		//No visibility for enclosing data members:
		//A static variable in the enclosing class can be declared as non-static
		private String m_PrivStaticVar;
		
		private static String m_StaticProtVar;
		
		public StaticInnerClass(String privVar) {
			this.m_PrivVar = privVar;
		}
		
		public void accessInnerVar() {
			System.out.println("Non-static Inner var: " + this.m_PrivVar);
		}

		
		public void accessEnclosingNonStaticVar() {
			//System.out.println("Non-static Inner Enclosing var: " + m_ProtVar);
			System.out.println("Enclosing non-static fields are not accessible");
		}
		
		public void accessEnclosingStaticVar() {
			System.out.println("Enclosing static field: " + m_EnclosingStaticVar);
		}
		
		public void accessShadowedInnerStaticVar() {
			System.out.println("Shadowed static field: " + m_StaticProtVar);
		}
		
		public void accessShadowedEnclosingStaticVar() {
			System.out.println("Shadowed enclosing static field: " + InnerClassTypes.m_StaticProtVar);
		}
	}
	
	//A non-static inner class
	public class NonStaticInner {
		
		private String m_PrivVar;
		
		//Static variables not allowed in a non-static inner class
		//private static m_StaticVar;
		
		public NonStaticInner(String var) {
			this.m_PrivVar = var;
		}
		
		/*
		 * Inner class variable shadowing
		 */
		public void accessParam(String m_PrivVar) {
			System.out.println("Param value: " + m_PrivVar);
		}
		
		public void accessLocalVar(String m_PrivVar) {
			//String m_PrivVar = "Local var";	- Duplicate variable not allowed
			System.out.println("Duplicate variable with same name as parameter not allowed");
		}
		
		public void accessLocalVar() {
			String m_PrivVar = "Local";
			System.out.println("Local var: " + m_PrivVar);
		}
		
		/**
		 * Enclosing class variable shadowing
		 */
		public void accessInnerVar(String m_PrivVar) {
			System.out.println("Inner var: " + this.m_PrivVar);
		}
		
		public void accessEnclosingVar(String m_PrivVar) {
			System.out.println("Enclosing var: " + InnerClassTypes.this.m_PrivVar);
		}
		
		
	}
}
