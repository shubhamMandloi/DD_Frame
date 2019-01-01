/**
 * 
 */
package utility;

import java.io.File;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.UnhandledAlertException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.Reporter;

import testsuitebase.Suitebase;
import testsuitebase.TestResultStatus;

/**
 * @author spart
 *
 */
public class SeleniumUtils {
	public static Logger add_Log = Logger.getLogger("rootLogger");
	public static int explicitWaitTime = 60;
	public static boolean checkMandatoryField(WebDriver driver, By element) {
		if (driver.findElement(element).getCssValue("background-color").equals("rbga(249,251,185,1)"))
			return true;
		else
			return false;
	}
	public static void clearText(WebDriver driver, By locator) {
		try {
			waitForVisibilityOfElement(driver, locator, explicitWaitTime);
			driver.findElement(locator).clear();
		} catch (Exception e) {
			add_Log.info("Failed for clear text of element");
			Reporter.log("Failed for clear text of element<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void clearText(WebDriver driver, WebPageElements ele) {
		try {
			WebElement element = getWebElement(driver, ele);
			element.clear();
		} catch (Exception e) {
			add_Log.info("Failed for clear text of " + ele.getName());
			Reporter.log("Failed for clear text of " + ele.getName() + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void click(WebDriver driver, By element, String elementDesc) {

		try {
			waitForVisibilityOfElement(driver, element, explicitWaitTime);
			waitForClickabilityOfElement(driver, element, explicitWaitTime);
			driver.findElement(element).click();
			add_Log.info("Successfully clicked on the " + elementDesc);
			Reporter.log("Successfully clicked on the " + elementDesc + "<BR>");

		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("argument[0].click();", element);
			} catch (Exception e2) {
				add_Log.info("Failed to click on the " + elementDesc);
				Reporter.log("Failed to click on the " + elementDesc + "<BR>");

				e2.printStackTrace();
				TestResultStatus.TestFail = true;
				System.out.println(e2.getMessage());
				Assert.fail();
			}
		}
	}

	public static void click(WebDriver driver, WebElement element) {
		try {
			element.click();
			add_Log.info("Successfully clicked on" + element);
			Reporter.log("Successfully clicked on" + element + "<BR>");

		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("argument[0].click();", element);
			} catch (Exception e2) {
				add_Log.info("Failed to click on " + element);
				Reporter.log("Failed to click on " + element + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}
		}

	}

	public static void click(WebDriver driver, WebElement element, String elementDesc) {

		try {
			element.click();
			add_Log.info("Successfully clicked on the " + elementDesc);
			Reporter.log("Successfully clicked on the " + elementDesc + "<BR>");

		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("argument[0].click();", element);
			} catch (Exception e2) {
				add_Log.info("Failed to click on the " + elementDesc);
				Reporter.log("Failed to click on the " + elementDesc + "<BR>");

				TestResultStatus.TestFail = true;
				e2.printStackTrace();
				System.out.println(e2.getMessage());
				Assert.fail();

			}
		}
	}

	public static void doubleClick(WebDriver driver, By locator, String eleDesc) {
		try {
			WebElement element = getElement(driver, locator);
			Actions actDoubleClick = new Actions(driver);
			actDoubleClick.doubleClick(element).perform();
			add_Log.info("Sucessfully double Click on element " + element + ".");
			Reporter.log("Sucessfully double Click on element " + element + ".");
		} catch (Exception e) {
			add_Log.info("Failed for double Click on " + eleDesc);
			Reporter.log("Failed for double Click on " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void doubleClick(WebDriver driver, WebElement element, String eleDesc) {
		try {
			Actions actDoubleClick = new Actions(driver);
			actDoubleClick.doubleClick(element).perform();
			add_Log.info("Double Click on " + eleDesc);
			Reporter.log("Double Click on " + eleDesc + "<BR>");

		} catch (Exception e) {
			add_Log.info("Not able to Double Click on " + eleDesc);
			Reporter.log("Not able to Double Click on " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();

		}
	}

	public static void enterText(WebDriver driver, By locator, Keys key, String eleDesc) throws InterruptedException {

		try {
			waitForClickabilityOfElement(driver, locator, explicitWaitTime);
			driver.findElement(locator).clear();
			driver.findElement(locator).sendKeys(key);
			add_Log.info("Key-" + key + " entered in " + eleDesc + " textBox.");
			Reporter.log("Key-" + key + " entered in " + eleDesc + " textBox.");

		} catch (Exception e) {
			add_Log.info("Failed for enter key in " + eleDesc);
			Reporter.log("Failed for enter key in " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void enterText(WebDriver driver, By locator, String text, String eleDesc)
			throws InterruptedException {
		if (text != null) {
			try {
				if (isElementPresent(driver, locator)) {

					waitForClickabilityOfElement(driver, locator, explicitWaitTime);
					driver.findElement(locator).clear();
					driver.findElement(locator).sendKeys(text);
					add_Log.info(text + "entered in " + eleDesc + " textBox.");
					Reporter.log(text + "entered in " + eleDesc + " textBox.<BR>");
				} else {
					add_Log.info(eleDesc + " is not present");
					Reporter.log(eleDesc + " is not present<BR>");
				}
			} catch (Exception e) {
				add_Log.info("Failed for enter text in " + eleDesc);
				Reporter.log("Failed for enter text in " + eleDesc + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}

		} else {
			add_Log.info("Null Value in Excel for " + eleDesc);
			Reporter.log("Null Value in Excel for " + eleDesc);
		}

	}

	public static void enterText(WebDriver driver, WebElement element, Keys key, String eleDesc) {

		if (key != null) {
			try {
				waitForVisibilityOfElement(driver, element, explicitWaitTime);
				element.sendKeys(key);

			} catch (Exception e) {
				add_Log.info("Failed for enter key in " + eleDesc);
				Reporter.log("Failed for enter key in " + eleDesc + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}

		} else {
			add_Log.info("Null Value in Excel for " + eleDesc);
			Reporter.log("Null Value in Excel for " + eleDesc);
		}

	}

	public static void enterText(WebDriver driver, WebElement element, String text, String eleDesc) {

		if (text != null) {
			try {
				waitForVisibilityOfElement(driver, element, explicitWaitTime);
				element.sendKeys(text);

			} catch (Exception e) {
				add_Log.info("Failed for enter text in " + eleDesc);
				Reporter.log("Failed for enter text in " + eleDesc + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}

		} else {
			add_Log.info("Null Value in Excel for " + eleDesc);
			Reporter.log("Null Value in Excel for " + eleDesc);
		}

	}

	public static String getAlertText(WebDriver driver) {
		String alertText = null;
		try {
			Alert alert = driver.switchTo().alert();
			alertText = alert.getText();
			alert.accept();
			return alertText;
		} catch (Exception e) {
			add_Log.info("Alert not present  ");
			Reporter.log("Alert not present.<br>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
			return null;
		}
	}

	public static String getDate(String format) {
		return new SimpleDateFormat(format).format(new Date()).toString();
	}

	public static WebElement getElement(WebDriver driver, By locator) {
		return new WebDriverWait(driver, explicitWaitTime)
				.until(ExpectedConditions.visibilityOfElementLocated(locator));
	}

	public static String getEleText(WebDriver driver, By element) {
		return driver.findElement(element).getText();
	}

	public static String getFutureDate(int days, String format) {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.DATE, days);
		return dateFormatter.format(calendar.getTime());

	}

	public static String getFutureDate(String date, int days, String format) throws Exception {
		SimpleDateFormat dateFormatter = new SimpleDateFormat(format);
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormatter.parse(date));
		calendar.add(Calendar.DATE, days);
		return dateFormatter.format(calendar.getTime());

	}

	public static boolean getOption_chk(WebDriver driver, WebPageElements ele, String expOptions) {

		boolean result = false;
		try {
			List<WebElement> allOptions;
			if (ele.getLocator().equalsIgnoreCase("Xpath")) {
				int count = 0, i = 0;
				allOptions = driver.findElements(By.xpath(ele.getValue()));
				String[] expViewOptions = expOptions.split(",");
				for (WebElement option : allOptions) {
					if (expViewOptions[i].equals(option.getText())) {
						count++;
						if (count == allOptions.size())
							result = true;
					}
				}
			}
		} catch (Exception e) {
			add_Log.info("Not able to fetch Options" + ele.getName() + "Element");
			Reporter.log("Not able to fetch Options" + ele.getName() + "Element<br>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
			return false;
		}
		return result;
	}

	public static String getText(WebDriver driver, WebPageElements ele) {
		String text = null;
		try {
			WebElement element = null;
			element = getWebElement(driver, ele);
			text = element.getText();
		} catch (Exception e) {
			add_Log.info("Failed to get text for " + ele.getName());
			Reporter.log("Failed to get text for " + ele.getName() + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
		return text;
	}

	public static String getValue(WebDriver driver, WebPageElements ele) {
		String text = null;
		try {
			WebElement element = null;
			element = getWebElement(driver, ele);
			text = element.getAttribute("value");
		} catch (Exception e) {
			add_Log.info("Failed to get value for " + ele.getName());
			Reporter.log("Failed to get value for " + ele.getName() + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
		return text;
	}

	public static WebElement getWebElement(WebDriver driver, WebPageElements ele) {
		WebElement element = null;
		try {
			switch (ele.getLocator().toLowerCase()) {
			case "xpath": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(ele.getValue())));

				break;
			}
			case "id": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.id(ele.getValue())));

				break;
			}
			case "name": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.name(ele.getValue())));

				break;
			}
			case "linktext": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.linkText(ele.getValue())));
				break;
			}
			case "partiallinktext": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.partialLinkText(ele.getValue())));

				break;
			}
			case "classname": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.className(ele.getValue())));

				break;
			}
			case "tagname": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.tagName(ele.getValue())));

