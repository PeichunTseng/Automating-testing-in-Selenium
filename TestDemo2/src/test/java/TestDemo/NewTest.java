package TestDemo;
import org.testng.annotations.Test;
import org.testng.AssertJUnit;
import org.testng.Reporter;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.ClickAction;
import org.testng.Assert;
import org.testng.annotations.*;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;



	public class NewTest {
		WebDriver driver;
		File f;
		String regUrl;
		String logUrl;
		String driverPath;
		SoftAssert sa;
		FileInputStream fi;
		Workbook workbook;
		Sheet sheet1;
		Row row;
		
		
		public static void screenShot(WebDriver webdriver,String path){
	        TakesScreenshot scrShot =((TakesScreenshot)webdriver);
	        File Screenshot=scrShot.getScreenshotAs(OutputType.FILE);
	        File Destination=new File(path);
	        try {
	            FileUtils.copyFile(Screenshot, Destination);
	        }catch (IOException e) {
	            System.err.println("An IOException was caught :"+e.getMessage());
	        }
	    }
		@BeforeTest
		public void beforeTest() throws IOException {
			f = new File("src/AmazonTest.xls");
			fi = new FileInputStream(f);
			workbook = WorkbookFactory.create(fi);
			sheet1 = workbook.getSheetAt(0);
			row = sheet1.getRow(1);
			Cell driverPathCell = row.getCell(1);
			driverPath = driverPathCell.toString();

			row = sheet1.getRow(2);
			Cell regURLCell = row.getCell(1);
			 regUrl = regURLCell.toString();
			//System.out.print(driverPath);
			 
			row = sheet1.getRow(3);
			Cell logURLCell = row.getCell(1);
			logUrl = logURLCell.toString();
			
			System.setProperty("webdriver.chrome.driver",driverPath);
			driver  = new ChromeDriver();
			sa = new SoftAssert();
		}
		
		
//		negative
		@Test( priority = 1 , description="My Test Fail")
		public void negative() throws InterruptedException, IOException {
			driver.get(regUrl);
			String title = driver.getTitle();
			String[] data1 = new String[4];
			for(int i=0;i<4;i++) {
				row = sheet1.getRow(4+i);
				Cell Cell = row.getCell(1);
				data1[i]=Cell.toString();
			}
			
			driver.findElement(By.xpath("//input[@id='ap_customer_name']")).sendKeys(data1[0]);
			driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys(data1[1]);
			driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys(data1[2]);
			driver.findElement(By.xpath("//input[@id='ap_password_check']")).sendKeys(data1[3]);
			SoftAssert assertion = new SoftAssert();
			assertion.assertEquals(data1[2], data1[2],"Not same password");
			driver.findElements(By.className("a-button-input")).get(0).click();
			System.out.print(driver.findElements(By.className("a-button-input")).get(0)); 
			
			Reporter.log("Negative Case Number: 01");
			Reporter.log("Excepted: Same as password entered before");
			Reporter.log("Actual: Not same password");
			Reporter.log("Status: Failed");
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/negative-sign-up.png");
			Thread.sleep(3000);
			AssertJUnit.assertEquals(title, driver.getTitle(), "Negative Case");
			
//			boolean errorNameMessage = driver.findElement(By.xpath("//div[@id='auth-password-mismatch-alert']//div[contains(text(),'Passwords must match')]")).isDisplayed();
//	        assertTrue(errorNameMessage, "Passwords is not match");
			//Assert.assertEquals(driver.getTitle(), "Test — failed", "Passwords must match"); 
	        sa.assertAll();
			
			
		}
		
		//positive
		@Test( priority = 2 , description="My Test Successful")
		public void positive() throws InterruptedException, IOException {
			driver.get(regUrl);
			String title = driver.getTitle();
			String[] data2 = new String[4];
			for(int i=0;i<4;i++) {
			row = sheet1.getRow(8+i);
			Cell Cell = row.getCell(1);
			data2[i]=Cell.toString();
			}
			driver.findElement(By.xpath("//input[@id='ap_customer_name']")).sendKeys(data2[0]);
			driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys(data2[1]);
			driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys(data2[2]);
			driver.findElement(By.xpath("//input[@id='ap_password_check']")).sendKeys(data2[3]);
			driver.findElements(By.className("a-button-input")).get(0).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/positive-sign-up.png");
			Thread.sleep(3000);
			driver.findElements(By.className("a-icon-logo")).get(0).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/amazon-after-sign-up.png");
			Thread.sleep(3000);
//			driver.get(logUrl);
			//driver.findElements(By.className("nav-line-2")).get(0).click();
//			driver.findElements(By.className("nav-icon.nav-arrow")).get(0).click();
			sa.assertNotEquals(title, driver.getTitle(), "Positive Case");
			sa.assertAll();
		}
//		
//		
		//sign in
		@Test( priority = 3 )
		public void login() throws IOException, InterruptedException {
			driver.get(logUrl);
			String[] data3 = new String[2];
			for(int i=0;i<2;i++) {
			row = sheet1.getRow(12+i);
			Cell Cell = row.getCell(1);
			data3[i]=Cell.toString();
			}
			driver.findElement(By.xpath("//input[@id='ap_email']")).sendKeys(data3[0]);
			driver.findElement(By.xpath("//input[@id='continue']")).click();
			Thread.sleep(3000);
			driver.findElement(By.xpath("//input[@id='ap_password']")).sendKeys(data3[1]);
			driver.findElement(By.xpath("//input[@id='signInSubmit']")).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/sign-in.png");
			Thread.sleep(3000);
			driver.get("https://www.amazon.com");
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/amazon-after-sign-in.png");
			
			
		}
		
		@Test( priority = 4)
		public void addItemsToCart() throws IOException, InterruptedException{
			driver.get("https://www.amazon.com");
			driver.findElement(By.xpath("//input[@id='twotabsearchtextbox']")).sendKeys("Hershey's chocolate bars");
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/searchHersheys.png");
			driver.findElements(By.className("nav-input")).get(0).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/search-result.png");
			driver.findElement(By.xpath("//*[@id='search']//img[@src='https://m.media-amazon.com/images/I/61ZhqmPIdSL._AC_UL320_ML3_.jpg']")).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/click-on-product.png");
			driver.findElement(By.id("add-to-cart-button")).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/add-product-to-cart.png");
			driver.findElement(By.id("hlb-view-cart-announce")).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/go-to-cart.png");
			driver.findElements(By.className("a-button-input")).get(0).click();
			//this.screenShot(driver, "/Users/peichun/Desktop/Selenium/TestDemo2/screenShot/proceed-to-checkout.png");
//			driver.findElement(By.linkText("Lightning Deals")).click();
//			driver.findElement(By.id("101_dealView_0")).click();
//			//driver.findElementByLinkText("Amazon Devices").click();
//			
//			driver.findElements(By.className("a-declarative")).get(0).click();
	}
////		
//		@Test( priority = 4)
//		public void searchItems() throws IOException, InterruptedException{
//			
//		}
//	  
//	
//
//		@AfterTest
//		public void afterTest() {
//			
////			driver.quit();
//		}
		
		}






    
