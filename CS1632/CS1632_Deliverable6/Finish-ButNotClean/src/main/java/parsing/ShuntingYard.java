package parsing;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class ShuntingYard<T extends Token> {
  public ShuntingYard() {}

  public ArrayList<T> toPostfix(ArrayList<T> expression) {
    Stack<T> theStack = new Stack<T>();
    ArrayList<T> postfix = new ArrayList<T>();

    for (T token : expression) {
      if (token.isPrimitive())
        postfix.add(token);
      else if (token.isLeftParen()) {
        theStack.push(token);
      } else if (token.isRightParen()) {
        postfix = matchParen(postfix, theStack);
      } else {
        while(shouldPopOperator(theStack, token))
          postfix.add(theStack.pop());
        theStack.push(token);
      }
    }

    return popAll(postfix, theStack);
  }

  /**
   * The Shunting Yard Algorithm uses a stack to track operators.
   * Sometimes, it is necessary to pop an operator off of the stack.
   * Specifically, when the current token is an operator of lower precedence
   * than the top of the stack, an operator should be popped.
   * Operators are all tokens except characters and parentheses.
   *
   * @param stack the stack of tokens currently being converted
   * @param t the current token being considered
   * @return whether a token should be popped off the stack
   */
  private boolean shouldPopOperator(Stack<T> stack, T token) {
    if (stack.isEmpty() || token.isPrimitive()
        || token.isLeftParen() || token.isRightParen()
        || token.getPrecedence() < stack.peek().getPrecedence()) {
      return false;
    }
    return true;
  }

  /**
   * Pops operators from the stack until a matching (left) parentheses is
   * found. Adds all popped non-parentheses operators to the postfix expression.
   *
   * @param postfix the postfix expression to add to
   * @param theStack the stack to pop from
   * @return the updated postfix expression
   */
  private ArrayList<T> matchParen(ArrayList<T> postfix, Stack<T> theStack) {
    try {
      while (!theStack.peek().isLeftParen()) {
        postfix.add(theStack.pop());
      }
      theStack.pop();
    } catch(EmptyStackException ese) {
      throw new IllegalStateException();
    }

    return postfix;
  }

  /**
   * Adds all remaining operators on the stack to a postfix expression.
   *
   * @param postfix the postfix expression to add to
   * @param theStack the stack to pop from
   * @return the updated postfix expression
   */
  private ArrayList<T> popAll(ArrayList<T> postfix, Stack<T> theStack) {
    while (!theStack.isEmpty()) {
      if (theStack.peek().isRightParen() || theStack.peek().isLeftParen()) {
         throw new IllegalStateException();
      }
      postfix.add(theStack.pop());
    }

    return postfix;
  }
}
