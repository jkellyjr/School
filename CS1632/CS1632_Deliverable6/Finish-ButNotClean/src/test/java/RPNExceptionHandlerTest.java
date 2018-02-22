package rpn;

import java.math.BigInteger;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import rpnexceptions.ElementsInStackAfterEvaluationException;
import rpnexceptions.InsufficientVariablesForOperatorException;
import rpnexceptions.UnknownKeywordException;
import rpnexceptions.UseOfVariableBeforeInitializationException;

/**
 * This class tests Requirements 13, 14, 15, 16, 17, 18, 20, 21, and 22.
 */
public class RPNExceptionHandlerTest {
  RPNExceptionHandler _rpneh;

  @Before
  public void createExceptionHandler() {
    _rpneh = new RPNExceptionHandler(true);
  }

  /**
   * One comment for all five tests, because they're basically the same.
   * Each test has an error message. It has an expected format, which includes
   * a line number and a message variable.
   * These tests create a dummy exception with a known message variable and
   * use 1 as a line number so we can predict the message.
   * Requirements 13, 15, 17, 20, and 22.
   */

  @Test
  public void correctErrorMessageUnassignedVariable() {
    UseOfVariableBeforeInitializationException exception =
      new UseOfVariableBeforeInitializationException("A");
    String error = _rpneh.generateError(exception, 1);
    String expected = "Line 1: Variable A not initalized";
    assertEquals(expected, error);
  }

  @Test
  public void correctErrorMessageInsufficentVariables() {
    InsufficientVariablesForOperatorException exception =
      new InsufficientVariablesForOperatorException("+");
    String error = _rpneh.generateError(exception, 1);
    String expected = "Line 1: Operator + applied to empty stack";
    assertEquals(expected, error);
  }

  @Test
  public void correctErrorMessageExcessVariablesInStack() {
    ElementsInStackAfterEvaluationException exception =
      new ElementsInStackAfterEvaluationException("4");
    String error = _rpneh.generateError(exception, 1);
    String expected = "Line 1: 4 elements in stack after evaluation";
    assertEquals(expected, error);
  }

  @Test
  public void correctErrorMessageUnknownKeyword() {
    UnknownKeywordException exception =
      new UnknownKeywordException("WARGHBL");
    String error = _rpneh.generateError(exception, 1);
    String expected = "Line 1: Unknown keyword WARGHBL";
    assertEquals(expected, error);
  }

  @Test
  public void correctErrorMessageOther() {
    String error = _rpneh.generateError(new Exception(), 1);
    String expected = "Line 1: Could not evaluate expression";
    assertEquals(expected, error);
  }

  /**
   * In file mode, you terminate on an exception.
   * Tests Requirements 13, 15, 17, 20, and 22.
   */
  @Test
  public void shouldTerminateInFileMode() {
    assertEquals(true, _rpneh.shouldTerminate());
  }

  /**
   * In REPL mode, you display a message, but continue, on an exception.
   * Tests Requirements 14, 16, 18, and 21.
   */
  @Test
  public void shouldNotTerminateInREPLMode() {
    _rpneh = new RPNExceptionHandler(false);
    assertEquals(false, _rpneh.shouldTerminate());
  }

  /**
   * Again, explaining a whole bunch at once.
   * Each type of error has an error code.
   * The numbered errors have 1 - 4. Other errors have 5.
   * These tests check to make sure RPNExceptionHandler generates the right
   * error code for each exception.
   * Requirements 13, 15, 17, 20, and 22.
   */

  @Test
  public void correctErrorCodeUnassignedVariable() {
    UseOfVariableBeforeInitializationException exception =
      new UseOfVariableBeforeInitializationException("A");
    int error = _rpneh.getErrorCode(exception);
    assertEquals(1, error);
  }

  @Test
  public void correctErrorCodeInsufficentVariables() {
    InsufficientVariablesForOperatorException exception =
      new InsufficientVariablesForOperatorException("+");
    int error = _rpneh.getErrorCode(exception);
    assertEquals(2, error);
  }

  @Test
  public void correctErrorCodeExcessVariablesInStack() {
    ElementsInStackAfterEvaluationException exception =
      new ElementsInStackAfterEvaluationException("4");
    int error = _rpneh.getErrorCode(exception);
    assertEquals(3, error);
  }

  @Test
  public void correctErrorCodeUnknownKeyword() {
    UnknownKeywordException exception =
      new UnknownKeywordException("WARGHBL");
    int error = _rpneh.getErrorCode(exception);
    assertEquals(4, error);
  }

  @Test
  public void correctErrorCodeOther() {
    int error = _rpneh.getErrorCode(new Exception());
    assertEquals(5, error);
  }
}
