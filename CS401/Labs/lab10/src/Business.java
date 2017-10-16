/**
 * Business software class extends the software class
 * @author johnkellyjr
 *
 */
public class Business extends Software{
	private String kind;
	
	/**
	 *No args constructor
	 */
	public Business(){
		setKind("");
	}
	
	/**
	 * Constructor: sets up the business 
	 * @param name from software
	 * @param price from software
	 * @param quantity from software
	 * @param company from software
	 */
	public Business(String name, double price, int quantity, SoftwareHouse company){
        super(name, price, quantity, company);
        setKind(kind);
    }
	
	/**
	 * Sets the kind of software
	 * @param kind of software
	 */
	public void setKind(String kind){
		this.kind=kind;
	}
	
	/**
	 * Gets the kind of software
	 * @return the kind of software
	 */
	public String getKind(){
		return kind;
	}
}
