package pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import testsuitebase.Suitebase;
import utility.SeleniumUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class HomeScreen {
	public static Logger add_log = Logger.getLogger("rootLogger");
	final int waitTime = 120;
	WebDriver driver;

	public HomeScreen(WebDriver driver) {
		this.driver = driver;

	}

	public void exceptionLogWrite(Exception e) {

		Suitebase.failCaseLog.put(Suitebase.subTestCaseNo, e.getMessage() + " ");
		Suitebase.subCaseResult.put(Suitebase.subTestCaseNo, "Fail");
		Suitebase.softAssert.assertTrue(false);
		driver.navigate().refresh();
	}

	public ArrayList<String> method1(ArrayList<String> validation, String testCase) {
		// *add code to validate TC*
		List<WebElement> el = driver.findElements(By.xpath("//input"));

		int count = 0;
		ArrayList<String> xPathList = new ArrayList<>();
		String testString;
		for (WebElement e : el) {
			System.out.println(e.getTagName() + "    " + e.getText());

			testString = SeleniumUtils.generateXPATH(e, "");
			xPathList.add(testString);
			count++;

		}
		System.out.println(count);

		SeleniumUtils.WritePASSLog("TEst extend reports", testCase);
		System.out.println(xPathList);

		return validation;
	}

}
