package parsing;

public interface Token {
  public int getPrecedence();
  public boolean isPrimitive();
  public boolean isLeftParen();
  public boolean isRightParen();
}
