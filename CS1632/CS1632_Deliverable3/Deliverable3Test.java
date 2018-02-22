// CS1632 Deliverable 3: Selenium Web Testing
// By: John Kelly and Nathan Davidson

import static org.junit.Assert.*;
import org.junit.*;
import java.util.logging.*;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.htmlunit.HtmlUnitDriver;
import java.util.List;


public class Deliverable3Test
{
    static WebDriver driver;

    @BeforeClass
    public static void setUpDriver()
    {
    	java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(Level.OFF);
    	System.setProperty("org.apache.commons.logging.Log", "org.apache.commons.logging.impl.NoOpLog");
    	driver = new HtmlUnitDriver();
    }

    @Before
    public void setup() throws Exception
    {
        driver.get("https://cs1632ex.herokuapp.com/");
    }

    //Ensure the homepage contains the text "Used for CS1632 Software Quality Assurance, taught by Bill Laboon"
    @Test
    public void testHomepageUsageText()
    {
        try {
            WebElement e = driver.findElement(By.className("row"));
            String elementText = e.getText();
            assertTrue(elementText.contains("Used for CS1632 Software Quality Assurance, taught by Bill Laboon"));
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    //Ensure the homepage contains the text "Welcome, friend, to a land of pure calculation"
    @Test
    public void testHomepageWelcomeText()
    {
        try {
            WebElement e = driver.findElement(By.className("lead"));
            String elementText = e.getText();
            assertTrue(elementText.contains("Welcome, friend,") && elementText.contains("to a land of pure calculation"));
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    // Ensure every page has five links
    @Test
    public void testNumLinks()
    {
        String[] navLinks = {"CS1632 D3 Home", "Factorial", "Fibonacci", "Hello", "Cathedral Pics"};
        int numLinks = 0;

        for (int i = 0; i < navLinks.length; i++) {
            driver.findElement(By.linkText(navLinks[0])).click();
            List<WebElement> links = driver.findElements(By.tagName("a"));
            assertEquals(5, links.size());
        }
    }

    // Ensure every page has five links and the correct url from left to right
    @Test
    public void testLinkRoutes()
    {
        String[] links = {"CS1632 D3 Home", "Factorial", "Fibonacci", "Hello", "Cathedral Pics"};
        String[] routes = {"/", "/fact", "/fib", "/hello", "/cathy"};

        for (int i = 0; i < links.length && i < routes.length; i++) {
            driver.findElement(By.linkText(links[i])).click();
            String url = driver.getCurrentUrl();
            assertTrue(url.contains(routes[i]));
        }
    }

    // Ensure every page has the five links hand correct url right to left starting at cathy
    @Test
    public void testLinkRoutesReverse()
    {
        driver.get("https://cs1632ex.herokuapp.com/cathy");

        String[] links = {"Cathedral Pics", "Hello", "Fibonacci", "Factorial", "CS1632 D3 Home"};
        String[] routes = {"/cathy", "/hello", "/fib", "/fact", "/"};

        for (int i = 0; i < links.length && i < routes.length; i++) {
            driver.findElement(By.linkText(links[i])).click();
            String url = driver.getCurrentUrl();
            assertTrue(url.contains(routes[i]));
        }
    }

    // Ensure the factorial page prints the correct result given a valid number
    @Test
    public void testFactorialValid()
    {
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).sendKeys("7");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("5040"));
        //NKD: Would prefer for this to be calculated by some function, rather than a magic number
        //However, java.lang.Math doesn't have fibonacci or factorial functions AFAIK
    }

    //Test that 1 factorial returns the correct value
    //Edge case
    @Test
    public void testFactorialMin()
    {
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).sendKeys("1");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("1"));
        //NKD: Would prefer for this to be calculated by some function, rather than a magic number
        //However, java.lang.Math doesn't have fibonacci or factorial functions AFAIK
    }

    //NKD: Testing max for fact is iffy. Its ~9e157. Doing an affirmative test (e.g. like the above) seems loose.
    //Could test for not error, but that also doesn't seem great. Not sure.

