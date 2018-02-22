
import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.*;
import java.util.ArrayList;


public class RentACatTest
{
    RentACat _rc;

    // Setup instance of RentACat
    @Before
    public void setup()
    {
        _rc = new RentACat();
    }


    // Ensure that the base case is rented = false, by passing in a dummy object and verifying the output is false
    @Test
    public void returnCatTest()
    {
        Cat c = Mockito.mock(Cat.class);
        boolean res = _rc.returnCat(c);
        assertFalse(res);
    }


    // Ensure when .returnCat returns true, rentCat also returns true
    @Test
    public void rentCatReturnTrue()
    {
        Cat c = Mockito.mock(Cat.class);
        Mockito.when(c.getRented()).thenReturn(true);
        boolean res = _rc.rentCat(c);
        assertTrue(res);
    }


    // Ensure when .returnCat returns false, rentCat also returns false
    @Test
    public void rentCatReturnFalse()
    {
        Cat c = Mockito.mock(Cat.class);
        Mockito.when(c.getRented()).thenReturn(false);
        boolean res = _rc.rentCat(c);
        assertFalse(res);
    }


    // Ensure that .listCats does not return anything if there are only null Cat objects
    @Test
    public void listCatsNullTest()
    {
        Cat c = Mockito.mock(Cat.class);
        Cat k = Mockito.mock(Cat.class);
        ArrayList<Cat> cats = new ArrayList<Cat>();
        cats.add(c);
        cats.add(k);
        String res = _rc.listCats(cats);
        assertEquals(res, "");
    }


    // Ensure that if a null cat object is passed into .catAvailable, false is returned identifiying the cat is unavailable
    @Test
    public void catAvailableNullTest()
    {
        Cat c = Mockito.mock(Cat.class);
        ArrayList<Cat> cats = new ArrayList<Cat>();
        cats.add(c);
        boolean res = _rc.catAvailable(1, cats);
        assertFalse(res);
    }


    // Ensure that .getCat correctly checks against the .getId to return the correct cat
    @Test
    public void getCatIDTest()
    {
        Cat k = new Cat(5, "wiskers");
        ArrayList<Cat> cats = new ArrayList<Cat>();
        cats.add(k);
        Cat c = Mockito.mock(Cat.class);
        Mockito.when(c.getId()).thenReturn(5);
        Cat k2 = _rc.getCat(5, cats);

        assertEquals(k, k2);
    }


    // Ensure that .getCat returns null if  a null Cat object is contained withing the ArrayList
    @Test
    public void getCatNullObjectTest()
    {
        Cat c = Mockito.mock(Cat.class);
        ArrayList<Cat> cats = new ArrayList<Cat>();
        cats.add(new Cat(1, "wiskers"));
        cats.add(c);
        Cat k = _rc.getCat(2, cats);
        assertNull(k);
    }
}
