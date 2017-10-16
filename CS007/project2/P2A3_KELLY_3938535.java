/* Author: John Kelly Jr
Filename: P2A3_KELLY_3938535
Description: Create a simulations of a craps game.
This particular program will allow the user to try his or her luck. */

import java.util.Scanner; // Import scanner
import java.util.Random; // Import random number generator

public class P2A3_KELLY_3938535
{
	public static void main(String[] args)
	{
		Scanner input = new Scanner(System.in);

		System.out.println("Hello, please enter your first name:");
		String firstName = input.next();

		System.out.println("Hello, please enter your last name:");
		String lastName = input.next();

		System.out.println("Hello " + firstName + " " + lastName +
					", welcome to John Kelly "
				+ "Jr's craps simulation. Best of luck!");

		System.out.println("Here are some instructions:");
		System.out.println("If you roll a 7 or 11 on the first roll you win.");
		System.out.println("If you roll a 2, 3, or 12 on the first roll you lose.");
		System.out.println("If you roll any other number, the \"come out roll\""
					+ " becomes the \"point number.\"");
		System.out.println("You must keep rolling until you roll a 7, "
				+ "in which you lose, or roll the \"point number\""
				+ ", in which you win.");

		// Welcome message


		Random R = new Random();

		int dice = R.nextInt(11) + 2; // Two die


		System.out.println("You rolled a: " + dice);


		if(dice == 7 || dice == 11) // A win
			System.out.println("Congratulations you won!");


		else
		{
			if (dice == 2 || dice == 3 || dice == 12) // A loss
				System.out.println("I'm sorry, you lost.");

			else
				System.out.println("Your new point number is: " + dice
				+ ". Good luck!");

		}
	}
}
