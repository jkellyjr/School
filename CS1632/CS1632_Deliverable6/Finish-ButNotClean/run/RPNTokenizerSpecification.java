import java.util.regex.Pattern;

/**
 * This code is not directly relevant to any requirements.
 */
public class RPNTokenizerSpecification
  implements TokenizerSpecification<RPNToken> {
    RPNTokenFactory theTokenFactory;
    public RPNTokenizerSpecification() {
      theTokenFactory = new RPNTokenFactory();
    }

    public int longestMatch(String expression) {
      throw new UnsupportedOperationException();
    }

    public RPNToken createToken(String text) throws IllegalTokenException {
      return theTokenFactory.createToken(text);
    }
}
