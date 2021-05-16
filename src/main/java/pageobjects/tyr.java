package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.util.concurrent.TimeUnit;

public class tyr {
    private static tyr obj;

    public static void main(String[] args) throws InterruptedException {


        System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir") + "\\src\\test\\resources\\browserdrivers\\chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.saucedemo.com/");
        driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        WebDriverWait exp_wait = new WebDriverWait(driver,10);

        WebElement login_txtBox = (exp_wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@id='user-name']"))));

         //login_txtBox = driver.findElement(By.xpath("//input[@id='user-name']"));

        //login_txtBox.sendKeys(Keys.ARROW_DOWN);
        JavascriptExecutor js = (JavascriptExecutor) driver;

        js.executeScript("arguments[0].setAttribute('value','ABCD')", driver.findElement(By.xpath("//input[@id='user-name']")));

        Thread.sleep(3000);
        Assert.assertEquals(login_txtBox.getAttribute("value"),"ABCD");
        Thread.sleep(3000);



        driver.close();


    }

}
