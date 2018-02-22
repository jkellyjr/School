package rpn;

import rpnexceptions.ElementsInStackAfterEvaluationException;
import rpnexceptions.InsufficientVariablesForOperatorException;
import rpnexceptions.UnknownKeywordException;
import rpnexceptions.UseOfVariableBeforeInitializationException;

/**
 * This file contains code relevent to Requirements 13, 14, 15, 16, 17, 18, 20,
 * 21, and 22.
 */
public class RPNExceptionHandler {
  private boolean isFileMode;

  /**
   * I think the way I'm using exceptions here is wrong. Like, it works, and
   * it does what I want, but it feels somehow wrong that I'm basically
   * using them as signals.
   * TODO: is this the issue with performance?
   *
   * In any case, this does the exception handling for the program. It
   * composes error messages, determines if termination is the correct response
   * to an exception, and provides error codes.
   *
   * @param isFileMode since file mode terminates on exception, but REPL mode
   * doesn't, the exception handler needs to know which mode the program is in.
   */
  public RPNExceptionHandler(boolean isFileMode) {
    this.isFileMode = isFileMode;
  }

  /**
   * Composes the error messages for exceptions/errors. Four kinds of
   * exceptions/errors have special messages:
   *  1. Using a variable before initialization reports the offending variable
   *  2. Providing insufficient variables for an operator reports the operator.
   *  3. If the stack has excess elements after evaluation, the number of
   *  excess elements is reported.
   *  4. If an known keyword is used, the keyword is reported.
   * In all cases, the line number where the error occurs is reported. For all
   * exceptions/errors except the four listed, only the line number is reported.
   *
   * This code contributes to fulfilling Requirements 13, 14, 15, 16, 17, 18,
   * 20, 21, and 22.
   */
  public String generateError(Exception e, int lineNumber) {
    if (UseOfVariableBeforeInitializationException.class.isInstance(e)) {
      return "Line " + lineNumber + ": Variable " + e.getMessage()
             + " not initalized";
    } else if (InsufficientVariablesForOperatorException.class.isInstance(e)) {
      return "Line " + lineNumber + ": Operator " + e.getMessage()
             + " applied to empty stack";
    } else if (ElementsInStackAfterEvaluationException.class.isInstance(e)) {
      return "Line " + lineNumber + ": " + e.getMessage()
             + " elements in stack after evaluation";
    } else if (UnknownKeywordException.class.isInstance(e)) {
      return "Line " + lineNumber + ": Unknown keyword "
             + e.getMessage();
    } else {
      return "Line " + lineNumber + ": Could not evaluate expression";
    }
  }

  /**
   * Exceptions terminate execution in File Mode but not REPL Mode.
   *
   * This code contributes to fulfilling Requirements 13, 14, 15, 16, 17, 18,
   * 20, 21, and 22.
   */
  public boolean shouldTerminate() {
    return isFileMode;
  }

  /**
   * Determines the exit code for an exception/error. The four defined errors
   * are mapped to 1 - 4. For all others, it is 5.
   *
   * This code contributes to fulfilling Requirements 13, 15, 17, 20, and 22.
   */
  public int getErrorCode(Exception e) {
    if (UseOfVariableBeforeInitializationException.class.isInstance(e)) {
      return 1;
    } else if (InsufficientVariablesForOperatorException.class.isInstance(e)) {
      return 2;
    } else if (ElementsInStackAfterEvaluationException.class.isInstance(e)) {
      return 3;
    } else if (UnknownKeywordException.class.isInstance(e)) {
      return 4;
    } else {
      return 5;
    }
  }
}
