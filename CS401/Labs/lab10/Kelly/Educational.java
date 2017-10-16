/**
 * Educational class that extends the Software class
 * @author johnkellyjr
 *
 */

public class Educational extends Software {
	private String subject;
	private String eduLevel;
	
	/**
	 * No args constructor
	 */
	public Educational(){
		setSubject("");
		setEduLevel("");
	}
	
	/**
	 * Constructor: sets up the educational
	 * @param name from software
	 * @param price from software
	 * @param quantity from software
	 * @param company from software
	 */
	public Educational(String name, double price, int quantity, SoftwareHouse company){
        super(name, price, quantity, company);
        setSubject(subject);
        setEduLevel(eduLevel);
    }
	
	/**
	 * Set the subject
	 * @param subject 
	 */
	public void setSubject(String subject){
		subject=subject.toLowerCase();
				
		if(!subject.equals("health")&&!subject.equals("literature")&&!subject.equals("math")
				&&!subject.equals("science")&&!subject.equals("social studies")){
			//throw new IllegalArgumentException("Your subject is not supported.");
		}
		this.subject=subject;	
	}
	
	/**
	 * Set the education level
	 * @param eduLevel education level
	 */
	public void setEduLevel(String eduLevel){
		eduLevel=eduLevel.toLowerCase();
		
		
		if(!eduLevel.equals("kindergarten")&&!eduLevel.equals("elementary")&&!eduLevel.equals("middle school")
				&&!eduLevel.equals("high school")&&!eduLevel.equals("college")){
			//throw new IllegalArgumentException("You education level is not supported.");
		}
		this.eduLevel=eduLevel;
	}
	
	/**
	 * Get the subject
	 * @return the subject
	 */
	public String getSubject(){
		return subject;
	}
	
	/**
	 * Get the education level
	 * @return
	 */
	public String getEduLevel(){
		return eduLevel;
	}
	
	public boolean equals(String eduLevel, String subject){
		if(this.eduLevel.equalsIgnoreCase(eduLevel))return true;
		else if(this.subject.equalsIgnoreCase(subject))return true;
		else return false;
	}
}
