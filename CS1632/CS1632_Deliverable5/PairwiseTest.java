/* CS1632 Deliverable 5
 * By: John  Kelly
 *
 * The PairWiseTester Class is the tester class for PairWise.
 */

import static org.junit.Assert.*;
import org.junit.*;
import java.util.ArrayList;

public class PairwiseTest
{
    Pairwise _p;

    @Before
    public void setup()
    {
        _p = new Pairwise();
    }

    // Ensure Pairwise object is not null
    @Test
    public void pwNotNull()
    {
        assertNotNull(_p);
    }

    // Ensure .validateArgs only accets two arguments
    @Test
    public void tooManyArgs()
    {
        String[] str = {"hello"};
        boolean res = _p.validateArgs(str);
        assertFalse(res);
    }

    // Ensure the .validateArgs accepts two arguments
    @Test
    public void happyArgs()
    {
        String[] str = {"hello", "world"};
        boolean res = _p.validateArgs(str);
        assertTrue(res);
    }

    // Ensure the .validateParameters truncates arguments longer than 10 characters
    @Test
    public void longParam()
    {
        String[] str = {"hello", "whatsupmygoodfriend"};
        String[] res = _p.validateParameters(str);
        assertTrue(res[1].equals("whatsupmyg"));
    }

    // Ensure .validateParameters does not modify strings shorter than 10 character
    @Test
    public void happyParam()
    {
        String[] str = {"hello", "friend"};
        String[] res = _p.validateParameters(str);
        assertTrue(res[0].equals(str[0]) && res[1].equals(str[1]));
    }

    // Ensure .buildTruthTable returns null when an invalid column number is provided
    @Test
    public void negativeColumnTT()
    {
        int[][] tt = _p.buildTruthTable(-5);
        assertNull(tt);
    }

    // Ensure .buildTruthTable returns a valid table given a valid argument
    @Test
    public void happyTT()
    {
        int[][] tt = _p.buildTruthTable(2);
        int[][] t = {{0,0}, {1,0},  {0,1}, {1,1}};
        assertEquals(tt, t);
    }

    // Ensure .showResults returns false if given invalid parameter
    @Test
    public void nullParamsDisplay()
    {
        boolean res = _p.showResults(null, new int[1][1]);
        assertFalse(res);
    }

    // Ensure .showResults returns true if given valid parameters
    @Test
    public void happyDisplay()
    {
        boolean res = _p.showResults(new String[1], new int[1][1]);
        assertTrue(res);
    }

    // Ensure .getTWay returns null given invalid arguments
    // @Test
    // public void nullTWay()
    // {
    //     ArrayList<String> res = _p.getTWay(null, new String[0], new ArrayList<String>(), "", 0, 0, 0, 0);
    //     assertNull(res);
    // }
    //
    // // Ensure .getTWay does not return null given valif arguments
    // @Test
    // public void happyTWay()
    // {
    //     ArrayList<String> res = _p.getTWay(new String[0], new String[0], new ArrayList<String>(), "", 0, 0, 0, 0);
    //     assertNotNull(res);
    // }
}
