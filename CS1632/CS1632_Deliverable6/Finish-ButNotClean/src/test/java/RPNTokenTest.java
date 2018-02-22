package rpn;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * This class does not test any requirements.
 */
public class RPNTokenTest {
  RPNToken _t1;
  RPNToken _t2;

  @Before
  public void createTokens() {
    _t1 = new RPNToken(RPNConstants.TOKEN_NUMBER, "10000");
    _t2 = new RPNToken(RPNConstants.TOKEN_NUMBER, "10000");
  }

  @Test
  public void getCorrectType() {
    assertEquals(RPNConstants.TOKEN_NUMBER, _t1.getType());
  }

  @Test
  public void getCorrectText() {
    assertEquals("10000", _t1.getText());
  }

  @Test
  public void indenticalTokensEqual() {
    assertTrue(_t1.equals(_t2));
    assertTrue(_t2.equals(_t1));
  }
}
