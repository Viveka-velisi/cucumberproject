package commonfunctions;

import static org.testng.Assert.assertEquals;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

public class FunctionLibrary {
public static WebDriver driver;
public static Properties conpro;
public static WebDriver startBrowser() throws Throwable {
	conpro=new Properties();
	conpro.load(new FileInputStream("./PropertyFile/Environment.properties"));
	if(conpro.getProperty("Browser").equalsIgnoreCase("chrome"))
	{
		driver=new ChromeDriver();
		driver.manage().window().maximize();
	}
	else if(conpro.getProperty("Browser").equalsIgnoreCase("firefox")) {
		driver=new FirefoxDriver();
	}
	else {
		Reporter.log("Browser value is not matchinng",true);
	}
	return driver;
}
public static void openUrl() {
	driver.get(conpro.getProperty("Url"));
}
public static void waitForElement(String ltype,String lvalue,String mywait) {
	WebDriverWait wait=new WebDriverWait(driver, Duration.ofSeconds(Integer.parseInt(mywait)));
		if(ltype.equalsIgnoreCase("xpath")) {
			wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(lvalue)));
			
	}if(ltype.equalsIgnoreCase("name")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.name(lvalue)));
		
}
	if(ltype.equalsIgnoreCase("id")) {
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(lvalue)));
		
}
		}
public static void typeAction(String ltype,String lvalue,String testdata) {
	if(ltype.equalsIgnoreCase("xpath")) {
		driver.findElement(By.xpath(lvalue)).clear();
		driver.findElement(By.xpath(lvalue)).sendKeys(testdata);
	}
	if(ltype.equalsIgnoreCase("name")) {
		driver.findElement(By.name(lvalue)).clear();
		driver.findElement(By.name(lvalue)).sendKeys(testdata);
	}
	if(ltype.equalsIgnoreCase("id")) {
		driver.findElement(By.id(lvalue)).clear();
		driver.findElement(By.id(lvalue)).sendKeys(testdata);
	}
}
public static void ClickAction(String ltype,String lvalue) {
	if(ltype.equalsIgnoreCase("xpath")) {
		driver.findElement(By.xpath(lvalue)).click();
	}
	if(ltype.equalsIgnoreCase("name")) {
		driver.findElement(By.name(lvalue)).click();
	}
	if(ltype.equalsIgnoreCase("id")) {
		driver.findElement(By.id(lvalue)).sendKeys(Keys.ENTER);
	}
}
public static void validateTitle(String Exp_data) {
	String Actual_title=driver.getTitle();
	try {
		Assert.assertEquals(Actual_title, Exp_data,"Title is not Matching");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
}
public static void CloseBrowser() {
	driver.quit();
}
public static void dropdownlist(String ltype,String lvalue,String testdata) {
	if(ltype.equalsIgnoreCase("xpath")) {
		int value=Integer.parseInt(testdata);
		Select element= new Select(driver.findElement(By.xpath(lvalue)));
		element.selectByIndex(value);
	}
	if(ltype.equalsIgnoreCase("name")) {
		int value=Integer.parseInt(testdata);
		Select element= new Select(driver.findElement(By.name(lvalue)));
		element.selectByIndex(value);
	}
	if(ltype.equalsIgnoreCase("id")) {
		int value=Integer.parseInt(testdata);
		Select element= new Select(driver.findElement(By.id(lvalue)));
		element.selectByIndex(value);
	}
	
}
public static void capturestock(String ltype,String lvalue) throws Throwable {
	String stocknum="";
	if(ltype.equalsIgnoreCase("xpath")) {
		stocknum =driver.findElement(By.xpath(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("name")) {
		stocknum =driver.findElement(By.name(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("id")) {
		stocknum =driver.findElement(By.id(lvalue)).getAttribute("value");
	}
	FileWriter fw=new FileWriter("./CaptureData/stocknumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	 bw.write(stocknum);
	 bw.flush();
	 bw.close();
}
public static void stocktable() throws Throwable {
	FileReader fr= new FileReader("./CaptureData/stocknumber.txt");
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(5000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[8]/div/span/span")).getText();
	Reporter.log(exp_data+"  "+Act_data,true);
	try {
		Assert.assertEquals(exp_data, Act_data,"stocknum not matching in table");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
			
	
	}
public static void capturesupplier(String ltype,String lvalue) throws Throwable {
	String suppliernum="";
	if(ltype.equalsIgnoreCase("xpath")) {
		suppliernum =driver.findElement(By.xpath(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("name")) {
		suppliernum =driver.findElement(By.name(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("id")) {
		suppliernum =driver.findElement(By.id(lvalue)).getAttribute("value");
	}
	FileWriter fw= new FileWriter("./CaptureData/suppliernumber.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	 bw.write(suppliernum);
	 bw.flush();
	 bw.close();
}
public static void suppliertable() throws Throwable {
	FileReader fr= new FileReader("./CaptureData/suppliernumber.txt");
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(5000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[6]/div/span/span")).getText();
	Reporter.log(exp_data+"  "+Act_data,true);
	try {
		Assert.assertEquals(exp_data, Act_data,"supplier num not matching in table");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
			
	
	}
public static void capturecustomernum(String ltype,String lvalue) throws Throwable {
	String customernum="";
	if(ltype.equalsIgnoreCase("xpath")) {
		customernum =driver.findElement(By.xpath(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("name")) {
		customernum =driver.findElement(By.name(lvalue)).getAttribute("value");
	}
	if(ltype.equalsIgnoreCase("id")) {
		customernum
		=driver.findElement(By.id(lvalue)).getAttribute("value");
	}
	FileWriter fw= new FileWriter("./CaptureData/customernum.txt");
	BufferedWriter bw=new BufferedWriter(fw);
	 bw.write(customernum);
	 bw.flush();
	 bw.close();
}
public static void customertable() throws Throwable {
	FileReader fr= new FileReader("./CaptureData/customernum.txt");
	BufferedReader br=new BufferedReader(fr);
	String exp_data=br.readLine();
	if(!driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).isDisplayed())
		driver.findElement(By.xpath(conpro.getProperty("search-panel"))).click();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).clear();
	driver.findElement(By.xpath(conpro.getProperty("Search-textbox"))).sendKeys(exp_data);
	driver.findElement(By.xpath(conpro.getProperty("search-button"))).click();
	Thread.sleep(5000);
	String Act_data = driver.findElement(By.xpath("//table[@class='table ewTable']/tbody/tr[1]/td[5]/div/span/span")).getText();
	Reporter.log(exp_data+"  "+Act_data,true);
	try {
		Assert.assertEquals(exp_data, Act_data,"customer num not matching in table");
	} catch (AssertionError e) {
		System.out.println(e.getMessage());
	}
			
	
	}
}




































