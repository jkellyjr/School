/**
 * Coin Toss
 * Test Version C
 * @author johnkellyjr
 *The program asks a user for their guess of the number of heads, the number of heads
 *is determined with a for loop and random number generator. The computer guess is 
 *determined by a random number generator, the difference, using absolute value, is how
 *the winner is determined. A do while loop continues the program until a user wins.
 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.Random;

public class CoinToss {
 
	/**
	 * Flips the coin to count the number of heads
	 * @return the number of heads
	 */
	public static int getHeads(){
		int heads=0;
		Random r=new Random();
		
		for(int i=0; i<7; i++){
			int side = r.nextInt(2);
	        if (side == 1) 
	            heads++;
		}
		return heads;
 }
	
	/**
	 * Gets the number the user would like to guess
	 * @return the guessed number
	 */
	public static int getUserGuess(){
		int userGuess=0;
		boolean error;
		Scanner kb = new Scanner(System.in);
		do{
			error=false;
			try{
					System.out.println("\nPlayer 1, how many heads do you think will show up"+
							"\n(out of 7 flips): ");
					userGuess=kb.nextInt();
					
					if(userGuess<0||userGuess>7){
						System.out.println("\nYour guess must be between 0 and 7.");
						error=true;
					}
			
			}catch(InputMismatchException ime){
				System.out.println("\nYou must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || userGuess<0 || userGuess>7 );
		return userGuess;
	}
	
	/**
	 * Gets the number the computer guesses
	 * @return the computers guess
	 */
	public static int getCompGuess(){
		Random r=new Random();
		int compGuess=r.nextInt(7)+1;	
		return compGuess;
	}
	
	/**
	 * Gets the winner of that round
	 * @param userGuess the user's guess
	 * @param compGuess the computer's guess
	 * @param heads the number of heads
	 */
	public static int getClosest(int userGuess, int compGuess, int heads){
		int winner=0;
		
		int userDiff=heads-userGuess;
		int compDiff=heads-compGuess;
		
		userDiff=Math.abs(userDiff);
		compDiff=Math.abs(compDiff);
		
		if(compDiff<userDiff){
			winner=1;
			return winner;
		}
		else if(userDiff<compDiff){
			winner=2;
			return winner;
		}
		else{
			winner=3;
			return winner;
		}	
	}
	
	public static void main(String[]args){
		System.out.println("\t\t\tCoin Toss");
		int user=0;
		int comp=0;
		do{			
			int heads=getHeads();
			int compGuess=getCompGuess();
			int userGuess=getUserGuess();		
			
			System.out.println("\nThe computer guesses there will be "+compGuess+
					" heads.");
			System.out.print("There are: "+heads+" heads.");
			
			int winner=getClosest(userGuess, compGuess, heads);
	
			if(winner==1){
				System.out.println("\tThe computer wins this round.");
				comp+=1;
			}
			else if(winner==2){
				System.out.println("\tPlayer 1 wins this round.");
				user+=1;
			}
			else if(winner==3){
				System.out.println("\tIt's a tie.");
				user+=1;
				comp+=1;
			}		
			
			System.out.println("The score is: "+"\n\tPlayer 1: "+user+"\n\tComputer: "+comp+"\n");
			if(comp==10&&user==10){
				comp-=1;
				user-=1;
				System.out.println("\nThe game is a tie, next point wins.");
			}
			else if(user==10) System.out.println("\nPlayer 1 wins the game.");
			else if(comp==10) System.out.println("\nThe computer wins the game.");
			
							
		}while(comp!=10&&user!=10);
	}
}
