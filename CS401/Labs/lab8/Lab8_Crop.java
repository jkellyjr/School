/*************************************************
  Lab 8
  By: John Kelly Jr (3/30/15)

	Allows control of the crop's name, yield, and price.
************************************************/

public class Lab8_Crop {
	private String crop;
	private double yield;
	private double price;

	// default
	public Crop(){
		crop= "Wheat";
		yield = 8000;
		price = 0.003;
	}

	// copy constructor
	public Crop(Crop c){
		crop=c.getName();
		yield=c.getYield();
		price=c.getPrice();
	}

	// assignment
	public Crop(String str, double y, double p){
		setName(str);
		setYield(y);
		setPrice(p);
	}

	// set name
	public void setName(String str){
		crop=str;
	}

	// set yield
	public void setYield(double y)throws IllegalArgumentException{

		if(y<0){
			IllegalArgumentException iae = new IllegalArgumentException
					("The yield must be greater than 0.");
			throw iae;
		}
		yield=y;
	}

	// set price
	public void setPrice(double p){
		if (p<0){
			IllegalArgumentException iae = new IllegalArgumentException
					("The yield must be greater than 0.");
			throw iae;
		}
		price=p;
	}

	// return name
	public String getName(){
		return crop;
	}

	// return yield
	public double getYield(){
		return yield;
	}

	// return price
	public double getPrice(){
		return price;
	}


	// checks name of crop
	public boolean equals(Crop c){
		if(c.crop.equalsIgnoreCase(crop))
			return true;
		else
			return false;
	}

	// compares crop details
	public boolean equals(String crop, double yield, double price){
		if(this.crop.equalsIgnoreCase(crop) && this.yield==yield && this.price==price)
			return true;
		else
			return false;
	}

	// converts output to a string
	public String toString(){
		String stringObject= "\n\tName: "+crop+"\n\tYield: "+yield+"\n\tPrice: $"+price;
		return stringObject;
	}
}
