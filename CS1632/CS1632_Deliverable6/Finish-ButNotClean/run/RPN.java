import java.util.Scanner;
import java.util.Stack;
import java.util.ArrayList;

/**
 * This file contains code relevent to Requirements 1, 6, 9, 22, 23, 24.
 */
public class RPN {
  /**
   * Main function.
   * This function has exclusive rights to call System.exit(). It has *almost*
   * exclusive rights to System.out, but giving RPNInput access to System.out
   * lets me have an extremely elegant and efficient solution to the problem of
   * empty line skipping.
   *
   * @param args the filenames to be evaluated if the program is runnign in
   * file mode, nothing otherwise
   */
  public static void main(String[] args) {
    /**
     * None of these should throw an exception. If something would cause an
     * exception, it should be deferred until calls that happen in the main
     * loop. This allows us to use a single try/catch block to capture all
     * exceptions that might arise.
     *
     * This design decision and the try/catch fulfills Requirement 22.
     */
    RPNInput inputReader = new RPNInput(args);
    RPNTokenizer theTokenizer = new RPNTokenizer();
    //Poorly named.
    RPNEvaluator theEvaluator = new RPNEvaluator(args.length != 0);
    RPNExceptionHandler theExceptionHandler =
      new RPNExceptionHandler(args.length != 0);

    while (true) {
      try {
        String line = inputReader.nextLine();
        ArrayList<RPNToken> theTokens = theTokenizer.tokenize(line);

        /**
         * Result always returns something, but the convention is to return
         * non-null if something should be printed.
         *
         * This code contributes to fulfilling Requirement 9.
         */
        String result = theEvaluator.execute(theTokens);
        if (result != null) {
          System.out.println(result); //Requirements 24 and 9
          //There is a race between stdout and stderr.
          System.out.flush();
          Thread.sleep(10); //Look, it fixes the race condition, okay?
          //Have I mentioned that time is just an absolutely garbage fire
          //of a dimension?
        }

      /**
       * IllegalStateExceptions are thrown when an error is encountered
       * because something that one of the "numbered" exceptions should have
       * caught has propagated further than it should.
       * As a result, this *should* never fire. So while it does print a
       * stack trace, the user should never see it in normal operations.
       */
      } catch(IllegalStateException ise) {
        ise.printStackTrace();
        System.err.println("Something went horribly wrong");
        System.exit(6);
      //Requirement 6
      } catch(QuitException qe) {
        System.exit(0);
      } catch(Exception e) {
        String error =
          theExceptionHandler.generateError(e, inputReader.getLineNumber());
        System.err.println(error); //Requirement 23
        System.err.flush();
        try {
          Thread.sleep(10);
        } catch(InterruptedException ie) {}
        if (theExceptionHandler.shouldTerminate()) {
          System.exit(theExceptionHandler.getErrorCode(e));
        }
      }
    }
  }
}
