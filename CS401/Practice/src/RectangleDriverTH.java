public class RectangleDriverTH {
    public static void main(String[] args) {
        RectangleTH rec = new RectangleTH(5, 10);
        
        System.out.println("rec's data is:");
        System.out.println("\tLength = " + rec.getLength());
        System.out.println("\tWidth = " + rec.getWidth());
        System.out.println("\tArea = " + rec.getArea());
        System.out.println("Rec's data is: "+rec);
        
        //making a copy of rec:
        RectangleTH r2 = new RectangleTH(rec);
        //r2.setLength(rec.getLength());
        //r2.setWidth(rec.getWidth());
        
        System.out.println("r2's data is:");
        System.out.println("\tLength = " + r2.getLength());
        System.out.println("\tWidth = " + r2.getWidth());
        System.out.println("\tArea = " + r2.getArea());
        
        if (r2.equals(rec)) {
            System.out.println("rec and r2 are equal");
        }
        else {
            System.out.println("rec and r2 are not equal");
        }
        
        rec.setWidth(5);
        
        System.out.println("rec's data is:");
        System.out.println("\tLength = " + rec.getLength());
        System.out.println("\tWidth = " + rec.getWidth());
        System.out.println("\tArea = " + rec.getArea());
        
        System.out.println("r2's data is:");
        System.out.println("\tLength = " + r2.getLength());
        System.out.println("\tWidth = " + r2.getWidth());
        System.out.println("\tArea = " + r2.getArea());
        
        RectangleTH r3 = new RectangleTH();
        System.out.println("r3's data is:");
        System.out.println("\tLength = " + r3.getLength());
        System.out.println("\tWidth = " + r3.getWidth());
        System.out.println("\tArea = " + r3.getArea());
        
    }
}