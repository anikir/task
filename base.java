package Task.task;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class base {

	WebDriver driver;
	
	public WebDriver initalizedriver()
	{
		System.setProperty("webdriver.chrome.driver",
				"C:\\Aniket\\Study\\Java_Driver\\Chrome\\chromedriver_win32\\chromedriver.exe");
		 driver = new ChromeDriver();
		 driver.manage().window().maximize();
		 driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		 return driver;
	}
	


}
