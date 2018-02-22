package rpn;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import rpnexceptions.QuitException;

/**
 * This class tests Requirements 4, 10, 31, 32, and 35.
 */
public class RPNInputFileModeTest {
  RPNInput _rpni;

  /**
   * Input should be case insenstive. We test this by reading a line from two
   * files that contain the same input, but with different cases. If the lines
   * read are equal, input (and therefore variables and keywords) is case
   * insenstive.
   * Requirements 4 and 10.
   */
  @Test
  public void inputCaseInsensitive() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn"});
    String normal = _rpni.nextLine();
    _rpni = new RPNInput(new String[] {"lowercase.rpn"});
    String lowercase = _rpni.nextLine();
    assertEquals(normal, lowercase);
  }

  /**
   * This does not test any requirements, but it's a necessary assumption if
   * we use String.split for tokenization. Same methodology as previous.
   */
  @Test
  public void inputSkipsLeadingWhitespace() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn"});
    String normal = _rpni.nextLine();
    _rpni = new RPNInput(new String[] {"leadingwhitespace.rpn"});
    String leadingwhitespace = _rpni.nextLine();
    assertEquals(normal, leadingwhitespace);
  }

  /**
   * Again, a file without X and a file with X. We want to ignore X, so we read
   * from both and assert equal. In this case, X is "empty lines".
   * Requirement 31.
   */
  @Test
  public void inputSkipsEmptyLines() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn"});
    String normal = _rpni.nextLine();
    _rpni = new RPNInput(new String[] {"emptylines.rpn"});
    String emptylines = _rpni.nextLine();
    assertEquals(normal, emptylines);
  }

  /**
   * Reading a line should give us a line number of 1.
   * Requirement 32.
   */
  @Test
  public void correctLineNumberWithinFile() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn"});
    _rpni.nextLine();
    assertEquals(1, _rpni.getLineNumber());
  }

  /**
   * Part of ignoring empty lines is not incrementing line number.
   * Requirement 31.
   */
  @Test
  public void lineNumberSkipsEmptyLines() throws Exception {
    _rpni = new RPNInput(new String[] {"emptylines.rpn"});
    _rpni.nextLine();
    assertEquals(1, _rpni.getLineNumber());
  }

  /**
   * Concatenated files should have a single line number. So we read through
   * one file and into another, then check line number.
   * Requirement 35.
   */
  @Test
  public void lineNumberAcrossFiles() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn", "leadingwhitespace.rpn"});
    _rpni.nextLine();
    _rpni.nextLine();
    assertEquals(2, _rpni.getLineNumber());
  }

  /**
   * An empty file shouldn't do anything. Therefore, nextLine should produce
   * the same output with or without it.
   */
  @Test
  public void emptyFileNoEffect() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn"});
    String normal = _rpni.nextLine();
    _rpni = new RPNInput(new String[] {"emptyfile.rpn", "normal.rpn"});
    String emptyFile = _rpni.nextLine();
    assertEquals(normal, emptyFile);
  }

  /**
   * We terminate normally if we reach the end of input.
   * This is a QuitException
   */
  @Test(expected = QuitException.class)
  public void quitExceptionAtEndOfInput() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn", "leadingwhitespace.rpn"});
    _rpni.nextLine();
    _rpni.nextLine();
    _rpni.nextLine();
  }

  /**
   * We terminate normally if we reach the end of input even if there's a file
   * with no data there.
   * This is a QuitException.
   */
  @Test(expected = QuitException.class)
  public void quitExceptionAtEndOfInputEmptyFile() throws Exception {
    _rpni = new RPNInput(new String[] {"normal.rpn", "leadingwhitespace.rpn",
                                       "emptyfile.rpn"});
    _rpni.nextLine();
    _rpni.nextLine();
    _rpni.nextLine();
  }
}
