/**
 * 
 */
package utility;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import testsuitebase.Suitebase;

/**
 * @author spart
 *
 */
public class ScreenshotUtility implements ITestListener {

	String screenShotOnFail = "yes";
	String screenShotOnPass = "no";
	WebDriver driver;

	public void onStart(ITestContext tr) {
		System.out.println("Start");
	}

	public void onFinish(ITestContext tr) {
		System.out.println("Finish");
	}

	public void onTestSuccess(ITestResult tr) {
		if (screenShotOnPass.equals("yes")) {
			captureScreenShot(tr, "pass");

		}
	}

	public void onTestFailure(ITestResult tr) {
		if (screenShotOnFail.equals("yes")) {
			captureScreenShot(tr, "fail");

		}
		System.out.println(tr.getName());
	}

	public void onTestStart(ITestResult tr) {
		System.out.println("onTestStart");
	}

	public void onTestSkipped(ITestResult tr) {
		System.out.println("onTestSkipped");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult tr) {
		System.out.println("onTestFailedButWithinSuccessPercentage");
	}

	public void captureScreenShot(ITestResult result, String status) {
		String destDir = "";
		String passFailMethod = result.getMethod().getRealClass().getSimpleName() + "."
				+ result.getMethod().getMethodName();
		File scrFile = ((TakesScreenshot) ((Suitebase) result.getInstance()).getDriver())
				.getScreenshotAs(OutputType.FILE);

		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");

		if (status.equalsIgnoreCase("fail")) {
			destDir = "src/test/resources/screenshots/Failures";
		} else if (status.equalsIgnoreCase("pass")) {
			destDir = "src/test/resources/screenshots/Success";
		}

		new File(destDir).mkdirs();
		String destFile = passFailMethod + " - " + dateFormat.format(new Date()) + ".jpeg";

		try {
			FileUtils.copyFile(scrFile, new File(destDir + "/" + destFile));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}