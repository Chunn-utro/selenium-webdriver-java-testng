package webdriver;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.util.List;
import java.util.concurrent.TimeUnit;

public class Topic_22_FindElement_FindElements {
    WebDriver driver;
    String projectPath = System.getProperty("user.dir");
    String osName = System.getProperty("os.name");

    @BeforeClass
    public void beforeClass() {
        if (osName.contains("Windows")) {
            System.setProperty("webdriver.gecko.driver", projectPath + "\\browserDrivers\\geckodriver.exe");
        } else {
            System.setProperty("webdriver.gecko.driver", projectPath + "/browserDrivers/geckodriver");
        }

        driver = new FirefoxDriver();
        driver.get("http://live.techpanda.org/index.php/customer/account/login");
        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.manage().window().maximize();

    }

    @Test
    public void TC_01_FindElement () {
        // - Tìm thấy duy nhất một element/ node
            // Thao tác trực tiếp lên node đó
            //  Vì nó tìm thấy nên không cần chờ hết timeout là 10s
            driver.findElement(By.cssSelector("input#email"));


        // - Tìm thấy nhiều hơn element/ node
            // Nó sẽ thao tác với node đầu tiên, không quan tâm các node còn lại
        driver.findElement(By.cssSelector("input[type='email']")).sendKeys("sherlock@gmail.com");


        // - Không tìm thấy element/ node nào
        // Có cơ chế tìm lại = nửa giây 0.5s sẽ tìm lại một lần
        // Nếu trong thời gian ang tìm mà thấy element thì thỏa mãn đk => pass
        // Nếu hết thời gian mà vẫn không tìm thấy
        // + Đánh fail testcase này tại step này
        // + Throw ra 1 ngoại lệ: NoSuchElementExcption
        driver.findElement(By.cssSelector("input[type='check']"));

    }

    @Test
    public void TC_02_FindElements () {
        // - Tìm thấy duy nhất một element/ node
        // Tìm thấy và lưu nó vào list = 1 element
        //  Vì nó tìm thấy nên không cần chờ hết timeout là 10s
        List<WebElement> elements = driver.findElements(By.cssSelector("input#email"));
        System.out.println("List element number = " + elements.size());

        // - Tìm thấy nhiều hơn element/ node
        // Tìm thấy và lưu nó vào list = element tương ứng
        elements = driver.findElements(By.cssSelector("input[type='email']"));
        System.out.println("List element number = " + elements.size());


        // - Không tìm thấy element/ node nào
        // Có cơ chế tìm lại = nửa giây 0.5s sẽ tìm lại một lần
        // Nếu trong thời gian ang tìm mà thấy element thì thỏa mãn đk => pass
        // Nếu hết thời gian mà vẫn không tìm thấy
        // + Không đánh fail testcase + vẫn chạy step tiếp theo
        // + Trả về 1 list rỗng (empty) = 0
        elements = driver.findElements(By.cssSelector("input[type='check']"));
        System.out.println("List element number = " + elements.size());

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
