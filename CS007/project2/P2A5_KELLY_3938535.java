/* Author: John Kelly Jr
Filename: P2A5_KELLY_3938535
Description: Create a simulations of a craps game.
This particular program will allow the user to try his or her luck. */

import java.util.Scanner; // Import scanner
import java.util.Random; // Import random number generator
import javax.swing.JOptionPane; // Import dialog box

public class P2A5_KELLY_3938535
{
	public static void main(String[] args)
	{

		Scanner input = new Scanner(System.in);
		Random R = new Random();

		// Welcome message

		int tries = JOptionPane.showInputDialog("Please enter in how many"
				+ " times you would like to play:"); // Dialog box


		for(int tried; tried; tried--)





			int dice = R.nextInt(11) + 2; // Two die

			System.out.println("You rolled a: " + dice);


			if(dice == 7 || dice == 11) // A win
			{
				System.out.println("Congratulations you won!");
				System.exit(0);
			}

			else
			{
				if (dice == 2 || dice == 3 || dice == 12) // A loss
				{
					System.out.println("I'm sorry, you lost.");
					System.exit(0);
				}


				else
					System.out.println("Your new point number is: " + dice
						+ ". Good luck!");

			}


			System.out.println("Please enter in \"yes\" if you would like to "
				+ "play again.");
			String confirmation = input.next();

			while (confirmation.equals("yes"))
			{
				int dice2 = R.nextInt(11) + 2; // Two die

				System.out.println("You rolled a: " + dice2);


			if (dice2 == 7)
				{
				System.out.println("I'm sorry, you lost.");
				System.exit(0);
			}

			else if (dice2 == dice)
			{
				System.out.println("Congratulations you won!");
				System.exit(0);
			}

			else
				System.out.println("I'm sorry, you did not win.");

			System.out.println("Please enter in \"yes\" if you would like to "
				+ "play again.");

			String confirmation2 = input.next();

			if (confirmation2.equals(confirmation))

				System.out.println("Best of luck!");

			else
				System.exit(0);

			System.exit(0); // End of dialog box
		}
	}

}
