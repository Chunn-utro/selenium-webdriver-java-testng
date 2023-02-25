package webdriver;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class Topic_07_Web_Element_Ex1 {
	WebDriver driver;
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	By emailTextBox = By.id("mail");
	By under18Radio = By.cssSelector("#under_18");
	By education = By.cssSelector("#edu");
	By nameUser5Text = By.xpath("//h5[text()='Name: User5']");
	By password = By.xpath("//input[@id='disable_password']");
	By biographyTextArea = By.cssSelector("#bio");
	By jobRole1 = By.cssSelector("#job1");
	By jobRole2 = By.cssSelector("#job2");
	By devCheckBox = By.cssSelector("#development");
	By slider1 = By.cssSelector("#slider-1");
	By slider2 = By.cssSelector("#slider-2");

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
	public void TC_01_Displayed() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// Textbox/ textarea is displayed => enter text => print
		if (driver.findElement(emailTextBox).isDisplayed()) {
			driver.findElement(emailTextBox).sendKeys("Automation Testing");
			System.out.println("EmailTextBox is displayed");
		} else {
			System.out.println("EmailTextBox is not displayed");
		}

		if (driver.findElement(under18Radio).isDisplayed()) {
			driver.findElement(under18Radio).click();
			System.out.println("Under18Radio is displayed");
		} else {
			System.out.println("Under18Radio is not displayed");

		}

		if (driver.findElement(education).isDisplayed()) {
			driver.findElement(education).sendKeys("Automation Testing");
			System.out.println("Education is displayed");
		} else {
			System.out.println("Education is not displayed");

		}

		if (driver.findElement(nameUser5Text).isDisplayed()) {
			System.out.println("NameUser5Text is displayed");
		} else {
			System.out.println("NameUser5Text is not displayed");

		}

	}

	@Test
	public void TC_02_Enabled() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		if (driver.findElement(emailTextBox).isEnabled()) {
			System.out.println("EmailTextBox is enable");

		} else {
			System.out.println("EmailTextBox is not enable");

		}

		if (driver.findElement(password).isEnabled()) {
			System.out.println("Password is enable");

		} else {
			System.out.println("Password is not enable");

		}

		if (driver.findElement(biographyTextArea).isEnabled()) {
			System.out.println("BiographyTextArea is enable");

		} else {
			System.out.println("BiographyTextArea is not enable");

		}

		if (driver.findElement(under18Radio).isEnabled()) {
			System.out.println("Under18Radio is enable");

		} else {
			System.out.println("Under18Radio is not enable");

		}

		if (driver.findElement(slider2).isEnabled()) {
			System.out.println("Slider2 is enable");

		} else {
			System.out.println("Slider2 is not enable");

		}

	}

	@Test
	public void TC_03_Selected() {
		driver.get("https://automationfc.github.io/basic-form/index.html");

		// verify checkbox & radio button is deselected
		Assert.assertFalse(driver.findElement(under18Radio).isSelected());
		Assert.assertFalse(driver.findElement(devCheckBox).isSelected());

		// Click to checkbox/ radio
		driver.findElement(under18Radio).click();
		driver.findElement(devCheckBox).click();

		// verify checkbox & radio button is selected
		Assert.assertTrue(driver.findElement(under18Radio).isSelected());
		Assert.assertTrue(driver.findElement(devCheckBox).isSelected());

	}

	@Test
	public void TC_04_MailChimp() {
		driver.get("https://login.mailchimp.com/signup/");

		driver.findElement(By.id("email")).sendKeys("automation.sherlock@gmail.com");

		By passwordTextBox = By.id("new_password");

		// Verify lower case
		driver.findElement(passwordTextBox).sendKeys("abcde");

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Verify upper case
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("ABCDE");

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Verify number case
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("12345");

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Verify special number case
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("@#$%^");

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char not-completed']")).isDisplayed());

		// Verify chars >= 8
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("asdfghjkl");

		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='uppercase-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='number-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='special-char not-completed']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());

		// Verify full data
		driver.findElement(passwordTextBox).clear();
		driver.findElement(passwordTextBox).sendKeys("Auto@123");

		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='lowercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='uppercase-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='number-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='special-char completed']")).isDisplayed());
		Assert.assertFalse(driver.findElement(By.xpath("//li[@class='8-char completed']")).isDisplayed());
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
