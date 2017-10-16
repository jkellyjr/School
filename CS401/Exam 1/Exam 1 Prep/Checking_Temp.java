//Checking and adjusting the Temperature
import java.util.Scanner;
public class Checking_Temp {

	public static void main(String[] args) {
		Scanner kb=new Scanner(System.in);
		
		System.out.println("Enter the substance's temperature (in degrees C): ");
		double temp=kb.nextDouble();
		
		while(temp>=102.5){
			System.out.println("The temperature is too high. Turn the thermostat down"
					+ " \nand wait 5 minutes. Then take the temperature again "
					+ "\nand enter it here: ");
			temp=kb.nextDouble();
		}
		
		System.out.println("The temperature is acceptable. Check again in 15 minutes.");

	}

}
