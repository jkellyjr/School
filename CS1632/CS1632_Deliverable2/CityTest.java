// Method for Unit Testing the City class
// By: John Kelly

import static org.junit.Assert.*;
import org.junit.*;
import org.mockito.*;
import java.util.Random;
import java.lang.Math;


public class CityTest
{
    // Re-usable City Reference
    City _city;


    // Create a new City instance before every test
    @Before
    public void setup()
    {
        _city = new City();
    }


    // Assert a new City instance is not null
    @Test
    public void cityNotNull()
    {
        assertNotNull(_city);
    }


    // Ensure that .getCurrentLocation returns the correct location given a valid Location has been set
    @Test
    public void getCurrentLocationTest()
    {
        Location loc = new Location("doggo", "fluffy", "squishy");
        boolean set = _city.setCurrentLocation(loc);
        assertTrue(set);
        Location res = _city.getCurrentLocation();
        assertEquals(loc, res);
    }


    // Ensure .setCurrentLocation returns false if a null location is passed in
    @Test
    public void setCurrentLocationNull()
    {
        Location loc = Mockito.mock(Location.class);
        boolean res = _city.setCurrentLocation(loc);
        assertFalse(res);
    }


    // Ensure that two seed values produce the same value by .getRandomNum
    @Test
    public void randomSeedNumTest()
    {
        int x = _city.getRandomNum(5);
        int y = _city.getRandomNum(5);
        assertEquals(x, y);
    }


    // Ensure .setDriverStart returns true,  3 is passed into the method
    @Test
    public void setDriverStartTest()
    {
        boolean res = _city.setDriverStart(3);
        assertTrue(res);
    }


    // Ensure that the movement direction will be 2 (i.e. EW) if a 5 is passed into the
    @Test
    public void getMoveDirectionTest()
    {
        int x = _city.getMoveDirection(5);
        assertEquals(x, 2);
    }


    // Ensure that int returned will not get caught in an infinite loop of % 0
    @Test
    public void getMoveDirectionZero()
    {
        int x = _city.getMoveDirection(0);
        int y = _city.getMoveDirection(6);

        assertEquals(1, x);
        assertEquals(1, y);
    }


    // Ensure .moveLocation returns true if the int passed in is 1 or 2 and false if anything else
    @Test
    public void moveLocationTest()
    {
        boolean res1 = _city.moveLocation(1);
        boolean res2 = _city.moveLocation(3);
        assertTrue(res1);
        assertFalse(res2);
    }


    // Ensure the .getStreetName function returns the correct street based off the location and direction
    // provided as arguments
    @Test
    public void getStreetNameTest()
    {
        Location test = new Location("Guy Fieri", "fun st", "flavor town");
        String str = _city.getStreetName(test, 1);
        assertEquals(str, "flavor town");
    }


    // Ensure that if a null Location is passed into the .getStreetName function, a null String
    // is returned
    @Test
    public void getStreetNameNull()
    {
        Location loc = Mockito.mock(Location.class);
        String str = _city.getStreetName(loc, 1);
        assertNull(str);
    }


    // Ensure that .getOutsideCity returns the correct city the driver is heading towards, given the street
    // the driver is leaving on
    @Test
    public void getOutsideCityTest()
    {
        String str = _city.getOutsideCity("Fourth Ave");
        assertEquals(str, "Philadelphia");
    }


    // Ensure that if an invalid street is pased into .getOutsideCity, null will be returned
    @Test
    public void getOutsideCityInvalid()
    {
        String str = _city.getOutsideCity("21 jump st");
        assertNull(str);
    }
}
