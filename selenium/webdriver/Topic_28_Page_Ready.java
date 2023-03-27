package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.List;

public class Topic_28_Page_Ready {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    JavascriptExecutor jsExecutor;
    WebDriverWait explicitWait;
    Actions action;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        action = new Actions(driver);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_TestProject () {
        driver.get("https://blog.testproject.io/");

        String keysearch = "Selenium";

        // Page này có trigger, cần thao tác của người dùng thì page mới ready, chạy hàm => true
        // Hover chuột vào field search
        action.moveToElement(driver.findElement(By.cssSelector("aside#secondary input.search-field"))).perform();
        Assert.assertTrue(isPageLoadedSuccess());

        driver.findElement(By.cssSelector("aside#secondary input.search-field")).sendKeys(keysearch);
        driver.findElement(By.cssSelector("aside#secondary span.glass")).click();
        Assert.assertTrue(driver.findElement(By.cssSelector("h2.page-title>span")).isDisplayed());
        Assert.assertTrue(isPageLoadedSuccess());

        // Verify post title with key search = Selemiun
        List<WebElement> postTitles = driver.findElements(By.cssSelector("h3.post-title>a"));
        for(WebElement title : postTitles) {
            String postTitleText = title.getText();
            System.out.println(postTitleText);
            Assert.assertTrue(postTitleText.contains(keysearch));
        }

    }

    public boolean isPageLoadedSuccess() {
        WebDriverWait explicitWait = new WebDriverWait(driver, 30);
        JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
        ExpectedCondition<Boolean> jQueryLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return (Boolean) jsExecutor.executeScript("return (window.jQuery != null) && (jQuery.active === 0);");
            }
        };

        ExpectedCondition<Boolean> jsLoad = new ExpectedCondition<Boolean>() {
            @Override
            public Boolean apply(WebDriver driver) {
                return jsExecutor.executeScript("return document.readyState").toString().equals("complete");
            }
        };
        return explicitWait.until(jQueryLoad) && explicitWait.until(jsLoad);
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