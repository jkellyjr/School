// CS1632: Software Quality Assurance
// By: John Kelly Jr

import java.lang.*;


public class CitySim9005
{
    // Main Logic
    public static void main(String[] args) {

        int seed = validateArgs(args);
        if (seed == -1) {
            System.out.println("Invalid argument. Exiting...");
            System.exit(0);
        }

        City city = new City();

        boolean goodRun = run(city, seed);
        if (!goodRun) {
            System.out.println("Invalid setup. Exiting...");
            System.exit(0);
        }
    }


    // Helper function to validate seed value
    public static int validateArgs(String[] args)
    {
        int seed = -1;

        if (args.length != 1) {
            return seed;
        }

        try {
            seed = Integer.parseInt(args[0]);

        } catch (IllegalArgumentException e) {
            return seed;
        }
        return seed;
    }


    // Controls driver set
    public static boolean run(City city, int seed)
    {
        if (seed < 0) {
            return false;
        }

        for (int i = 1; i <= 5; i++) {
            System.out.println("\n=======================================================\n");

            boolean okStart = setupStart(city, seed, i);
            if (!okStart) {
                System.exit(0);
            }

            boolean goodDriver = driver(city, seed + i, i);
            if (!goodDriver) {
                break;
            }
            System.out.println("\n=======================================================\n");
        }

        return true;
    }


    // Controls One Driver
    public static boolean driver(City city, int seed, int driver)
    {
        boolean outside = false;
        boolean noError = true;
        String street = null;
        int x = 1;

        while (!outside) {
            Location current = city.getCurrentLocation();
            int direction = city.getMoveDirection(seed + x);
            street = city.getStreetName(current, direction);
            noError = city.moveLocation(direction);
            Location newLoc = city.getCurrentLocation();

            if (newLoc == null || current == null) {
                return false;
            }

            if (newLoc.name.equals("Outside City")) {
                outside = true;
            }

            noError = formatOutput(driver, current, newLoc, street);
            x++;

            if (!noError) {
                return false;
            }
        }

        System.out.println("Driver " + driver + " has gone to " + city.getOutsideCity(street) + "!");
        return true;
    }


    // Set the starting location for each driver
    public static boolean setupStart(City city, int seed, int offset)
    {
        if (offset < 0) {
            return false;
        }

        int randStart = city.getRandomNum(seed + offset);
        boolean goodStart = city.setDriverStart(randStart);

        if (randStart < 0 || !goodStart) {
            return false;
        }

        return true;
    }


    // Format  Acceptable Output
    public static boolean formatOutput(int driver, Location currLoc, Location newLoc, String street)
    {
        if (currLoc.name == null || newLoc.name == null) {
            return false;
        }

        try {
            System.out.println("Driver " + driver + " heading from " + currLoc.name + " to " + newLoc.name + " via " + street + ".");
            return true;
        } catch (IllegalArgumentException e) {
            System.out.println("[formatOutput] Invalid argument.");
            return false;
        } catch (Exception e) {
            System.out.println("[formatOutput] Error.");
            return false;
        }
    }
}
