/* Author: John Kelly Jr
Filename: P3A2_KELLY_3938535
Description: Create the game of hangman. */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Scanner;

public class P3A2_KELLY_3938535
{
        public static class Body
       {
         public String gallow()
         {
                 String gall = ("--------|\n|\n|\n|\n|\n-------------");
                return gall;
         }

         public String head()
         {
                String head1 = ("--------|\n" + "|      (*_*)" + "\n|\n|\n|\n-------------");
                return head1;
         }

         public String body()
         {
                String body1 = ("--------|\n" + "|      (*_*)" + "\n|        |" + "\n|        |" + "\n|" + "\n-------------");
                return body1;
         }

         public String rightLeg()
         {
                String rightleg1 = ("--------|\n" + "|      (*_*)" + "\n|        |" + "\n|        |" + "\n|         \\" + "\n-------------");
                return rightleg1;
         }

         public String leftLeg()
         {
                String leftleg1 = ("--------|\n" + "|      (*_*)" + "\n|        |" + "\n|        |" + "\n|       / \\" + "\n-------------");
                return leftleg1;
         }

         public String rightArm()
         {
                String rightarm1 = ("--------|\n" + "|      (*_*)" + "\n|        |\\" + "\n|        |" + "\n|       / \\" + "\n-------------");
                return rightarm1;
         }

         public String leftArm()
          {
                String leftarm1 = ("--------|\n" + "|      (*_*)" + "\n|       /|\\" + "\n|        |" + "\n|       / \\" + "\n-------------");
                return leftarm1;
          }
       }

        public static void main(String[] args) throws FileNotFoundException
        {
                playGame();

                while (true) {
                System.out.println("Would you like to play again? Y or N...");
                Scanner keyBoard = new Scanner(System.in);
                String response = keyBoard.next().toLowerCase();
                if (!(response.equals("y") || response.equals("n"))) {
                        System.out.println("Please respond with only Y or N");
                        continue;
                } else {
                        if (response.equals("y")) {
                        playGame();
                        } else {
                        System.out.println("thx come again");
                                break;
                        }
                }
                }

        }

        public static void addLetterToLettersArray(String[] arr, char c, String word) {
                ArrayList<Integer> list = new ArrayList<Integer>();
                for(int i = 0; i < word.length(); i++){
                    if(word.charAt(i) == c){
                       list.add(i);
                    }
                }

                for (int i : list) {
                        arr[i] = "" + c;
                }
        }

        public static int numberOfOccurencesInString(char c, String s)
        {
                int counter = 0;
                for( int i=0; i<s.length(); i++ ) {
                    if( s.charAt(i) == c ) {
                        counter++;
                    }
                }
                return counter;
        }


        public static void playGame() throws FileNotFoundException
        {
            int numberWrong = 0;
            int numberCorrect = 0;
            boolean won = false;
            Body person = new Body();

            Scanner sc = new Scanner(new File("P3A2_KELLY_3938535.txt"));
            List<String> lines = new ArrayList<String>();
            while (sc.hasNextLine()) {
              lines.add(sc.nextLine());
            }
            String[] arrOfWords = lines.toArray(new String[0]);

            Random rn = new Random();
            int index = rn.nextInt(arrOfWords.length);

            String word = arrOfWords[index];
            int length = word.length();

            ArrayList<Character> charArray = new ArrayList<Character>();

            String[] letters = new String[word.length()];

            for (int i = 0; i < letters.length; i++) {
                letters[i] = "_";
            }

            System.out.print("Hello, and welcome to John Kelly's hangman."
                    + "\n You will have six attempts to guess the proper leters,"
                    + "\n but you are not allowed to guess the entire word at once."
                    + "\n You have six guesses. Good luck! \n");


            System.out.println("Your word is " + word.length() + " letters.");

            while (numberWrong < 6 || !won)
            {
                Scanner keyBoard = new Scanner(System.in);

                char c = keyBoard.next().charAt(0);
                c = Character.toLowerCase(c);

                    if (charArray.contains(c))
                    {
                            // character already accounted for, don't count... tell user he's already asked this letter
                    System.out.println("You've already entered that letter.");
                    continue;
                    }

                    else
                    {
                            // user hasn't asked about this letter... continue
                            charArray.add(c);

                            if(word.contains(""+c))
                            {
                                    //user guessed letter correctly
                                        addLetterToLettersArray(letters, c, word);
                                    System.out.println(c + " is a letter in the word!");
                                    numberCorrect += numberOfOccurencesInString(c, word);

                                    if (numberCorrect == word.length())
                                    {
                                            //game over! you won.
                                            won = true;
                                            break;
                                    }
                    }

                    else
                    {
                            // doesn't contain word
                            System.out.println(c + " is not in the word.");
                            numberWrong += 1;

                            switch(numberWrong)
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


                            if (numberWrong >= 6)
                            {
                                    //user lost
                                    break;
                            }
                    }


                    System.out.println(Arrays.toString(letters));
                    System.out.print("Used Letters: (");


                    for (Character aCar : charArray)
                    {
                        System.out.print(aCar + ", ");
                    }

                    System.out.print(")");
                    }
            }


            //end of game message
            if (won)
            {
                    System.out.println("Congrats on winning!");
                    System.out.println(Arrays.toString(letters));
            }

            else
            {
                    System.out.println("I'm sorry but you lost");
            }

        }
}
