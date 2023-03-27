package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_16_Popup_Part_IV_Random_Not_In_DOM {
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
    public void TC_01_Random_Not_In_DOM_De_Hieu () {
        driver.get("https://dehieu.vn/");
        sleepInSecond(15);

        By popUp = By.cssSelector("div.popup-content");

        // findElement -> sẽ bị fail khi không tìm thấy element -> ném ra ngoại lệ: NoSuchElementException
        // findElements -> sẽ không bị fail khi không tìm thấy element -> trả về rỗng

        // isDisplayed()
        if (driver.findElements(popUp).size() > 0 && driver.findElements(popUp).get(0).isDisplayed()) {
            driver.findElement(By.cssSelector("input#popup-name")).sendKeys("Sherlock");
            driver.findElement(By.cssSelector("input#popup-email")).sendKeys(emailAddress);
            driver.findElement(By.cssSelector("input#popup-phone")).sendKeys("0987654321");
            sleepInSecond(10);

            if (driver.findElement(By.cssSelector("iframe[data-testid='dialog_iframe']")).isDisplayed()) {
                sleepInSecond(3);

                driver.findElement(By.xpath("//div[@aria-label='đóng']")).click();
                sleepInSecond(2);
            }

            driver.findElement(By.cssSelector("button#close-popup")).click();
            sleepInSecond(3);
        }

        driver.findElement(By.xpath("//a[text()='Tất cả khóa học']")).click();
        sleepInSecond(3);

        String courseName = "Khóa học Thiết kế và Thi công Hệ thống BMS";
        driver.findElement(By.id("#search-courses")).sendKeys(courseName);
        driver.findElement(By.cssSelector("button#search-course-button")).click();
        sleepInSecond(3);

        // Duy nhất 1 course hiển thị
        Assert.assertEquals(driver.findElements(By.cssSelector("div.course")).size(), 1 );
        Assert.assertEquals(driver.findElement(By.cssSelector("div.course-content>h4")).getText(), courseName);

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
