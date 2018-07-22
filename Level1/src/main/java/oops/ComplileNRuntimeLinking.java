package oops;

import oops.models.Animal;
import oops.models.Dog;
import oops.models.Flesh;
import oops.models.Water;

public class ComplileNRuntimeLinking {

	public static void main(String[] args) {
		ComplileNRuntimeLinking cnr = new ComplileNRuntimeLinking();
		cnr.testNoOverride();
	}

	private void testNoOverride() {
		Animal animal = new Dog();
		Flesh flesh = new Flesh();
		Water water = new Water();
		animal.eat(flesh);			//Prints -> Animal eats flesh
		animal.drink(water);		//Prints -> Dog drinks water
	}

}
