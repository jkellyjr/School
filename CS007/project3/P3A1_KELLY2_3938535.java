/*John Kelly Jr Hang man
 * This is the game of hangman, this program gives the user 6 lives to guess the letters which
 * comprise a random word which comes rowom a pre-created word file
 */
package capstone_CS0007;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.swing.JOptionPane;
public class P3A1_KELLY2_3938535 
{
	
		@SuppressWarnings({ "unchecked", "unused", "resource" })
		
		public static void main(String[] args) throws FileNotFoundException 
		{
		
			
			//Welcome message
			JOptionPane.showMessageDialog(null, "Welcome to the game of hang man, you must"
					+ "\nsuccessivley guess the word within 6 guesses or the guy gets hanged."
					+ "\nPress 'OK' to continue");
			
			String copy;
			
			//This do loop iterates the loop as long as the user wants to play
			File list = new File("listWords.txt");
			
			Scanner read = new Scanner(list);
			String go = "";
			Check prime = new Check();
			Select getRandom = new Select();
			Body person = new Body();
			
			do
			{
				do
				{
					go = read.nextLine();
					
					//testArrayWordlaration of the array lists
					ArrayList <String> chosenWord = new ArrayList <String>();
					String wordRandom = getRandom.toRandom();
					
					//This array list contains the original word
					chosenWord = (ArrayList<String>) atos(hint(wordRandom)).clone();
					String origString = chosenWord.toString();
					
					//this array list is responsible of keeping track of the letters tseeWrd the user has guessed right
					ArrayList <String> lett = new ArrayList <String>();
					
					final int word = chosenWord.size();
					
					final String [] ret = new String [word];
					
					chosenWord.toArray(ret);
					
					String testArrayWord;
					
					int count = 0;
					String alpha = "i";
					System.out.println("The word has " + chosenWord.size() + " letters.");
					
					int inccount = 0;
					String seewrd;
					String listString = "";
					
					Scanner input = new Scanner(System.in);
					
					System.out.println("Your hint is: " + hintword(wordRandom).toUpperCase());
					//This do loops iterated until the guy is hanged
						
					do
					{
							prime.setArraylist(chosenWord);
							testArrayWord = input.nextLine();
							prime.setString(testArrayWord);
							
							//Test = true
							if(prime.toTest())
							{
		    					
								System.out.println("Good guess");
								alpha.equals(testArrayWord);
		    					
		    					
								//this method is responsible for keep track of how many letters of the right letter guessed are in the word
								int checkMultiple = multiple(chosenWord, testArrayWord);
								if(checkMultiple > 1)
								{
									for(int chooseToAdd = 0; chooseToAdd < checkMultiple; chooseToAdd++)
									{
										chosenWord.remove(testArrayWord);
									}
									
									lett.add(testArrayWord);
								}
							else
							{
								lett.add(testArrayWord);
								chosenWord.remove(testArrayWord);
		    					}
		    					
		    					
		    					String [] check = new String[word];
		    					String [] check1 = new String[word];
		    					
		    					lett.toArray(check);
		    					
		    					System.out.print("So far you have guessed the following ");
		    					
		    					int cr = 1;
		    					
		    					
		    					for(int row = 0; row < check1.length; row++)
		    					{
		    						check1[row] = "  ";
		    					}
		    					
		    					
		    					//this loop prints out the letters in the order they appear in the word
		    					for(int colmn = 0; colmn < ret.length; colmn++)
		    					{
		    						System.out.print(check1[colmn]  + cr + ".");
		    						cr++;
		    						for(int c = 0; c < check.length; c++)
		    						{
		    							if (ret[colmn].equals(check[c]))
		    							{
		    								System.out.print(" " + "|" + check[c].toUpperCase()+ "|");
		    							}
		    						}
		    					}
		    					
		    					System.out.println();
		    					System.out.println("There are " + checkMultiple + " " +  testArrayWord + "'s in the word");
		    					
		    					
		    					if(inccount == 0)
		    						System.out.println(person.gallow());
		    					
		    					
		    					else if (inccount > 0)
		    					{
		    						//Switch statement which displays different parts of the guy depending on the number of wrong guesses
		    						switch(inccount)
		    						{
		    							case 1:
		    								System.out.println(person.head());
		    									break;
		    							case 2:
		    								System.out.println(person.body());
		    									break;
		    							case 3:
		    								System.out.println(person.rightLeg());
		    									break;
		    							case 4:
		    								System.out.println(person.leftLeg());
		    									break;
		    							case 5:
		    								System.out.println(person.rightArm());
		    									break;
		    						}
		    					}
		    					
		    				}
		    				
		    				
		    				//Test = false
		    				else if(!prime.toTest())
		    				{
		    					if(lett.contains(testArrayWord))
		    					{
		    						System.out.println("There are no more " + testArrayWord + "'s");
		    					}
		    					
		    					else
		    					{
		    						System.out.println("Bad guess, there are no " + testArrayWord + "'s in the word");
		    						
		    						String [] check = new String[word];
		    						String [] check1 = new String[word];
		    						
		    						lett.toArray(check);
		    						
		    						System.out.print("So far you have guessed the following ");
		    						
		    						for(int row = 0; row < check1.length; row++)
		    						{
		    							check1[row] = "  ";
		    						}
		    						
		    						
		    						int cntr = 1;
		    						
		    						
		    						//this loop prints the letters they are in the word
		    						for(int n = 0; n < ret.length; n++)
		    						{
		    							System.out.print(check1[n] + cntr +  ".");
		    							cntr++;
		    							
		    							for(int c = 0; c < check.length; c++)
		    							{
		    								if (ret[n].equals(check[c]))
		    								{
		    									System.out.print("|" + check[c].toUpperCase() + "|");
	            			
		    								}
		    							}
		    						}
		    						
		    						
		    						System.out.println();
		    						++inccount;
		    						
		    						
		    						//switch statement similar to the one in the test= true section
		    						switch(inccount)
		    						{
		    						
		    							case 1:
		    								System.out.println(person.head());
		    									break;
		    							case 2:
		    								System.out.println(person.body());
		    									break;
		    							case 3:
		    								System.out.println(person.rightLeg());
		    									break;
		    							case 4:
		    								System.out.println(person.leftLeg());
		    									break;
		    							case 5:
		    								System.out.println(person.rightArm());
		    									break;
		    							case 6:
		    								System.out.println(person.leftArm());
		    									break;
		    						}	
		    					}
		    				}
					}
						
					while(chosenWord.size() >0 && inccount < 6);
						
						if(chosenWord.size() > 0)
						{
							System.out.println("You have used up all your guesses, you lose. Your word"
									+ " was " + origString.toUpperCase() + ".");
							copy= JOptionPane.showInputDialog("If you would like to start a new game please type the letter 'y'");
						}
						
						else
							
							System.out.println("You won, the word was " + origString.toUpperCase() + ".");
							copy= JOptionPane.showInputDialog("If you would like to start a new game please type the letter 'y'");
						
							//continues loop while user presses the leter "y"
			}
			while(copy.equals("y") || copy.equals("Y"));
			JOptionPane.showMessageDialog(null, "Thank you for playing");
		}
			while(copy.equals("y"));
				
				if(!read.hasNext())
					
					JOptionPane.showMessageDialog(null, "The game has run out of words, thank you for playing");
	} 
	    
		
		
	
	
