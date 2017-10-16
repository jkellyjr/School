/*************************************************
  Lab 5
  By: John Kelly Jr (2/16/15)

	The program will calculate the amount that the user must pay for their internet
	package. It will save the information to a file, and create a menu of usage history.
*************************************************/

import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class Lab5 {
	public static void main(String[] args){
		Scanner kb = new Scanner(System.in);

		System.out.println("\tInternet Service Expenses Calculator\n");

		boolean error = false;
		String fileName = null;

		do{
			error=false;
			try{
				System.out.println("Please enter in the data file of prior internet usage.");
				fileName=kb.nextLine();

			}catch(InputMismatchException ime){
				System.out.println("Please enter in a file name.");
				error=true;
				kb.nextLine();

			}catch(Exception e){
				System.out.println("Sorry, but something went wrong.");
				error=true;
				kb.nextLine();
			}
		}while(error);

		// create file object
		File file = new File (fileName);

		if(file.exists()){
			Scanner inputFile = null;

			do{
				error=false;
				try {
					inputFile = new Scanner(file);

				} catch (FileNotFoundException fnfe) {
					System.out.println("The file cannot be found.");
					error=true;
					kb.nextLine();

				}catch(Exception e){
					System.out.println("Sorry, but something went wrong.");
					error=true;
					kb.nextLine();
				}
			}while(error);

			double totalPaid=0;
			double fileHours=0;
			double paid=0;
			double x=0;
			double y=0;
			int c=0;


			while(inputFile.hasNext()){//reads data in from file
				c++;
				inputFile.next();// consumes cable package in the file

				fileHours=inputFile.nextDouble();
				x= x+fileHours;

				paid=inputFile.nextDouble();
				y = y+paid;
				totalPaid = paid+ totalPaid;
			}

			double avgHours=0;
			double avgPaid=0;

			avgHours= x/c;
			avgPaid= y/c;

			System.out.println("\nUsage history:");
			System.out.println("\t Average hours used: "+ avgHours);
			System.out.printf("\t Average paid: $%.2f", avgPaid);
			System.out.printf("\n\t Total paid: $%.2f", totalPaid);

			inputFile.close();
		}

		if(!file.exists()){
			System.out.println("The file does not exist. Now it has been created.");
		}

		FileWriter fwriter = null;

		do{
			error=false;
			try {
				// creates file if dne
				fwriter = new FileWriter(fileName,true);

			} catch (IOException ioe) {
				System.out.println("Sorry, but something went wrong.");
				error=true;
				kb.nextLine();
			}
		}while(error);

		// appends file
		PrintWriter outputFile= new PrintWriter(fwriter);

		String cable=null;

		do{
			error=false;
			System.out.println("\nPlease enter in your cable package:");
			cable = kb.nextLine();
			cable=cable.toLowerCase();

			if (!cable.equals("a") && !cable.equals("b") && !cable.equals("c")){
				System.out.println("You have entered in an invalid package");
				error=true;
			}

		}while(error);

		double hours=0;
		int givenHours;
		double variableCost;
		double fixedCost;
		double cost;

		if (cable.equals("a")|| cable.equals("b")||cable.equals("c")){

			do{	//validates hours
				error=false;
				try{
					System.out.println("Please enter in the number of hours used:");
					hours=kb.nextDouble();

					if(hours<0){//makes sure hours are greater than 0
						System.out.println("The hours must be greater than 0.");
						error=true;
					}

				}catch(InputMismatchException ime){
					System.out.println("Please enter a number.");
					error=true;
					kb.nextLine();
				}catch(Exception e){
					System.out.println("Sorry, but something went wrong.");
					error=true;
					kb.nextLine();
				}

			}while(error);

			if(cable.equals("a")){//cable packages
				givenHours=10;
				variableCost=2;
				fixedCost=9.95;

				cost=fixedCost;

				if(hours>givenHours){
					cost += (hours-givenHours)*variableCost;
				}

				System.out.printf("Your total charge is: $%.2f",cost);
				outputFile.println(cable+" "+hours+" "+cost);//outputs to file
			}
			else if(cable.equals("b")) {
				givenHours=20;
				variableCost=1;
				fixedCost=13.95;

				cost=fixedCost;
				if(hours>givenHours)
					cost += (hours-givenHours)*variableCost;

				System.out.printf("Your total charge is: $%.2f",cost);
				outputFile.println(cable+" "+hours+" "+cost);
			}
			else{// cable package c
				cost=19.95;
				System.out.printf("Your total charge is: $%.2f",cost);
				outputFile.println(cable+" "+hours+" "+cost);
			}
		}
		kb.close();
		outputFile.close();
	}
}
