package rpn;

import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import rpnexceptions.UnknownKeywordException;

/**
 * This class does not directly test any requirements.
 */
public class RPNTokenFactoryTest {
  RPNTokenFactory _rpntf;
  RPNToken _t1;
  RPNToken _t2;

  @Before
  public void createFactory() {
    _rpntf = new RPNTokenFactory();
  }

  /**
   * These do not test requirements. They test tokens. We known what the token
   * for a given text should be, so we make it manual then check factory output.
   */

  @Test
  public void correctTokenNumber() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_NUMBER, "1000");
    _t2 = _rpntf.createToken("1000");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenAdditon() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_ADDITION, "+");
    _t2 = _rpntf.createToken("+");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenSubtraction() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_SUBTRACTION, "-");
    _t2 = _rpntf.createToken("-");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenMultiplication() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_MULTIPLICATION, "*");
    _t2 = _rpntf.createToken("*");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenDivision() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_DIVISION, "/");
    _t2 = _rpntf.createToken("/");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenVariable() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_VARIABLE, "A");
    _t2 = _rpntf.createToken("A");
    assertTrue(_t1.equals(_t2));
  }

  @Test
  public void correctTokenReserved() throws Exception {
    _t1 = new RPNToken(RPNConstants.TOKEN_RESERVED, "LET");
    _t2 = _rpntf.createToken("LET");
    assertTrue(_t1.equals(_t2));
  }
}
