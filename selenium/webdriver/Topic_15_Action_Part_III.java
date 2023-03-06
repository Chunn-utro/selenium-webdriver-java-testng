package webdriver;

import org.openqa.selenium.*;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.Point;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.Color;
import org.openqa.selenium.support.Colors;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.awt.*;
import java.awt.event.InputEvent;
import java.io.*;
import java.nio.charset.Charset;
import java.util.concurrent.TimeUnit;

public class Topic_15_Action_Part_III {
    WebDriver driver;
    Actions action;
    Colors color;
    JavascriptExecutor jsExecutor;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    String dragDropHelperPath = projectPath + "//dragAndDrop//drag_and_drop_helper.js";


    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "//browserDrivers//geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

       // driver = new ChromeDriver();
        driver = new FirefoxDriver();
        action = new Actions(driver);
        jsExecutor = (JavascriptExecutor) driver;

        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Dould_Click () {
        driver.get("https://automationfc.github.io/basic-form/index.html");

        // Scroll đến element đó
        jsExecutor.executeScript("arguments[0].scrollIntoView(true);", driver.findElement(By.xpath("//button[text()='Double click me']")));
        sleepInSecond(1);

        action.doubleClick(driver.findElement(By.xpath("//button[text()='Double click me']"))).perform();
        sleepInSecond(2);

        Assert.assertEquals(driver.findElement(By.cssSelector("p#demo")).getText(), "Hello Automation Guys!");

    }

    @Test
    public void TC_02_Right_CLick_Context_Click () {
        driver.get("http://swisnl.github.io/jQuery-contextMenu/demo.html");

        //Right click
        action.contextClick(driver.findElement(By.cssSelector("span.context-menu-one"))).perform();
        sleepInSecond(2);

        // Verify Quit hiển thị
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());

        // Hover vào Quit
        action.moveToElement(driver.findElement(By.cssSelector("li.context-menu-icon-quit"))).perform();
        sleepInSecond(2);

        // Verify context quit được hiển thị
        Assert.assertTrue(driver.findElement(By.cssSelector("li.context-menu-icon-quit.context-menu-visible.context-menu-visible")).isDisplayed());

        driver.findElement(By.cssSelector("li.context-menu-icon-quit")).click();

        driver.switchTo().alert().accept();
        sleepInSecond(2);

        Assert.assertFalse(driver.findElement(By.cssSelector("li.context-menu-icon-quit")).isDisplayed());



    }
    @Test
    public void TC_03_Drag_And_Drop_HTML4 () {
        driver.get("https://automationfc.github.io/kendo-drag-drop/");

        WebElement smallCircle = driver.findElement(By.cssSelector("div#draggable"));
        WebElement bigCircle = driver.findElement(By.cssSelector("div#droptarget"));

        action.dragAndDrop(smallCircle, bigCircle).perform();

        // Verify Text
        Assert.assertEquals(bigCircle.getText(), "You did great!");

        // Verify background color
        String bigCircleColorRBG = bigCircle.getCssValue("background-color");
        System.out.println(bigCircleColorRBG);

        String bigCircleColorHEXA = Color.fromString(bigCircleColorRBG).asHex();
        System.out.println(bigCircleColorHEXA);

        bigCircleColorHEXA = bigCircleColorHEXA.toUpperCase();
        System.out.println(bigCircleColorHEXA);

        Assert.assertEquals(bigCircleColorHEXA, "#03A9F4");


    }

    @Test
    public void TC_03_Drag_And_Drop_HTML5 () throws IOException {
        String jsHelper = getContentFile(dragDropHelperPath);

        driver.get("https://automationfc.github.io/drag-drop-html5/");

        String sourceCss = "div#column-a";
        String targetCss = "div#column-b";

        // A to B
        jsHelper = jsHelper + "$(\"" + sourceCss + "\").simulateDragDrop({ dropTarget: \"" + targetCss + "\"});";
        jsExecutor.executeScript(jsHelper);

        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

        // B to A
        jsExecutor.executeScript(jsHelper);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());

    }
    @Test
    public void TC_03_Drag_And_Drop_HTML5_Robot () throws IOException, AWTException {

        driver.get("https://automationfc.github.io/drag-drop-html5/");

        dragAndDropHTML5ByXpath("//div[@id='column-a']", "//div[@id='column-b']");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='B']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='A']")).isDisplayed());

        dragAndDropHTML5ByXpath("//div[@id='column-b']", "//div[@id='column-a']");
        sleepInSecond(2);
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-a']/header[text()='A']")).isDisplayed());
        Assert.assertTrue(driver.findElement(By.xpath("//div[@id='column-b']/header[text()='B']")).isDisplayed());


    }

    public String getContentFile(String filePath) throws IOException {
        Charset cs = Charset.forName("UTF-8");
        FileInputStream stream = new FileInputStream(filePath);
        try {
            Reader reader = new BufferedReader(new InputStreamReader(stream, cs));
            StringBuilder builder = new StringBuilder();
            char[] buffer = new char[8192];
            int read;
            while ((read = reader.read(buffer, 0, buffer.length)) > 0) {
                builder.append(buffer, 0, read);
            }
            return builder.toString();
        } finally {
            stream.close();
        }
    }


    public void dragAndDropHTML5ByXpath(String sourceLocator, String targetLocator) throws AWTException {

        WebElement source = driver.findElement(By.xpath(sourceLocator));
        WebElement target = driver.findElement(By.xpath(targetLocator));

        // Setup robot
        Robot robot = new Robot();
        robot.setAutoDelay(500);

        // Get size of elements
        Dimension sourceSize = source.getSize();
        Dimension targetSize = target.getSize();

        // Get center distance
        int xCentreSource = sourceSize.width / 2;
        int yCentreSource = sourceSize.height / 2;
        int xCentreTarget = targetSize.width / 2;
        int yCentreTarget = targetSize.height / 2;

        Point sourceLocation = source.getLocation();
        Point targetLocation = target.getLocation();

        // Make Mouse coordinate center of element
        sourceLocation.x += 20 + xCentreSource;
        sourceLocation.y += 110 + yCentreSource;
        targetLocation.x += 20 + xCentreTarget;
        targetLocation.y += 110 + yCentreTarget;

        // Move mouse to drag from location
        robot.mouseMove(sourceLocation.x, sourceLocation.y);

        // Click and drag
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mousePress(InputEvent.BUTTON1_MASK);
        robot.mouseMove(((sourceLocation.x - targetLocation.x) / 2) + targetLocation.x, ((sourceLocation.y - targetLocation.y) / 2) + targetLocation.y);

        // Move to final position
        robot.mouseMove(targetLocation.x, targetLocation.y);

        // Drop
        robot.mouseRelease(InputEvent.BUTTON1_MASK);
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
