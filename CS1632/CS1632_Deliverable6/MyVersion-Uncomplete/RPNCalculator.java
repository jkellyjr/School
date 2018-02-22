
import java.util.ArrayList;
import java.util.Stack;
import java.math.BigInteger;
import java.util.EmptyStackException;
import java.util.HashMap;

public class RPNCalculator {
    private ArrayList<String> tokenList;
    private Stack<BigInteger> theStack;
    private HashMap<String, BigInteger> theVariables;

    public RPNCalculator(ArrayList<String> tl) {
        this.tokenList = tl;
        this.theStack = new Stack<BigInteger>();
        this.theVariables = new HashMap<String, BigInteger>();
    }


    public String compute() {
        theStack.clear();
        keywordCheck();
        for (String token: tokenList) {

            switch (token) {
                case "+":
                    theStack.push(addition());
                    break;
                case "-":
                    theStack.push(subtraction());
                    break;
                case "*":
                    theStack.push(multiply());
                    break;
                case "/":
                    theStack.push(divide());
                    break;
                // case "[A-Za-z]":
                //     if (!theVariables.containsKey(token)) {
                //         //TODO throw new exception
                //     }
                //     System.out.println("pushing the variable onto");
                //     theStack.push(theVariables.get(token));
                //     break;
                default:
                    if (token.matches("[A-Za-z]")) {
                        if (!theVariables.containsKey(token)) {
                            //TODO throw new exception
                        }
                        System.out.println("pushing the variable onto: " + theVariables.get(token));
                        theStack.push(theVariables.get(token));
                        break;
                    } else {
                        theStack.push(new BigInteger(token));
                    }


            }
        }

        BigInteger result = theStack.pop();

        if (theStack.size() != 0) {
            //TODO throw elements left over exception
        }

        return result.toString();
    }

    private boolean keywordCheck() {
        if (tokenList.get(0).equalsIgnoreCase("let")) {
            tokenList.remove(0);
            String var = tokenList.remove(0);
            BigInteger val = new BigInteger(tokenList.get(0));
            System.out.println("$$$$ val: " + val);
            theVariables.put(var, val);
            System.out.println("pushing the variable onto: " + theVariables.get(var));
            return true;
        }
        return false;
    }

    private BigInteger addition() {
        try {
            BigInteger firstOperand = theStack.pop();
            BigInteger secondOperand = theStack.pop();
            System.out.println("First: " + firstOperand);
            System.out.println("Second: " + secondOperand);

            return firstOperand.add(secondOperand);
        } catch (EmptyStackException e) {
            System.out.println("Reached the end of the stack (+)");
        }
        return null;
    }

    private BigInteger subtraction() {
        try {
            BigInteger firstOperand = theStack.pop();
            BigInteger secondOperand = theStack.pop();
            return secondOperand.subtract(firstOperand);
        } catch (EmptyStackException e) {
            System.out.println("Reached the end of the stack (-)");
        }
        return null;
    }

    private BigInteger multiply() {
        try {
            BigInteger firstOperand = theStack.pop();
            BigInteger secondOperand = theStack.pop();
            return secondOperand.multiply(firstOperand);
        } catch (EmptyStackException e) {
            System.out.println("Reached the end of the stack (*)");
        }
        return null;
    }

    private BigInteger divide() {
        try {
            BigInteger firstOperand = theStack.pop();
            BigInteger secondOperand = theStack.pop();
            if (secondOperand.equals(BigInteger.ZERO)) {
                throw new ArithmeticException("Cannot divide by zero.");
            }
            return secondOperand.divide(firstOperand);
        } catch (EmptyStackException e) {
            System.out.println("Reached the end of the stack (/)");
        }
        return null;
    }
}
