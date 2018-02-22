package rpn;

import java.math.BigInteger;

import java.util.Stack;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import rpnexceptions.InsufficientVariablesForOperatorException;

/**
 * This class tests Requirements 5 and 15.
 */
public class RPNArithmeticTest {
  Stack<BigInteger> _s;
  BigInteger _r;

  @Before
  public void initializeStack() {
    _s = new Stack<BigInteger>();
    _s.push(new BigInteger("15"));
    _s.push(new BigInteger("3"));
  }

  /**
   * Testing against expected result. Requirement 5.
   */
  @Test
  public void additionWithEnoughOperands()
    throws InsufficientVariablesForOperatorException {
    _r = RPNArithmetic.addition(_s);
    assertEquals("18", _r.toString());
  }

  /**
   */
  @Test
  public void subtractionWithEnoughOperands()
    throws InsufficientVariablesForOperatorException {
    _r = RPNArithmetic.subtraction(_s);
    assertEquals("12", _r.toString());
  }

  /**
   */
  @Test
  public void multiplicationWithEnoughOperands()
    throws InsufficientVariablesForOperatorException {
    _r = RPNArithmetic.multiplication(_s);
    assertEquals("45", _r.toString());
  }

  /**
   */
  @Test
  public void divisionWithEnoughOperands()
    throws InsufficientVariablesForOperatorException {
    _r = RPNArithmetic.division(_s);
    assertEquals("5", _r.toString());
  }

  /**
   * Generates appropriate out of operands message. Requirement 15.
   */
  @Test
  public void additionWithoutEnoughOperands() {
    try {
      _s.pop();
      _r = RPNArithmetic.addition(_s);
    } catch (InsufficientVariablesForOperatorException ivfoe) {
      assertEquals("+", ivfoe.getMessage());
    }
  }

  /**
   */
  @Test
  public void subtractionWithoutEnoughOperands() {
    try {
      _s.pop();
      _r = RPNArithmetic.subtraction(_s);
    } catch (InsufficientVariablesForOperatorException ivfoe) {
      assertEquals("-", ivfoe.getMessage());
    }
  }

  /**
   */
  @Test
  public void multiplicationWithoutEnoughOperands() {
    try {
      _s.pop();
      _r = RPNArithmetic.multiplication(_s);
    } catch (InsufficientVariablesForOperatorException ivfoe) {
      assertEquals("*", ivfoe.getMessage());
    }
  }

  /**
   */
  @Test
  public void divisionWithoutEnoughOperands() {
    try {
      _s.pop();
      _r = RPNArithmetic.division(_s);
    } catch (InsufficientVariablesForOperatorException ivfoe) {
      assertEquals("/", ivfoe.getMessage());
    }
  }

  /**
   * Honestly, this doesn't really matter. The program doesn't crash with
   * a stack trace, and if this somehow happens it probably counts as an error.
   */
  @Test(expected = NullPointerException.class)
  public void throwsExceptionOnNullStack()
    throws InsufficientVariablesForOperatorException {
    RPNArithmetic.addition(null);
  }
}
