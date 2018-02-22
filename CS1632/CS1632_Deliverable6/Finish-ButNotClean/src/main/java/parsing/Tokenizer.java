package parsing;

import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Stack;

public class Tokenizer<T extends Token> {
  TokenizerSpecification<T> specification;

  /**
   * Creates a tokenizer for a given specification (e.g. regular expressions,
   * arithmetic).
   *
   * @param specification the specification to use
   */
  public Tokenizer(TokenizerSpecification<T> specification) {
    this.specification = specification;
  }

  /**
   * Tokenizes an input string according to the provided specification.
   * Tokens are identified by prefixes of the input, with the longest prefix
   * preferred.
   *
   * @param expression a text to tokenize
   * @return an arraylist of tokens
   */
  public ArrayList<T> tokenize(String expression)
    throws IllegalTokenException {
    ArrayList<T> theTokens = new ArrayList<T>();

    while (expression.length() > 0) {
      int longestToken = specification.longestMatch(expression);
      //Emit an error if the tokenizer encounters an invalid token
      if (longestToken == 0) {
        throw new IllegalStateException();
      }
      String text = expression.substring(0, longestToken);
      expression = expression.substring(longestToken);
      T t = specification.createToken(text);
      //To skip certain parts of input (i.e. comments, whitespace) map them
      //to null tokens.
      if (t != null) {
        theTokens.add(t);
      }
    }

    return theTokens;
  }

  public ArrayList<T> tokenize(String[] expression)
    throws IllegalTokenException {
    ArrayList<T> theTokens = new ArrayList<T>();

    for (String s : expression) {
      T t = specification.createToken(s);
      if (t != null) {
        theTokens.add(t);
      }
    }

    return theTokens;
  }
}
