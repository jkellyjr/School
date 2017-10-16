/*************************************************
  Lab 6
  By: John Kelly Jr (2/22/15)

	The program uses the three basic methods to get a number, get a filename,
	and count the lines in the file.
*************************************************/

import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class Lab6 {

	// gets a valid number from the user and returns it
	public static double getValidNumber(double LOWER_BOUND, double UPPER_BOUND){
		Scanner kb = new Scanner(System.in);
		boolean error = false;
		double num = 0.0;

		do{
			error=false;

			try{
				System.out.println("Please enter in a number:");
				num = kb.nextDouble();

				if(num < LOWER_BOUND || num > UPPER_BOUND){
					System.out.println("Your number must be between the limits "+LOWER_BOUND+" and "+ UPPER_BOUND);
					error=true;
				}

			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number");
				error=true;
				kb.nextLine();

			}catch(Exception e){
				System.out.println("I'm sorry, something has gone wrong.");
				error=true;
				kb.nextLine();
			}

		}while(error);

		return num;//returns the number
	}

	// gets and returns a valid filename
	public static String getFilename()throws IOException{
		Scanner kb = new Scanner(System.in);
		boolean error;
		String filename = null;
		File file = null;

		do{
			error=false;
			try{
				System.out.println("Please enter in a filename:");
				filename=kb.nextLine();
				file = new File(filename);

				if (!file.exists()){
					System.out.println("That file cannot be found.");
				}

			}catch(InputMismatchException ime){
				System.out.println("You must enter in a filename.");
				error=true;
				kb.nextLine();
			}catch(NoSuchElementException nse){
				System.out.println("There is a problem with the file.");
				error=true;
			}
		}while(!file.exists() || error);

		return filename;
	}

	/**
	 * The countFileLines method takes in a filename as the parameter, opens,
	 * and reads the file while counting the files lines.
	 * @param The filename
	 * @return The number of lines in the file.
	 * @throws IOException
	 */
	// counts the number of lines in the specified file
	public static int countFileLines(String filename) throws IOException{
		int c = 0;//counter
			File file = new File (filename);
			Scanner inFile=new Scanner(file);

			try{
				if (file.exists()){
					while(inFile.hasNextLine()){
						c++;
						// reads until newline character
						inFile.nextLine();
					}
				}
			}catch(NoSuchElementException nsee){
				System.out.println("An error has occured.");
			}
		inFile.close();

		return c;
	}

	// main logic
	public static void main(String[] args) throws IOException{
		final int LOWER_BOUND = 0;
		final int UPPER_BOUND = 100;

		double num=getValidNumber(LOWER_BOUND, UPPER_BOUND);
		System.out.println("Number: "+num);

		Scanner kb=new Scanner(System.in);

		String filename = null;
		boolean error;
		File file=null;
		do{
			error=false;
			try{
				filename = getFilename();
				file = new File(filename);

			}catch(NoSuchElementException nse){
				System.out.println("main NSE");
				error=true;
				kb.nextLine();

			}catch(Exception e){
				System.out.println("An error has occured.");
				error=true;
				kb.nextLine();
			}
		}while(!file.exists() || error);

		int numLines = countFileLines(filename);

		System.out.println("The file "+filename+" has "+numLines+" lines.");
	}
}
