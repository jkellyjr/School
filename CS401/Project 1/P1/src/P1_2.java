/** Project 1 by:John Kelly Jr
 * 
 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class P1_2 {
	public static void main(String[] args) throws IOException {
		Scanner kb=new Scanner(System.in);
		boolean error;
		
		String fileName=null;
		do{//gets file and catches exceptions
			error=false;
			try{				
				System.out.println("Please enter in the file name: ");
				fileName=kb.nextLine();
			}catch(InputMismatchException ime){
				System.out.println("Please enter in a file name.");
				error=true;
				kb.nextLine();
			}catch(Exception e){
				System.out.println("An error has occured.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		
		File file = new File (fileName);
			
		if(!file.exists()){
			System.out.println("The file does not exist.");
			main(args);
		}
		
		Scanner inputFile=null;//reads from file
		do{
			error=false;
			try {
				inputFile = new Scanner(file);
			} catch (FileNotFoundException fnfe) {
				System.out.println("The file cannot be found.");
				error=true;
				kb.nextLine();
			}catch(Exception e){
				System.out.println("Sorry, but something went wrong.");
				error=true;	
				kb.nextLine();
			}
		}while(error);
				
		double balance=0;		
		String product1=null;
		double price1=0;
		int quantity1=0;		
		String product2=null;
		double price2=0;
		int quantity2=0;		
		String product3=null;
		double price3=0;
		int quantity3=0;
		
		int choice=0;
		int choice2=0;
										
		balance = inputFile.nextDouble(); //reads the file 			
		product1 = inputFile.next();
		price1 = inputFile.nextDouble();
		quantity1 = inputFile.nextInt();				
		product2 = inputFile.next();
		price2 = inputFile.nextDouble();
		quantity2 = inputFile.nextInt();				
		product3 = inputFile.next();
		price3 = inputFile.nextDouble();
		quantity3 = inputFile.nextInt();
		
		inputFile.close();
		
		FileWriter fwriter = new FileWriter(fileName,false);
		do
		{
			PrintWriter outputFile= new PrintWriter(fwriter);//allows amending
			
			System.out.printf("Current balance: $%.2f \n", balance);
			System.out.printf("1. "+product1+"\t("+quantity1+" at $%.2f) \n",price1);
			System.out.printf("2. "+product2+"\t("+quantity2+" at $%.2f) \n",price2);
			System.out.printf("3. "+product3+"\t("+quantity3+" at $%.2f) \n",price3);
			System.out.println("0. Exit\n");
		
			do{//gets data for sub menus
				error=false;
				try{
					System.out.println("Please enter the number of your choice:");
					choice = kb.nextInt();
				}
				catch(InputMismatchException ime){
					System.out.println("Im sorry, but you must enter a number.");
					error=true;
					kb.nextLine();//consumes next line
				}catch(Exception e){
					System.out.println("Error. Please enter the number again please.");
					error=true;
					kb.nextLine();
				}
			}while(error);
					
			if(choice==1){//first sub menu
				do{
					System.out.printf("\nCurrent balance: $%.2f \n", balance);
					System.out.println("Current quantity: "+quantity1);
					System.out.printf("Current price: $%.2f \n", price1);
					
					System.out.println("1. Sell "+product1);
					System.out.println("2. Buy "+product1);
					System.out.println("3. Change price");
					System.out.println("0. Exit\n");
					
					do{	
						error=false;
						try{
							System.out.println("Please enter choice:");
							choice2 = kb.nextInt();
						}catch(InputMismatchException ime){
							System.out.println("Im sorry, but you must enter a number.");
							error=true;
							kb.nextLine();//consumes next line
						}catch(Exception e){
							System.out.println("Error. Please enter the number again please.");
							error=true;
							kb.nextLine();
						}
					}while(error);
					
					if(choice2==1){
						do{
							error= false;
							int selling=0;
							String proceed=null;
							try{//handling exceptions 
								System.out.println("Amount to sell (current quantity: "+quantity1+"): ");
								selling = kb.nextInt();
								
								if(selling>quantity1){//Checks to make sure there is enough inventory
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();
										
									if(proceed.equals("no"))// return to main menu
										break; 
								}
									
								double revenue = selling * price1;
								balance+= revenue;//adds to balance
								quantity1-= selling;//subtracts units sold
								
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();//consumes next line
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error); 	
						
					}//ends choice2==1 
					
					else if(choice2==2){
						do{
							error= false;
							int purchaseAmount=0;
							double purchasePrice=0;
							try{
								System.out.println("Amount to buy:");
								purchaseAmount = kb.nextInt();
								System.out.println("Price per item:");
								purchasePrice = kb.nextDouble();
								
								double cost = purchasePrice *purchaseAmount;
								balance-= cost;
								quantity1+= purchaseAmount;
																
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}							
						}while(error);
					}//end sub menu 2
					
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price1 = kb.nextDouble();
							}catch(InputMismatchException ime){//Prints but then throws exception
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error);
					}
					else if(choice2==0)
						choice2=0;

					else{
						System.out.println("You must enter a number on the list.");
						choice2=0;
					}
				}while(choice2!=0);
			}
			else if(choice==2){
				do{					
					System.out.printf("Current balance: $%.2f \n", balance);
					System.out.println("Current quantity: "+quantity2);
					System.out.printf("Current price: $%.2f \n", price2);
					
					System.out.println("1. Sell "+product2);
					System.out.println("2. Buy "+product2);
					System.out.println("3. Change price");
					System.out.println("0. Exit\n");
					
					do{	
						error=false;
						try{
							System.out.println("Please enter choice:");
							choice2 = kb.nextInt();
						}catch(InputMismatchException ime){
							System.out.println("Im sorry, but you must enter a number.");
							error=true;
							kb.nextLine();//consumes next line
						}catch(Exception e){
							System.out.println("Error. Please enter the number again please.");
							error=true;
							kb.nextLine();
						}
					}while(error);
					
					if(choice2==1){
						do{
							error= false;
							int selling=0;
							String proceed=null;
							try{//handling exceptions 
								System.out.println("Amount to sell (current quantity: "+quantity2+"): ");
								selling = kb.nextInt();
								
								if(selling>quantity2){//Checks to make sure there is enough inventory
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();
										
									if(proceed.equals("no"))// return to main menu
										break; 
								}
									
								double revenue = selling * price2;
								balance+= revenue;//adds to balance
								quantity2-= selling;//subtracts units sold
								
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();//consumes next line
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error); 							
					}//ends choice2==1 					
					else if(choice2==2){
						do{
							error= false;
							int purchaseAmount=0;
							double purchasePrice=0;
							try{
								System.out.println("Amount to buy:");
								purchaseAmount = kb.nextInt();
								System.out.println("Price per item:");
								purchasePrice = kb.nextDouble();
								
								double cost = purchasePrice *purchaseAmount;
								balance-= cost;
								quantity2+= purchaseAmount;
																
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}							
						}while(error);
					}//end sub menu 2			
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price2 = kb.nextDouble();
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error);
					}
					else if(choice2==0)
						choice2=0;
					else{
						System.out.println("You must enter a number on the list.");
						choice2=0;
					}
				}while(choice2!=0);
			}
			else if(choice==3){
				do{
					System.out.printf("Current balance: $%.2f \n", balance);
					System.out.println("Current quantity: "+quantity3);
					System.out.printf("Current price: $%.2f \n", price3);
					
					System.out.println("1. Sell "+product3);
					System.out.println("2. Buy "+product3);
					System.out.println("3. Change price");
					System.out.println("0. Exit\n");
					
					do{	
						error=false;
						try{
							System.out.println("Please enter choice:");
							choice2 = kb.nextInt();
						}catch(InputMismatchException ime){
							System.out.println("Im sorry, but you must enter a number.");
							error=true;
							kb.nextLine();//consumes next line
						}catch(Exception e){
							System.out.println("Error. Please enter the number again please.");
							error=true;
							kb.nextLine();
						}
					}while(error);
					
					if(choice2==1){
						do{
							error= false;
							int selling=0;
							String proceed=null;
							try{//handling exceptions 
								System.out.println("Amount to sell (current quantity: "+quantity2+"): ");
								selling = kb.nextInt();
								
								if(selling>quantity2){//Checks to make sure there is enough inventory
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();
										
									if(proceed.equals("no"))// return to main menu
										break; 
								}
									
								double revenue = selling * price3;
								balance+= revenue;//adds to balance
								quantity3-= selling;//subtracts units sold
								
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();//consumes next line
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error); 							
					}//ends choice2==1 					
					else if(choice2==2){
						do{
							error= false;
							int purchaseAmount=0;
							double purchasePrice=0;
							try{
								System.out.println("Amount to buy:");
								purchaseAmount = kb.nextInt();
								System.out.println("Price per item:");
								purchasePrice = kb.nextDouble();
								
								double cost = purchasePrice *purchaseAmount;
								balance-= cost;
								quantity3+= purchaseAmount;
																
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}							
						}while(error);
					}//end sub menu 2			
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price3 = kb.nextDouble();
							}catch(InputMismatchException ime){
								System.out.println("Im sorry, but you must enter a number.");
								error=true;
								kb.nextLine();		
							}catch(Exception e){
								System.out.println("Error. Please enter the number again please.");
								error=true;
								kb.nextLine();
							}
						}while(error);
					}
					else if(choice2==0)
						choice2=0;
					else{
						System.out.println("You must enter a number on the list.");
						choice2=0;
					}
				}while(choice!=0);
			}
			else if(choice==0)
				System.exit(0);
			else{
				System.out.println("You have entered invalid data. Please enter a new number:");
				choice = kb.nextInt(); 
			}
			
			outputFile.println(balance);
			outputFile.println(product1+" "+price1+" "+quantity1);
			outputFile.println(product2+" "+price2+" "+quantity2);
			outputFile.println(product3+" "+price3+" "+quantity3);
			
			outputFile.close();
		}while(choice!=0);	
		
		kb.close();
	}

}
