//Integer division 
import java.util.Scanner;

public class Division {

	public static void main(String[] args) {
		Scanner kb = new Scanner (System.in);
		System.out.println("Enter numerator: ");
		int numerator = kb.nextInt();
		System.out.println("Enter denominator:");
		int denominator = kb.nextInt();
		
		while(denominator==0){
			System.out.println("The denominator cannot be zero:");
			System.out.println("Enter denominator:");
			denominator = kb.nextInt();
		}
		
		int quotient = numerator / denominator;
		int remainder = numerator % denominator;
		
		double  numerator1=numerator;
		double denominator1=denominator;
		
		double result = numerator1 / denominator1;
		
		System.out.println("After dividing, the result is "+result+"; the quotient is "+
		quotient+" and the remainder is "+remainder+".");
	
	}

}
