/**Project 2
 * John Kelly Jr
 * 
 * The program will calculate your approximate taxes and display how much 
 * an approximate number to invest.I used an expenses class instead of
 * a deduction class, because I do not know enough about the tax system
 * (that is different from my project 2 proposal).
 */
import java.util.InputMismatchException;
import java.util.NoSuchElementException;
import java.util.Scanner;
import java.io.*;
import java.text.DecimalFormat;
import javax.swing.JOptionPane;


public class P2 {
	
	/**
	 * Asks for a filename and returns it if it is valid
	 * @return the filename
	 */
	public static String getFilename(){
		Scanner kb=new Scanner(System.in);
		boolean error;
		File file=null;
		String filename=null;
		do{
			error=false;
			try{
				System.out.println("\nPlease enter in a filename: ");
				filename=kb.nextLine();
				file= new File(filename);//creates file object
				
				if(!file.exists())
					System.out.println("The file cannot be found.");
				
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
	 * Finds an approximate after tax income
	 * @return the after tax income
	 */
	public static double getAfterTaxIncome(){
		Scanner kb=new Scanner(System.in);
		double basePay=0.0;
		boolean error;
		double afterTaxIncome=0.0;
		String married=null;
		String filingTogether=null;
		String headHousehold=null;
		double secondBasePay=0;
		do{//gets base pay
			error=false;
			try{
				System.out.println("Please enter in your base pay: ");
				basePay=kb.nextDouble();
				kb.nextLine();
				if(basePay<=0)
					System.out.println("Your base pay must be greater than zero.");
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a number.");
				error=true;
				kb.nextLine();
			}
		}while(error||basePay<=0);
		do{
			error=false;
			try{// whether they are married or not
				System.out.println("\nAre you married?");
				married=kb.nextLine();
				married=married.toLowerCase();				
				if(!married.equals("yes") && !married.equals("no")){
					System.out.println("Please answer the question (yes or no).");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		
		if(married.equals("yes")){//are they filing together
			do{
				error=false;
				try{
					System.out.println("Are you filing with your husband or wife?");
					filingTogether=kb.nextLine();
					filingTogether=filingTogether.toLowerCase();
					if(!filingTogether.equals("yes") && !filingTogether.equals("no")){
						System.out.println("Please answer the question (yes or no).");
						error=true;
					}
				}catch(InputMismatchException ime){
					System.out.println("Please enter in yes or no.");
					error=true;
					kb.nextLine();
				}
			}while(error);
			if(filingTogether.equals("yes")){
				do{
					error=false;
					try{
						System.out.println("Please enter in your spouse's base pay:");
						secondBasePay=kb.nextDouble();
						kb.nextLine();
					}catch(InputMismatchException ime){
						System.out.println("You must enter in a number.");
						error=true;
						kb.nextLine();
					}
				}while(error||secondBasePay<=0);
			}
			do{
				error=false;
				try{// whether they are the head of the house hold
					System.out.println("\nAre you the head of the household?");
					headHousehold=kb.nextLine();
					headHousehold=headHousehold.toLowerCase();				
					if(!headHousehold.equals("yes") && !headHousehold.equals("no")){
						System.out.println("Please answer the question (yes or no).");
						error=true;
					}
				}catch(InputMismatchException ime){
					System.out.println("Please enter in yes or no.");
					error=true;
					kb.nextLine();
				}
			}while(error);
		}
		//calculations for tax brackets for *2016*
		if(married.equals("yes")){
			if(filingTogether.equals("yes")){
				double totalBasePay=basePay+secondBasePay;
				if(totalBasePay>=464851){
					afterTaxIncome= totalBasePay*.604;
					return afterTaxIncome;
				}
				if(totalBasePay>=4115001&&totalBasePay<=464850){
					afterTaxIncome= totalBasePay*.65;
					return afterTaxIncome;
				}
				if(totalBasePay>=230451&&totalBasePay<=411500){
					afterTaxIncome= totalBasePay*.67;
					return afterTaxIncome;
				}
				if(totalBasePay>=151201&&totalBasePay<=230450){
					afterTaxIncome= totalBasePay*.72;
					return afterTaxIncome;
				}
				if(totalBasePay>=74901&&totalBasePay<=151200){
					afterTaxIncome= totalBasePay*.75;
					return afterTaxIncome;
				}
				if(totalBasePay>=18451&&totalBasePay<=74900){
					afterTaxIncome= totalBasePay*.85;
					return afterTaxIncome;
				}
				if(totalBasePay<=18450){
					afterTaxIncome= totalBasePay*.9;
					return afterTaxIncome;
				}
			}
			else if(filingTogether.equals("no")&&headHousehold.equals("no")){//they're filing separately 
				if(basePay>=232426){
					afterTaxIncome= basePay*.604;
					return afterTaxIncome;
				}
				if(basePay>=205751&&basePay<=232425){
					afterTaxIncome= basePay*.65;
					return afterTaxIncome;
				}
				if(basePay>=115226&&basePay<=205750){
					afterTaxIncome= basePay*.67;
					return afterTaxIncome;
				}
				if(basePay>=75601&&basePay<=115225){
					afterTaxIncome= basePay*.72;
					return afterTaxIncome;
				}
				if(basePay>=37451&&basePay<=75600){
					afterTaxIncome= basePay*.75;
					return afterTaxIncome;
				}
				if(basePay<=9226&&basePay<=37450){
					afterTaxIncome= basePay*.85;
					return afterTaxIncome;
				}
				if(basePay<=9225){
					afterTaxIncome= basePay*.9;
					return afterTaxIncome;
				}			
			}
			else{//head of the household 
				if(basePay>=439001){
					afterTaxIncome= basePay*.604;
					return afterTaxIncome;
				}
				if(basePay>=411501&&basePay<=439000){
					afterTaxIncome= basePay*.65;
					return afterTaxIncome;
				}
				if(basePay>=209851&&basePay<=411500){
					afterTaxIncome= basePay*.67;
					return afterTaxIncome;
				}
				if(basePay>=129601&&basePay<=209850){
					afterTaxIncome= basePay*.72;
					return afterTaxIncome;
				}
				if(basePay>=50201&&basePay<=129600){
					afterTaxIncome= basePay*.75;
					return afterTaxIncome;
				}
				if(basePay>=13151&&basePay<=50200){
					afterTaxIncome= basePay*.85;
					return afterTaxIncome;
				}
				if(basePay>=13150){
					afterTaxIncome= basePay*.9;
					return afterTaxIncome;
				}
			}
		}
		else{//they're single
			if(basePay>=413201){
				afterTaxIncome= basePay*.604;
				return afterTaxIncome;
			}
			if(basePay>=411501&&basePay<=413200){
				afterTaxIncome= basePay*.65;
				return afterTaxIncome;
			}
			if(basePay>=189301&&basePay<=411500){
				afterTaxIncome= basePay*.67;
				return afterTaxIncome;
			}
			if(basePay>=90751&&basePay<=189300){
				afterTaxIncome= basePay*.72;
				return afterTaxIncome;
			}
			if(basePay>=37451&&basePay<=90750){
				afterTaxIncome= basePay*.75;
				return afterTaxIncome;
			}
			if(basePay>=9226&&basePay<=37450){
				afterTaxIncome= basePay*.85;
				return afterTaxIncome;
			}
			if(basePay<=9225){
				afterTaxIncome= basePay*.9;
				return afterTaxIncome;
			}			
		}
		return afterTaxIncome;//should never be called
	}
	
	/**
	 * Gets disposable income using an expenses class
	 * @param afterTaxIncome from a method
	 * @return approximate disposable income
	 */
	public static double getDisposableIncome(double afterTaxIncome){
		Scanner kb=new Scanner(System.in);
		double disposableIncome=0;
		double expenses=0;
		boolean error;
		double totalExpenses=0;
		String subtractExpenses=null;
		do{
			error=false;
			try{
				System.out.println("Would you like to subtract your expenses?");
				subtractExpenses=kb.nextLine();
				subtractExpenses=subtractExpenses.toLowerCase();
				if(!subtractExpenses.equals("yes")&&!subtractExpenses.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		
		if (subtractExpenses.equals("no")){
			disposableIncome=afterTaxIncome;
			return disposableIncome;
		}
		
		//creates the expenses object
		Expenses exp = new Expenses();
		
		String subtractRent=null;
		double rent=0;
		do{
			error=false;
			try{
				System.out.println("Do you have rent that you would like to subtract?");
				subtractRent=kb.nextLine();
				subtractRent=subtractRent.toLowerCase();
				if(!subtractRent.equals("yes")&&!subtractRent.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractRent.equals("yes")){
			rent=exp.getRent();
			totalExpenses+=rent;
		}
		String subtractElectricBill=null;
		double electricBill=0;
		do{
			error=false;
			try{
				System.out.println("Do you have an electric bill that you would like to subtract?");
				subtractElectricBill=kb.nextLine();
				subtractElectricBill=subtractElectricBill.toLowerCase();
				if(!subtractElectricBill.equals("yes")&&!subtractElectricBill.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractElectricBill.equals("yes")){
			electricBill=exp.getElectricBill();
			totalExpenses+=electricBill;
		}
		String subtractWaterBill=null;
		double waterBill=0;
		do{
			error=false;
			try{
				System.out.println("Do you have a water bill that you would like to subtract?");
				subtractWaterBill=kb.nextLine();
				subtractWaterBill=subtractWaterBill.toLowerCase();
				if(!subtractWaterBill.equals("yes")&&!subtractWaterBill.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractWaterBill.equals("yes")){
			waterBill=exp.getWaterBill();
			totalExpenses+=waterBill;
		}
		String subtractSewerBill=null;
		double sewerBill=0;
		do{
			error=false;
			try{
				System.out.println("Do you have a sewer bill that you would like to subtract?");
				subtractSewerBill=kb.nextLine();
				subtractSewerBill=subtractSewerBill.toLowerCase();
				if(!subtractSewerBill.equals("yes")&&!subtractSewerBill.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractSewerBill.equals("yes")){
			sewerBill=exp.getSewerBill();
			totalExpenses+=sewerBill;
		}
		String subtractGasBill=null;
		double gasBill=0;
		do{
			error=false;
			try{
				System.out.println("Do you have a gas  bill that you would like to subtract?");
				subtractGasBill=kb.nextLine();
				subtractGasBill=subtractGasBill.toLowerCase();
				if(!subtractGasBill.equals("yes")&&!subtractGasBill.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractGasBill.equals("yes")){
			gasBill=exp.getGasBill();
			totalExpenses+=gasBill;
		}
		String subtractFoodBudget=null;
		double foodBudget=0;
		do{
			error=false;
			try{
				System.out.println("Would you like to subtract your food budget?");
				subtractFoodBudget=kb.nextLine();
				subtractFoodBudget=subtractFoodBudget.toLowerCase();
				if(!subtractFoodBudget.equals("yes")&&!subtractFoodBudget.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractFoodBudget.equals("yes")){
			foodBudget=exp.getFoodBudget();
			totalExpenses+=foodBudget;
		}
		String subtractEntertainmentBudget=null;
		double entertainmentBudget=0;
		do{
			error=false;
			try{
				System.out.println("Would you like to subtract your entertainmnet budget?");
				subtractEntertainmentBudget=kb.nextLine();
				subtractEntertainmentBudget=subtractEntertainmentBudget.toLowerCase();
				if(!subtractEntertainmentBudget.equals("yes")&&!subtractEntertainmentBudget.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractEntertainmentBudget.equals("yes")){
			entertainmentBudget=exp.getEntertainmentBudget();
			totalExpenses+=entertainmentBudget;
		}
		String subtractCarExpenses=null;
		double carExpenses=0;
		do{
			error=false;
			try{
				System.out.println("Would you like to subtract your car expenses?");
				subtractCarExpenses=kb.nextLine();
				subtractCarExpenses=subtractCarExpenses.toLowerCase();
				if(!subtractCarExpenses.equals("yes")&&!subtractCarExpenses.equals("no")){
					System.out.println("Please enter in yes or no.");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in yes or no.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		if(subtractCarExpenses.equals("yes")){
			carExpenses=exp.getCarExpenses();
			totalExpenses+=carExpenses;
		}
	
		System.out.printf("\nYour total expenses are: $.2f ",totalExpenses);
		disposableIncome=afterTaxIncome-totalExpenses;
		return disposableIncome;
	}
	
	/**
	 * Finds the percentage they would like to invest and multiplies it by disposableIncome
	 * @param disposableIncome
	 * @return monetaryInvestment Amount they want to invest
	 */
	public static double getMonetary(double disposableIncome){
		double monetaryInvestment=disposableIncome;
		Scanner kb=new Scanner(System.in);
		double invest=0;
		boolean error;
		boolean error2;
		
		System.out.println("\nIt is recomended to invest between 5% and "
				+ "10% of your dispoable income.");
		do{
			error=false;
			String confirm=null;
			try{
					System.out.println("What percentage would you like to invest?");
					invest=kb.nextDouble();
					
					if(invest>=10||invest<=5){
						do{
							error2=false;
							try{
									System.out.println("\nAre you sure you would like to invest: "+invest+"%?");				
									confirm=kb.nextLine();
									kb.nextLine();
							}catch(InputMismatchException ime){
								System.out.println("\nPlease enter in \"yes\" or \"no\".");
								error2=true;
								kb.nextLine();
							}	
						}while(error2&&!confirm.equalsIgnoreCase("yes"));
					}
					
			}catch(InputMismatchException ime){
				System.out.println("Please enter in a number, or try removing the \"%\" sign.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		
		invest=invest/100;//makes it a decimal
		monetaryInvestment=monetaryInvestment*invest;
		
		return monetaryInvestment;
	}
	
	/**
	 * Displays the information
	 * @param monetaryInvestment
	 */
	public static void displayData(double monetaryInvestment){
		DecimalFormat df = new DecimalFormat("#,###.00");
		double minBonds=monetaryInvestment*.35;
		double maxBonds=monetaryInvestment*.5;
		double minBlue=monetaryInvestment*.25;
		double maxBlue=monetaryInvestment*.4;
		double minRisk=monetaryInvestment*.05;
		double maxRisk=monetaryInvestment*.15;
		
		JOptionPane.showMessageDialog(null,"\n\tRecomended minimum bond expenditure: $"+df.format(minBonds)+
				"\n\tRecomended maximum bond expenditure: $"+df.format(maxBonds)+
				"\n\n\tRecomended minimum blue chip expenditure: $"+df.format(minBlue)+
				"\n\tRecomended maximum blue chip expenditure: $"+df.format(maxBlue)+
				"\n\n\tRecomended minimum risky stock expenditure: $"+df.format(minRisk)+
				"\n\tRecomended maximum risky stock expenditure: $"+df.format(maxRisk),
				"Our recomended investment...",JOptionPane.PLAIN_MESSAGE);
		
		System.out.println("\n\n\t\t\tOur recomended investment...");
		System.out.println("\n\t\tRecomended minimum bond expenditure: $"+df.format(minBonds));
		System.out.println("\t\tRecomended maximum bond expenditure: $"+df.format(maxBonds));
		System.out.println("\n\t\tRecomended minimum blue chip expenditure: $"+df.format(minBlue));
		System.out.println("\t\tRecomended maximum blue chip expenditure: $"+df.format(maxBlue));
		System.out.println("\n\t\tRecomended minimum risky stock expenditure: $"+df.format(minRisk));
		System.out.println("\t\tRecomended maximum risky stock expenditure: $"+df.format(maxRisk));
		System.out.println("\n\n\t\t\t\tThank you...");
	}
	
	public static void main(String[] args){
		Scanner kb=new Scanner(System.in);
		boolean error;
		System.out.println("\t\t\t Investment Help");
		System.out.println("This program will help strengthen your portfolio.\n");
		
		//String filename=getFilename();
		double afterTaxIncome=getAfterTaxIncome();
		System.out.printf("Your after tax income is approximately $%.2f\n",afterTaxIncome);
		double disposableIncome=getDisposableIncome(afterTaxIncome);
		double monetaryInvestment=getMonetary(disposableIncome);
		
		displayData(monetaryInvestment);
		
		
		
		//System.out.printf("Your disposable income is approximately: $%.2f\n",disposableIncome);
	}
}
