package rpn;

public interface RPNConstants {
  public static final String NUMBER_REGEX = "-?[0-9]+";
  public static final String OPERATOR_REGEX = "[+\\-*/]";
  public static final String VARIABLE_REGEX = "[A-Z]";
  public static final String RESERVED_REGEX = "(PRINT)|(LET)|(QUIT)";
  public static final String WHITESPACE_REGEX = "[ \t]+";

  public static final int TOKEN_NUMBER = 1;
  public static final int TOKEN_ADDITION = 2;
  public static final int TOKEN_SUBTRACTION = 3;
  public static final int TOKEN_MULTIPLICATION = 4;
  public static final int TOKEN_DIVISION = 5;
  public static final int TOKEN_VARIABLE = 6;
  public static final int TOKEN_RESERVED = 7;
  public static final int TOKEN_INVALID = 8;

  public static final int NO_COMMAND = 0;
  public static final int COMMAND_PRINT = 1;
  public static final int COMMAND_LET = 2;
  public static final int COMMAND_LET_AND_PRINT = 3;
  public static final int COMMAND_QUIT = 4;
}
