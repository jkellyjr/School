/***************************************
  Lab 4
  By: John Kelly Jr (2/5/15)

	Wil calulate the floor of a logarithm (specified by y)
***************************************/

import java.util.Scanner;

public class Lab4_3{

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);

		System.out.println("Please enter in x for the log function:");
		int x = input.nextInt();

		// cannot be lowrer than 0
		while (x < 1){
			System.out.println("Pleas enter in a value greater than zero.");
			System.exit(0);

		}

		System.out.println("Please enter in b for the log function:");
		int b = input.nextInt();

		while (b < 1){
			System.out.println("Pleas enter in a value greater than zero.");
			System.exit(0);

		}

		double q = x/b; //quotient
		int y = 1; //counter & answer

		// calculations
		while(q >= b){
			q /= b;
			y++;
		}

		System.out.println("The floor of the logarithm is: "+y);
	}
}
