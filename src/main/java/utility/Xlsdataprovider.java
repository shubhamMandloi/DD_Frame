/**
 * 
 */
package utility;

import org.testng.annotations.DataProvider;

import java.lang.reflect.Method;

/**
 * @author spart
 *
 */
public class Xlsdataprovider {
	@DataProvider(name = "Home")
	public static Object[][] ContainsSearchFetchData(Method a) {
		FetchExcelDataSet excelDataset = new FetchExcelDataSet();
		Object[][] dataSet = excelDataset.getDataSetAsObjectArray(
				System.getProperty("user.dir") + "\\src\\test\\resources\\excelfiles\\DD_frame.xls", "Home",
				a.getName());
		return dataSet;

	}

	@DataProvider(name = "Upload")
	public static Object[][] ContainsSearchFetchData1(Method a) {
		FetchExcelDataSet excelDataset = new FetchExcelDataSet();
		Object[][] dataSet = excelDataset.getDataSetAsObjectArray(
				System.getProperty("user.dir") + "\\src\\test\\resources\\excelfiles\\DD_frame.xls", "Upload",
				a.getName());
		return dataSet;

	}

}