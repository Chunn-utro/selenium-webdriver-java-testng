package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.Date;
import java.util.function.Function;

public class Topic_27_FluentWait {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;
    FluentWait<WebDriver> fluentDriver;
    FluentWait<WebElement> fluentElement;

    long allTime = 15;
    long pollingTime = 100;

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Fluent_Wait () {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        findElementFluent("//div[@id='start']/button").click();
        Assert.assertEquals(findElementFluent("//div[@id='finish']/h4").getText(), "Hello World!");
    }

    @Test
    public void TC_02_Fluent_Wait () {
        driver.get("https://automationfc.github.io/fluent-wait/");

        WebElement countdownTime = findElementFluent("//div[@id='javascript_countdown_time']");

        fluentElement = new FluentWait<WebElement>(countdownTime);

        fluentElement.withTimeout(Duration.ofSeconds(allTime))
                .pollingEvery(Duration.ofMillis(pollingTime))
                .ignoring(NoSuchElementException.class);

        fluentElement.until(new Function<WebElement, Boolean>() {
            @Override
            public Boolean apply(WebElement element) {
                String text = element.getText();
                System.out.println(text);
                return text.endsWith("00");
            }
        });

        }

    public WebElement findElementFluent (String xpathLocator) {
        fluentDriver = new FluentWait<WebDriver>(driver);

        // Set tổng thời gian và tần số
        fluentDriver.withTimeout(Duration.ofSeconds(allTime))
                .pollingEvery(Duration.ofMillis(pollingTime))
                .ignoring(NoSuchElementException.class);

        // Apply điều kiện
        return fluentDriver.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver driver) {
                return driver.findElement(By.xpath(xpathLocator));
            }
        });
    }

    public void sleepInSecond(long timeInSecond) {
        try {
            Thread.sleep(timeInSecond * 1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }


    public String getTimeStamp () {
        // Show ra cái time-stamp tại thời điểm gọi cái hàm này
        // Time stamp = ngày - giờ - phút - giây
        Date date = new Date();
        return date.toString();
    }

    @AfterClass
    public void afterClass() {

        //driver.quit();
    }
}