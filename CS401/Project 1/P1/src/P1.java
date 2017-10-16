 /** Project 1 by:John Kelly Jr
 *
 * The program reads an inventory file entered by the user, and creates an inventory calculator.
 *
 * First the file is read in using a scanner, then try catch statements stop bad input, and an if
 * statement is used to make sure the file exists. Once the file is read in, I used a do while loop
 * for the main menu, an if else if statement was used to display sub-menus of the products offered.
 * Once in the scope of the if else if, a do while loop was used to display the sub menu, scanner
 * class was used to ask the user and determine the action they wished to perform. While in the inner
 * do while loop, the necessary calculations were run to validate data as well as output the updated
 * variables back the the main menu and sub menus. Finally, FileWriter and PrintWriter were used to
 * overwrite the existing file, and output the new data from calculations performed.

 */
import java.util.InputMismatchException;
import java.util.Scanner;
import java.io.*;

public class P1 {
	public static void main(String[] args) throws IOException {
		Scanner kb=new Scanner(System.in);
		boolean error;


		/********************GET FILE NAME*************************/
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

		/********************CREATE FILE OBJECT*************************/

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

		/********************BEGIN TO READ IN FILE*************************/

		double balance;
		String product1;
		double price1;
		int quantity1;
		String product2;
		double price2;
		int quantity2;
		String product3;
		double price3;
		int quantity3;

		int choice=0;
		int choice2=0;
		//reads the file and assigns variables
		balance = inputFile.nextDouble();
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

		/********************PRINT OUT DATA*************************/
		//amending set to false (original data was garbage collected)
		FileWriter fwriter = new FileWriter(fileName,false);
		do
		{//main menu
			PrintWriter outputFile= new PrintWriter(fwriter);

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

					while(choice!=1 && choice!=2 && choice!=3 && choice!=0){
						System.out.println("You must enter an available choice.");
						System.out.println("Please enter the number of your choice:");
						choice = kb.nextInt();
					}
				}
				catch(InputMismatchException ime){
					System.out.println("Im sorry, but you must enter a number.");
					error=true;
					kb.nextLine();
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
							while(choice2!=1 && choice2!=2 && choice2!=3 && choice2!=0){
								System.out.println("You must enter an available choice.");
								System.out.println("Please enter the number of your choice:");
								choice2 = kb.nextInt();
							}
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

					if(choice2==1){
						do{
							error= false;
							int selling=0;
							String proceed=null;
							try{
								System.out.println("Amount to sell (current quantity: "+quantity1+"): ");
								selling = kb.nextInt();

								if(selling>quantity1){
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();

									if(proceed.equals("no"))
										break;
								}

								double revenue = selling * price1;
								balance+= revenue;
								quantity1-= selling;

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
								if(cost<balance){
									balance-= cost;
									quantity1+= purchaseAmount;
								}
								else{
									System.out.println("You cannot charge more than the company's balance.\n");
									choice2=0;
								}

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
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price1 = kb.nextDouble();
								while(price1<0){
									System.out.println("There cannot be a negative price.");
									System.out.println("New price:");
									price3 = kb.nextDouble();
								}
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
							while(choice2!=1 && choice2!=2 && choice2!=3 && choice2!=0){
								System.out.println("You must enter an available choice.");
								System.out.println("Please enter the number of your choice:");
								choice2 = kb.nextInt();
							}
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
							try{
								System.out.println("Amount to sell (current quantity: "+quantity2+"): ");
								selling = kb.nextInt();

								if(selling>quantity2){
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();

									if(proceed.equals("no"))
										break;
								}

								double revenue = selling * price2;
								balance+= revenue;
								quantity2-= selling;

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
								if(cost<balance){
									balance-= cost;
									quantity1+= purchaseAmount;
								}
								else{
									System.out.println("You cannot charge more than the company's balance.\n");
									choice2=0;
								}

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
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price2 = kb.nextDouble();
								while(price2<0){
									System.out.println("There cannot be a negative price.");
									System.out.println("New price:");
									price3 = kb.nextDouble();
								}
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
							while(choice2!=1 && choice2!=2 && choice2!=3 && choice2!=0){
								System.out.println("You must enter an available choice.");
								System.out.println("Please enter the number of your choice:");
								choice2 = kb.nextInt();
							}
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

					if(choice2==1){
						do{
							error= false;
							int selling=0;
							String proceed=null;
							try{
								System.out.println("Amount to sell (current quantity: "+quantity2+"): ");
								selling = kb.nextInt();

								if(selling>quantity2){
									System.out.println("Warning:Selling more than is in stock!");
									System.out.println("Do you wish to proceed (yes or no):");
									proceed = kb.next();
									proceed = proceed.toLowerCase();

									if(proceed.equals("no"))
										break;
								}

								double revenue = selling * price3;
								balance+= revenue;
								quantity3-= selling;

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
								if(cost<balance){
									balance-= cost;
									quantity1+= purchaseAmount;
								}
								else{
									System.out.println("You cannot charge more than the company's balance.\n");
									choice2=0;
								}

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
					else if(choice2==3){
						do{
							error=false;
							try{
								System.out.println("New price:");
								price3 = kb.nextDouble();
								while(price3<0){
									System.out.println("There cannot be a negative price.");
									System.out.println("New price:");
									price3 = kb.nextDouble();
								}
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

			outputFile.println(balance);
			outputFile.println(product1+" "+price1+" "+quantity1);
			outputFile.println(product2+" "+price2+" "+quantity2);
			outputFile.println(product3+" "+price3+" "+quantity3);

			outputFile.close();
		}while(choice!=0);

		kb.close();
	}

}
