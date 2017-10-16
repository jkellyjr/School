/**John Kelly Jr
 * Test version: B
 * Program: Surface area of a cylinder
 * 
 * Scanner to take in the two variables (height and radius), do-while loops, while loops and
 * try/catch statements were used to validate the data. Then using the Math class methods,
 * the proper calculations were performed, and the surface area was displayed. 
 */
import java.util.InputMismatchException;
import java.util.Scanner;
public class Surface_Area {

	public static void main(String[] args) {
		Scanner kb=new Scanner(System.in);
		double radius=0.0;
		double height=0.0;
		boolean error;
		do{//data validation
			error=false;
			try{
				System.out.println("Enter the radius:");
				radius=kb.nextDouble();
				while(radius<=0){//number validation
					System.out.println("The number must be positive.");
					System.out.println("Enter the radius:");
					radius=kb.nextDouble();
				}
			}catch(InputMismatchException ime){
				System.out.println("The input must be a number.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		do{
			error=false;
			try{
				System.out.println("Enter the height:");
				height=kb.nextDouble();
				while(height<=0){
					System.out.println("The number must be positive.");
					System.out.println("Enter the height:");
					height=kb.nextDouble();
				}
			}catch(InputMismatchException ime){
				System.out.println("The input must be a number.");
				error=true;
				kb.nextLine();
			}
		}while(error);
		
		//using math methods (pi and power)
		double sa= (2*Math.PI*Math.pow(radius,2))+(2*Math.PI* radius*height);
		System.out.println("The surface area is: "+sa);
		
		kb.close();
	}

}
