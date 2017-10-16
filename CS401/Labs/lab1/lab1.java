/***************************************
  Lab 1
  By: John Kelly Jr (1/8/15)

***************************************/
public class lab1{

    public static void main(String[] args){
        String name = "John Kelly Jr";
        double sideA = 9;
        double sideB = 4;
        double sideC = 10;

        double semiperimeter1 = 0.5 * (sideA + sideB + sideC);
        System.out.println("The semiperimeter of the triangle is "+semiperimeter1+".");

        double semiperimeter2 = (sideA + sideB + sideC) / 2;
        System.out.println("The semiperimeter of the triangle is "+semiperimeter2+".");

        System.out.println(name+", the program is now ending.");
    }
}
