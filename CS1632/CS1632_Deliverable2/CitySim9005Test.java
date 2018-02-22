// Method for testing CitySim9005 Class
// By: John Kelly Jr

import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.*;


public class CitySim9005Test
{
    // Re-usable CitySim9005 instance
    CitySim9005 _cs;


    // Create a new CitySim9005 instance before every test
    @Before
    public void setup()
    {
        _cs = new CitySim9005();
    }


    // Assert a new CitySim9005 instance is not null
    @Test
    public void cityNotNull()
    {
        assertNotNull(_cs);
    }


    // Ensure -1 is returned if no arguments are passed into .validateArgs
    @Test
    public void validateArgsNone()
    {
        String[] str = new String[1];
        int x = _cs.validateArgs(str);
        assertEquals(-1, x);
    }


    // Ensure -1 is returned if two int arguments (represented as strings) are passed into .validateArgs
    @Test
    public void validateArgsTwo()
    {
        String[] str = {"2", "3"};
        int x = _cs.validateArgs(str);
        assertEquals(-1, x);
    }


    // Ensure -1 is returned if a string is passed into .validateArgs
    @Test
    public void validateArgsString()
    {
        String[] str = {"hi"};
        int x = _cs.validateArgs(str);
        assertEquals(-1, x);
    }


    // Ensure the an int is returned when one int (represented as a string) is passed into .validateArgs
    @Test
    public void validateArgsTest()
    {
        String[] str = {"3"};
        int x = _cs.validateArgs(str);
        assertEquals(3, x);
    }


    // Ensure that the .run function returns true on valid values passed as arguments
    @Test
    public void runTest()
    {
        City c = new City();
        boolean res = _cs.run(c, 5);
        assertTrue(res);
    }


    // Ensure that if the .setupStart has not been called before the .drive
    // false will be returned
    @Test
    public void driverNoSetup()
    {
        City c = Mockito.mock(City.class);
        boolean res = _cs.driver(c, 5, 1);
        assertFalse(res);
    }


    // Ensure .driver returns false if City's getCurrentLocation returns a null value
    @Test
    public void driverNullLoc()
    {
        City c = Mockito.mock(City.class);
        Mockito.when(c.getCurrentLocation()).thenReturn(null);
        boolean res = _cs.driver(c, 1, 1);
        assertFalse(res);
    }


    // Ensure .driver returns false when City's .moveLocation returns false
    @Test
    public void driverMoveFalse()
    {
        City c = Mockito.mock(City.class);
        Mockito.when(c.moveLocation(1)).thenReturn(false);
        City ct = new City();
        _cs.setupStart(ct, 1, 1);
        boolean res = _cs.driver(c, 5, 1);
        assertFalse(res);
    }


    // Ensure .setupStart returns true, given a valid City object, seed as an int, and a positive int representing the offset
    @Test
    public void setupStartTest()
    {
        City c = new City();
        boolean res = _cs.setupStart(c, 1, 1);
        assertEquals(res, true);
    }


    // Ensure .setupStart returns false if City's .setDriverStart returns false
    @Test
    public void setupStartFalse()
    {
        City c = Mockito.mock(City.class);
        Mockito.when(c.setDriverStart(1)).thenReturn(false);
        boolean res = _cs.setupStart(c, 1, 1);
        assertFalse(res);
    }


    //Ensure .formatOutput returns true, given an int representing the driver, valid two valid Location objects,  and a valid String
    @Test
    public void formatOutputTest()
    {
        Location loc1 = new Location("ricksville", "wubba dub", "dub");
        Location loc2 = new Location("morty town", "oh no", "oh jeez");
        boolean res = _cs.formatOutput(1, loc1, loc2, "flavortown");
        assertTrue(res);
    }


    // Ensure .formatOutput returns false if one of the Location instances is null
    @Test
    public void formatOutputNull()
    {
        Location loc1 = new Location("ricksville", "wubba dub", "dub");
        Location loc2 = Mockito.mock(Location.class);
        boolean res = _cs.formatOutput(1, loc1, loc2, "flavortown");
        assertFalse(res);
    }
}
