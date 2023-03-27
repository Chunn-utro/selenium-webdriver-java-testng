package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class Topic_18_Window_Tab {
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
    public void TC_01_ByID () {
        // Parent page
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Lấy ra cái ID của window/tab mà nó đang đứng (active)
        String parentPageWindowID = driver.getWindowHandle();
        System.out.println("Parent Page: " + parentPageWindowID);

        // Click vào google link để bật ra tab mới
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(3);

        // Case 1: chỉ có 2 window hoặc 2 ta

        // Lấy ra tất cả ID
        // Sau đó dùng vòng lặp duyệt và kiểm tra
        // Cái ID nào khác ID của parent window thì switch
        switchToWindowByID(parentPageWindowID);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");

        String googleWindowID = driver.getWindowHandle();
        switchToWindowByID(googleWindowID);
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");

    }

    @Test
    public void TC_02_ByTitle () {
        // Parent page
        driver.get("https://automationfc.github.io/basic-form/index.html");

        String parentID = driver.getWindowHandle();

        // Click vào google link để bật ra tab mới
        driver.findElement(By.xpath("//a[text()='GOOGLE']")).click();
        sleepInSecond(3);

        switchToWindowByTitle("Google");
        driver.findElement(By.cssSelector("input[name='q']")).sendKeys("Automation");
        sleepInSecond(2);
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.google.com.vn/");


        switchToWindowByTitle("Selenium WebDriver");
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");

        // Click vào facebook
        driver.findElement(By.xpath("//a[text()='FACEBOOK']")).click();
        sleepInSecond(2);

        switchToWindowByTitle("Facebook – log in or sign up");
        driver.findElement(By.cssSelector("input#email")).sendKeys("SherlockHomels@gmail.com");
        Assert.assertEquals(driver.getCurrentUrl(), "https://www.facebook.com/");

        switchToWindowByTitle("Selenium WebDriver");
        Assert.assertEquals(driver.getCurrentUrl(), "https://automationfc.github.io/basic-form/index.html");

        // Click vào Tiki
        driver.findElement(By.xpath("//a[text()='TIKI']")).click();
        sleepInSecond(2);

        switchToWindowByTitle("Tiki - Mua hàng online giá tốt, hàng chuẩn, ship nhanh");
        driver.findElement(By.cssSelector("input[data-view-id='main_search_form_input']")).sendKeys("macbook pro");

        closeWindowWithoutParent(parentID);
        sleepInSecond(2);

    }

    @Test
    public void TC_03_Live_Guru () {
        driver.get("http://live.techpanda.org/index.php/mobile.html");

        String parentID = driver.getWindowHandle();

        driver.findElement(By.xpath("//a[@title='Samsung Galaxy']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Samsung Galaxy has been added to comparison list.");

        driver.findElement(By.xpath("//a[@title='Sony Xperia']/parent::h2/following-sibling::div[@class='actions']//a[@class='link-compare']")).click();
        Assert.assertEquals(driver.findElement(By.xpath("//li[@class='success-msg']//span")).getText(), "The product Sony Xperia has been added to comparison list.");

        driver.findElement(By.cssSelector("button[title='Compare']")).click();

        switchToWindowByTitle("Products Comparison List - Magento Commerce");
        Assert.assertTrue(driver.findElement(By.xpath("//h1[text()='Compare Products']")).isDisplayed());

        // driver.findElement(By.xpath("//button[@title='Close Window']")).click();

        closeWindowWithoutParent(parentID);

        switchToWindowByTitle("Mobile");
    }

    // Dùng cho duy nhất 2 window/tab
    public void switchToWindowByID (String otherID) {
        Set<String> allWindowIDs = driver.getWindowHandles();

        for (String id : allWindowIDs) {
            if (!id.equals(otherID)) {
                driver.switchTo().window(id);
                sleepInSecond(2);

            }
        }

    }

    // Dùng cho nhiều window/tab
    public void switchToWindowByTitle(String expectedPageTitle) {
        Set<String> allWindowIDs = driver.getWindowHandles();

        int count = 1;

        for (String id : allWindowIDs) {
            // switch từng ID trước
            driver.switchTo().window(id);

            // Lấy ra title của page
            String actualPageTitle = driver.getTitle();
            System.out.println("Actual Title: " + actualPageTitle + " - " + count);
            count++;

            if (actualPageTitle.equals(expectedPageTitle)) {
                sleepInSecond(2);
                break;
            }
        }

    }

    public void closeWindowWithoutParent (String parentID) {
        Set<String> allwindowIDs = driver.getWindowHandles();

        for (String id : allwindowIDs) {
            if (!id.equals(parentID)){
                driver.switchTo().window(id);
                driver.close();
                sleepInSecond(2);
            }
        }

        driver.switchTo().window(parentID);


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
