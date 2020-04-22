package example;		

import org.openqa.selenium.By;		
import org.openqa.selenium.WebDriver;		
import org.openqa.selenium.firefox.FirefoxDriver;		
import org.testng.Assert;		
import org.testng.annotations.Test;	
import org.testng.annotations.BeforeTest;	
import org.testng.annotations.AfterTest;		
public class NewTest {		
	    private static WebDriver driver;
	    @BeforeTest
		public void beforeTest() {	
	    	WebDriverManager.chromedriver().setup();
	    	driver = new ChromeDriver();
	    	        driver.get("https://www.gmail.com/");
	    	        driver.manage().window().maximize();
	    	        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
  
		}
		@Test				
		public void testEasy() {	
			driver.get("http://demo.guru99.com/test/guru99home/");  
			String title = driver.getTitle();				 
			Assert.assertTrue(title.contains("Demo Guru99 Page")); 		
		}	
				
		@AfterTest
		public void afterTest() {
			driver.quit();			
		}		
}	
//package example;
//
//import org.testng.annotations.Test;
//import org.testng.annotations.BeforeTest;
//import org.testng.annotations.AfterTest;
//
//public class NewTest {
//  @Test
//  public void f() {
//  }
//  @BeforeTest
//  public void beforeTest() {
//  }
//
//  @AfterTest
//  public void afterTest() {
//  }
//
//}
