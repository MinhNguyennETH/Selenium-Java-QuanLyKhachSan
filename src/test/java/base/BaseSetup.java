package base;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import java.time.Duration;

public class BaseSetup {
    public WebDriver driver;

    @BeforeClass
    public void setup() {

        // Test on Chrome
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();

        //Test on FireFox
//        WebDriverManager.firefoxdriver().setup();
//        driver = new FirefoxDriver();

        //Test on Edge
//        WebDriverManager.edgedriver().setup();
//        driver = new EdgeDriver();

        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(20));
        driver.manage().window().maximize();
    }

    @AfterClass
    public void tearDown() {
        driver.quit();// Đóng Chrome
    }
}
