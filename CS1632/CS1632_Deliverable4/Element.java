// CS1632 Deliverable 4
// By: John Kelly

import java.io.*;
import java.util.*;
import java.text.StringCharacterIterator;

public class Element
{
    // Fields
    private static String DICT_FILE_NAME = "elements.txt";


    // Program entrance
    public static void main(String[] args)
    {
        HashMap<String, String> dictionary = buildDictionary(DICT_FILE_NAME);
        if (dictionary == null)  System.exit(0);

        BufferedReader reader = handleInputFile(args);
        if (reader == null)  System.exit(0);

        boolean goodProcess = processInputFile(reader, dictionary);
        if (!goodProcess) System.exit(0);
    }

    // Returns an open buffered reader object, handle possible errors
    public static BufferedReader handleInputFile(String[] args)
    {
        if (args.length != 1) {
            System.out.println("Error: Enter only one argument, the file to read.");
            return null;
        }
        File file = new File(args[0]);
        return openFile(args[0], file);
    }

    // Return buffered reader object
    public static BufferedReader openFile(String filename, File file)
    {
        if (file == null || filename == null) {
            System.out.println("Error: Invalid file argument.");
            return null;
        }

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            if (reader == null) {
                System.out.println("Error: Unable to open file for reading.");
                return null;
            }
        } catch (FileNotFoundException e) {
            System.out.println("Error: File \"" + filename + "\" does not exist.");
            return null;
        }
        return reader;
    }

    // Retruns the dictionary of the file implemented through hash map
    public static HashMap<String, String> buildDictionary(String filename)
    {
        if (filename == null) {
            System.out.println("Error: Invalid filename argument.");
            return null;
        }

        File file = new File(filename);
        BufferedReader reader = openFile(filename, file);
        if (reader == null) return null;

        HashMap<String, String> hm = new HashMap<String, String>();

        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                line = line.replaceAll("\\s", "");
                line = line.replaceAll("\"", "");
                int x = line.indexOf(":");
                String key = line.substring(0, x);
                String val = line.substring(x + 1, line.length() - 1);
                hm.put(key.toLowerCase(), val.toLowerCase());
            }
        } catch(IOException e) {
            System.out.println("Error: Unable to build dictionary.");
            return null;
        }
        return hm;
    }

    // Handler for processing the input file and comparing against the dictionary
    public static boolean processInputFile(BufferedReader reader, HashMap<String, String> dict)
    {
        if (reader == null || dict == null) {
            System.out.println("Error: Invalid buffered reader argument.");
            return false;
        }

        try {
            String line = "";
            while ((line = reader.readLine()) != null) {
                processWord(line, dict);
            }
        } catch (IOException e) {
            System.out.println("Error: Unable to process the input file.");
            return false;
        }
        return true;
    }

    // Compare possible keys to keys in the dictionary hash map
    public static boolean processWord(String word, HashMap<String, String> dict)
    {
        if (word == null || dict == null) {
            System.out.println("Error: Invalid word and dictionary hash map.");
            return false;
        }

        String[] keys = new String[word.length()];
        String[] vals = new String[word.length()];
        String str = word.replaceAll("\\s", "");
        str = str.toLowerCase();

        boolean miss = false;
        String k = "";
        String dk = "";
        int j = 0;
        for (int i = 0; i < str.length(); i++) {
            k = String.valueOf(str.charAt(i));
            if (i == str.length() - 1) {
                dk = "";
            }
            else {
                dk = k + String.valueOf(str.charAt(i + 1));
            }

            if (dict.containsKey(k)) {
                if (dict.containsKey(dk)) {
                    keys[j] = dk;
                    vals[j] = dict.get(dk);
                    j++;
                    i++;
                }
                else {
                    keys[j] = k;
                    vals[j] = dict.get(k);
                    j++;
                }
            }
            else if (dict.containsKey(dk)) {
                keys[j] = dk;
                vals[j] = dict.get(dk);
                j++;
                i++;
            }
            else {
                miss = true;
                break;
            }
        }

        if (!miss) {
            String s = "";
            String t = "";
            for (int i = 0; i < j; i ++) {
                s = s + (keys[i] + " - ");

            }
            System.out.println(s.substring(0, s.length() - 2));
            for (int i = 0; i < j; i ++) {
                t = t + (vals[i] + " - ");
            }
            System.out.println(t.substring(0, t.length() - 2));
        }
        else {
            System.out.println("Could not create name \"" + word + "\" out of elements.");
        }
        return true;
    }
}
