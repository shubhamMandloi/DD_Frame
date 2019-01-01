package testsuitebase;

import java.io.FileInputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.asserts.SoftAssert;

import utility.CopyExcel;
import utility.FetchExcelDataSet;
import utility.Read_XLS;
import utility.ScreenshotUtility;
import utility.SeleniumUtils;

public class Suitebase {

	public Read_XLS testCaseListExcelOne = null;
	public Read_XLS testCaseListExcelSearch = null;
	public Read_XLS testCaseListExcelSearchContains = null;
	public Read_XLS testCaseListExcelResultContains = null;
	public Read_XLS masterRuleSheetData = null;
	public Read_XLS masterRuleSheetData_Allocation = null;
	public Read_XLS masterRuleSheetData_Collateral = null;
	public String decryptedPassword = null;
	public Logger add_Log = null;
	public Properties config = null;
	InheritableThreadLocal<WebDriver> driver = new InheritableThreadLocal<WebDriver>();
	public String caseToRun = null;
	public SeleniumUtils utils;
	public ScreenshotUtility screenUtility = new ScreenshotUtility();
	public CopyExcel copyExcel = new CopyExcel();
	protected FetchExcelDataSet objFD = new FetchExcelDataSet();
	public String sysRating = null;
	public String IPRID = null;
	public WebDriver existingChromeBrowser;
	public WebDriver existingMozilaBrowser;
	public WebDriver existingIEBrowser;

	public HashMap<String, String> URLs = null;
	public HashMap<String, String> listOfRules = null;
	public HashMap<String, String> listOfRoles = null;
	public HashMap<String, String> resultData = null;

	public ArrayList<HashMap<String, String>> credentials = null;

	public HashMap<String, String> users = null;
	public String username = null;
	public String password = null;
	// public loginPage objLoginPage = new loginPage();
	public static SoftAssert softAssert = new SoftAssert();
	public static HashMap<String, String> resultTest = new HashMap<String, String>();
	public static HashMap<String, String> subCaseResult = new HashMap<String, String>();
	public static HashMap<String, String> failCaseLog = new HashMap<String, String>();
	public static HashMap<String, String> timeLoad = new HashMap<String, String>();
	public static HashMap<String, List<String>> writeResult = new HashMap<String, List<String>>();
	public static List<String> values = new ArrayList<String>();

	public static double finishExec, startExec, endExec, totalTimeExec;
	public static String subTestCaseNo = null;
	public static String centralLocationDownload = "C://users/spart/CentralDownload/";
	public static String centralLocationUpload = "C://users/spart/CentralUpload/";

	Read_XLS filePath = null;
	String sheetName = "RuleMaster";
	public DecimalFormat df = new DecimalFormat("#.##");

	public WebDriver getDriver() {
		return driver.get();
	}

	public void init() throws IOException {
		add_Log = Logger.getLogger("rootLogger");

		testCaseListExcelSearchContains = new Read_XLS(
				System.getProperty("user.dir") + "/src/test/resources/excelfiles/DD_frame.xls");
		masterRuleSheetData = new Read_XLS(
				System.getProperty("user.dir") + "/src/test/resources/excelfiles/DD_frame.xls");

		add_Log.info("All Excel Files Initialised Successfully");

		config = new Properties();
		FileInputStream fip = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\main\\resources\\config\\Config.properties");
		config.load(fip);
		add_Log.info("Config Properties file loaded Successfully");
	}

	public void loadWebBrowser() throws InterruptedException {
		if (config.getProperty("testBrowser").equalsIgnoreCase("Mozila") && existingMozilaBrowser != null) {
			driver.set(existingMozilaBrowser);
			return;
		} else if (config.getProperty("testBrowser").equalsIgnoreCase("chrome") && existingChromeBrowser != null) {
			driver.set(existingChromeBrowser);
			return;
		} else if (config.getProperty("testBrowser").equalsIgnoreCase("IE") && existingIEBrowser != null) {
			driver.set(existingIEBrowser);
			return;
		}
		switch (config.getProperty("testBrowser").toUpperCase()) {
		case "MOZILA": {
			driver.set(new FirefoxDriver());
			add_Log.info("Firefox Driver Instance loaded Successfully");
			break;
		}
		case "CHROME": {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//chromedriver.exe");
			String downloadFilePath = System.getProperty("user.dir") + "/src/test/resources/excelfiles/Downloads/";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_Content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilePath);
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--start-maximized");
			options.setExperimentalOption("useAutomationExtension", false);

			DesiredCapabilities cap = DesiredCapabilities.chrome();
			cap.setCapability("chrome.binary", "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe");
			cap.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			cap.setCapability(ChromeOptions.CAPABILITY, options);

			driver.set(new ChromeDriver(cap));

			// For Remote connection
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options1 = new ChromeOptions();
			options1.addArguments("--test-type");
			options1.addArguments("disable-infobars");
			options1.addArguments("--start-maximized");
			options1.setExperimentalOption("useAutomationExtension", false);
			capabilities.setBrowserName("chrome");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options1);

			add_Log.info("Chrome Driver Instance loaded successfully");
			break;
		}
		case "IE": {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//iedriver.exe");
			driver.set(new InternetExplorerDriver());
			add_Log.info("IE Driver Instance loaded successfully");
			break;
		}
		case "PHANTOM": {
			System.setProperty("phantomjs.binary.path",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//phantomjs.exe");
			add_Log.info("Phantom Driver Instance loaded successfully");
			break;
		}

		}

	}

	public void closeWebBrowser() {
		getDriver().quit();
	}

	public String getData(LinkedHashMap<String, String> data, String key) {
		if (data.get(key) != null && data.get(key).length() > 0)
			return data.get(key);
		else
			return "";
	}
}
