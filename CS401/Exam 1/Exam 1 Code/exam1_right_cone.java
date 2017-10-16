/*********************************************
  Exam 1 Version B
  By: John Kelly Jr

  Deteremine the variable the user woud like to solve for of a right circular cone.
*********************************************/

import java.util.Scanner;
import java.io.PrintStream;
import java.text.*;

public class Exam1_Right_Cone{

  // main logic
  public static void main(String[] args){
    double height = 0;
    double radius = 0;
    double volume = 0;

    String variable = getVariable();
    //System.out.println("solving for: "+variable);
    /*double radius = getRadius();
    //System.out.println("radius: "+ radius);
    double height = getHeight();
    //System.out.println("height: "+ height);
    double volume = getVolume();
    //System.out.println("volume: "+ volume);*/

    // determine height
    if(variable.equals("height")){
      radius = getRadius();
      volume = getVolume();
      height = determineHeight(radius, volume);
      System.out.format("The height of the right circular cone is: %.5f%n \n", height);
    }

    // determines radius
    if(variable.equals("radius")){
      height = getHeight();
      volume = getVolume();
      radius = determineRadius(height, volume);
      System.out.format("The radius of the right circular cone is: %.5f%n \n", radius);
    }

    // determines volume
    if(variable.equals("volume")){
      height = getHeight();
      radius = getRadius();
      volume = determineVolume(height, radius);
      System.out.format("The volume of the right cicular cone is: %.5f%n \n", volume);
    }

  }

  // gets the desired variable to solve for and returns it
  public static String getVariable(){
    Scanner kb = new Scanner(System.in);
    String variable = null;

    System.out.println("Enter the variable you would like to solve for: ");
    variable = kb.nextLine().toLowerCase();

    // check to make sure it is a valid variable name
    if( !variable.equals("height") && !variable.equals("volume") && !variable.equals("radius")){
      System.out.println("Please enter in a valid variable (i.e. height, volume, or radius)...");
      getVariable();
    }

    return variable;
  }

  // gets and returns the radius
  public static double getRadius(){
    Scanner kb = new Scanner(System.in);
    double radius = 0;

    System.out.println("Please enter in the radius: ");
    radius = kb.nextDouble();

    // if they don't enter in a number
    if(radius == 0){
      System.out.println("Please enter in a number: ");
      radius = kb.nextDouble();
      return radius;
    }

    // if negative number
    if(radius < 0){
      System.out.println("The radius must be greater than zero. \nPlease enter the radius: ");
      radius = kb.nextDouble();
      return radius;
    }

    return radius;
  }

  // gets and returns the height
  public static double getHeight(){
    Scanner kb = new Scanner(System.in);
    double height = 0.0;

    System.out.println("Please enter in the height: ");
    height = kb.nextDouble();

    // if they don't enter in a number
    if(height == 0){
      System.out.println("Please enter in a number...");
      height = kb.nextDouble();
      return height;
    }

    // if negative number
    if(height < 0){
      System.out.println("The height must be greater than zero. \nPlease enter n the height: ");
      height = kb.nextDouble();
      return height;
    }

    return height;
  }

  // gets and returns the volume
  public static double getVolume(){
    Scanner kb = new Scanner(System.in);
    double volume = 0;

    System.out.println("Please enter in the volume: ");
    volume = kb.nextDouble();

    // if they don't enter in a number
    if(volume == 0){
      System.out.println("Please enter in a number: ");
      volume = kb.nextDouble();
      return volume;
    }

    // if negative number
    if(volume < 0){
      System.out.println("The volume must be greater than zero. \nPlease enter in the volume: ");
      volume = kb.nextDouble();
      return volume;
    }

    return volume;
  }

  // calculates the height
  public static double determineHeight(double radius, double volume){
    double height = 3 * ( volume / ( (Math.PI) * Math.pow(radius, 2) ));
    return height;
  }

  // calculates teh radius
  public static double determineRadius(double height, double volume){
    double radius = Math.sqrt( 3*( volume / ( (Math.PI) * height) ));
    return radius;

  }

  // calculates the volume
  public static double determineVolume(double height, double radius){
    double volume = (( (Math.PI) * Math.pow(radius, 2) * height) / 3);
    return volume;
  }

}
