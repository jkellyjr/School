/*************************************************
  Lab 4
  By: John Kelly Jr (2/5/15)

	The program will tell the user which streets are eastbound or westbound in NYC.
**************************************************/

import java.util.Scanner;

public class Lab4_2{

	public static void main(String[] args){
		Scanner input = new Scanner(System.in);

		System.out.println("This program will tell you the direction of the streets in NYC.");

		String repeat;

		do{
			System.out.println("\nPlease enter in the street number:");
			int street = input.nextInt();

			// valid street number check
			while (street < 1 || street > 155){

				System.out.println("The number has to be between 1 and 155.");
				System.out.println("\nPlease enter in the street number:");
				street = input.nextInt();
			}


			int x = (street % 2); // determines even or odd

			if (x == 1){
				System.out.println("The street is westbound.");
			}
			else{
				System.out.println("The street is westboud");
			}

			System.out.println("\nWould you like to try again?");
			repeat = input.next();
			repeat = repeat.toLowerCase();

		} while(repeat.equals("yes"));
	}
}
