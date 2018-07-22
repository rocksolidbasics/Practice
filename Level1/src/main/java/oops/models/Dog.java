package oops.models;

public class Dog extends Animal {

	// This is an overload - hence compile time linking
	// @Override - This will mark an error on this method
	public void eat(Flesh flesh) {
		System.out.println("Dog eats " + flesh);
	}

	// This is an override - hence runtime linking
	@Override
	public void drink(Water water) {
		System.out.println("Dog drinks " + water);
	}
}
