package lambda.defaultif;

public interface InterfaceA {
	
	public void test();
	
	default void print() {
		System.out.println("Interface A");
	}
}
