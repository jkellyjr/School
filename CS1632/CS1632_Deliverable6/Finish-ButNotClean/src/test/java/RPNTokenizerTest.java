package rpn;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class tests Requirements 8, 9, 11, 12, and 34.
 */
public class RPNTokenizerTest {
  RPNTokenizer _rpnt;

  @Before
  public void createTokenizer() {
    _rpnt = new RPNTokenizer();
  }

  /**
   * It shouldn't matter what kind of whitespace separates tokens.
   * Requirement 34.
   */
  @Test
  public void differentDelimitersYieldSameTokens() throws Exception {
    String firstLine = "PRINT \t10\t \t10     +";
    String secondLine = "PRINT\t10\t\t10\t +";
    ArrayList<RPNToken> firstTokens = _rpnt.tokenize(firstLine);
    ArrayList<RPNToken> secondTokens = _rpnt.tokenize(secondLine);
    if (firstTokens.size() != secondTokens.size()) {
      fail();
    } else {
      for (int i = 0; i < firstTokens.size(); i++) {
        assertTrue(firstTokens.get(0).equals(secondTokens.get(0)));
      }
    }
  }

  /**
   * LET-ing without a variable to assign to is an error.
   * Requirement 8.
   */
  @Test(expected = Exception.class)
  public void letWithoutVariableInvalid() throws Exception {
    String line = "LET 10 10 +";
    _rpnt.tokenize(line);
  }

  /**
   * LET-ing without an expression is an error.
   * Requirement 12..
   */
  @Test(expected = Exception.class)
  public void letWithoutExpressionInvalid() throws Exception {
    String line = "LET A";
    _rpnt.tokenize(line);
  }

  /**
   * PRINT-ing without an expression is an error.
   * Requirement 9.
   */
  @Test(expected = Exception.class)
  public void printWithoutExpressionInvalid() throws Exception {
    String line = "PRINT";
    _rpnt.tokenize(line);
  }

  /**
   * You an only have one command per line.
   */
  @Test(expected = Exception.class)
  public void multipleReservedWordsInvalid() throws Exception {
    String line = "LET A PRINT 10 10 +";
    _rpnt.tokenize(line);
  }

  /**
   * Reserved words must start a line.
   * Requirement 11.
   */
  @Test(expected = Exception.class)
  public void reservedWordAfterFirstIndexInvalid() throws Exception {
    String line = "10 A 10 10 + PRINT A";
    _rpnt.tokenize(line);
  }
}
