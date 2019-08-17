package lambda.defaultif;

public interface InterfaceB {
	
	public void test();

	default void print() {
		System.out.println("Interface B");
	}
}
