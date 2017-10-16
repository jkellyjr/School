/*************************************************
  Lab 9
  By: John Kelly Jr (4/2/15)

	Asks the users for the number of strings they would like to add to an arraylist, searches the
	arrylist and returns the number of occurances
************************************************/

import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.ArrayList;

public class Lab9 {

	// creates and returns the list of strings specified by the user
	public static ArrayList getString(String str, int num){
		Scanner kb = new Scanner(System.in);
		String str1 = null;
		boolean error;

		//creates an empty arrayList
		ArrayList <String> strList=new ArrayList <String> ();

		for(int i=0; i<num; i++){
			do{
				error=false;
				try{
					System.out.println(str);
					str1=kb.nextLine();
					strList.add(str1);

					if(!str1.matches("[a-zA-Z]+")){
						System.out.println("You must enter in a string.\n");
						num++;//adds 1 back to num to keep correct numbers
					}

				}catch(InputMismatchException ime){
					System.out.println("You must enter in a string.");
					error=true;
					kb.nextLine();
				}

			}while(error&&!str1.matches("[a-zA-Z]+"));
		}
		return strList;
	}

	// counts occurences of a specific string
	public static int countValues(ArrayList strList, String str1){
		Scanner kb=new Scanner(System.in);
		boolean error;
		String searchStr=null;
		do{
			error=false;
			try{
				System.out.println(str1);
				searchStr=kb.nextLine();

				if(!searchStr.matches("[a-zA-Z]+")){
					System.out.println("You must enter in a string.\n");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("You must enter in a string.");
				error=true;
				kb.nextLine();
			}
		}while(error && !searchStr.matches("[a-zA-Z]+"));

		//finds occurrences
		int counter=Collections.frequency(strList, searchStr);

		//deletes last element
		if(counter>=2){
			int x=strList.lastIndexOf(searchStr);
			strList.remove(x);
		}

		return counter;
	}


	// gets and validates the number of strings to be added to the array list
	public static int getNum(){
		Scanner kb=new Scanner(System.in);
		int num=0;
		boolean error;

		do{
			error=false;
			try{
				System.out.println("How many strings would you like to enter?");
				num=kb.nextInt();

				if(num<=0){
					System.out.println("The number must be greater than zero.\n");
					error=true;
				}
			}catch(InputMismatchException ime){
				System.out.println("Please enter in a number.\n");
				error=true;
				kb.nextLine();
			}
		}while(error && num<=0);

		return num;
	}

	// main logic
	public static void main(String[]args){
		Scanner kb=new Scanner(System.in);
		String str="Please enter in a string: ";
		String str1="What would you like to search for?";

		int num=getNum();

		ArrayList strList=getString(str, num);

		int counter= countValues(strList, str1);

		System.out.println("\nThe ArrayList: "+strList);

	}
}
