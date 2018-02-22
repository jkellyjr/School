

import java.util.ArrayList;
import java.io.IOException;

public class RPN {
    static RPNInput inputHandler;
    static RPNCalculator rpnCalculator;
    RPNExceptions rpnExceptions = new RPNExceptions();
    boolean rpnMode = false;



    public static void main(String[] args) {
        inputHandler = new RPNInput(args);
        boolean goodControl = inputController();
        if (!goodControl) {
            System.exit(0);
        }

    }

    public static boolean inputController() {
        while (inputHandler.hasNextLine()) {
            try {
                String line = inputHandler.getNextLine();
                ArrayList<String> tokenList = inputHandler.getTokenList(line);

                if (inputHandler.containsKeywordQuit()) {
                    return false;
                }

                RPNCalculator rpnCalculator = rpnCalculator = new RPNCalculator(tokenList);

                String result = rpnCalculator.compute();
                System.out.println(result);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return true;
    }


}
