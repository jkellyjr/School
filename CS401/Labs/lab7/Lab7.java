/*************************************************
  Lab 7
  By: John Kelly Jr (3/18/15)

	Controls the RightTriangle class
************************************************/

public class Lab7 {
	public static void main(String[]args){

		//creates the object of the RightTraingle class
		RightTriangle rt = new RightTriangle();

		//sets the leg lengths
		rt.setLegA(5);
		rt.setLegB(10);

		//assigning the values to variables
		double legA = rt.getLegA();
		double legB = rt.getLegB();

		System.out.println("Leg A is: "+legA);
		System.out.println("Leg B is: "+legB);

		double hypotenuse=rt.getHypotenuse(legA,legB);
		double area=rt.getArea(legA, legB);

		System.out.println("The hypotenuse is: "+ hypotenuse);
		System.out.println("The perimeter is: "+rt.getPerimeter(hypotenuse, legA, legB));
		System.out.println("The area of the triangle is: "+area);
	}
}
