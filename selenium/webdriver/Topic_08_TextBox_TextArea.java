package webdriver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_08_TextBox_TextArea {
	WebDriver driver;
	Random rand = new Random();
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");
	String employeeID = String.valueOf(rand.nextInt(99999));
	String passpostNum = "4878-896-09-3480";
	String commentInput = "This is Sherlock Homles\nIn 221B Street";

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
	public void TC_01_Add_New_Employee() {
		driver.get("https://opensource-demo.orangehrmlive.com/web/index.php/auth/login");

		// Login Admin page
		driver.findElement(By.name("username")).sendKeys("Admin");
		driver.findElement(By.name("password")).sendKeys("admin123");
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
		sleepInSecond(5);

		// Click PIM page
		driver.findElement(By.xpath("//li//span[text()='PIM']")).click();
		sleepInSecond(3);

		// Click add employee page
		driver.findElement(By.xpath("//a[text()='Add Employee']")).click();
		sleepInSecond(3);

		// Input data for add employee
		driver.findElement(By.name("firstName")).sendKeys("Sherlock");
		driver.findElement(By.name("lastName")).sendKeys("Homles");
		
		WebElement employeeIDTextBox = driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input"));
		
		// Clear data of employee id
		employeeIDTextBox.sendKeys(Keys.chord(Keys.CONTROL, "a"));
		employeeIDTextBox.sendKeys(Keys.DELETE);
		employeeIDTextBox.sendKeys(employeeID);
		driver.findElement(By.xpath("//p[text()='Create Login Details']/parent::div//span")).click();
 
		// input details data for employee
		driver.findElement(By.xpath("//label[text()='Username']/parent::div/following-sibling::div/input")).sendKeys("Sher.Homles" + employeeID);
		driver.findElement(By.xpath("//label[text()='Password']/parent::div/following-sibling::div/input")).sendKeys("221BStreet@");
		driver.findElement(By.xpath("//label[text()='Confirm Password']/parent::div/following-sibling::div/input")).sendKeys("221BStreet@");
		driver.findElement(By.xpath("//button[contains(., 'Save')]")).click();
		sleepInSecond(7);

		// Verify data of employee
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), "Sherlock");
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), "Homles");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"),employeeID);

		// Click Immigration page
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(4);

		// Click passport button
		driver.findElement(By.xpath("//h6[text()='Assigned Immigration Records']/following-sibling::button")).click();
		driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).sendKeys(passpostNum);
		driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).sendKeys(commentInput);
		driver.findElement(By.xpath("//button[contains(., 'Save')]")).click();
		sleepInSecond(5);

		// Click Edit button
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);

		// Verify data of employee
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"),passpostNum);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"),commentInput);

		// Logout account
		driver.findElement(By.cssSelector("p.oxd-userdropdown-name")).click();
		driver.findElement(By.xpath("//a[text()='Logout']")).click();
		sleepInSecond(3);
		
		
		// Login account of empployee
		driver.findElement(By.name("username")).sendKeys("Sher.Homles" + employeeID);
		driver.findElement(By.name("password")).sendKeys("221BStreet@");
		driver.findElement(By.cssSelector("button.orangehrm-login-button")).click();
		sleepInSecond(4);
		
		// CLick my infor
		driver.findElement(By.xpath("//li//span[text()='My Info']")).click();
		sleepInSecond(3);
		
		// Verify data of employee
		Assert.assertEquals(driver.findElement(By.name("firstName")).getAttribute("value"), "Sherlock");
		Assert.assertEquals(driver.findElement(By.name("lastName")).getAttribute("value"), "Homles");
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Employee Id']/parent::div/following-sibling::div/input")).getAttribute("value"),employeeID);

		// Click Immigration page
		driver.findElement(By.xpath("//a[text()='Immigration']")).click();
		sleepInSecond(4);
		
		// Click Edit button
		driver.findElement(By.cssSelector("i.bi-pencil-fill")).click();
		sleepInSecond(3);
		
		// Verify data of employee
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Number']/parent::div/following-sibling::div/input")).getAttribute("value"),passpostNum);
		Assert.assertEquals(driver.findElement(By.xpath("//label[text()='Comments']/parent::div/following-sibling::div/textarea")).getAttribute("value"),commentInput);

	}

	@Test
	public void TC_02_Verify_Employee() {

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