	//this method is responsible keeping track of letters which repeat in a word
	public static int multiple(ArrayList <String> pit, String ent)
	{
		int count = 0;
	    	
		@SuppressWarnings("unused")
		String prime = "f";
		@SuppressWarnings("unused")
		int results;
		for(int cat = 0; cat < pit.size(); cat++)
		{
			if(pit.get(cat).equals(ent))
			{
				count++;
			}
		
		}
				return count;
	}
	    
		
		
	
	//This method will take the word in a txt file and then chosenWorderts to an array list
	public static ArrayList <String> atos(String cave) throws FileNotFoundException
	{
	    
		@SuppressWarnings("unused")
			int count = 0;
			
			char[] cArray = cave.toCharArray();
		ArrayList <String> strin = new ArrayList <String>(cArray.length);
			
		for(char c : cArray)
		{
			strin.add(String.valueOf(c));
		}
			
		return strin;
	}
	  
		
		
	//Stores word in array
	public static String hint(String gem) throws FileNotFoundException
	{
		int x = 0;
		@SuppressWarnings("unused")
			int y = 0;
	    	
		String [] java = gem.split(",");
		String [] m = new String[1];
			
		x = m.length;
			
		@SuppressWarnings({ "rawtypes", "unused" })
			
		ArrayList[] hit =  new ArrayList [x];
		m[0] = java[0];
		return m[0];
	}
		
	    	
	  //Retrieves the hint
	  public static String hintword(String stringSplit)
	  {
			
	    	String [] splitArray = stringSplit.split(",");
		String [] equalsArray = new String[1];
			
		equalsArray[0] = splitArray[1];
		String returnWord = equalsArray[0].toString();
			
			
		return returnWord;
	}
		
}



