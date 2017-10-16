//Special Dates
import java.util.Scanner;
public class Special_Dates {
	public static void main(String[] args) {
		Scanner kb=new Scanner(System.in);
		
		System.out.println("Enter the numeric month: ");
		int month =kb.nextInt();
		
		while(month<1 || month>12 ){
			System.out.println("That is not a valid numberic month.");
			System.out.println("Enter the numeric month: ");
			month =kb.nextInt();
		}

		System.out.println("Enter the numeric day: ");
		int day=kb.nextInt();
		
		while(day<1 || day> 31){
			System.out.println("That is not a valid numeric day");
			System.out.println("Enter the numeric day");
			day=kb.nextInt();
		}
		
		System.out.println("Enter the year: ");
		int year = kb.nextInt();
		
		
	}

}
