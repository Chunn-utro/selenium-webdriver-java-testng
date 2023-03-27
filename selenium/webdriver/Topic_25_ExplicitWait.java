package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;


public class Topic_25_ExplicitWait {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;

    // File name
    String sherlockFilename = "sherlock.jpg";
    String ironManFilename = "ironman.jpg";

    // File image path
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

        // Apply 15s cho các trạng thái cụ thể
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_Not_Enough_Time_Visible () {

        driver.get("https://automationfc.github.io/dynamic-loading/");

        explicitWait = new WebDriverWait(driver, 3);

        driver.findElement(By.cssSelector("div[id='start']>button")).click();

        // Thiếu thời gian để cho 1 element tiếp theo hoạt động được
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[id='finish']>h4")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div[id='finish']>h4")).getText(), "Hello World!");


    }

    @Test
    public void TC_02_Enough_Time_Invisible () {
        driver.get("https://automationfc.github.io/dynamic-loading/");

        explicitWait = new WebDriverWait(driver, 5);

        driver.findElement(By.cssSelector("div[id='start']>button")).click();

        // Wait cho một element biến mất
        // Step 1: click start button
        // Step 2: Loading
        // Step 3: Hello Text
        // Wait cho loading icon biến mất
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div#loading")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div[id='finish']>h4")).getText(), "Hello World!");


    }


    @Test
    public void TC_03_More_Time () {

        driver.get("https://automationfc.github.io/dynamic-loading/");

        explicitWait = new WebDriverWait(driver, 50);

        driver.findElement(By.cssSelector("div[id='start']>button")).click();

        // Thiếu thời gian để cho 1 element tiếp theo hoạt động được
        explicitWait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector("div[id='finish']>h4")));

        Assert.assertEquals(driver.findElement(By.cssSelector("div[id='finish']>h4")).getText(), "Hello World!");

    }

    @Test
    public void TC_04_Ex6_Ajax_Loading () {

        driver.get("https://demos.telerik.com/aspnet-ajax/ajaxloadingpanel/functionality/explicit-show-hide/defaultcs.aspx");

        explicitWait = new WebDriverWait(driver, 20);

        // Wait cho Date Time picker được hiển thị
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div.RadCalendar")));

        // Verify cho selected dates là không có ngày nào được chọn
        Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(), "No Selected Dates to display.");

        // Wait cho ngày 22 được phép click
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='22']")));

        // Click vào ngày 22
        driver.findElement(By.xpath("//a[text()='22']")).click();

        // Wait cho đến khi Ajax loading icon không còn xuất hiện nữa
        explicitWait.until(ExpectedConditions.invisibilityOfElementLocated(By.cssSelector("div[id*='RadCalendar1']>div.raDiv")));

        // Wait cho ngày vừa được click là clickable
        explicitWait.until(ExpectedConditions.elementToBeClickable(By.xpath("//a[text()='22']/parent::td[@class='rcSelected']")));

        // Verify cho selected dates là ngày 22 đã được chọn
        Assert.assertEquals(driver.findElement(By.cssSelector("span#ctl00_ContentPlaceholder1_Label1")).getText(), "Wednesday, March 22, 2023");

    }

    @Test
    public void TC_05_Ex7_UploadFile () {
        driver.get("https://gofile.io/uploadFiles");

        explicitWait = new WebDriverWait(driver, 20);

        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("div#filesUpload button.filesUploadButton")));

        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(sherlockFilePath + "\n" + ironManFilePath);

        // Wait cho các progress bar (thanh trạng thái load ảnh) của từng file biến mất
        explicitWait.until(ExpectedConditions.invisibilityOfAllElements(driver.findElements(By.cssSelector("div.mainUploadFilesList div.progress-bar"))));

        // Wait cho Upload message thành công được visible
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[contains(@class, 'mainUploadSuccess')]//div[text()='Your files have been successfully uploaded']")));

        // Verify message hiển thị
        Assert.assertTrue(driver.findElement(By.xpath("//div[contains(@class, 'mainUploadSuccess')]//div[text()='Your files have been successfully uploaded']")).isDisplayed());

        // Wait cho link download visible
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//a[@class='ajaxLink' and contains(text(), 'gofile.io')]")));

        // CLick vào link
        driver.findElement(By.xpath("//a[@class='ajaxLink' and contains(text(), 'gofile.io')]")).click();

        // Wait + verify luôn : cho các filename và button download hiển thị
        Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + ironManFilename + "']/parent::a/parent::div/following-sibling::div//span[text()='Download']"))).isDisplayed());
        Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + sherlockFilename + "']/parent::a/parent::div/following-sibling::div//span[text()='Download']"))).isDisplayed());

        // Wait + verify luôn : cho các filename và button play hiển thị
        Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + ironManFilename + "']/parent::a/parent::div/following-sibling::div//span[text()='Play']"))).isDisplayed());
        Assert.assertTrue(explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[text()='" + sherlockFilename + "']/parent::a/parent::div/following-sibling::div//span[text()='Play']"))).isDisplayed());


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