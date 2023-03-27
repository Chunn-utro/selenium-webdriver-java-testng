package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_21_Element_Condition_Status {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, 10);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Visible_Displayed_Visibility () {
        driver.get("https://www.facebook.com/");

        // 1 - Có hiển thị trên UI (bắt buộc)
        // 1 - Có trong HTML (bắt buộc)

        // Wait cho emailTextbox hiển thị
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.id("email")));

        driver.findElement(By.id("email")).sendKeys("automation@gmail.com");
    }

    @Test
    public void TC_02_Invisible_Undisplayed_Invisibility_I () {
        driver.get("https://www.facebook.com/");

        // 2 - không hiển thị trên UI (bắt buộc)
        // 1 - Có trong HTML

        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        // chờ cho re-submit email TextBox không hiển thị trong 10s
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));


    }
    @Test
    public void TC_02_Invisible_Undisplayed_Invisibility_II () {
        driver.get("https://www.facebook.com/");

        // 2 - không hiển thị trên UI (bắt buộc)
        // 2 - không Có trong HTML

        // chờ cho re-submit email TextBox không hiển thị trong 10s
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.name("reg_email_confirmation__")));


    }

    @Test
    public void TC_03_Presence_I () {
        driver.get("https://www.facebook.com/");

        // 2 - không hiển thị trên UI (bắt buộc)
        // 2 - không Có trong HTML

        // chờ cho re-submit email TextBox không hiển thị trong 10s
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation__")));


    }

    @Test
    public void TC_03_Presence_II () {
        driver.get("https://www.facebook.com/");

        // 2 - không hiển thị trên UI (bắt buộc)
        // 2 - không Có trong HTML

        // chờ cho re-submit email TextBox không hiển thị trong 10s
        explicitWait.until(ExpectedConditions.presenceOfElementLocated(By.name("reg_email_confirmation__")));


    }

    @Test
    public void TC_04_Staleness () {
        driver.get("https://www.facebook.com/");

        // 2 - không hiển thị trên UI (bắt buộc)
        // 2 - không Có trong HTML

        driver.findElement(By.xpath("//a[@data-testid='open-registration-form-button']")).click();

        //Phase 1: element có trong DOM
        WebElement reEnterEmailTextBox = driver.findElement(By.name("reg_email_confirmation__"));

        // Thao tác với element khác làm cho element biến mất trong DOM
        //...

        // Close popup
        driver.findElement(By.cssSelector("img._8idr")).click();

        // chờ cho re-submit email TextBox không hiển thị trong 10s
        explicitWait.until(ExpectedConditions.stalenessOf(reEnterEmailTextBox));


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
