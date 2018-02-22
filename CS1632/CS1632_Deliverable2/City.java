// Implementation of the City Map for CitySim9005
// By: John Kelly Jr

import java.util.Random;
import java.lang.Math;


public class City
{
    // Fields
    private Location hotel = new Location();
    private Location diner = new Location();
    private Location coffee = new Location();
    private Location library = new Location();
    private Location outsideCity = new Location();
    private Location current = new Location();


    // Constructor
    public City()
    {
        hotel = new Location("Hotel", "Fourth Ave", "Bill St");
        diner = new Location("Diner", "Fourth Ave", "Phil St");
        coffee = new Location("Coffee", "Fifth Ave", "Phil St");
        library = new Location("Library", "Fifth Ave", "Bill St");
        outsideCity = new Location("Outside City", null, null);

        hotel.setNextNS(library);
        hotel.setNextEW(diner);
        diner.setNextNS(coffee);
        diner.setNextEW(outsideCity);
        coffee.setNextNS(diner);
        coffee.setNextEW(library);
        library.setNextNS(hotel);
        library.setNextEW(outsideCity);
    }


    // Returns the current location
    public Location getCurrentLocation()
    {
        return current;
    }


    // Set the current location
    public boolean setCurrentLocation(Location loc)
    {
        if (loc.name == null) {
            return false;
        }

        current = loc;
        return true;
    }


    // Returns positive random number if a seed is provided
    public int getRandomNum(int seed)
    {
        Random rand = new Random((long) seed);
        int n = rand.nextInt();
        n = Math.abs(n);
        return n;
    }


    // Given a positive int, the function sets current as the start location and returns true
    public boolean setDriverStart(int randNum)
    {
        boolean res = true;

        if (randNum < 0) {
            return false;
        }

        int x = randNum % 4;

        if (x == 0) {
            res = setCurrentLocation(hotel);
        } else if (x == 1) {
            res = setCurrentLocation(diner);
        } else if (x == 2) {
            res = setCurrentLocation(coffee);
        } else {
            res = setCurrentLocation(library);
        }

        if (current.name == null) {
            return false;
        }
        return res;
    }


    // Given a positive int, the function returns the direction the driver moves
    public int getMoveDirection(int randNum)
    {
        // Ensure Mod != 0
        int x = randNum % 6;
        if (x == 0) {
            x+=2;
        }
        int y = x / 2;
        if (y == 1) {
            return 1;
        } else {
            return 2;
        }
    }


    // Updates current location to the new location
    // specified by the direction argument (1: NS, 2: EW)
    public boolean moveLocation(int direction)
    {
        if (direction == 1) {
            current = current.nextNS;
            return true;
        } else if (direction == 2) {
            current = current.nextEW;
            return true;
        }
        return false;
    }


    // Given the location arriving from, the function returns the street that was
    // used to travel to the new location
    public String getStreetName(Location loc, int direction)
    {
        if (direction == 1) {
            return loc.streetNS;
        } else if (direction == 2) {
            return loc.streetEW;
        } else {
            System.out.println("[moveDriver] Something went wrong.");
            return null;
        }
    }


    // Returns the city the driver is going to based on the street they are leaving on, which is
    // passed in as an argument
    public String getOutsideCity(String street)
    {
        if (street.equals("Fourth Ave")) {
            return "Philadelphia";
        } else if (street.equals("Fifth Ave")){
            return "Cleveland";
        } else {
            return null;
        }
    }
}
