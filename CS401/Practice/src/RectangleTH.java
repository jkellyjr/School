/**
 * Represents a rectangle by storing its length and width.
 * @author Michael Lipschultz
 */
public class RectangleTH {
    private double length;
    private double width;
    
    /**
     * Constructor to assign values to each field (length and width).
     * @param len Length of rectangle.
     * @param wid Width of rectangle.
     */
    public RectangleTH(double len, double wid) {
        //option 1:
        /*
        if (len <= 0 || wid <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("one or more of your arguments was not positive");
            throw iae;
        }
        length = len;
        width = wid;
        */
        
        //option 2:
        setLength(len);
        setWidth(wid);
    }
    
    /**
     * Copy constructor to make a duplicate of a rectangle object.
     * @param rectangle The rectangle to duplicate.
     */
    public RectangleTH(RectangleTH r) {
        //option 1:
        length = r.length;
        width = r.width;
        /*
        //option 2:
        length = r.getLength();
        width = r.getWidth();
        
        //option 3:
        setLength(r.getLength());
        setWidth(r.getWidth());
        */
    }
    
    /**
     * No-args constructor, which just initializes the length and width
     * to default values (both 1).
     */
    public RectangleTH() {
        length = 1;
        width = 1;
    }
    
    
    /**
     * Set length of rectangle and update area.
     * @param len New value for length.
     * @throws IllegalArgumentException Thrown when length is 0 or negative.
     */
    public void setLength(double len) throws IllegalArgumentException {
        if (len <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("length must be positive");
            throw iae;
        }
        length = len;
    }
    
    /**
     * Gets the length of the rectangle.
     * @return Length of the rectangle.
     */
    public double getLength() {
        return length;
    }
    
    /**
     * Set width of rectangle and update area.
     * @param wid New value for width.
     * @throws IllegalArgumentException Thrown when width is 0 or negative.
     */
    public void setWidth(double wid) throws IllegalArgumentException {
        if (wid <= 0) {
            IllegalArgumentException iae = new IllegalArgumentException("width must be positive");
            throw iae;
        }
        width = wid;
    }
    
    /**
     * Gets the width of the rectangle.
     * @return Width of the rectangle.
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Returns the area of the rectangle.
     * @return The area of the rectangle
     */
    public double getArea() {
        double area = length * width;
        return area;
    }
    
    
    public String toString() {
        String returnval = "width = "+width+", length = "+length;
        return returnval;
    }
    
    
    /**
     * Compares two rectangles for equality by comparing the lengths and widths.  Rotation is allowed, so the length of one can be the width of the other.
     * @return true if the rectangles are equal.
     */
    public boolean equals(RectangleTH r) {
        /*
        //option 1: same dimensions in same order
        if (r.width == width && r.length == length) return true;
        return false;
        */
        //option 2: same dimensions, but rotation won't affect
        //equality
        if (r.width == width && r.length == length) return true;
        else if (r.width == length && r.length == width) return true;
        else return false;
        /*
        //option 3: equal areas?
        if (getArea() == r.getArea()) return true;
        else return false;
        */
    }
    
    public boolean equals(double width, double length) {
        if (this.width == width && this.length == length) return true;
        else if (this.width == length && this.length == width) return true;
        else return false;
    }
    
}