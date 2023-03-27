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

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_16_Popup_Part_III_Random_In_DOM {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String emailAddress = "testdemo" +getRandomNumber() + "@gmail.com";


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
    public void TC_01_Random_In_DOM_JavaCode_Geeks () {
        driver.get("https://www.javacodegeeks.com/");
        sleepInSecond(15);

        By lePopup = By.cssSelector("div.lepopup-popup-container>div:not([style^='display:none'])");

        if(driver.findElement(lePopup).isDisplayed()) {
            // Nhập email
            driver.findElement(By.cssSelector("div.lepopup-input>input")).sendKeys(emailAddress);
            sleepInSecond(3);
            driver.findElement(By.cssSelector("a[data-label='Get the Books'],[data-label='OK']>span")).click();
            sleepInSecond(5);

            // Verify message
            Assert.assertEquals(driver.findElement(By.cssSelector("div.lepopup-element-html-content h4")).getText(), "Thank you!");
            Assert.assertTrue(driver.findElement(By.cssSelector("div.lepopup-element-html-content p")).getText().contains("Your sign-up request was successful. We will contact you shortly."));

            // Đóng Popup đi qua step tiếp theo
            sleepInSecond(6);

        }

        String articleName = "Agile Testing Explained";
        // Qua step tiếp
        driver.findElement(By.xpath("//input[@name='s']")).sendKeys("Agile Testing Explained");
        sleepInSecond(3);
        driver.findElement(By.cssSelector("button#search-submit")).click();

        sleepInSecond(3);
        Assert.assertEquals(driver.findElement(By.cssSelector("li.post-item:first-child h2>a")).getText(), articleName);

    }

    @Test
    public void TC_02_Random_In_DOM_VNK_Edu() {
        driver.get("https://vnk.edu.vn/");
        sleepInSecond(40);

        By popUp = By.cssSelector("div.thrv_wrapper.thrv-columns");
        //div#tve-p-scroller

        if (driver.findElement(popUp).isDisplayed()) {
            // Close Popup hoặc click vào link để đăng ký khóa học
            driver.findElement(By.cssSelector("div.thrv_icon")).click();
            sleepInSecond(5);

        }

        driver.findElement(By.xpath("//button[text()='Danh sách khóa học']")).click();
        sleepInSecond(4);
        // Verify Page Title
        Assert.assertEquals(driver.getTitle(), "Lịch khai giảng các khóa học tại VNK EDU | VNK EDU");


    }


    public int getRandomNumber() {
        return new Random().nextInt(9999);
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
