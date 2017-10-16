/***************************************
  Lab 4
  By: John Kelly Jr (2/5/15)

	The program will flip a coin and count the number of heads.
***************************************/

import java.util.Scanner;
import java.util.Random;

public class Lab4_1 {

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		System.out.println("How many times would you like to flip the coin?");
		int tries = input.nextInt();

		int heads = 0;

		while (tries < 0 ){
			System.out.println("You have entered in a negative number, please try again.");
			System.out.println("\nPlease enter how many times to flip a coin:");
			tries = input.nextInt();

		}

		// flip the coin
		for (int i = 0; i<= tries ; i++){
			Random attempts = new Random ();
			int flip = attempts.nextInt(2);

			// heads
			if (flip == 1){
				heads++;
			}
		}
		System.out.println("Done. Heads showed up "+ heads +" times." );

	}
}