    // Ensure that the factorial page returns the default value of 1 when passed an invalid integer argument: 0
    //Edge Case
    @Test
    public void testFactorialInvalidInteger()
    {
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).sendKeys("0");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("1"));
    }

    // Ensure that the factorial page returns the default value of 1 when a non-integer argument
    //Edge Case
    @Test
    public void testFactorialInvalidNonInteger()
    {
        driver.findElement(By.linkText("Factorial")).click();
        driver.findElement(By.name("value")).sendKeys("hello");
        try {
            WebElement submit = driver.findElement(By.cssSelector("input"));
            submit.sendKeys(Keys.RETURN);
            WebElement e = driver.findElement(By.className("jumbotron"));
            String s1 = e.getText();
            assertTrue(s1.contains("1"));
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    // Ensure the fibonacci returns the correct result for a value in range
    @Test
    public void testFibonacciValid()
    {
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.name("value")).sendKeys("7");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("13"));
    }

    // Ensure the fibonacci returns the correct result for the minimum (1)
    @Test
    public void testFibonacciMin()
    {
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.name("value")).sendKeys("1");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("1"));
    }

    // Ensure the fibonacci returns the correct result for the maximum (100)
    // Fails: fibonacci returns 1
    @Test
    public void testFibonacciMax()
    {
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.name("value")).sendKeys("100");
        WebElement submit = driver.findElement(By.cssSelector("input"));
        submit.sendKeys(Keys.RETURN);
        WebElement e = driver.findElement(By.className("jumbotron"));
        String s1 = e.getText();
        assertTrue(s1.contains("354224848179261915075"));
    }

    // Ensure that the fibonacci page returns the default value of 1 when passed an invalid argument: a string
    // Edge Case
    @Test
    public void testFibonacciInvalidInteger()
    {
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.name("value")).sendKeys("0");
        try {
            WebElement submit = driver.findElement(By.cssSelector("input"));
            submit.sendKeys(Keys.RETURN);
            WebElement e = driver.findElement(By.className("jumbotron"));
            String s1 = e.getText();
            assertTrue(s1.contains("1"));
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    // Ensure that the fibonacci page returns the default value of 1 when passed an invalid argument: a string
    // Corner Case
    // Fails: passing "hello" results in an internal server error
    @Test
    public void testFibonacciInvalidNonInteger()
    {
        driver.findElement(By.linkText("Fibonacci")).click();
        driver.findElement(By.name("value")).sendKeys("hello");
        try {
            WebElement submit = driver.findElement(By.cssSelector("input"));
            submit.sendKeys(Keys.RETURN);
            WebElement e = driver.findElement(By.className("jumbotron"));
            String s1 = e.getText();
            assertTrue(s1.contains("1"));
        } catch (NoSuchElementException e) {
            fail();
        }
    }

    // Ensure the default text is displayed when the Hello page is not provided a trail
    @Test
    public void testHelloNoTrail()
    {
        driver.findElement(By.linkText("Hello")).click();
        WebElement e = driver.findElement(By.className("jumbotron"));
        String elementText = e.getText();
        assertTrue(elementText.contains("Hello CS1632, from Prof. Laboon!"));
    }

    // Ensure if the Hello page url has a trail, the page will display the value passed in (i.e. "Hello CS1632, from friends! ")
    @Test
    public void testHelloTrailString()
    {
        driver.get("https://cs1632ex.herokuapp.com/hello/friends");
        WebElement e = driver.findElement(By.className("jumbotron"));
        String elementText = e.getText();
        assertTrue(elementText.contains("Hello CS1632, from friends!"));
    }

    // Ensure if the Hello page url has a trail, the page will display the value passed in (i.e. "Hello CS1632, from 77! ")
    @Test
    public void testHelloTrailInteger()
    {
        driver.get("https://cs1632ex.herokuapp.com/hello/77");
        WebElement e = driver.findElement(By.className("jumbotron"));
        String elementText = e.getText();
        assertTrue(elementText.contains("Hello CS1632, from 77!"));
    }

    //A trail is provided, but it contains no text. The website should display "Hello CS1632, from !"
    //Fails: the website displays the default message
    //Edge case
    @Test
    public void testHelloEmptyTrail()
    {
        driver.get("https://cs1632ex.herokuapp.com/hello//");
        WebElement e = driver.findElement(By.className("jumbotron"));
        String elementText = e.getText();
        assertTrue(elementText.contains("Hello CS1632, from !"));
    }

    // Ensure the Cathedral Pics page displays three images
    @Test
    public void testNumCathyPics()
    {
        driver.findElement(By.linkText("Cathedral Pics")).click();
        List<WebElement> links = driver.findElements(By.tagName("img"));
        assertEquals(3, links.size());
    }

    // Check to make sure the list of images on the Cathy page is numbered
    //NKD: not sure how to implement this right now --> ensure there is an "ol" tag meaning ordered list (opposite of "ul")
    @Test
    public void testNumCathyList()
    {
        driver.findElement(By.linkText("Cathedral Pics")).click();
        try {
            WebElement olTag = driver.findElement(By.tagName("ol"));
            assertNotNull(olTag);
        } catch (NoSuchElementException e) {
            fail();
        }
    }
}
