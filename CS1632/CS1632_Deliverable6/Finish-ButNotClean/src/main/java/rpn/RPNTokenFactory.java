package rpn;

import rpnexceptions.UnknownKeywordException;

import parsing.IllegalTokenException;

import java.util.regex.Pattern;

/**
 * This file contains code relevent to Requirements 2, 3, 4, and 5.
 */
public class RPNTokenFactory {
  public RPNTokenFactory() {}

  /**
   * Creates the appropriate token for a given text.
   * Text can always be determined literally.
   * Type is determined by getType().
   *
   * @param text the text to create a token for.
   * @return the token created.
   */
  public RPNToken createToken(String text) {
    int type = getType(text);
    return new RPNToken(type, text);
  }

  /**
   * Determines the type of a token.
   *
   * This code contributes to fulfilling Requirements 2, 3, 4, and 5.
   *
   * @param text the text of the token being created
   * @return the type of the token the given text corresponds to.
   */
  private int getType(String text) {
    if (Pattern.matches(RPNConstants.NUMBER_REGEX, text)) {
      return RPNConstants.TOKEN_NUMBER;
    } else if (Pattern.matches(RPNConstants.OPERATOR_REGEX, text)) {
      switch (text) {
        case "+":
          return RPNConstants.TOKEN_ADDITION;
        case "-":
          return RPNConstants.TOKEN_SUBTRACTION;
        case "*":
          return RPNConstants.TOKEN_MULTIPLICATION;
        case "/":
          return RPNConstants.TOKEN_DIVISION;
      }
    } else if (Pattern.matches(RPNConstants.VARIABLE_REGEX, text)) {
      return RPNConstants.TOKEN_VARIABLE;
    } else if (Pattern.matches(RPNConstants.RESERVED_REGEX, text)) {
      return RPNConstants.TOKEN_RESERVED;
    }
    return RPNConstants.TOKEN_INVALID;
  }
}
