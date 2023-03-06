package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_15_Action_Part_II {
    WebDriver driver;
    Actions action;

    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "//browserDrivers//geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        action = new Actions(driver);


        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Click_And_Hold () {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));

        // 1 - Click vào số 1 (sourse)
        action.clickAndHold(listNumbers.get(0))
        // 2 - Vẫn giữ chuột/ chưa nhả
        // 3 - Di chuột tới số mong muốn
                .moveToElement(listNumbers.get(7))
                // 4 - Thả chuột
                .release()
                // Execute
                .perform();

        sleepInSecond(2);

        List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
        Assert.assertEquals(listSelectedNumber.size(), 8);

    }

    @Test
    public void TC_02_Click_And_Hold_Random () {
        driver.get("https://automationfc.github.io/jquery-selectable/");

        Keys key = null;
        if(osName.contains("Window")) {
            key = Keys.CONTROL;
        } else {
            key = Keys.COMMAND;
        }

        List<WebElement> listNumbers = driver.findElements(By.cssSelector("ol#selectable>li"));

        // Nhấn Ctrl xuống
        action.keyDown(key).perform();

        // Click chọn các số random
        action.click(listNumbers.get(0))
                .click(listNumbers.get(3))
                .click(listNumbers.get(5))
                .click(listNumbers.get(10)).perform();

        // Nhả phím Ctrl
        action.keyUp(key).perform();

        // Verify
        List<WebElement> listSelectedNumber = driver.findElements(By.cssSelector("ol#selectable>li.ui-selected"));
        Assert.assertEquals(listSelectedNumber.size(), 4);

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
