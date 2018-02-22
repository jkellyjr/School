/* CS1632 Deliverable 5
 * By: John  Kelly
 *
 * The PairWise Class is the main driver for the program.
 */

import java.lang.*;
import java.util.*;

 public class Pairwise {

     private static ArrayList<String> combos;

    /**
    * Entrance to the program
    */
    public static void main(String[] args) {
        boolean goodArgs = validateArgs(args);
        if (!goodArgs) {
            System.exit(0);
        }

        String[] params = validateParameters(args);

        int[][] basicTT = buildTruthTable(args.length);
        if (basicTT == null) {
            System.exit(0);
        }

        if (params.length == 2) {
            boolean goodDisplay = showResults(params, basicTT);
            if (!goodDisplay) {
                System.exit(0);
            }
        }

        combos = new ArrayList<String>();
        String[] data = new String[params.length];
        combos = getTWay(params, data, "", 0, params.length - 1, 0, params.length - 1);


    }

    /**
    * Returns true if there are at least two arguments, false if not
    */
    public static boolean validateArgs(String[] args) {
        if (args.length < 2) {
            System.out.println("Please enter at least two paramenters!");
            return false;
        }
        return true;
    }

    /**
    * Returns a string array of valid paramenters (len <= 10), null if error
    */
    public static String[] validateParameters(String[] args) {
        if (args == null) {
            System.out.println("Error: Invalid args argument.");
            return new String[0];
        }

        String[] str = new String[args.length];
        for (int i = 0; i < args.length; i++) {
            if (args[i].length() > 10) {
                str[i] = args[i].substring(0, 10);
            } else {
                str[i] = args[i];
            }
        }
        return str;
    }

    /**
    * Returns a 2d array of generic truth table, null if error
    */
    public static int[][] buildTruthTable(int columns) {
        if (columns < 0) {
            System.out.println("Error: Invalid column number argument.");
            return null;
        }

        int rows = (int) Math.pow(2, columns);
        int[][] tt = new int[rows][columns];

        for (int i = 0; i < rows; i++) {
            for (int j = columns - 1; j >= 0; j--) {
                tt[i][j] = (i / (int) Math.pow(2,  j)) % 2;
            }
        }
        return tt;
    }

    /**
    * Computes t-combinations
    */
    public static ArrayList<String> getTWay(String[] params, String[] data, String prefix, int s, int e, int i, int t) {

        if (params == null) {
            System.out.println("Error: Invalid params argument given.");
            return null;
        }

        if (i == t) {
            for (int j = 0; j < t; j++) {
                String line = data[j];
                String str = line.replaceAll("/", "");
                int count = line.length() - str.length();
                if (count == t ) {
                    combos.add(line.substring(1, line.length()));
                }
            }
            return combos;
        }

        for (int j = s; (j <= e) && ((e - j + 1) >= t - i); j++) {
            data[i] = prefix + "/" + params[j];
            getTWay(params, data, data[i], j + 1, e, i + 1, t);
        }
        return combos;
    }


    /**
    * Displays the results to the user, false if error
    */
    public static boolean showResults(String[] params, int[][] covering) {
        if (params == null || covering == null) {
            System.out.println("Error: Invalid params or covering array as arguments.");
            return false;
        }

        for (int i = 0; i < params.length; i++) {
            System.out.printf("%-10s", params[i]);
        }
        System.out.println();

        int rows = covering[0].length;
        int columns = covering.length;

        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < rows; j++) {
                System.out.printf("%-10d", covering[i][j]);
            }
            System.out.println();
        }
        return true;
    }

 }
