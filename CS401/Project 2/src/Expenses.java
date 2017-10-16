/**Expenses class for project 2
 * @author johnkellyjr
 *
 */
import java.util.InputMismatchException;
import java.util.Scanner;

public class Expenses {
	public double afterTaxIncome;
	
	/**
	 * Gets the rent
	 * @return rent
	 */
	public static double getRent(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double rent=0;
		do{
			error=false;
			try{
				System.out.println("Please enter in your rent: ");
				rent=kb.nextDouble();
				kb.nextLine();
				if(rent<=0)
					System.out.println("Your rent must be greater than zero.");
			}catch(InputMismatchException ime/Users/johnkellyjr/Desktop/Project 2/src/Expenses.java){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || rent<=0);
		return rent;
	}
	
	/**
	 * Gets the electric bill
	 * @return electric bill
	 */
	public static double getElectricBill(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double electricBill=0;
		do{
			error=false;
			try{
				System.out.println("Please enter in your electric bill: ");
				electricBill=kb.nextDouble();
				kb.nextLine();
				if(electricBill<=0)
					System.out.println("Your electric bill must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || electricBill<=0);
		return electricBill;	
	}
	
	/**
	 * Gets the water bill
	 * @return the water bill
	 */
	public static double getWaterBill(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double waterBill=0;
		do{
			error=false;
			try{
				System.out.println("Please enter in your water bill: ");
				waterBill=kb.nextDouble();
				kb.nextLine();
				if(waterBill<=0)
					System.out.println("Your water bill must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || waterBill<=0);
		return waterBill;
	}
	
	/**
	 * Gets the sewer bill
	 * @return sewer bill
	 */
	public static double getSewerBill(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double sewerBill=0;
		do{
			error=false;
			try{
				System.out.println("Please enter in your sewer bill: ");
				sewerBill=kb.nextDouble();
				kb.nextLine();
				if(sewerBill<=0)
					System.out.println("Your sewer bill must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || sewerBill<=0);
		return sewerBill;
	}
	
	/**
	 * Gets the gas bill
	 * @return gas bill
	 */
	public static double getGasBill(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double gasBill=0;
		do{
			error=false;
			try{
				System.out.println("Please enter in your gas bill: ");
				gasBill=kb.nextDouble();
				kb.nextLine();
				if(gasBill<=0)
					System.out.println("Your gas bill must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || gasBill<=0);
		return gasBill;
	}
	
	/**
	 * Gets the food budget
	 * @return food budget
	 */
	public static double getFoodBudget(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double foodBudget=0;
		do{
			error=false;
			try{
				System.out.println("Please enter the approximate amount you spend on food per month: ");
				foodBudget=kb.nextDouble();
				kb.nextLine();
				if(foodBudget<=0)
					System.out.println("Your food budget must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || foodBudget<=0);
		foodBudget=foodBudget*12;
		return foodBudget;
	}
	
	/**
	 * Gets the entertainment budget
	 * @return entertainment budget
	 */
	public static double getEntertainmentBudget(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double entertainmentBudget=0;
		do{
			error=false;
			try{
				System.out.println("Please enter the approximate amount you spend on entertainment per month: ");
				entertainmentBudget=kb.nextDouble();
				kb.nextLine();
				if(entertainmentBudget<=0)
					System.out.println("Your entertainment budget must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || entertainmentBudget<=0);
		entertainmentBudget=entertainmentBudget*12;
		return entertainmentBudget;
	}
	
	/**
	 * Gets car expenses
	 * @return car expenses
	 */
	public static double getCarExpenses(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		double carExpenses=0;
		do{
			error=false;
			try{
				System.out.println("Please enter your approximate monthly car expenses: ");
				carExpenses=kb.nextDouble();
				kb.nextLine();
				if(carExpenses<=0)
					System.out.println("Your car expenses must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error || carExpenses<=0);
		carExpenses=carExpenses*12;
		return carExpenses;
	}
}
