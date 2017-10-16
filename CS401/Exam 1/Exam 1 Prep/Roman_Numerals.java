//Roman Numerals

import java.util.Scanner;

public class Roman_Numerals {
	public static void main(String[]args){
		Scanner kb=new Scanner(System.in);
		System.out.println("Enter a number (1-10): ");
		int num =kb.nextInt();
		
		while(num<1 || num>10){
			System.out.println("The number must be between 1 and 10.");
			System.out.println("Enter a number (1-10): ");
			num =kb.nextInt();

		}
		
		if (num==1)
			System.out.println("I");
		else if (num==2)
			System.out.println("II");
		else if(num==3)
			System.out.println("III");
		else if(num==4)
			System.out.println("IV");
		else if(num==5)
			System.out.println("V");
		else if(num==6)
			System.out.println("VI");
		else if(num==7)
			System.out.println("VII");
		else if(num==8)
			System.out.println("VIII");
		else if(num==9)
			System.out.println("IX");
		else
			System.out.println("X");
		
	}

}
