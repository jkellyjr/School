// CS1632 Deliverable 4 Element Test Class
// By: John Kelly

import static org.junit.Assert.*;
import org.junit.*;
import java.io.*;
import java.util.*;

public class ElementTest
{
    // Reusable Element
    Element _e;

    // Create new Element instances before every test
    @Before
    public void setup()
    {
        _e = new Element();
    }

    // Ensure Element instance is not null
    @Test
    public void elementNotNul()
    {
        assertNotNull(_e);
    }

    // Ensure an error message is displayed if more than one files are passed in
    @Test
    public void testMoreArgs()
    {
        String[] str = new String[2];
        BufferedReader reader = _e.handleInputFile(str);
        assertNull(reader);
    }

    // Esnure an error message is displayed if a file is not given as an argument
    @Test
    public void testFewArgs()
    {
        String[] str = new String[0];
        BufferedReader reader = _e.handleInputFile(str);
        assertNull(reader);
    }

    // Ensure and error mesage is displayed if a null file object is passed to .openFile
    @Test
    public void openFileNull()
    {
        BufferedReader reader = _e.openFile("hello", null);
        assertNull(reader);
    }

    // Ensure an error message is displayed the file specified is not in the root directory
    @Test
    public void openFNF()
    {
        BufferedReader reader = _e.openFile("hello", new File("bob.txt"));
        assertNull(reader);
    }

    // Ensure a valid BufferedReader object is returned when .openFile is passed valid arguments
    @Test
    public void openFileHappy()
    {
        BufferedReader reader = _e.openFile("friends", new File("elements.txt"));
        assertNotNull(reader);
    }

    // Ensure .buildDictionary returns a valid hashmap given a valid String filename
    @Test
    public void buildDictHappy()
    {
        HashMap<String, String> hm = _e.buildDictionary("elements.txt");
        assertNotNull(hm);
    }

    // Ensure .buildDictionary returns an error message when an invalid filename is passed
    @Test
    public void buildDictNull()
    {
        HashMap<String, String> hm = _e.buildDictionary(null);
        assertNull(hm);
    }

    // Ensure .processInputFile returns false given an invalid dictionary hash map
    @Test
    public void processInputFileNullDict()
    {
        File file = new File("elements.txt");
        boolean res = true;
        try {
            res = _e.processInputFile(new BufferedReader(new FileReader(file)), null);
        } catch (FileNotFoundException e) {
            fail();
        }
        assertFalse(res);
    }

    // Ensure .processInputFile returns false given an invalid BufferedReader object
    @Test
    public void processInputFileNullReader()
    {
        boolean res = _e.processInputFile(null, new HashMap<String, String>());
        assertFalse(res);
    }

    // Ensure .processInputFile return true given all valid argument
    @Test
    public void ProcessInputFileHappy()
    {
        File file = new File("elements.txt");
        boolean res = true;
        try {
            res = _e.processInputFile(new BufferedReader(new FileReader(file)), new HashMap<String, String>());
        } catch (FileNotFoundException e) {
            fail();
        }
        assertTrue(res);
    }

    // Ensure .processWord returns false given an invalid dictorary hash map
    @Test
    public void processWordNull()
    {
        boolean res = _e.processWord("hello", null);
        assertFalse(res);
    }

    // Ensure .processWord returns true given valid word and dictionary hashmap
    @Test
    public void processWordHappy()
    {
        boolean res = _e.processWord("hello", new HashMap<String, String>());
        assertTrue(res);
    }
}
