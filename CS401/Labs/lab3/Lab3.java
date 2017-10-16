/***************************************
  Lab 3
  By: John Kelly Jr (1/22/15)

	This program will calculate your basic monthy bill based off a specific
	cable package
***************************************/

import java.util.Scanner;

public class Lab3{

	public static void main(String[] args){

		Scanner input = new Scanner(System.in);


		System.out.println("Please enter in your cable package:");
		String cable = input.nextLine();
		cable = cable.toLowerCase();


		double variable_cost;
		double total_variable_cost;
		double fixed_cost;
		double given_hours;
		double total_cost;

		// the different cable packages
		if (cable.equals("a") || cable.equals("b") || cable.equals("c")){
			System.out.println("Please enter in the number of hours used:");
			double hours_used = input.nextDouble();

			if(hours_used < 0){
				System.out.println("The number of hours used must be greater than one.");
				System.exit(0);
			}

			if(cable.equals("a")){
				given_hours = 10;
				variable_cost = 2;
				fixed_cost = 9.95;

				double m = (hours_used - given_hours);

				if(m < 0 ){
					System.out.printf("Your total charge is $%.2f", fixed_cost);
					System.exit(0);

				}else{
					total_variable_cost = (m * variable_cost);
					total_cost = (total_variable_cost + fixed_cost);

					System.out.printf("Your total charge is $%.2f" + "", total_cost);
					System.exit(0);

				}
			}

			else if(cable.equals("b")){

				given_hours = 20;
				variable_cost = 1;
				fixed_cost = 13.95;

				double m = (hours_used - given_hours);
				if(m < 0 ){

					System.out.printf("Your total charge is $%.2f", fixed_cost);
					System.exit(0);

				}else{

					total_variable_cost = (m * variable_cost);
					total_cost = (total_variable_cost + fixed_cost);
					System.out.printf("Your total charge is $%.2f"
							+ "", total_cost);
					System.exit(0);
				}
			}

			// c cable package
			else{
				fixed_cost = 19.95;
				System.out.printf("Your total charge is $%.2f \n", fixed_cost);
				System.exit(0);

			}
		}

		// not a cable package
		else{
			System.out.println("Please enter in a valid cable pakage");
			System.exit(0);
		}
	}
}
