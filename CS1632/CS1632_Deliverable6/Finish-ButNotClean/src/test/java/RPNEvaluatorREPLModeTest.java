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
 * Most of these are repeated from RPNEvaluatorREPLModeTest. Changed ones
 * are at the top. They are the only ones with comments.
 *
 * The unique tests in this file test Requirements 19, 24, and 25.
 */
public class RPNEvaluatorREPLModeTest {
  RPNTokenizer _rpnt;
  ArrayList<RPNToken> _t;
  RPNEvaluator _rpne;
  String _r;

  @Before
  public void createClasses() {
    _rpnt = new RPNTokenizer();
    _rpne = new RPNEvaluator(false);
  }

  /**
   * Tests requirement 24.
   */
  @Test
  public void letReturnsAssignedValue() throws Exception {
    _t = _rpnt.tokenize("LET A 10 10 +");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  /**
   * Tests requirement 25.
   */
  @Test
  public void simpleExpressionReturnsResult() throws Exception {
    _t = _rpnt.tokenize("10 10 +");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  /**
   * Tests requirement 25.
   */
  @Test
  public void printAndSimpleExpressionEqual() throws Exception {
    assertEquals(_rpne.execute(_rpnt.tokenize("PRINT 10 10 +")),
                 _rpne.execute(_rpnt.tokenize("10 10 +")));
  }

  /**
   * If the expression you tried to initialize a variable to wasn't valid,
   * that variable never really initialized. Therefore, using it would be
   * using an unitialized variable.
   * Requirement 19.
   */
  @Test(expected = UseOfVariableBeforeInitializationException.class)
  public void variableNotInitializedAfterInvalidLet() throws Exception {
    _t = _rpnt.tokenize("LET A 10 10");
    try {
      _rpne.execute(_t);
    } catch (Exception e) {}
    _t = _rpnt.tokenize("PRINT A");
    _rpne.execute(_t);
  }

  /*****************************************************************************
   * Everything past this point is the same as RPNEvaluatorFileModeTest.java
   * These tests are just here to make sure that whatever file mode checks
   * happen to exist don't somehow do something horrifying.
   ****************************************************************************/

  @Test
  public void printReturnsResult() throws Exception {
    _t = _rpnt.tokenize("PRINT 10 10 +");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  @Test
  public void letSetsVariable() throws Exception {
    _t = _rpnt.tokenize("LET A 10 10 +");
    _rpne.execute(_t);
    _t = _rpnt.tokenize("PRINT A");
    _r = _rpne.execute(_t);
    assertEquals("20", _r);
  }

  @Test(expected = QuitException.class)
  public void quitThrowsQuitException() throws Exception {
    _t = _rpnt.tokenize("QUIT");
    _rpne.execute(_t);
  }

  @Test(expected = QuitException.class)
  public void quitIgnoresLaterSymbols() throws Exception {
    _t = _rpnt.tokenize("QUIT 10 10 +");
    _rpne.execute(_t);
  }

  @Test(expected = UseOfVariableBeforeInitializationException.class)
  public void useOfVariableBeforeInitializationThrowsException()
    throws Exception {
    _t = _rpnt.tokenize("PRINT A");
    _rpne.execute(_t);
  }

  @Test
  public void correctRepeatedEvaluation() throws Exception {
      _t = _rpnt.tokenize("PRINT 10 10 +");
      _r = _rpne.execute(_t);
      assertEquals("20", _r);
      _t = _rpnt.tokenize("PRINT 10 10 +");
      _r = _rpne.execute(_t);
      assertEquals("20", _r);
  }

  @Test(expected = ElementsInStackAfterEvaluationException.class)
  public void exceptionForTooManyOperands() throws Exception {
    _t = _rpnt.tokenize("PRINT 10 10 10 +");
    _rpne.execute(_t);
  }

  @Test
  public void noNumberOverflow() throws Exception {
    _t = _rpnt.tokenize("PRINT 10000000000000000000000");
    _rpne.execute(_t);
  }

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

  @Test(expected = UnknownKeywordException.class)
  public void invalidTokenThrowsUnknownKeywordException() throws Exception {
    _t = _rpnt.tokenize("PRINT warghbl");
    _rpne.execute(_t);
  }

  @Test(expected = QuitException.class)
  public void invalidTokenAfterQuitThrowQuitException() throws Exception {
    _t = _rpnt.tokenize("QUIT warghbl");
    _rpne.execute(_t);
  }
}
