package rpn;

import java.util.regex.Pattern;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Technically, this doesn't correspond to a class, and perhaps it should.
 * That said, the regexes used to define tokens need to be tested somewhere
 * and doing it here makes it explicit what I'm testing.
 * Arguably, this corresponds to RPNConstants.
 *
 * This class tests Requirements 2, 3, 4, 5, 29
 */

public class TokenTypeTest {
  /**
   * These four tests make sure that numbers match all numbers, but only
   * numbers. I wasn't sure we needed negative numbers, but the example has
   * negative numbers, so go go gadget Requirement 29!
   * Requirements 3 and 29.
   */

  @Test
  public void matchValidNumbers() {
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "1000"));
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "7"));
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "0003"));
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX,
      "1111111111110003253472851308"));
  }

  @Test
  public void matchNumberZero() {
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "0"));
  }

  @Test
  public void matchNegativeNumbers() {
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "-0"));
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX, "-10"));
    assertTrue(Pattern.matches(RPNConstants.NUMBER_REGEX,
      "-1000000000000000000000"));
  }

  @Test
  public void notMatchDecimalNumbers() {
    assertFalse(Pattern.matches(RPNConstants.NUMBER_REGEX, "10.0"));
    assertFalse(Pattern.matches(RPNConstants.NUMBER_REGEX, "0.355"));
    assertFalse(Pattern.matches(RPNConstants.NUMBER_REGEX, "-.277"));
  }

  /**
   * These three tests test operator matching. You should match the four
   * "basic" operators. You should not match sequences, or fancy stuff like
   * modulo or bitwise and.
   * Requirement 5.
   */

  @Test
  public void matchOperators() {
    assertTrue(Pattern.matches(RPNConstants.OPERATOR_REGEX, "+"));
    assertTrue(Pattern.matches(RPNConstants.OPERATOR_REGEX, "-"));
    assertTrue(Pattern.matches(RPNConstants.OPERATOR_REGEX, "*"));
    assertTrue(Pattern.matches(RPNConstants.OPERATOR_REGEX, "/"));
  }

  @Test
  public void notMatchOperatorSequences() {
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "--"));
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "*+"));
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "//"));
  }

  @Test
  public void notMatchExtraOperators() {
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "^"));
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "="));
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "%"));
    assertFalse(Pattern.matches(RPNConstants.OPERATOR_REGEX, "&"));
  }

  /**
   * Once again, some stuff you should match, then more stuff you shouldn't.
   * This time, for variables.
   * You should match a single letter, but not more than one. You especially
   * shouldn't match reserved words.
   * Requirement 4.
   */

  @Test
  public void matchSingleCharacterVariables() {
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "A"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "Q"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "T"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "Z"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "L"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "P"));
    assertTrue(Pattern.matches(RPNConstants.VARIABLE_REGEX, "B"));
  }

  @Test
  public void notMatchMultipleCharacterVariables() {
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "AA"));
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "TTTTT"));
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "AGHEABAHEA"));
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "WARGHBL"));
  }

  @Test
  public void variablesNotMatchReservedWords() {
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "LET"));
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "PRINT"));
    assertFalse(Pattern.matches(RPNConstants.VARIABLE_REGEX, "QUIT"));
  }

  /**
   * One final time. Matching reserved words is good. Matching other things is
   * bad.
   * Requirement 2.
   */

  @Test
  public void reservedWordsMatchCorrectly() {
    assertTrue(Pattern.matches(RPNConstants.RESERVED_REGEX, "LET"));
    assertTrue(Pattern.matches(RPNConstants.RESERVED_REGEX, "PRINT"));
    assertTrue(Pattern.matches(RPNConstants.RESERVED_REGEX, "QUIT"));
  }

  @Test
  public void reservedWordsNotMatchOtherValues() {
    assertFalse(Pattern.matches(RPNConstants.RESERVED_REGEX, "BEAR"));
    assertFalse(Pattern.matches(RPNConstants.RESERVED_REGEX, "WARGHBL"));
    assertFalse(Pattern.matches(RPNConstants.RESERVED_REGEX, "A"));
  }

  /**
   * We have a string with a boatload of whitespace. After stripping leading
   * whitespace, splitting it on our Whitespace regular expression should
   * leave us with no empty strings in the array.
   */
  @Test
  public void noEmptyStringsAfterWhitespaceStripAndSplit() {
    String toSplit = "  \t8  110 00 PR INT tt T\taa1\t\t120 ++ \t 13r12\t\t";
    String[] split = toSplit.replaceFirst(RPNConstants.WHITESPACE_REGEX, "")
      .split(RPNConstants.WHITESPACE_REGEX);
    for (int i = 0; i < split.length; i++) {
      assertNotEquals("", split[i]);
    }
  }
}
