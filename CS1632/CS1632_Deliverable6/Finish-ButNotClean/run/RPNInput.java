import java.io.File;
import java.io.FileNotFoundException;

import java.net.URL;

import java.util.Scanner;

/**
 * This file contains code relevent to Requirements 4, 10, 26, 28, 30, 31, 32,
 * and 35.
 */
public class RPNInput {
  String[] filenames;
  int nextFile;
  Scanner input;
  int lineNumber;
  boolean isFileMode;

  /**
   * Not much to see here. We have to set up a lot of state.
   * The one thing is that we assign input to null if we can't find the file
   * we've been passed. As mentioned in main, this is to help out with
   * requirement 22.
   *
   * Track nextFile instead of currFile to make boundary checking easier.
   *
   * @param filenames the files to read from. If the array is empty, we are
   * in REPL mode.
   */
  public RPNInput(String[] filenames) {
    if (filenames.length == 0) {
      this.filenames = null;
      this.nextFile = 0;
      this.input = new Scanner(System.in);
      this.lineNumber = 0;
      this.isFileMode = false;
    } else {
      this.filenames = filenames;
      this.nextFile = 1;
      try {
        File infile = getFile(filenames[0]);
        this.input = new Scanner(infile);
      } catch (Exception e) {
        this.input = null;
      }
      this.lineNumber = 0;
      this.isFileMode = true;
    }
  }

  /**
   * Reads in lines from the appropriate input source (File : a file, REPL :
   * command line).
   *
   * This code contributes to fulfilling Requirements 28 and 35.
   *
   * @return a line of input that is upper case and has no leading whitespace.
   */
  public String nextLine() throws QuitException, FileNotFoundException,
                                  InterruptedException {
    if (!isFileMode) {
      System.out.print(">");
      System.out.flush();
      Thread.sleep(10);
    } else if (endOfFile()) {
      moveToNextFile();
    }

    /**
     * If you reach this line without encoutnering an exception and without
     * any more input, you have reached the end of the last file and can
     * therefore normally terminate at this point.
     */
    if (!input.hasNextLine()) {
      throw new QuitException();
    }

    /**
     * I think this is pretty elegant. Recursively look for input until you get
     * a non-empty line.
     */
    String line = readLine();
    if (line.equals("")) {
      return nextLine();
    }

    lineNumber++; //Increment after recursion to count only non-empty lines
    return line;
  }

  /**
   * Since line number only goes up and only does so when you read new lines,
   * this is the natural place to store it.
   *
   * This code contributes to fulfilling Requirements 26, 32, and 35.4, 10,
   */
  public int getLineNumber() {
    return lineNumber;
  }

  /**
   * Manually converting case every time is annoying. So we just "cast" as we
   * read in.
   *
   * This code contributes to fulfilling Requirements 4 and 10.
   *
   * @return a line from the appropriate source, stripped of leading whitespace
   * and in all caps.
   */
  private String readLine() {
    String line = input.nextLine();
    line = line.toUpperCase();
    line = line.replaceFirst("^" + RPNConstants.WHITESPACE_REGEX, "");
    return line;
  }

  /**
   * Check to see if we are at the end of the current file we're reading from.
   * This allows us to smoothly switch to the next file when one finishes.
   *
   * Throws a FileNotFoundException if there is no file we're reading from.
   *
   * This code contributes to Requirement 30.
   *
   * @return true if in file mode and reading from a file without input. False
   * otherwise.
   */
  private boolean endOfFile() throws FileNotFoundException {
    if (input == null) {
      throw new FileNotFoundException();
    }

    if (isFileMode) {
      return !input.hasNextLine();
    } else {
      return false;
    }
  }

  /**
   * Attempts to move to the next file, provided it's appropriate to do so.
   *
   * This code contributes to Requirement 30.
   */
  private void moveToNextFile() throws FileNotFoundException {
    if ((!endOfFile())
        || nextFile >= filenames.length
        || !isFileMode) {
      return;
    }

    while (!input.hasNextLine()
           && nextFile < filenames.length) {
      File infile = getFile(filenames[nextFile++]);
      input = new Scanner(infile);
    }
  }

  /**
   * In the interests of full disclosure, my understanding of this is pretty
   * much "it is black magic". I think what is going on is that it gets the
   * path to the file name you want, and then loads it, but I really couldn't
   * say one way or the other.
   */
  private File getFile(String filename) throws FileNotFoundException {
    URL filepath =
      ClassLoader.getSystemClassLoader().getResource(filename);
    return new File(filepath.getFile());
  }
}
