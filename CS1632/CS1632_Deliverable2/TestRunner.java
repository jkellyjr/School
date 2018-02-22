// Test Control for the CitySim9005 Class and City Class
// By: John Kelly

import java.util.ArrayList;
import org.junit.runner.*;
import org.junit.runner.notification.*;

public class TestRunner
{
    public static void main(String[] args)
    {
        ArrayList<Class> classesToTest = new ArrayList<Class>();
        boolean anyFailures = false;

        // Add Classes Under Test
        classesToTest.add(CityTest.class);
        classesToTest.add(CitySim9005Test.class);

        // Loop Through all JUnit Tests
        for (Class c: classesToTest) {
            Result res = JUnitCore.runClasses(c);

            // Print Out Failures
            for (Failure fail: res.getFailures()) {
                System.out.println(fail.toString());
            }

            // Set Failure Flag
            if (!res.wasSuccessful()) {
                anyFailures = true;
            }
        }

        // Print Results
        if (anyFailures) {
            System.out.println("\n\n\t\t At least one failure, stated above.");
        } else {
            System.out.println("\n\n\t\t All tests passed, off to flavortown.\n");
        }
    }
}
