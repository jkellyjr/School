/**********************************************
  Project 2 Part 2
  By: John Kelly Jr (11/2/14)

  This is a basic version of craps.
**********************************************/

import java.util.Scanner;
import java.util.Random;
import javax.swing.JOptionPane;

public class P2P2 {
	public static void main(String[] args) {

		Scanner input = new Scanner(System.in);
		Random rand = new Random();

		// Welcome message
		int tried = JOptionPane.showInputDialog("Please enter in how many"
				+ " times you would like to play:"); // Dialog box


    // play the specified number of times
		for (tried; tried < 0; tried--) {

			int dice = rand.nextInt(11) + 2; // Two die

			System.out.println("You rolled a: " + dice);

      		// a win
			if (dice == 7 || dice == 11) {
				System.out.println("Congratulations you won!");
				System.exit(0);
			}

		     // a loss
			 if (dice == 2 || dice == 3 || dice == 12) {
			   System.out.println("I'm sorry, you lost.");
			   System.exit(0);
			 }

			else {
				System.out.println("Your new point number is: " + dice
					+ ". Good luck!");
			}


			System.out.println("Please enter in \"yes\" if you would like to "
				+ "play again.");
			String confirmation = input.next();

			while (confirmation.equals("yes")) {
				int dice2 = rand.nextInt(11) + 2; // Two die

				System.out.println("You rolled a: " + dice2);

        	 	// a loss
				if (dice2 == 7){
  				  System.out.println("I'm sorry, you lost.");
  				  System.exit(0);
  			  	}

        		// a win
				else if (dice2 == dice){
  				  System.out.println("Congratulations you won!");
  				  System.exit(0);
  			  	}

				else {
            		System.out.println("I'm sorry, you did not win.");
          		}


				System.out.println("Please enter in \"yes\" if you would like to "
  				  + "play again.");

			  	String confirmation2 = input.next();

				 if (confirmation2.equals(confirmation)) {
             		System.out.println("Best of luck!");
	           	}

        		// end of game
			 	else{
             		System.exit(0);
           		}

      		}

			System.exit(0); // End of dialog box
		}
	}
}
