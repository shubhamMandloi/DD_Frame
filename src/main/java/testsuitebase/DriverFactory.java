package testsuitebase;

import java.net.URL;
import java.util.HashMap;

import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

public class DriverFactory {
	private DriverFactory() {

	}

	private static DriverFactory instance = new DriverFactory();

	public static DriverFactory getInstance() {
		return instance;

	}

	ThreadLocal<WebDriver> driver = new ThreadLocal<WebDriver>() {

		@Override
		protected WebDriver initialValue() {
			System.setProperty("WebDriver.chrome.driver",
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

			return new ChromeDriver(cap);

			// for remote run
			/*-
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.addArguments("--test-type");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			options.setExperimentalOption("useAutomationExtension", false);
			capabilities.setBrowserName("chrome");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);
			
			RemoteWebDriver remoteDriver = null;
			try {
				remoteDriver = new RemoteWebDriver(new URL("http://10.106.94.44:8888/wd/hub"), capabilities);
				Thread.sleep(1500);
			} catch (Exception e) {
				// catch (MalformedURLException | InterruptedException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());
				
			}
			return (WebDriver) remoteDriver;
			*/

		}

	};

	ThreadLocal<WebDriver> remoteDriver = new ThreadLocal<WebDriver>() {

		@Override
		protected WebDriver initialValue() {
			System.setProperty("WebDriver.chrome.driver",
					System.getProperty("user.dir") + "//src//test//resources//browserdrivers//chromedriver.exe");
			String downloadFilePath = System.getProperty("user.dir") + "/src/test/resources/excelfiles/Downloads/";
			HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
			chromePrefs.put("profile.default_Content_settings.popups", 0);
			chromePrefs.put("download.default_directory", downloadFilePath);
			// for remote run
			DesiredCapabilities capabilities = DesiredCapabilities.chrome();
			ChromeOptions options = new ChromeOptions();
			options.setExperimentalOption("prefs", chromePrefs);
			options.addArguments("--test-type");
			options.addArguments("disable-infobars");
			options.addArguments("--start-maximized");
			options.setExperimentalOption("useAutomationExtension", false);
			capabilities.setBrowserName("chrome");
			capabilities.setPlatform(Platform.WINDOWS);
			capabilities.setCapability(ChromeOptions.CAPABILITY, options);

			RemoteWebDriver remoteDriver = null;
			try {
				remoteDriver = new RemoteWebDriver(new URL("http://10.106.94.44:8888/wd/hub"), capabilities);
				Thread.sleep(1500);
			} catch (Exception e) {
				// catch (MalformedURLException | InterruptedException e) {
				e.printStackTrace();
				System.out.println(e.getMessage());

			}
			return (WebDriver) remoteDriver;

		}

	};

	public WebDriver getDriver() {
		return driver.get();
	}

	public WebDriver getRemoteDriver() {
		return remoteDriver.get();
	}

	public void removeDriver() {
		driver.get().quit();
		driver.remove();
	}
}