				break;
			}
			case "css": {
				element = new WebDriverWait(driver, explicitWaitTime)
						.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(ele.getValue())));
				break;
			}

			}
		} catch (NoSuchElementException e) {
			add_Log.info("Failed to find element : " + ele.getName());
			Reporter.log("Failed to find element : " + ele.getName() + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
		if (element == null) {
			TestResultStatus.TestFail = true;
			Assert.fail();
		}
		return element;

	}

	public static boolean isAlertPresent(WebDriver driver) {
		try {
			driver.switchTo().alert().accept();
			driver.getTitle();
			return true;
		} catch (UnhandledAlertException ex1) {
			return false;
			// TODO: handle exception
		}
	}

	public static boolean isCheckboxChecked(WebDriver driver, By element) {
		return driver.findElement(element).isSelected();
	}

	public static boolean isElementPresent(WebDriver driver, By locator) {
		if (driver.findElement(locator).isDisplayed())
			return true;
		else
			return false;

	}

	public static void mouseOver(WebDriver driver, By locator, String eleDesc) {
		WebElement element = getElement(driver, locator);
		Actions actMouseOver = new Actions(driver);
		try {
			actMouseOver.moveToElement(element).build().perform();
			add_Log.info("Mouse hover on element " + eleDesc);
			Reporter.log("Mouse hover on element " + eleDesc + "<BR>");
		} catch (Exception e) {
			add_Log.info("Failed to mouse over on " + eleDesc);
			Reporter.log("Failed to mouse over on " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static WebElement returnActiveElement(WebDriver driver, By locator) {
		List<WebElement> allListedElement = driver.findElements(locator);
		WebElement returnElement = null;
		for (WebElement isActiveElement : allListedElement) {
			if (isActiveElement.isDisplayed()) {
				returnElement = isActiveElement;
				break;
			}

		}
		return returnElement;
	}

	public static void rightClick(WebDriver driver, By locator, String eleDesc) {
		try {
			WebElement element = getElement(driver, locator);
			Actions actRightClick = new Actions(driver);
			actRightClick.contextClick(element).build().perform();
			add_Log.info("Sucessfully right Click on element " + eleDesc + ".");
			Reporter.log("Sucessfully right Click on element " + eleDesc + ".");
		} catch (Exception e) {
			add_Log.info("Failed for right Click on " + eleDesc);
			Reporter.log("Failed for right Click on " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void rightClick(WebDriver driver, WebElement locator, String eleDesc) {
		try {

			Actions actRightClick = new Actions(driver);
			actRightClick.contextClick(locator).build().perform();
			add_Log.info("Sucessfully right Click on element " + eleDesc + ".");
			Reporter.log("Sucessfully right Click on element " + eleDesc + ".");
		} catch (Exception e) {
			add_Log.info("Failed for right Click on " + eleDesc);
			Reporter.log("Failed for right Click on " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void scrollIntoView(WebDriver driver, By locator, String eleDesc) {
		try {
			WebElement element = getElement(driver, locator);
			((JavascriptExecutor) driver).executeScript("arguments[0],scrollIntoView(true);", element);

		} catch (Exception e) {
			add_Log.info("Failed to scroll into " + eleDesc);
			Reporter.log("Failed to scroll into " + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();

		}

	}

	public static void scrollIntoViewHorizontally(WebDriver driver, By locator, String eleDesc) {
		try {
			WebElement element = getElement(driver, locator);
			JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			jsExecutor.executeScript("arguments[0].scrollIntoView();", element);
		} catch (Exception e) {
			add_Log.info("Failed to Scrolling on element" + eleDesc);
			Reporter.log("Failed to Scrolling on element" + eleDesc + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
	}

	public static void selectDropdown(WebDriver driver, WebPageElements ele, String text) {
		if (text.length() > 0) {
			try {
				new Select(getWebElement(driver, ele)).selectByVisibleText(text);
				add_Log.info("Set value " + text + " on element : " + ele.getName());
				Reporter.log("Set value " + text + " on element : " + ele.getName() + "<BR>");

			} catch (Exception e) {
				add_Log.info("Failed to set text " + text + " on element : " + ele.getName());
				Reporter.log("Failed to set text " + text + " on element : " + ele.getName() + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}
		} else {
			add_Log.info("No value to select on element : " + ele.getName());
			Reporter.log("No value to select on element : " + ele.getName() + "<BR>");

		}
	}

	public static void selectDropdownByValue(WebDriver driver, WebPageElements ele, String text) {
		if (text.length() > 0) {
			try {
				new Select(getWebElement(driver, ele)).selectByValue(text);
				add_Log.info("Set value " + text + " on element : " + ele.getName());
				Reporter.log("Set value " + text + " on element : " + ele.getName() + "<BR>");

			} catch (Exception e) {
				add_Log.info("Failed to set value " + text + " on element : " + ele.getName());
				Reporter.log("Failed to set value " + text + " on element : " + ele.getName() + "<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}
		} else {
			add_Log.info("No value to select on element : " + ele.getName());
			Reporter.log("No value to select on element : " + ele.getName() + "<BR>");

		}
	}

	public static void sendKeyElement(WebDriver driver, WebPageElements ele, String expInput) {
		WebElement element = null;
		try {
			if (ele.getLocator().equalsIgnoreCase("xpath")) {
				element = getWebElement(driver, ele);
				element.sendKeys(expInput);
			}
		} catch (Exception e) {
			try {
				JavascriptExecutor executor = (JavascriptExecutor) driver;
				executor.executeScript("arguments[0].click();", ele);
			} catch (Exception e2) {
				add_Log.info("Not able to SendKeys " + expInput + " on" + ele.getName() + "Element");
				Reporter.log("Not able to SendKeys " + expInput + " on" + ele.getName() + "Element<BR>");
				TestResultStatus.TestFail = true;
				e.printStackTrace();
				System.out.println(e.getMessage());
				Assert.fail();
			}
			// TODO: handle exception
		}
	}

	public static void switchToPopup(WebDriver driver, WebElement element) {
		try {
			click(driver, element);
			driver.switchTo().window(driver.getWindowHandle());

		} catch (Exception e) {

			add_Log.info("Failed to switch popup");
			Reporter.log("Failed to switch popup<BR>");
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}

	public static void verifyMessage(WebDriver driver, WebPageElements ele, String expectedMsg) {
		getWebElement(driver, ele);
		if (isAlertPresent(driver)) {
			if (!(getAlertText(driver).trim()).equalsIgnoreCase(expectedMsg)) {
				add_Log.info(getAlertText(driver).trim() + " does not match with " + expectedMsg);
				Reporter.log(getAlertText(driver).trim() + " does not match with " + expectedMsg + ".<br>");
				TestResultStatus.TestFail = true;
				Assert.fail();
			}
		} else if (expectedMsg.length() > 0) {
			if (!(getWebElement(driver, ele).getText()).equalsIgnoreCase(expectedMsg)) {
				add_Log.info(getAlertText(driver).trim() + " does not match with " + expectedMsg);
				Reporter.log(getAlertText(driver).trim() + " does not match with " + expectedMsg + ".<br>");
				TestResultStatus.TestFail = true;
				Assert.fail();

			}
		} else {
			add_Log.info(ele.getName() + "=value in null in InputData excel sheet");
			Reporter.log(ele.getName() + "=value in null in InputData excel sheet.<br>");
			TestResultStatus.TestFail = true;
			Assert.fail();

		}
	}

	public static boolean verifyTextPresent(WebDriver driver, WebPageElements ele, String expText) {
		if (!(getText(driver, ele)).equalsIgnoreCase(expText)) {
			add_Log.info(getText(driver, ele).trim() + " does not match with " + expText);
			Reporter.log(getText(driver, ele).trim() + " does not match with " + expText + ".<br>");
			TestResultStatus.TestFail = true;
			Assert.fail();
			return false;
		} else {
			add_Log.info(getText(driver, ele).trim() + " does not match with " + expText);
			Reporter.log(getText(driver, ele).trim() + " does not match with " + expText + ".<br>");
			return true;
		}
	}

	public static boolean verifyValuePresent(WebDriver driver, WebPageElements ele, String expText) {
		if (!(getValue(driver, ele)).equalsIgnoreCase(expText)) {
			add_Log.info(getValue(driver, ele).trim() + " does not match with " + expText);
			Reporter.log(getValue(driver, ele).trim() + " does not match with " + expText + ".<br>");
			TestResultStatus.TestFail = true;
			Assert.fail();
			return false;
		} else {
			add_Log.info(getValue(driver, ele).trim() + " does not match with " + expText);
			Reporter.log(getValue(driver, ele).trim() + " does not match with " + expText + ".<br>");
			return true;
		}

	}

	public static void waitForClickabilityOfElement(WebDriver driver, By locator, int time) {

		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(locator));

		} catch (Exception e) {
			add_Log.info("Failed for Clickability of element");
			Reporter.log("Failed for Clickability of element<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}

	}

	public static void waitForClickabilityOfElement(WebDriver driver, WebElement element, int time) {

		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.elementToBeClickable(element));

		} catch (Exception e) {
			add_Log.info("Failed for visibility of element");
			Reporter.log("Failed for visibility of element<BR>");

			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}

	}

	public static void waitForInvisibilityOfElement(WebDriver driver, By element, String eleDesc, int waitTime) {
		try {
			new WebDriverWait(driver, waitTime).until(ExpectedConditions.invisibilityOfElementLocated(element));
		} catch (Exception e) {
			add_Log.info(eleDesc + " took more time than expected to disappear on screen");
			Reporter.log(eleDesc + " took more time than expected to disappear on screen<Br>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();

		}
	}

	public static void waitForVisibilityOfElement(WebDriver driver, By locator, int time) {

		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));

		} catch (Exception e) {
			add_Log.info("Failed for visibility of element");
			Reporter.log("Failed for visibility of element<BR>");

			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}

	}

	public static void waitForVisibilityOfElement(WebDriver driver, By element, String eleDesc, int waitTime) {
		try {
			new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
			add_Log.info(eleDesc + " took more time than expected to appear on screen");
			Reporter.log(eleDesc + " took more time than expected to appear on screen<Br>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();

		}
	}

	public static ArrayList<String> waitForVisibilityOfElement(WebDriver driver, By element, String eleDesc,
			int waitTime, ArrayList<String> validation) {
		try {
			new WebDriverWait(driver, waitTime).until(ExpectedConditions.visibilityOfElementLocated(element));
		} catch (Exception e) {
			add_Log.info(eleDesc + " took more time than expected to appear on screen");
			Reporter.log(eleDesc + " took more time than expected to appear on screen<Br>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			validation.add("Fail");
			// Assert.fail();

		}
		return validation;
	}

	public static void waitForVisibilityOfElement(WebDriver driver, WebElement element, int time) {

		try {
			new WebDriverWait(driver, time).until(ExpectedConditions.visibilityOf(element));

		} catch (Exception e) {
			add_Log.info("Failed for visibility of element");
			Reporter.log("Failed for visibility of element<BR>");

			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}

	}
	
	

	public static void WriteFAILLog(String message, String subTestCaseNo) {
		add_Log.info(message + ", Hence Validation Failed.");
		Reporter.log(message + ", Hence Validation Failed.<BR>");
		TestResultStatus.TestFail = true;
		Assert.fail();
		Suitebase.resultTest.put(subTestCaseNo, "Fail");
		Suitebase.failCaseLog.put(subTestCaseNo, message + ", Hence Validation Failed.");
	}

	public static void WritePASSLog(String message, String subTestCaseNo) {
		add_Log.info(message + ", Hence Validation Successful.");
		Reporter.log(message + ", Hence Validation Successful.<BR>");
	}

	public String element_Exit;

	public HashMap<String, String> metaData = new HashMap<String, String>();

	public Properties configUrl = new Properties();

	public void copyExcel(String fileName) {

		File srcFile = new File(
				System.getProperty("user.dir") + "\\src\\test\\resources\\ExcelFiles\\" + fileName + ".xls");
		String destDir = System.getProperty("user.dir") + "\\ReprtLog";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		String destFile = "Report - " + dateFormat.format(new Date()) + ".xls";
		try {
			FileUtils.copyFile(srcFile, new File(destDir + "/" + destFile));
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}

	public By getLocator(WebPageElements ele) {

		By element = null;
		try {
			switch (ele.getLocator().toLowerCase()) {
			case "xpath": {
				element = By.xpath(ele.getValue());
				break;
			}
			case "id": {
				element = By.id(ele.getValue());
				break;
			}
			case "name": {
				element = By.name(ele.getValue());
				break;
			}
			case "linktext": {
				element = By.linkText(ele.getValue());
				break;
			}
			case "partiallinktext": {
				element = By.partialLinkText(ele.getValue());
				break;
			}
			case "classname": {
				element = By.className(ele.getValue());
				break;
			}
			case "tagname": {
				element = By.tagName(ele.getValue());
				break;
			}
			case "css": {
				element = By.cssSelector(ele.getValue());
				break;
			}
			}
		} catch (Exception e) {
			add_Log.info("Failed to find element : " + ele.getName());
			Reporter.log("Failed to find element : " + ele.getName() + "<BR>");
			TestResultStatus.TestFail = true;
			e.printStackTrace();
			System.out.println(e.getMessage());
			Assert.fail();
		}
		if (element == null) {
			TestResultStatus.TestFail = true;
			Assert.fail();
		}
		return element;
	}
	
	
	public static String generateXPATH(WebElement childElement, String current) {
	    String childTag = childElement.getTagName();
	    if(childTag.equals("html")) {
	        return "/html[1]"+current;
	    }
	    WebElement parentElement = childElement.findElement(By.xpath("..")); 
	    List<WebElement> childrenElements = parentElement.findElements(By.xpath("*"));
	    int count = 0;
	    for(int i=0;i<childrenElements.size(); i++) {
	        WebElement childrenElement = childrenElements.get(i);
	        String childrenElementTag = childrenElement.getTagName();
	        if(childTag.equals(childrenElementTag)) {
	            count++;
	        }
	        if(childElement.equals(childrenElement)) {
	            return generateXPATH(parentElement, "/" + childTag + "[" + count + "]"+current);
	        }
	    }
	    return null;
	}

}