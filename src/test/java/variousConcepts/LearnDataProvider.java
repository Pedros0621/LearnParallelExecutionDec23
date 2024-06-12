package variousConcepts;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

public class LearnDataProvider {

	WebDriver driver;
	ExtentReports extent;
	ExtentTest test;

	@BeforeClass
	public void reportGenerator() {

		ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter("extentReport.html");
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		test = extent.createTest("Data Provider test");

	}

	@BeforeMethod
	public void init() {
		System.setProperty("webdriver.chrome.driver", "drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		test.log(Status.INFO, "Chrome broswer launched");
		driver.manage().deleteAllCookies();
		driver.get("https://codefios.com/ebilling/");
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@DataProvider(name = "loginData")
	public String[][] loginData() {

		String[][] data = new String[][] { { "demo@codefios.com", "abc123" },
				{ "testSelenium@techfios.com", "abc123" } };
		return data;
	}

	@Test(dataProvider = "loginData")
	public void loginTest(String userName, String password) {
		WebElement USER_NAME_ELEMENT = driver.findElement(By.xpath("//*[@id=\"user_name\"]"));
		WebElement PASSWORD_ELEMENT = driver.findElement(By.xpath("//*[@id=\"password\"]"));
		WebElement SIGNIN_BUTTON_ELEMENT = driver.findElement(By.xpath("//*[@id=\"login_submit\"]"));

		USER_NAME_ELEMENT.sendKeys(userName);
		PASSWORD_ELEMENT.sendKeys(password);
		SIGNIN_BUTTON_ELEMENT.click();
		test.log(Status.INFO, "Log in successfull!!");
	}
	@AfterClass
public void reporterClose() {
extent.flush();	
}
}
