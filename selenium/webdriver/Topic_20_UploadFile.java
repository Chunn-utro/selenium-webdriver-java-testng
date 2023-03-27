package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_20_UploadFile {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    JavascriptExecutor jsExecutor;
    String sherlockFilename = "sherlock.jpg";
    String ironManFilename = "ironman.jpg";

    String sherlockFilePath = projectPath + "\\uploadFiles\\" + sherlockFilename;
    String ironManFilePath = projectPath + "\\uploadFiles\\" + ironManFilename;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        jsExecutor = (JavascriptExecutor) driver;
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();
        driver.get("https://www.facebook.com/");
    }

    @Test
    public void TC_01_Upload_One_File_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // Load file
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(sherlockFilePath);
        sleepInSecond(2);
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(ironManFilePath);
        sleepInSecond(2);

        // Verify img được load lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sherlockFilename +"']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + ironManFilename +"']")).isDisplayed());

        // Upload file
        List<WebElement> buttonUpload = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement button : buttonUpload) {
            button.click();
            sleepInSecond(5);
        }

        // Verify upload thành công (link)
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sherlockFilename + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + ironManFilename + "']")).isDisplayed());

        // Verify img
        Assert.assertTrue(isImageLoaded("//img[contains(@src, '" + sherlockFilename + "')]"));
        Assert.assertTrue(isImageLoaded("//img[contains(@src, '" + ironManFilename + "')]"));
    }

    @Test
    public void TC_01_Upload_Multiple_File_Per_Time() {
        driver.get("https://blueimp.github.io/jQuery-File-Upload/");

        // Load file
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(sherlockFilePath + "\n" + ironManFilePath);
        sleepInSecond(2);

        // Verify img được load lên thành công
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + sherlockFilename +"']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//p[@class='name' and text()='" + ironManFilename +"']")).isDisplayed());

        // Upload file
        List<WebElement> buttonUpload = driver.findElements(By.cssSelector("table button.start"));
        for (WebElement button : buttonUpload) {
            button.click();
            sleepInSecond(5);
        }

        // Verify upload thành công (link)
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + sherlockFilename + "']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//a[text()='" + ironManFilename + "']")).isDisplayed());
        sleepInSecond(2);

        // Verify img
        Assert.assertTrue(isImageLoaded("//img[contains(@src, '" + sherlockFilename + "')]"));
        Assert.assertTrue(isImageLoaded("//img[contains(@src, '" + ironManFilename + "')]"));

    }

    public boolean isImageLoaded(String locator) {
        boolean status = (boolean) jsExecutor.executeScript(
                "return arguments[0].complete && typeof arguments[0].naturalWidth != 'undefined' && arguments[0].naturalWidth > 0",
                getElement(locator));
        return status;
    }

    public WebElement getElement(String locator) {
        return driver.findElement(By.xpath(locator));
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
