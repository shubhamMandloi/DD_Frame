/**
 * 
 */
package utility;

/**
 * @author spart
 *
 */
public class SuiteUtility {
	public static boolean writeResultUtility(Read_XLS xls, String sheetName, String colName, int rowNumber,
			String result) {
		return xls.writeResult(sheetName, colName, rowNumber, result);
	}

	public static boolean writeResultUtility1(Read_XLS filePath, String sheetName, String colName, int rowNumber,
			String result) {
		return filePath.writeResult1(sheetName, colName, rowNumber, result);
	}

	public static boolean writeResultUtility2(Read_XLS xls, String sheetName, String colName, int rowNumber,
			int result) {
		return xls.writeResult2(sheetName, colName, rowNumber, result);
	}

	public static boolean writeResultUtility3(Read_XLS xls, String sheetName, String colName, String testCase,
			String result) {
		return xls.writeResultTC(sheetName, colName, testCase, result);
	}

	public static boolean writeResultUtility4(Read_XLS xls, String sheetName, String colName, String testCase,
			String result) {
		return xls.writeResultTC1(sheetName, colName, testCase, result);
	}

	public static boolean writeResultUtility5(Read_XLS xls, String sheetName, String colName, String testCase,
			String result) {
		return xls.writeResultTC_SubCaseResult(sheetName, colName, testCase, result);
	}

	public static boolean writeResultUtility6(Read_XLS xls, String sheetName, String colName, String testCase,
			String result) {
		return xls.writeResultTC_Result(sheetName, colName, testCase, result);
	}

	public static boolean writeResultUtility7(Read_XLS xls, String sheetName, int rowNum, String rowName,
			String colName) {
		return xls.writeResult2(sheetName, rowNum, rowName, colName);
	}
}