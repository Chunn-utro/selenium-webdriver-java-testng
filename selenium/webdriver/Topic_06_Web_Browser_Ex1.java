package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_06_Web_Browser_Ex1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_Url() {
		driver.get("http://live.techpanda.org/");

		// Click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(3);

		// Verify Url in Login Page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

		// Click on Create An Account
		driver.findElement(By.xpath("//a[@title='Create an Account']")).click();
		sleepInSecond(2);

		// Verify Url in Register page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

	}

	@Test
	public void TC_02_Title() {
		driver.get("http://live.techpanda.org/");

		// Click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(3);

		// Verify title login page
		Assert.assertEquals(driver.getTitle(), "Customer Login");

		// Click on Create An Account
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);

		// Verify title Register page
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");

	}

	@Test
	public void TC_03_Navigate() {
		driver.get("http://live.techpanda.org/");

		// Click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(3);

		// Click on Create An Account
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);

		// Verify title Register page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

		// back login page
		driver.navigate().back();
		sleepInSecond(2);

		// Verify Url in Login Page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/login/");

		// forward to Register page
		driver.navigate().forward();
		sleepInSecond(2);

		// Verify title Register page
		Assert.assertEquals(driver.getCurrentUrl(), "http://live.techpanda.org/index.php/customer/account/create/");

		// Verify title Register page
		Assert.assertEquals(driver.getTitle(), "Create New Customer Account");

	}

	@Test
	public void TC_04_Page_Source() {
		driver.get("http://live.techpanda.org/");

		// Click My account
		driver.findElement(By.xpath("//div[@class='footer']//a[@title='My Account']")).click();
		sleepInSecond(3);

		// verify login page contains text "Login or Create an account"
		// verify page HTML có chưa đoạn text, chuỗi mong muốn hay không
		Assert.assertTrue(driver.getPageSource().contains("Login or Create an Account"));

		// Click on Create An Account
		driver.findElement(By.cssSelector("a[title='Create an Account']")).click();
		sleepInSecond(2);

		// Verify title Register page
		Assert.assertTrue(driver.getPageSource().contains("Create an Account"));
	}

	public void sleepInSecond(long timeInSecond) {
		try {
			Thread.sleep(timeInSecond * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	@AfterClass
	public void afterClass() {
		driver.quit();
	}
}
