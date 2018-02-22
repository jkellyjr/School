package rpn;

import parsing.Token;

/**
 * This is the token class. It does not directly implement any Requirements.
 */
public class RPNToken implements Token {
    int type;
    String text;

    public RPNToken(int type, String text) {
      this.type = type;
      this.text = text;
    }

    /**
     * Tokens are equal IFF they have the same text and type.
     */
    public boolean equals(RPNToken other) {
      if (this.type != other.type) {
        return false;
      } else if (!this.text.equals(other.text)) {
        return false;
      }
      return true;
    }

    public int getType() {
      return type;
    }

    public String getText() {
      return text;
    }

    /*
      Right now, these aren't important. I borrowed the code for this from
      another project, and it wants these here.
    */
    public int getPrecedence() {
      return 0;
    }

    public boolean isPrimitive() {
      return false;
    }

    public boolean isLeftParen() {
      return false;
    }

    public boolean isRightParen() {
      return false;
    }
}
