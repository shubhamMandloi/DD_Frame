package testsuitebase;

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
import utility.Read_XLS;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;

public class Suitebase {

	public Read_XLS testCaseListExcelSearchContains ;
	public Read_XLS masterRuleSheetData ;
	public String decryptedPassword;
	public Logger add_Log ;
	public Properties config ;
	InheritableThreadLocal<WebDriver> driver = new InheritableThreadLocal<>();
	public CopyExcel copyExcel = new CopyExcel();
	public WebDriver existingChromeBrowser;
	public WebDriver existingMozilaBrowser;
	public WebDriver existingIEBrowser;

	public HashMap<String, String> URLs = null;

	public ArrayList<HashMap<String, String>> credentials = null;

	public HashMap<String, String> users = null;
	public String username = null;
	public String password = null;

	public static SoftAssert softAssert = new SoftAssert();
	public static HashMap<String, String> resultTest = new HashMap<>();
	public static HashMap<String, String> subCaseResult = new HashMap<>();
	public static HashMap<String, String> failCaseLog = new HashMap<>();
	public static HashMap<String, String> timeLoad = new HashMap<>();
	public static HashMap<String, List<String>> writeResult = new HashMap<>();
	public static List<String> values = new ArrayList<>();

	public static String subTestCaseNo = null;

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

	public void loadWebBrowser()  {
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
		String testBrowser = config.getProperty("testBrowser");
		if ("MOZILA".equals(testBrowser)) {
			driver.set(new FirefoxDriver());
			add_Log.info("Firefox Driver Instance loaded Successfully");
		} else if ("CHROME".equals(testBrowser)) {
			System.setProperty("webdriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//chromedriver.exe");
			String downloadFilePath = System.getProperty("user.dir") + "/src/test/resources/excelfiles/Downloads/";
			HashMap<String, Object> chromePrefs = new HashMap<>();
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
			options.merge(cap);
			driver.set(new ChromeDriver(options));

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
		} else if ("IE".equals(testBrowser)) {
			System.setProperty("webdriver.ie.driver",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//iedriver.exe");
			driver.set(new InternetExplorerDriver());
			add_Log.info("IE Driver Instance loaded successfully");
		} else if ("PHANTOM".equals(testBrowser)) {
			System.setProperty("phantomjs.binary.path",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//phantomjs.exe");
			add_Log.info("Phantom Driver Instance loaded successfully");
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
