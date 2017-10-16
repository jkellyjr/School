/**John Kelly Jr Lab 6
 * 
 *The program uses the three basic methods to get a number, get a filename,
 *and count the lines in the file.
 *
 *The program uses the first method to get a number, and return it if it 
 *meets the parameter's criteria. The second method gets a filename, makes
 *sure that it exists, and returns the filename. The third method takes in 
 *the filename from the second method, counts the lines, and returns the
 *number of lines. The main method calls the other methods, and handles exceptions.
 */
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;

public class lab6 {
	
	/**
	 * The getValidNumber method gets a number, uses the parameter as the 
	 * limits, and returns that number.
	 * @param The lower limit
	 * @param The upper limit
	 * @return The number passed in if it passes the conditions 
	 */
	public static double getValidNumber(double LOWER_BOUND, double UPPER_BOUND){
		Scanner kb=new Scanner(System.in);
		boolean error=false;
		double num=0.0;
		do{
			error=false;
			try{//gets a number entered in from the user
				System.out.println("Please enter in a number:");
				num = kb.nextDouble();
				
				if(num < LOWER_BOUND || num > UPPER_BOUND){
					System.out.println("Your number must be between the limits.");
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

	/**
	 * The getFileName method asks the user for a filename, checks if it exists,
	 * and returns the filename if it exists. If it doesn't exist then it asks again. 
	 * @return The filename
	 * @throws IOException
	 */
	public static String getFilename()throws IOException{
		Scanner kb=new Scanner(System.in);
		boolean error;
		String filename=null;
		File file=null; //creates file object to use for data validation
		do{
			error=false;
			try{
				System.out.println("Please enter in a filename:");
				filename=kb.nextLine();
				file = new File(filename);
				
				if (!file.exists())
					System.out.println("That file cannot be found.");
				
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
	public static int countFileLines(String filename) throws IOException{
		int c = 0;//counter
			File file = new File (filename); 
			Scanner inFile=new Scanner(file);
			try{
				if (file.exists()){ 					
					while(inFile.hasNextLine()){
						c++;
						inFile.nextLine();//reads until \n
					}
				}
			}catch(NoSuchElementException nsee){
				System.out.println("An error has occured.");
			}	
		inFile.close();
		return c;
	}
	
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

