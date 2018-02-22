import java.util.ArrayList;
import java.util.regex.Pattern;
/**
 * This file contains code relevent to Requirements 6, 7, 8, 9, 11, 12, and 34.
 */
public class RPNTokenizer {
  private Tokenizer<RPNToken> theTokenizer;

  public RPNTokenizer() {
    theTokenizer = new Tokenizer<RPNToken>(new RPNTokenizerSpecification());
  }

  /**
   * Splits a line on whitespace, tokenizes it, and checks the basic validity
   * of the created tokens.
   *
   * This code contributes to fulfilling Requirement 34.
   *
   * @param line the line to tokenize
   * @return a list of tokens that have no "syntax errors"
   */
  public ArrayList<RPNToken> tokenize(String line) throws Exception {
    String[] tokens = line.split(RPNConstants.WHITESPACE_REGEX);
    ArrayList<RPNToken> theTokens = theTokenizer.tokenize(tokens);
    if (!checkTokenValidity(theTokens)) {
      throw new Exception();
    }
    return theTokens;
  }

  /**
   * Checks for RPN++ "Syntax Errors". These are:
   *  1. Let is not followed by a variable (Requirement 8).
   *  2. Let is not followed by an expression (Requirements 8 and 12).
   *  Note: while it is not clear from spec, examples indicate this is an
   *  "insufficient variables for operator" error. It is treated as such.
   *
   *  3. Print is not followed by an expression (Requirement 9).
   *  4. Keywords after the start of a line (Requirement 11).
   *
   * This code fulfills Requirements 8, 9, 11, and 12.
   *
   * @param theTokens a list of tokens to validate
   * @return false if the tokens have syntax errors, true if they do not.
   */
  private boolean checkTokenValidity(ArrayList<RPNToken> theTokens) throws
    InsufficientVariablesForOperatorException, QuitException,
    UnknownKeywordException {
    if (theTokens.get(0).getText().equals("LET")) {
      String tokenText = theTokens.get(1).getText();
      if (!Pattern.matches(RPNConstants.VARIABLE_REGEX, tokenText)) {
        return false;
      }
      if (theTokens.size() == 2) {
        throw new InsufficientVariablesForOperatorException("LET");
      }
    }

    if (theTokens.get(0).getText().equals("PRINT")) {
      if (theTokens.size() == 1) {
        return false;
      }
    }

    if (theTokens.get(0).getText().equals("QUIT")) {
      throw new QuitException();
    }

    for (int i = 0; i < theTokens.size(); i++) {
      if (i > 0 && theTokens.get(i).getType() == RPNConstants.TOKEN_RESERVED) {
        return false;
      } else if (theTokens.get(i).getType() == RPNConstants.TOKEN_INVALID) {
        throw new UnknownKeywordException(theTokens.get(i).getText());
      }
    }

    return true;
  }
}
