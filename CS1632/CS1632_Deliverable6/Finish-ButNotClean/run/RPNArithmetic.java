import java.math.BigInteger;

import java.util.EmptyStackException;
import java.util.Stack;

/**
 * This interface provides the arithmetic functions used by RPN.
 * Each function takes a Stack of BigIntegers as a parameter, pops as needed,
 * and returns the result of the appropriate operation.
 *
 * This code contributes to fulfilling Requirements 5, 15, and 16.
 */
public interface RPNArithmetic {
  public static BigInteger addition(Stack<BigInteger> theStack)
    throws InsufficientVariablesForOperatorException {
    try {
      BigInteger right = theStack.pop();
      BigInteger left = theStack.pop();
      return left.add(right);
    } catch (EmptyStackException ese) {
      throw new InsufficientVariablesForOperatorException("+");
    }
  }

  public static BigInteger subtraction(Stack<BigInteger> theStack)
    throws InsufficientVariablesForOperatorException {
    try {
      BigInteger right = theStack.pop();
      BigInteger left = theStack.pop();
      return left.subtract(right);
    } catch (EmptyStackException ese) {
      throw new InsufficientVariablesForOperatorException("-");
    }
  }

  public static BigInteger multiplication(Stack<BigInteger> theStack)
    throws InsufficientVariablesForOperatorException {
    try {
      BigInteger right = theStack.pop();
      BigInteger left = theStack.pop();
      return left.multiply(right);
    } catch (EmptyStackException ese) {
      throw new InsufficientVariablesForOperatorException("*");
    }
  }

  public static BigInteger division(Stack<BigInteger> theStack)
    throws InsufficientVariablesForOperatorException {
    try {
      BigInteger right = theStack.pop();
      BigInteger left = theStack.pop();
      return left.divide(right);
    } catch (EmptyStackException ese) {
      throw new InsufficientVariablesForOperatorException("/");
    }
  }
}
