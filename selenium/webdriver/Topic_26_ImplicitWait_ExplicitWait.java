package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Topic_26_ImplicitWait_ExplicitWait {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");
    WebDriverWait explicitWait;

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
    public void TC_01_Element_Found () {
        // Element có xuất hiện và không cần chờ hết timeout
        // Dù có set cả 2 loại wait thì không ảnh hưởng
        // Implicit dùng cho việc findElement/ findElemenets
        // Explicit apply cho các điều kiện của element

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver, 10);

        driver.get("https://www.facebook.com/");

        // Explicit
        System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
        explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#email")));
        System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());

        // Implicit
        System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
        driver.findElement(By.cssSelector("input#email"));
        System.out.println("Thời gian kết thúc của implicit: " + getTimeStamp());

    }

    @Test
    public void TC_02_ElementNotFound_Implicit () {
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get("https://www.facebook.com/");

        // Implicit
        System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
        try {
            driver.findElement(By.cssSelector("input#selenium"));
        } catch (Exception e) {
            System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
        }

        // Output
        // Có cơ chế tìm lại element sau mỗi 0,5s
        // Khi hết timeout sẽ đánh fail testcase này
        // Throw ra một exception: No such element


    }

    @Test
    public void TC_03_ElementNotFound_Implicit_Explicit () {
        //3.1 : Implicit = Explicit Timeout
        //3.2 : Implicit < Explicit Timeout
        //3.3 : Implicit > Explicit Timeout
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        explicitWait = new WebDriverWait(driver, 10);

        driver.get("https://www.facebook.com/");

        // Implicit
        System.out.println("Thời gian bắt đầu của implicit: " + getTimeStamp());
        try {
            driver.findElement(By.cssSelector("input#selenium"));
        } catch (Exception e) {
            System.out.println("Thời gian kết thúc của implicit: " + getTimeStamp());
        }


        // Explicit
        System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
        } catch (Exception e) {
            System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
        }


    }

    @Test
    public void TC_03_Element_Not_Found_Explicit () {
        explicitWait = new WebDriverWait(driver, 5);
        driver.get("https://www.facebook.com/");

        // Eplicit - By là tham số nhận vào của hàm explicit - visibilityOfElementLocated(By)
        // Implicit = 0
        // Tổng time = Explicit

        System.out.println("Thời gian bắt đầu của explicit: " + getTimeStamp());
        try {
            explicitWait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("input#selenium")));
        } catch (Exception e) {
            System.out.println("Thời gian kết thúc của explicit: " + getTimeStamp());
        }

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

        driver.quit();
    }
}