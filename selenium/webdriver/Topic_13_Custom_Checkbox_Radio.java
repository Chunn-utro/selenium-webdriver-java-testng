package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_13_Custom_Checkbox_Radio {
	WebDriver driver;
	JavascriptExecutor jsExecutor;
	Random rand = new Random();
	String projectPath = System.getProperty("user.dir");
	String osName = System.getProperty("os.name");

	@BeforeClass
	public void beforeClass() {
		if (osName.contains("Windows")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "//browserDrivers//geckodriver.exe");
		} else {
			System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
		}

		driver = new FirefoxDriver();
		// luôn khởi tạo sau webdriver
		jsExecutor = (JavascriptExecutor) driver;
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}

	@Test
	public void TC_01_() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(2);

		/*
		 * CASE 4: // Thẻ input bị ẩn nhưng vẫn lấy locator để click // Không dùng được
		 * hàm click của WebElement được, nó không cho thao tác trên những thẻ bị ẩn //
		 * Dùng hàm click() của JavaScript - JavascriptExecutor
		 */

		// Thao tác chọn dùng thư viện JavaScriptExecutor
		By radioButton = By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input");
		jsExecutor.executeScript("arguments[0].click()", driver.findElement(radioButton));
		sleepInSecond(2);

		// Verify đã chọn thành công
		Assert.assertTrue(driver.findElement(radioButton).isSelected());

	}

	@Test
	public void TC_02() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(2);

		/*
		 * CASE 3 // Dùng thẻ label/ div... để thao tác click // Dùng thẻ input để
		 * verify // vì hàm isSelected() nó chỉ work với thẻ input // Note: không nên
		 * dùng vì khi apply vào dự án thực tế vì 1 element phải defind tới nhiêều biến
		 * => dễ bị hiểu lầm, mất time để maintain
		 */

		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div[@class='mat-radio-container']")).click();

		Assert.assertTrue(driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div/input")).isSelected());

	}

	@Test
	public void TC_03_() {
		driver.get("https://tiemchungcovid19.gov.vn/portal/register-person");
		sleepInSecond(2);

		/*
		 * Case 2: // Dùng thẻ khác input để click, có thể là thẻ span, div, label... ->
		 * đang hiển thị // Nhưng thẻ này không verify được
		 */
		driver.findElement(By.xpath("//div[text()='Đăng ký cho người thân']/preceding-sibling::div[@class='mat-radio-container']")).click();
	}

	@Test
	public void TC_04_() {
		/*
		 * CASE 1: // Thẻ input bị che, không thao tác được // Nhưng dùng để verify được
		 */

	}

	@Test
	public void TC_05_Custom_CheckBox_Radio_Ex() {
		driver.get("https://docs.google.com/forms/d/e/1FAIpQLSfiypnd69zhuDkjKgqvpID9kwO29UCzeCVrGGtbNPZXQok0jA/viewform");
		sleepInSecond(2);

		By radioButton = By.cssSelector("div[aria-label='Hà Nội']");
		By checkbox = By.cssSelector("div[aria-label='Quảng Bình']");

		jsExecutor.executeScript("arguments[0].click();", driver.findElement(radioButton));
		sleepInSecond(2);
		jsExecutor.executeScript("arguments[0].click();", driver.findElement(checkbox));
		sleepInSecond(2);

		// Verify đã chọn thành công
		// Cách 1: dùng isDisplayed()
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Hà Nội'][aria-checked='true']")).isDisplayed());
		Assert.assertTrue(driver.findElement(By.cssSelector("div[aria-label='Quảng Bình'][aria-checked='true']")).isDisplayed());

		// Cách 2: dùng getAttribute()
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-label='Hà Nội'][aria-checked='true']")).getAttribute("aria-checked"), "true");
		Assert.assertEquals(driver.findElement(By.cssSelector("div[aria-label='Quảng Bình'][aria-checked='true']")).getAttribute("aria-checked"), "true");

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

		// driver.quit();
	}

}
