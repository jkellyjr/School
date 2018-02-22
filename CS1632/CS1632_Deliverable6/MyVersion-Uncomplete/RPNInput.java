
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.StringTokenizer;


public class RPNInput {
    private Scanner input;
    private boolean rpnMode;
    private int lineNumber;
    private boolean keywordQuit;
    private boolean keywordLet;
    private boolean keywordPrint;
    RPNFiles fh;

    public RPNInput(String[] files) {
        this.lineNumber = 0;
        this.keywordQuit = false;
        this.keywordLet = false;
        this.keywordPrint = false;

        if (files.length == 0) {
            this.rpnMode = true;
            this.input = new Scanner(System.in);
        } else {
            fh = new RPNFiles(files);
            input = fh.getScanner();
        }
    }

    public ArrayList<String> getTokenList(String line) {
        ArrayList<String> tokenList = new ArrayList<String>();

        if (line == null) {
            return null;
        } else {
            StringTokenizer st = new StringTokenizer(line);

            while (st.hasMoreTokens()) {
                String tmp = st.nextToken();

                if (tmp.equalsIgnoreCase("quit")) {
                    this.keywordQuit = true;
                } else if (tmp.equalsIgnoreCase("let")) {
                    this.keywordLet = true;
                } else if (tmp.equalsIgnoreCase("print")) {
                    this.keywordPrint = true;
                }

                tokenList.add(tmp);
            }
        }
        return tokenList;
    }


    public boolean hasNextLine() {
        if (rpnMode) {
            System.out.print("> ");
        }
        return input.hasNextLine();
    }

    public String getNextLine() {
        if (!input.hasNextLine()) {
            System.out.println("Reached end of file");
            return null;
        }
        lineNumber++;
        return input.nextLine();
    }

    // Accessors
    public Scanner getScanner() {
        return this.input;
    }

    public boolean isRPNMode() {
        return this.rpnMode;
    }

    public int getLineNumber() {
        return this.lineNumber;
    }

    public boolean containsKeywordQuit() {
        return this.keywordQuit;
    }

    public boolean containsKeywordLet() {
        return this.keywordLet;
    }

    public boolean containsKeywordPrint() {
        return this.keywordPrint;
    }
}
