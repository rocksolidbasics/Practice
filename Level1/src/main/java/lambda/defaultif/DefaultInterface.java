package lambda.defaultif;

public class DefaultInterface implements InterfaceA, InterfaceB {

	/**
	 * This method is the default method overriden, hence the compiler
	 * will call this method (as the nearest method), even though both
	 * InterfaceA and InterfaceB has the same methods
	 */
	@Override
	public void print() {
		InterfaceA.super.print();
	}

	@Override
	public void test() {
		System.out.println("Abstract method implemented");
	}
	
}
