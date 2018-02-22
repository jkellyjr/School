package rpn;

import rpnexceptions.ElementsInStackAfterEvaluationException;
import rpnexceptions.InsufficientVariablesForOperatorException;
import rpnexceptions.UseOfVariableBeforeInitializationException;
import rpnexceptions.UnknownKeywordException;
import rpnexceptions.QuitException;

import java.math.BigInteger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Stack;

/**
 * This file contains code relevent to Requirements 6, 7, 8, 9, 13, 14, 17,
 * 18, and 25.
 */
public class RPNEvaluator {
  private boolean isFileMode;
  private Stack<BigInteger> theStack;
  private HashMap<String, BigInteger> theVariables;

  /**
   * Constructor. Creates an evaluator in either File Mode or REPL Mode.
   * The difference between these modes is when execute returns the result
   * to be printed.
   * In File Mode, printing only occurs for the PRINT keyword.
   * In REPL Mode, printing occurs unless the QUIT keyword is encountered.
   *
   * @param isFileMode the mode for the evaluator
   */
  public RPNEvaluator(boolean isFileMode) {
    this.isFileMode = isFileMode;
    this.theStack = new Stack<BigInteger>();
    this.theVariables = new HashMap<String, BigInteger>();
  }

  /**
   * Executes a line of RPN language code.
   *
   * @param theTokens tokens representing the expression
   * @return the result if it should be printed, or null otherwise
   */
  public String execute(ArrayList<RPNToken> theTokens)
    throws ElementsInStackAfterEvaluationException,
    InsufficientVariablesForOperatorException,
    QuitException,
    UseOfVariableBeforeInitializationException,
    UnknownKeywordException {
    theStack.clear();

    int operation = getOperation(theTokens);

    /**
     * I know what you're thinking -- where's "no command"?
     * Well, it doesn't exist.
     * In File Mode, lines with no command have no effect on the program's
     * state, so executing them is a waste of time.
     * In REPL Mode, lines with no command are implicitly printed and don't
     * need a separate command.
     */
    switch (operation) {
      case RPNConstants.COMMAND_PRINT:
        return evaluate(theTokens).toString();
      case RPNConstants.COMMAND_LET:
        let(theTokens);
        break;
      case RPNConstants.COMMAND_LET_AND_PRINT:
        return let(theTokens);
      //Requirements 6 and 7
      case RPNConstants.COMMAND_QUIT:
        throw new QuitException();
    }
    return null;
  }

  /**
   * Evaluates an expression and assigns its result to a variable.
   *
   * This code contributes to fulfilling Requirement 8.
   *
   * @param theTokens the expression to evaluate, and the variable to assign
   * it to.
   * @return the string representation of the value of the expression
   */
  private String let(ArrayList<RPNToken> theTokens) throws
    ElementsInStackAfterEvaluationException,
    InsufficientVariablesForOperatorException,
    UseOfVariableBeforeInitializationException,
    UnknownKeywordException {
    String variable = theTokens.remove(0).getText();
    BigInteger result = evaluate(theTokens);
    theVariables.put(variable, result);
    return result.toString();
  }

  /**
   * Determines the operation to perform for a given token sequence.
   * If the leading token is a reserved keyword, a decision is made on that
   * basis.
   * Otherwise, you should either do nothing (file mode) or print (REPL mode)
   *
   * @param theTokens the tokens to determine an operation for
   * @return the appropriate operation code, defined in RPNConstants
   */
  private int getOperation(ArrayList<RPNToken> theTokens) {
    if (theTokens.get(0).getType() == RPNConstants.TOKEN_RESERVED) {
      //Remove the command, it is not needed for evaluation
      String text = theTokens.remove(0).getText();
      if (text.equals("QUIT")) {
        return RPNConstants.COMMAND_QUIT;
      } else if (text.equals("LET")) {
        if (isFileMode) {
          return RPNConstants.COMMAND_LET;
        } else {
          return RPNConstants.COMMAND_LET_AND_PRINT;
        }
      } else if (text.equals("PRINT")) {
        return RPNConstants.COMMAND_PRINT;
      }

      throw new IllegalStateException();
    }

    if (isFileMode) {
      return RPNConstants.NO_COMMAND;
    } else {
      return RPNConstants.COMMAND_PRINT; //Requirement 25
    }
  }

  /**
   * Evaluates a RPN expression.
   * At this point, the expression *should* contain just numbers and operators.
   * If it doesn't, you are in an IllegalState and should throw an Exception.
   *
   * Iterates over the tokens, pushing values to the stack, evaluating
   * operators and pushing results to the stack.
   *
   * Checks to see if the stack is empty after evaluation.
   *
   * This code contributes to fulfilling Requirements 8, 9, 13, 14, 17, and 18.
   */
  private BigInteger evaluate(ArrayList<RPNToken> theTokens) throws
    ElementsInStackAfterEvaluationException,
    InsufficientVariablesForOperatorException,
    UseOfVariableBeforeInitializationException,
    UnknownKeywordException {

    for (RPNToken t : theTokens) {
      switch (t.getType()) {
        case RPNConstants.TOKEN_NUMBER:
          theStack.push(new BigInteger(t.getText()));
          break;
        case RPNConstants.TOKEN_VARIABLE:
          if (!theVariables.containsKey(t.getText())) {
            throw new UseOfVariableBeforeInitializationException(t.getText());
          }
          theStack.push(theVariables.get(t.getText()));
          break;
        case RPNConstants.TOKEN_ADDITION:
          theStack.push(RPNArithmetic.addition(theStack));
          break;
        case RPNConstants.TOKEN_SUBTRACTION:
          theStack.push(RPNArithmetic.subtraction(theStack));
          break;
        case RPNConstants.TOKEN_MULTIPLICATION:
          theStack.push(RPNArithmetic.multiplication(theStack));
          break;
        case RPNConstants.TOKEN_DIVISION:
          theStack.push(RPNArithmetic.division(theStack));
          break;
        default:
          throw new UnknownKeywordException(t.getText());
      }
    }

    BigInteger result = theStack.pop();

    if (theStack.size() > 0) {
      throw new
        ElementsInStackAfterEvaluationException("" + (theStack.size() + 1));
    }

    return result;
  }
}
