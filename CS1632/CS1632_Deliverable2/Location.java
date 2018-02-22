//  Location Class for Individual Locations of CitySim9005
// By: John Kelly Jr


public class Location
{
    // Fields
    String name;
    String streetEW;
    String streetNS;
    Location nextEW;
    Location nextNS;

    // Constructors
    public Location(){}
    public Location(String name, String streetEW, String streetNS)
    {
        this.name = name;
        this.streetEW = streetEW;
        this.streetNS = streetNS;
        this.nextEW = null;
        this.nextNS = null;
    }

    // Set Next Locations
    public void setNextEW(Location ew)
    {
        nextEW = ew;
    }

    public void setNextNS(Location ns)
    {
        nextNS = ns;
    }
}
