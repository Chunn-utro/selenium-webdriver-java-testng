package webdriver;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.io.IOException;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_14_Alert {
    WebDriver driver;
    WebDriverWait explicitWait;
    Alert alert;
    Random rand = new Random();
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String authenFirefor = projectPath + "//autoIT//authen_firefox.exe";
    String authenChrome = projectPath + "//autoIT//authen_chrome.exe";
    String username = "admin";
    String password = "admin";


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "//browserDrivers//geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        explicitWait = new WebDriverWait(driver, 15);
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Accept_Alert () {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Alert']")).click();
        sleepInSecond(2);

        // 2 - Cần wait trước, khi nào xuất hiện thì mới switch và thao tác
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(), "I am a JS Alert");
        alert.accept();

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked an alert successfully");
    }

    @Test
    public void TC_02_Confirm_Alert () {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Confirm']")).click();
        sleepInSecond(2);

        // 2 - Cần wait trước, khi nào xuất hiện thì mới switch và thao tác
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(), "I am a JS Alert");
        alert.dismiss();

        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You clicked: Cancel");


    }

    @Test
    public void TC_03_Prompt_Alert () {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        driver.findElement(By.xpath("//button[text()='Click for JS Prompt']")).click();
        sleepInSecond(2);

        // 2 - Cần wait trước, khi nào xuất hiện thì mới switch và thao tác
        alert = explicitWait.until(ExpectedConditions.alertIsPresent());

        // Verify alert title đúng như mong đợi
        Assert.assertEquals(alert.getText(), "I am a JS prompt");

        String courseName = "FullStack Selemium";
        alert.sendKeys(courseName);
        sleepInSecond(2);
        alert.accept();


        Assert.assertEquals(driver.findElement(By.cssSelector("p#result")).getText(), "You entered: " + courseName);

    }

    @Test
    public void TC_04_Authentication_Alert_I () {
        // truyền trực tiếp username và password vào trong chính Url -> Tự động SignIn luôn
        // http:// [username] : [password]@the-internet.herokuapp.com/basic_auth
        // Khoong bật alert mà truyền thẳng vào url
        driver.get(passUserPasswToUrl("http://the-internet.herokuapp.com/basic_auth", "admin", "admin"));
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());

    }
    @Test
    public void TC_04_Authentication_Alert_II () {

        driver.get("http://the-internet.herokuapp.com/");
        String authenUrl = driver.findElement(By.xpath("//a[text()='Basic Auth'")).getAttribute("href");
        driver.get(passUserPasswToUrl(authenUrl, "admin", "admin"));

        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());

    }
    @Test
    public void TC_04_Authentication_Alert_III () throws IOException {
        //autoIT, chỉ dùng cho windows, không dùng cho mac

        if (driver.toString().contains("firefox")) {
            // Thực thi 1 file exe trong code java
            Runtime.getRuntime().exec(new String[] { authenFirefor, username, password});

        } else if (driver.toString().contains("chrome")){
            Runtime.getRuntime().exec(new String[] { authenChrome, username, password});

        }

        driver.get("http://the-internet.herokuapp.com/basic_auth");
        Assert.assertTrue(driver.findElement(By.xpath("//p[contains(text(), 'Congratulations! You must have the proper credentials.')]")).isDisplayed());

    }


    public String passUserPasswToUrl (String url, String username, String password){
        // Url : http://the-internet.herokuapp.com/basic_auth
        // Username : admin
        // Password: admin
        String[] arrayUrl = url.split("//");
        return arrayUrl[0] + "//" + username + ":" + password + "@" + arrayUrl[1];
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
