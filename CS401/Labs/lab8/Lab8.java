/*************************************************
  Lab 8
  By: John Kelly Jr (3/30/15)

	Controls the crop class
************************************************/

public class Lab8{

	public static void main(String[]args)throws IllegalArgumentException {

		// creates crop object with the specified paramaters
		Crop c = new Crop("Corn",5000, 0.25);
		System.out.println("C's data is: "+c);

		// creates a new crop using the default details
		Crop cr = new Crop();
		System.out.println("\nCr's data is: "+cr);

		// creates a copy of the first crop object
		Crop cro = new Crop(c);
		cro.setYield(5500);
		System.out.println("\nCro's data is: "+cro);

		//compares the first and second objects
		if(c.equals(cr)){
			System.out.println("\nThe first and second crops are equal.");
		}
		else{
			System.out.println("\nThe first and second crops are not equal.");
		}

		//compares the first and third objects
		if(c.equals(cro)){
			System.out.println("\nThe first and third crops are equal.");
		}
		else{
			System.out.println("\nThe first and third crops are not equal.");
		}
	}
}
