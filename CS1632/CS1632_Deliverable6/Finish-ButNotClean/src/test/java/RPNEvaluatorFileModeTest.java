package rpn;

import java.util.ArrayList;
import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import rpnexceptions.ElementsInStackAfterEvaluationException;
import rpnexceptions.UseOfVariableBeforeInitializationException;
import rpnexceptions.UnknownKeywordException;
import rpnexceptions.QuitException;

/**
 * This class tests Requirements 3, 5, 6, 7, 8, 9, 13, 20, and 29.
 */
public class RPNEvaluatorFileModeTest {
  RPNTokenizer _rpnt;
  ArrayList<RPNToken> _t;
  RPNEvaluator _rpne;
  String _r;

  @Before
  public void createClasses() {
    _rpnt = new RPNTokenizer();
    _rpne = new RPNEvaluator(true);
  }

  /**
   * In file mode, we don't print the result of anything except PRINT lines.
   * Therefore, an expression that doesn't include PRINT should return null.
   */
  @Test
  public void simpleExpressionReturnsNull() throws Exception {
    _t = _rpnt.tokenize("10 10 +");
    _r = _rpne.execute(_t);
    assertNull(_r);
  }

  /**
   * Letting a variable should initialize it, allowing you to set its value.
   * Once its value is set, you can check it with PRINT.
   * Requirement 8.
   */
  @Test
  public void letSetsVariable() throws Exception {
    _t = _rpnt.tokenize("LET A 10 10 +");
    _rpne.execute(_t);
    _t = _rpnt.tokenize("PRINT A");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  /**
   * Print should always return the result. Requirement 9.
   */
  @Test
  public void printReturnsResult() throws Exception {
    _t = _rpnt.tokenize("PRINT 10 10 +");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  /**
   * Nothing prints out for let. It returns null.
   */
  @Test
  public void letReturnsNull() throws Exception {
    _t = _rpnt.tokenize("LET A 10 10 +");
    _r = _rpne.execute(_t);
    assertNull(_r);
  }

  /**
   * A QuitException is how we signal that the program should terminate
   * normally.
   * Therefore, we should encounter one if we execute QUIT.
   * Requirement 6.
   */
  @Test(expected = QuitException.class)
  public void quitThrowsQuitException() throws Exception {
    _t = _rpnt.tokenize("QUIT");
    _rpne.execute(_t);
  }

  /**
   * Quit should not do anything else based on later symbols.
   * Requirement 7.
   */
  @Test(expected = QuitException.class)
  public void quitIgnoresLaterSymbols() throws Exception {
    _t = _rpnt.tokenize("QUIT 10 10 +");
    _rpne.execute(_t);
  }

  /**
   * Quit should quit even if there are invalid symbols after it. That is,
   * Quit takes precedence over errors. Since this is not how it worked in
   * my original implementation, I consider this a Requirement 29 test.
   */
  @Test(expected = QuitException.class)
  public void invalidTokenAfterQuitThrowQuitException() throws Exception {
    _t = _rpnt.tokenize("QUIT warghbl");
    _rpne.execute(_t);
  }

  /**
   * Using a variable before initialization is a "numbered error" (one with a
   * special message). Therefore, doing it should yield a special exception.
   * Requirement 13.
   */
  @Test(expected = UseOfVariableBeforeInitializationException.class)
  public void useOfVariableBeforeInitializationThrowsException()
    throws Exception {
    _t = _rpnt.tokenize("PRINT A");
    _rpne.execute(_t);
  }

  /**
   * No state should be preserved between evaluations.
   */
  @Test
  public void correctRepeatedEvaluation() throws Exception {
      _t = _rpnt.tokenize("PRINT 10 10 +");
      _r = _rpne.execute(_t);
      assertEquals("20", _r);
      _t = _rpnt.tokenize("PRINT 10 10 +");
      _r = _rpne.execute(_t);
      assertEquals("20", _r);
  }

  /**
   * Too many operands (left over values in stack) is a "numbered exception".
   * Requirement 17.
   */
  @Test(expected = ElementsInStackAfterEvaluationException.class)
  public void exceptionForTooManyOperands() throws Exception {
    _t = _rpnt.tokenize("PRINT 10 10 10 +");
    _rpne.execute(_t);
  }

  /**
   * Per the example output, the "stuff left in stack" error should include
   * the value that would normally be the result.
   * I kind of think this is off-by-one, so here's a test for it.
   * Requirement 29
   */
  @Test
  public void exceptionForTooManyOperandsCorrectMessage() throws Exception {
    try {
      _t = _rpnt.tokenize("PRINT 10 10 10 +");
      _rpne.execute(_t);
    } catch (ElementsInStackAfterEvaluationException eisaee) {
      assertEquals("2", eisaee.getMessage());
    }
  }

  /**
   * Because evaluation is where strings get converted to numbers, it is where
   * overflow is at risk of occurring.
   * Requirement 3.
   */
  @Test
  public void noNumberOverflow() throws Exception {
    _t = _rpnt.tokenize("PRINT 10000000000000000000000");
    _rpne.execute(_t);
  }

  /**
   * Make sure we evaluate the correct operation for a given operator.
   * Requirement 5.
   */
  @Test
  public void correctOperationsForOperators() throws Exception {
    _t = _rpnt.tokenize("PRINT 15 3 +");
    assertEquals("18", _rpne.execute(_t));
      _t = _rpnt.tokenize("PRINT 15 3 -");
    assertEquals("12", _rpne.execute(_t));
      _t = _rpnt.tokenize("PRINT 15 3 *");
    assertEquals("45", _rpne.execute(_t));
      _t = _rpnt.tokenize("PRINT 15 3 /");
    assertEquals("5", _rpne.execute(_t));
  }

  /**
   * Unknown keywords are discovered in evaluation. Well, the exception is
   * thrown their. TBF, I should move this over to tokenization.
   * TODO: That, if/when refactoring tokenization.
   * Anyway, this is another "numbered error".
   * Requirement 20.
   */
  @Test(expected = UnknownKeywordException.class)
  public void invalidTokenThrowsUnknownKeywordException() throws Exception {
    _t = _rpnt.tokenize("PRINT warghbl");
    _rpne.execute(_t);
  }
}
