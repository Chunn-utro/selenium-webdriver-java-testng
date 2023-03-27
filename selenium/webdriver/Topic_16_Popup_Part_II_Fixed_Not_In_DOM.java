package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_16_Popup_Part_II_Fixed_Not_In_DOM {
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

        FirefoxOptions options = new FirefoxOptions();
        options.setProfile(new FirefoxProfile());
        options.addPreference("dom.webnotifications.enabled", false);
        driver = new FirefoxDriver(options);

        // Map<String, Integer> prefs = new HashMap<String, Integer>();
        // prefs.put("profile.default_content_setting_values.notifications", 2);
        // ChromeOptions options = new ChromeOptions();
        // options.setExperimentalOption("prefs", prefs);
        // driver = new ChromeDriver(options);

        // ảnh hưởng trực tiếp đến findElement
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Fixed__Not_In_DOM_Tiki () {
        driver.get("https://tiki.vn/");
        sleepInSecond(2);

        By loginPopup = By.cssSelector("div.ReactModal__Content");

        // Verify loginPopup is not displayed
        // Dùng findElements để verify với size = 0 (kích thước so lượng element),
        // nếu khoong hiển thị sẽ trả về pass và tiếp tục chạy testcase
        Assert.assertEquals(driver.findElements(loginPopup).size(), 0);

        driver.findElement(By.cssSelector("div[data-view-id*='header_account']")).click();
        sleepInSecond(2);

        // Verify loginPopup is displayed
        Assert.assertEquals(driver.findElements(loginPopup).size(), 1);
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("p[class='login-with-email']")).click();
        sleepInSecond(2);

        driver.findElement(By.xpath("//button[text()='Đăng nhập']")).click();
        sleepInSecond(2);

        // Verify error
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Email không được để trống']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//span[@class='error-mess' and text()='Mật khẩu không được để trống']")).isDisplayed());

        driver.findElement(By.cssSelector("img.close-img")).click();
        Assert.assertEquals(driver.findElements(loginPopup).size(), 0);


    }

    @Test
    public void TC_02_Fixed_Not_In_DOM_Facebook () {
        driver.get("https://www.facebook.com/");
        sleepInSecond(2);

        By createAccountPopup = By.xpath("//div[text()='Sign Up']/parent::div/parent::div");

        Assert.assertEquals(driver.findElements(createAccountPopup).size(), 0);

        driver.findElement(By.cssSelector("a[data-testid='open-registration-form-button']")).click();
        sleepInSecond(3);

        Assert.assertEquals(driver.findElements(createAccountPopup).size(), 1);

        driver.findElement(By.name("lastname")).sendKeys("chun");
        driver.findElement(By.name("firstname")).sendKeys("chun");
        driver.findElement(By.name("reg_email__")).sendKeys("0987654321");
        driver.findElement(By.name("reg_passwd__")).sendKeys("Chunnt1112@");
        new Select(driver.findElement(By.id("day"))).selectByVisibleText("11");
        new Select(driver.findElement(By.id("month"))).selectByVisibleText("Dec");
        new Select(driver.findElement(By.id("year"))).selectByVisibleText("2000");
        driver.findElement(By.xpath("//label[text()='Female']/following-sibling::input")).isSelected();
        sleepInSecond(2);

        driver.findElement(By.xpath("//div[text()='Sign Up']/parent::div/preceding-sibling::img")).click();
        Assert.assertEquals(driver.findElements(createAccountPopup).size(), 0);

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
