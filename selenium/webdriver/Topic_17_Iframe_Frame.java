package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_17_Iframe_Frame {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "//browserDrivers//geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

       // driver = new ChromeDriver();
        driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Kyna_Iframe () {
        driver.get("https://skills.kynaenglish.vn/");
        sleepInSecond(5);

        // Verify Facebook vẫn là một element của trang
        Assert.assertTrue(driver.findElement(By.xpath("//iframe[contains(@src, 'kyna.vn')]")).isDisplayed());

        // switch qua iframe mới thao tác được lên element của iframe đó
        // driver.switchTo().frame(0);
        driver.switchTo().frame(driver.findElement(By.xpath("//iframe[contains(@src, 'kyna.vn')]")));

        Assert.assertEquals(driver.findElement(By.xpath("//a[text()='Kyna.vn']/parent::div/following-sibling::div")).getText(), "165K likes");

        // Cần switch về main page
        driver.switchTo().defaultContent();

        // switch qua iframe chat
        driver.switchTo().frame("cs_chat_iframe");

        // Click vào chat box
        driver.findElement(By.cssSelector("div.button_bar")).click();

        driver.findElement(By.cssSelector("input.input_name")).sendKeys("sherlock221");
        driver.findElement(By.cssSelector("input.input_phone")).sendKeys("0987655432");
        new Select(driver.findElement(By.id("serviceSelect"))).selectByVisibleText("TƯ VẤN TUYỂN SINH");
        driver.findElement(By.name("message")).sendKeys("Tư vấn khóa học Excel");
        sleepInSecond(3);

        // Từ iframe quay về main page
        driver.switchTo().defaultContent();

        // Search với từ khóa Excel
        driver.findElement(By.id("live-search-bar")).sendKeys("Excel");
        driver.findElement(By.cssSelector("button.search-button")).click();

        List<WebElement> courseName = driver.findElements(By.cssSelector("div.content>h4"));

        for (WebElement course : courseName) {
            Assert.assertTrue(course.getText().contains("Excel"));

        }

    }

    @Test
    public void TC_02_Right_CLick_Context_Click () {



    }
    @Test
    public void TC_03_Drag_And_Drop_HTML4 () {

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
        //driver.quit();
    }
}
