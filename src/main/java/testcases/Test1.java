package testcases;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.SkipException;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import pageobjects.HomeScreen;
import pageobjects.loginPage;
import testsuitebase.Suitebase;
import utility.Read_XLS;
import utility.SuiteUtility;

public class Test1 extends Suitebase {
	Read_XLS filePath = null;
	String testCaseName = null;
	public static String strFailureDesc = "";
	String sheetName = "Test1";
	String resultSheetName = "Result";
	public boolean testCasePass = true;
	public String fileLocation = null;
	public FileInputStream ipstr = null;
	public FileOutputStream opstr = null;
	public HSSFWorkbook wb = null;
	public HSSFSheet ws = null;
	public DecimalFormat df = new DecimalFormat("#.##");

	public boolean testFail = false;
	loginPage objLogin = null;
	HomeScreen objHome = null;
	int count = 0;
	String testCase = "";
	HashMap<String, String> param = new HashMap<String, String>();
	public boolean browserOpen = true;
	public static WebDriver driver = null;

	@BeforeClass(alwaysRun = true)
	public void setUp() throws Exception {
		init();
		filePath = masterRuleSheetData;
		URLs = masterRuleSheetData.getEnvUrl("Environment");
		System.out.println(URLs.size());
	}

	@Test(dataProvider = "Home", dataProviderClass = utility.Xlsdataprovider.class)
	public void TC1_Home(LinkedHashMap<String, String> data) throws Exception {

		testCaseName = getData(data, "TestCaseName");
		String caseToRun = getData(data, "CaseToRun");
		String role = getData(data, "Role");
		ArrayList<String> result = new ArrayList<String>();
		objLogin = PageFactory.initElements(getDriver(), loginPage.class);
		if (caseToRun.equalsIgnoreCase("N")) {
			System.out.println(testCaseName + " : CaseToRun = N , So Skipping Execution.");

			resultTest.put(testCaseName, "Skip");
			throw new SkipException(testCaseName + " is set to Skip");
		} else {
			for (String Key : URLs.keySet()) {
				URLs.get(Key);
				add_Log.info(URLs.get(Key));
				credentials = testCaseListExcelSearchContains.getLoginCredentials("User", role);
				for (int i = 0; i < credentials.size(); i++) {
					users = credentials.get(i);
					username = users.get("username");
					password = users.get("password");
					decryptedPassword = objLogin.decryptedUserPassword(password);
				}
				if (browserOpen) {
					loadWebBrowser();
					getDriver().get(URLs.get(Key));
					// objLogin.login(getDriver(), username, decryptedPassword, URLs.get(Key));
					objHome = PageFactory.initElements(getDriver(), HomeScreen.class);
					browserOpen = true;
				}
				try {
					System.out.println(testCaseName);
					double start = System.currentTimeMillis();
					ArrayList<String> resultList = objHome.method1(result, testCaseName);
					double end = System.currentTimeMillis();
					System.out.println("Took : " + ((end - start) / 1000) + "seconds");
					timeLoad.put(testCaseName, df.format((end - start) / 1000));

					if (resultList.contains("Fail"))
						resultTest.put(testCaseName, "Fail");
					else
						resultTest.put(testCaseName, "Pass");

				} catch (Exception e) {
					resultTest.put(testCaseName, "Fail");
					e.printStackTrace();
					System.out.println(e.getMessage());
				}

				if (!(getDriver() == null))
					getDriver().navigate().refresh();
			}

		}

	}

	@AfterMethod
	public void browserControl() {
		if (!(getDriver() == null))
			closeWebBrowser();
	}

	@SuppressWarnings("rawtypes")
	@AfterClass
	public void afterClass() throws InterruptedException {
		System.out.println("Result is : " + resultTest);
		for (Map.Entry m : resultTest.entrySet()) {
			String sheetName = "Home";
			add_Log.info(m.getKey().toString() + " : Reporting test data set line " + m.getKey().toString() + " as "
					+ m.getValue().toString() + " in Excel ");
			SuiteUtility.writeResultUtility6(filePath, sheetName, "Pass/Fail/Skip", m.getKey().toString(),
					m.getValue().toString());
		}
		for (Map.Entry m : timeLoad.entrySet()) {
			String sheetName = "Home";
			add_Log.info(m.getKey().toString() + " : Reporting test data set line " + m.getKey().toString() + " as "
					+ m.getValue().toString() + " in Excel ");
			SuiteUtility.writeResultUtility6(filePath, sheetName, "Pass/Fail/Skip", m.getKey().toString(),
					m.getValue().toString());
		}

	}
}
