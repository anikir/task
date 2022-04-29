package Task.task;

import java.io.IOException;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

public class tc1 extends base {

	WebDriver driver;
	
	
	@BeforeTest
	public void initalize()
	{
	    driver=initalizedriver();
		driver.get("http://damp-sands-8561.herokuapp.com/");
	}
	
	@Parameters({"username","pwd"})
	@Test
	public void login(String username,String pwd)
	{
		//login
		driver.findElement(By.cssSelector("input[autofocus='autofocus']")).sendKeys(username);
		driver.findElement(By.cssSelector("input[type='password']")).sendKeys(pwd);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		String sign=driver.findElement(By.className("alert-info")).getText();
		Assert.assertEquals(sign,"Signed in successfully.");	
	}
	
	@Test(dependsOnMethods={"login"},dataProvider="testdata")
	public void addlevel(String reading) throws IOException
	{
		driver.findElement(By.linkText("Levels")).click();
		driver.findElement(By.xpath("//a[text()='Add new']")).click();
		driver.findElement(By.cssSelector("input.form-control")).sendKeys(reading);
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		String done=driver.findElement(By.className("alert-info")).getText(); 
		Assert.assertEquals(done,"Entry was successfully created.");
	}
	
	@Test(dependsOnMethods= {"addlevel"})
	public void AddFifthLevel()
	{
		driver.findElement(By.linkText("Levels")).click();
		driver.findElement(By.xpath("//a[text()='Add new']")).click();
		driver.findElement(By.cssSelector("input.form-control")).sendKeys("98");
		driver.findElement(By.cssSelector("input[type='submit']")).click();
		String warning=driver.findElement(By.className("alert-warning")).getText(); 
		Assert.assertEquals(warning,"Maximum entries reached for this date.");
	}
	
	@Test(dependsOnMethods= {"AddFifthLevel"})
	public void dailyreportTitleCheck()
	{
		driver.findElement(By.linkText("Reports")).click();
		String value=driver.findElement(By.cssSelector("h3.panel-title")).getText();
		Assert.assertTrue(value.contains("Daily Report as of "));
		String data=driver.findElement(By.xpath("//tbody/tr")).getText();
		Assert.assertTrue(data.contains("88 132")); //hardcode min max value	
	}
	
	@Test(dependsOnMethods= {"dailyreportTitleCheck"})
	public void monthlyreportTitleCheck() throws InterruptedException
	{
		driver.findElement(By.linkText("Monthly")).click();
		Thread.sleep(1000);
		String value=driver.findElement(By.className("panel-title")).getText();
		Assert.assertTrue(value.contains("Monthly Report as of"));
	}
	
//	@AfterTest
//	public void close()
//	{
//		driver.close();
//	}
	
	@DataProvider(name="testdata")
	public Object[][] testdata()
	{	
		Object[][] a=new Object[4][1];
		a[0][0]="88";
		a[1][0]="97";
		a[2][0]="132";
		a[3][0]="101";
		return a;
	}
}
