public interface TokenizerSpecification<T extends Token> {
  public int longestMatch(String expression);
  public T createToken(String text) throws IllegalTokenException;
}
