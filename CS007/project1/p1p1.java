/********************************************
  Project 1 Part 1
  By: John Kelly Jr (9/23/14)

  This is a very basic budget calculator
********************************************/

import java.util.*;

public class p1p1{

  // main logic of the program
  public static void main(String[] args){

    System.out.println("\t\tBudget Calculator");
    System.out.println("\t---------------------------------");
    System.out.println("This is only a basic budget calculator, so please do not \n"
        + "take any of this information as the be-all and end-all.\n\n");

    Scanner in = new Scanner(System.in);

    // income
    System.out.println("Please enter your hourly rate: ");
    double hourlyRate = in.nextDouble();
    System.out.println("Please enter in your overtime rate: ");
    double overtimeRate = in.nextDouble();
    System.out.println("Please enter the number of regular hours worked: ");
    double regHours = in.nextDouble();
    System.out.println("Please enter in the number of overtime hours worked: ");
    double otHours = in.nextDouble();
    // payments
    System.out.println("Please enter in your montly rent: ");
    double rent = in.nextDouble();
    System.out.println("What is the precentage of your pay that goes towards your electricity? ");
    double electricPer = in.nextDouble();
    System.out.println("What is the percentage of your pay that goes towards your sewage processesing?");
    double sewagePer = in.nextDouble();
    System.out.println("What is the percentage of your pay that goes towards your water bill? ");
    double waterPer = in.nextDouble();
    System.out.println("What is the percentage of your pay that goes towards your gas bill? ");
    double gasPer = in.nextDouble();
    System.out.println("What is your monthly food budget? ");
    double food = in.nextDouble();
    System.out.println("What is your entertainment budget? ");
    double entertainment = in.nextDouble();
    System.out.println("What is the total of your monthy car expenses? ");
    double car = in.nextDouble();


    double income = (hourlyRate * regHours) + (overtimeRate * otHours);

    double electricBill = (income * electricPer);
    double sewageBill = (income * sewagePer);
    double waterBill = (income * waterPer);
    double gasBill = (income * gasPer);

    double billTotals = (electricBill + sewageBill + waterBill + gasBill);
    double expensesTotal = (rent + food + entertainment + car);

    double totalDue = (billTotals + expensesTotal);


    System.out.printf("Your net pay is: $%.2f \n" + (income - totalDue));

  }
}
