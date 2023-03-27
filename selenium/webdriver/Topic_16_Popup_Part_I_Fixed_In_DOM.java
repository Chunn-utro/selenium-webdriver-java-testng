package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.concurrent.TimeUnit;

public class Topic_16_Popup_Part_I_Fixed_In_DOM {
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

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Fixed_In_DOM_Ngoaingu () {
        driver.get("https://ngoaingu24h.vn/");
        sleepInSecond(2);

        By loginPopup = By.cssSelector("div#modal-login-v1 div.modal-content");

        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("button.login_")).click();
        sleepInSecond(2);

        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("input#account-input")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#password-input")).sendKeys("12345689");
        driver.findElement(By.cssSelector("button.btn-v1.btn-login-v1")).click();
        sleepInSecond(1);

        Assert.assertEquals(driver.findElement(By.xpath("//div[text()='Tài khoản không tồn tại!']")).getText(), "Tài khoản không tồn tại!");

    }

    @Test
    public void TC_02_Fixed_In_DOM_Kyna () {
        driver.get("https://skills.kynaenglish.vn/");
        sleepInSecond(2);

        By loginPopup = By.cssSelector("div#k-popup-account-login");

        // Undisplayed
        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("a.login-btn")).click();
        sleepInSecond(2);

        // Displayed
        Assert.assertTrue(driver.findElement(loginPopup).isDisplayed());

        driver.findElement(By.cssSelector("input#user-login")).sendKeys("automation@gmail.com");
        driver.findElement(By.cssSelector("input#user-password")).sendKeys("123456897");
        driver.findElement(By.cssSelector("button#btn-submit-login")).click();
        sleepInSecond(4);

        Assert.assertEquals(driver.findElement(By.cssSelector("div#password-form-login-message")).getText(), "Sai tên đăng nhập hoặc mật khẩu");

        driver.findElement(By.cssSelector("button.k-popup-account-close")).click();
        sleepInSecond(1);

        Assert.assertFalse(driver.findElement(loginPopup).isDisplayed());

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
