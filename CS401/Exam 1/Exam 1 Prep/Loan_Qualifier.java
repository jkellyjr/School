//Loan Qualifier

import java.util.Scanner;

public class Loan_Qualifier {
	
	public static void main(String[]args){
		Scanner kb=new Scanner(System.in);
		
		System.out.println("Enter annual salary: ");
		double sal=kb.nextDouble();
		
		while(sal>0){
			System.out.println("Your salary must be above zero.");
			System.out.println("Enter annual salary: ");
			sal=kb.nextDouble();
		}
		
		System.out.println("Enter the number of years you have been working: ");
		double yearsWorking=kb.nextDouble();
		System.out.println("Enter loan request: ");
		double loan=kb.nextDouble();
		
		double perSal= loan / sal;	
		if(perSal<.5)
			System.out.println("Your loan has been approved.");
		
		else if(yearsWorking<5)
			System.out.println("Your loan will need further review.");
		
		else if(sal<30000 && yearsWorking<6)
			System.out.println("Your loan has been denied");
		
		else if(sal<30000 && yearsWorking>6)
			System.out.println("Your loan will need further review.");
		
		else if(sal<50000 && loan<=5000)
			System.out.println("Your loan has been approved.");
		
		
		else if(sal<50000 && loan>5000)
			System.out.println("Your loan will be approved up to $5,000.");
		
		else
			System.out.println("Your loan has been approved.");
	}

}
