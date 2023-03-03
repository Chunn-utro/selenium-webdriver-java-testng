package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.Color;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.*;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Topic_12_Default_Button_Checkbox_Radio {
    WebDriver driver;
    Random rand = new Random();
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
        driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Add_New_Employee() {
        driver.get("https://www.fahasa.com/customer/account/create");

        driver.findElement(By.cssSelector("li.popup-login-tab-login")).click();

        // Verify button  disable
        By loginButton = By.cssSelector("button.fhs-btn-login");
        Assert.assertFalse(driver.findElement(loginButton).isEnabled());

        // Get button's background color and verify button color
        String loginButtonBackground = driver.findElement(loginButton).getCssValue("background-image");
        System.out.println(loginButtonBackground);
        Assert.assertTrue(loginButtonBackground.contains("rgb(224, 224, 224)"));

        // Input data and verify button is enable
        driver.findElement(By.cssSelector("#login_username")).sendKeys("0967254123");
        driver.findElement(By.cssSelector("#login_password")).sendKeys("123456789");
        Assert.assertTrue(driver.findElement(loginButton).isEnabled());

        // Get button's background color
        loginButtonBackground = driver.findElement(loginButton).getCssValue("background-color");

        // Verify button color
        Color loginButtonBackgroundColor = Color.fromString(loginButtonBackground);
        Assert.assertEquals("#C92127", loginButtonBackgroundColor.asHex().toUpperCase());
        System.out.println(loginButtonBackground);

    }

    @Test
    public void TC_02_Default_Checkbox_Radio() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        // Chọn 1 checkbox
        driver.findElement(By.xpath("//label[contains(text(), 'High Blood Pressure')]/preceding-sibling::input")).click();

        // Chọn 1 radio
        driver.findElement(By.xpath("//label[contains(text(), \"I don't drink\")]/preceding-sibling::input")).click();

        // Verify checkbox and radio is selected
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), 'High Blood Pressure')]/preceding-sibling::input")).isSelected());
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), \"I don't drink\")]/preceding-sibling::input")).isSelected());

        // Checkbox có thể bỏ chọn được
        driver.findElement(By.xpath("//label[contains(text(), 'High Blood Pressure')]/preceding-sibling::input")).click();
        // Verify checkbox is unselected
        Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(), 'High Blood Pressure')]/preceding-sibling::input")).isSelected());

        // Radio không bỏ chọn được
        driver.findElement(By.xpath("//label[contains(text(), \"I don't drink\")]/preceding-sibling::input")).click();
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), \"I don't drink\")]/preceding-sibling::input")).isSelected());

    }

    @Test
    public void TC_03_Multiple_Select_Checkbox() {
        driver.get("https://automationfc.github.io/multiple-fields/");

        List<WebElement> allElementCheckboxs = driver.findElements(By.cssSelector("input.form-checkbox"));

        // Dùng vòng lặp duyệt qua và click vào tất cả checkbox
        for (WebElement checkbox : allElementCheckboxs) {
            checkbox.click();
            sleepInSecond(1);
        }

        // Verify allcheckboxs is selected
        for (WebElement checkbox : allElementCheckboxs) {
            Assert.assertTrue(checkbox.isSelected());
        }

        // Nếu gặp một checkbox có tên là X thì mới click
        for (WebElement checkbox : allElementCheckboxs) {
            if (checkbox.getAttribute("value").equals("Anemia")) {
                checkbox.click();
            }

        }
    }


    @Test
    public void TC_04_Default_Checkbox_Radio2() {
        driver.get("https://demos.telerik.com/kendo-ui/checkbox/index");

        //check if checkbox is selected or not
        checkToCheckbox_Radio(By.xpath("//label[contains(text(), 'Dual-zone air conditioning')]/preceding-sibling::input"));
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone air conditioning')]/preceding-sibling::input")).isSelected());

        uncheckToCheckbox(By.xpath("//label[contains(text(), 'Dual-zone air conditioning')]/preceding-sibling::input"));
        Assert.assertFalse(driver.findElement(By.xpath("//label[contains(text(), 'Dual-zone air conditioning')]/preceding-sibling::input")).isSelected());

        // Radio
        driver.get("http://demos.telerik.com/kendo-ui/styling/radios");
        checkToCheckbox_Radio(By.xpath("//label[contains(text(), '2.0 Petrol, 147kW')]/preceding-sibling::input"));
        Assert.assertTrue(driver.findElement(By.xpath("//label[contains(text(), '2.0 Petrol, 147kW')]/preceding-sibling::input")).isSelected());
    }
    public void checkToCheckbox_Radio(By by){
        if (!driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
    }

    public void uncheckToCheckbox(By by){
        if (driver.findElement(by).isSelected()){
            driver.findElement(by).click();
        }
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

        driver.quit();
    }
}
