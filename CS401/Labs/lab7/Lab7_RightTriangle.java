/*************************************************
  Lab 7
  By: John Kelly Jr (3/18/15)

	This class contains methods for setting and getting the legs. It also caluates the hypotenuse,
	area, and perimter using the specified leg lengths.
************************************************/


import java.lang.Math;

public class Lab7_RightTriangle {
	private double legA=1;
	private double legB=1;


	// sets the length for leg a
	public void setLegA(double a)throws IllegalArgumentException{
		if(a<=0){
			IllegalArgumentException iae=new IllegalArgumentException
					("The length must be greater than 0.");
			throw iae;
		}
		legA=a;
	}

	// sets the length for leg b
	public void setLegB(double b)throws IllegalArgumentException{
		if(b<=0){
			IllegalArgumentException iae=new IllegalArgumentException
					("The length must be greater than 0.");
			throw iae;
		}
		legB=b;
	}

	// returns leg a
	public double getLegA(){
		return legA;
	}

	// returns leg b
	public double getLegB(){
		return legB;
	}

	// calculates the hypotenuse using the leg lengths provided
	public double getHypotenuse(double legA, double legB){
		double hypotenuse= Math.sqrt(Math.pow(legA,2)+Math.pow(legB,2));
		return hypotenuse;
	}

	// calculates the perimeter using the leg lengths provided
	public double getPerimeter(double hypotenuse, double legA, double legB){
		double perimeter=hypotenuse + legA + legB;
		return perimeter;
	}

	// calculates the area using teh leg lengths provided
	public double getArea(double legA, double legB){
		double area=(legA+legB)/2;
		return area;
	}

}
