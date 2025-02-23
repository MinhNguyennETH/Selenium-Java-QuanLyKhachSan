package login;

import base.BaseSetup;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.time.Duration;

public class TestLogin extends BaseSetup {

    @DataProvider(name = "loginData")
    public Object[][] loginData() {
        return new Object[][]{
                {"09152", "*******", true},          // Case of successful login
                {"aa", "09152", false},                 // In case the login name is wrong
                {"09152", "hg", false},                 // In case the password name is wrong
                {"", "09152", false},                   // In case the login name is left blank, the correct password
                {"09152", "", false},                   // In case the password is left blank, the correct username
                {"", "", false},                        // In case both login name and password are left blank
                {"' OR '1'='1", "' OR '1'='1", false},  // Case of SQL injection for username and password
                {"admin' --", "admin", false},          // Case of SQL injection with comment
                {"' OR '1'='1' --", "Admin123", false}, // Case of SQL injection with OR condition
                {"' OR '1'='1' #", "Minhnguyen", false} // Another SQL injection variant
        };
    }

    @Test(dataProvider = "loginData")
    public void testLogin(String username, String password, boolean expectedResults) {

        driver.get("url");
        driver.findElement(By.name("Username")).sendKeys(username);
        driver.findElement(By.name("Password")).sendKeys(password);
        driver.findElement(By.id("SerExtraNet5_Membership_LoginPanel0_LoginButton")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        if (expectedResults) {
            WebElement successMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//b[contains(text(),'SerExtraNet5')]")));
            Assert.assertTrue(successMessage.isDisplayed(), "Expected login to succeeded, but it failed.");

        } else {
            WebElement errorMessage = wait.until(ExpectedConditions.visibilityOfElementLocated(By.name("Username")));
            Assert.assertTrue(errorMessage.isDisplayed(), "Expected login to fail, but it succeeded.");
        }
    }
}



