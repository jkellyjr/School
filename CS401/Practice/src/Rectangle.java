/*Basic Class Practice
 * John Kelly Jr
 */
public class Rectangle {
	//Three private variables so other programmers cannot mess it up
	private double length;
	private double width;
	private double area;
	
	/**
	 * Set length rectangle
	 * @param len (New value for length.)
	 * @throws IllegalArgumentException (Thrown when length <=0)
	 */
	public void setLength(double len)throws IllegalArgumentException{
		//data validation
		if (len<=0){
			IllegalArgumentException iae= new 
					IllegalArgumentException("Length must be positive.");
			throw iae;
		}	
		length=len;
		//stops stale data
		area=width*length;
	}
}
