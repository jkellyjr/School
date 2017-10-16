/**Lab 10 Driver
 * @author johnkellyjr
 *
 */
public class Lab10 {
	public static void main(String[] args){
		
		Software sft = new Software();
		SoftwareHouse sfthse=new SoftwareHouse();
		Educational edu= new Educational();
		
		edu.setSubject("health");
		System.out.println(edu.getSubject());
		
		//edu.setEduLevel("kindergarten");
		//System.out.println(edu.getEduLevel());
		
		sft.setQuantity(5);
		System.out.println("sft q "+sft.getQuantity());
		
		sfthse.setZipcode("12345");
		System.out.println("sft house"+sfthse.getZipcode());
		
	}

}
