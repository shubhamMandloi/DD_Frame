package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.testng.Reporter;
import utility.SeleniumUtils;
import utility.WebPageElements;

import java.io.IOException;

public class loginPage extends SeleniumUtils {
	WebPageElements txt_userName = new WebPageElements("username", "xpath", "//input[@id='user-name']");
	WebPageElements txt_password = new WebPageElements("password", "xpath", "//input[@id='password']");
	WebPageElements btn_login = new WebPageElements("login", "xpath", "//input[@id='login-button']");

	public void login(WebDriver driver, String username, String password, String url) throws IOException {

		driver.get(url);

		getWebElement(driver, txt_userName).sendKeys(username);
		getWebElement(driver, txt_password).sendKeys(password);
		Reporter.log("Login credentials entered for : " + username + "<br>");


		click(driver,getWebElement(driver, btn_login));

		//driver.findElement(By.xpath("//a[@id='login-button']")).click();

	}

	public String decryptedUserPassword(String encryptString) {
		StringBuilder sbEncrypted = new StringBuilder(encryptString);
		sbEncrypted.reverse();
		StringBuilder decryptedString = new StringBuilder();
		for (int i = 0; i < sbEncrypted.length(); i++)
			decryptedString.append((char) ((int) sbEncrypted.charAt(i) - 10));

		return decryptedString.toString();
	}

}
