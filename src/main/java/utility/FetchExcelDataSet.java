/**
 * 
 */
package utility;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.comparator.LastModifiedFileComparator;
import org.apache.commons.io.filefilter.WildcardFileFilter;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
/*import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import bsh.org.objectweb.asm.Label;
import okhttp3.internal.http2.Header;*/

/**
 * @author spart
 *
 */
public class FetchExcelDataSet {
	HashMap<Integer, LinkedHashMap<String, String>> hashDataSet = new HashMap<Integer, LinkedHashMap<String, String>>();
	public ArrayList<HashMap<String, String>> xlsxDataSet = null;
	public HashMap<String, ArrayList<String>> xlsxDataSet2 = null;

	@SuppressWarnings("resource")
	public HashMap<Integer, LinkedHashMap<String, String>> makeTestData(String strExcelPath, String sheetName,
			String testCaseName) {
		FetchExcelDataSet fetchDataSet = new FetchExcelDataSet();
		HSSFSheet excelSheet = null;
		try {
			FileInputStream excelFileStream = new FileInputStream(strExcelPath);
			HSSFWorkbook excelWorkbook = new HSSFWorkbook(excelFileStream);
			excelSheet = excelWorkbook.getSheet(sheetName);
			int numRows = excelSheet.getLastRowNum();
			int columnIndex = -1;
			for (int count = 0; count < excelSheet.getRow(0).getLastCellNum(); count++) {
				if (excelSheet.getRow(0).getCell(count).getStringCellValue().equalsIgnoreCase("TestCaseName")) {
					columnIndex = count;
					break;
				}
			}
			int rowNo = 1;
			for (int rowCount = 1, validRows = 1; rowCount <= numRows; rowCount++) {
				if (excelSheet.getRow(rowCount).getCell(columnIndex).getStringCellValue()
						.equalsIgnoreCase(testCaseName + rowNo)) {
					hashDataSet.put(validRows - 1, fetchDataSet.getRowData(excelSheet, rowCount));
					validRows++;
				}
				rowNo++;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return hashDataSet;
	}

	private LinkedHashMap<String, String> getRowData(HSSFSheet excelSheet, int rowCount) {
		LinkedHashMap<String, String> hashRowData = new LinkedHashMap<String, String>();
		HSSFRow headerRow = excelSheet.getRow(0);
		HSSFRow row = excelSheet.getRow(rowCount);
		int totalInputValues = row.getLastCellNum();
		for (int cellCount = 0; cellCount < totalInputValues; cellCount++) {
			HSSFCell headerCell = headerRow.getCell(cellCount);
			// HSSFCell cell = row.getCell(cellCount,
			// org.apache.poi.ss.usermodel.Row.CREATE_NULL_AS_BLANK);
			HSSFCell cell = row.getCell(cellCount);
			// cell.setCellType(Cell.CELL_TYPE_STRING);

			hashRowData.put(headerCell.getStringCellValue(), cell.getStringCellValue());
		}

		return hashRowData;
	}

	private LinkedHashMap<String, String> getData(HashMap<Integer, LinkedHashMap<String, String>> hashMap,
			int rowNumber) {
		return hashMap.get(rowNumber);

	}

	public Object[][] getDataSetAsObjectArray(String strExcelPath, String sheetName, String testCaseName) {
		HashMap<Integer, LinkedHashMap<String, String>> hashDataSet = makeTestData(strExcelPath, sheetName,
				testCaseName);

		Object[][] objArray = new Object[hashDataSet.size()][1];
		for (int i = 0; i < hashDataSet.size(); i++) {
			objArray[i][0] = getData(hashDataSet, i);
		}
		return objArray;
	}

	@SuppressWarnings("resource")
	public ArrayList<HashMap<String, String>> getxlsxData(String strExcelPath, String sheetName) throws IOException {
		XSSFSheet excelSheet = null;
		FileInputStream excelFileStream = new FileInputStream(strExcelPath);
		XSSFWorkbook excelWorkbook = new XSSFWorkbook(excelFileStream);
		excelSheet = excelWorkbook.getSheet(sheetName);
		int numRows = excelSheet.getLastRowNum();
		HashMap<String, String> hashRowData = null;
		XSSFRow headerRow = excelSheet.getRow(1);
		int totalInputValues = headerRow.getLastCellNum();
		xlsxDataSet = new ArrayList<HashMap<String, String>>();

		for (int i = 2; i <= numRows; i++) {
			hashRowData = new HashMap<String, String>();
			XSSFRow row = excelSheet.getRow(i);
			for (int count = 0; count < totalInputValues; count++) {
				XSSFCell headerCell = headerRow.getCell(count);
				XSSFCell cell = row.getCell(count);
				// cell.setCellType(Cell.CELL_TYPE_STRING);
				hashRowData.put(headerCell.getStringCellValue(), cell.getStringCellValue());
			}
			xlsxDataSet.add(hashRowData);
		}
		return xlsxDataSet;
	}

	@SuppressWarnings("resource")
	public HashMap<String, ArrayList<String>> getxlsData2(String strExcelPath, String sheetName) throws IOException {
		XSSFSheet excelSheet = null;
		FileInputStream excelFileStream = new FileInputStream(strExcelPath);
		XSSFWorkbook excelWorkbook = new XSSFWorkbook(excelFileStream);
		excelSheet = excelWorkbook.getSheet(sheetName);
		int numRows = excelSheet.getLastRowNum();
		ArrayList<String> hashRowData = null;
		XSSFRow headerRow = excelSheet.getRow(1);
		int totalInputValues = headerRow.getLastCellNum();
		xlsxDataSet2 = new HashMap<String, ArrayList<String>>();

		for (int i = 0; i < totalInputValues;) {
			hashRowData = new ArrayList<String>();

			for (int j = 2; j < numRows; j++) {
				XSSFRow row = excelSheet.getRow(j);
				XSSFCell cell = row.getCell(i);
				DataFormatter df = new DataFormatter();
				String cellValue = df.formatCellValue(cell);
				if (cellValue.length() == 0) {
					cellValue = "blank";
				}
				hashRowData.add(cellValue);

			}
			i++;
			xlsxDataSet2.put("column" + i, hashRowData);
		}
		return xlsxDataSet2;
	}

	@SuppressWarnings("resource")
	public ArrayList<String> getXlsxHeader(String strExcelPath, String sheetName) throws IOException {
		XSSFSheet excelSheet = null;
		FileInputStream excelFileStream = new FileInputStream(strExcelPath);
		XSSFWorkbook excelWorkbook = new XSSFWorkbook(excelFileStream);
		excelSheet = excelWorkbook.getSheet(sheetName);

		ArrayList<String> hashRowData = new ArrayList<String>();
		XSSFRow headerRow = excelSheet.getRow(1);
		int totalInputValues = headerRow.getLastCellNum();

		for (int i = 0; i < totalInputValues;) {
			XSSFCell cell = headerRow.getCell(i);
			DataFormatter df = new DataFormatter();
			String cellValue = df.formatCellValue(cell);
			if (cellValue.length() == 0) {
				cellValue = "blank";
			}
			hashRowData.add(cellValue);
		}
		return hashRowData;
	}

	public void writeToExcel(String fileName, String sheetName, String colName, String data) throws IOException {
		FileInputStream ipstr = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\ExcelFiles\\" + fileName + ".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(ipstr);
		ipstr.close();
		XSSFSheet ws = wb.getSheet(sheetName);
		XSSFRow suiteRow = ws.getRow(0);
		XSSFRow row;
		XSSFCell cell;
		int numRows = ws.getLastRowNum();
		int colNum = suiteRow.getLastCellNum();
		int colNumber = 0;
		for (int i = 0; i < colNum; i++) {
			if (suiteRow.getCell(i).getStringCellValue().equals(colName.trim()))
				colNumber = i;
		}
		row = ws.createRow(++numRows);
		cell = row.createCell(colNumber);
		cell.setCellValue(data);
		FileOutputStream opstr = new FileOutputStream(
				System.getProperty("user.dir") + "\\src\\ExcelFiles\\" + fileName + ".xlsx");
		wb.write(opstr);
		opstr.close();
	}

	public void writeCompareResultToExcel(String fileName, String sheetName, String ID1, String ID2,
			String mismatchColName, String data1, String data2) throws IOException {
		FileInputStream ipstr = new FileInputStream(
				System.getProperty("user.dir") + "\\src\\ExcelFiles\\" + fileName + ".xlsx");
		XSSFWorkbook wb = new XSSFWorkbook(ipstr);
		ipstr.close();
		XSSFSheet ws = wb.getSheet(sheetName);
		XSSFRow suiteRow = ws.getRow(0);
		XSSFRow row;
		XSSFCell cell;
		int numRows = ws.getLastRowNum();
		int colNum = suiteRow.getLastCellNum();
		int colNumber = 0;

		row = ws.createRow(++numRows);
		cell = row.createCell(0);
		cell.setCellValue(ID1);
		cell = row.createCell(1);
		cell.setCellValue(ID2);
		cell = row.createCell(2);
		cell.setCellValue(mismatchColName);
		cell = row.createCell(3);
		cell.setCellValue(String.valueOf(CellType.STRING));
		cell.setCellValue(data1);
		cell = row.createCell(4);
		cell.setCellValue(data2);
		FileOutputStream opstr = new FileOutputStream(
				System.getProperty("user.dir") + "\\src\\ExcelFiles\\" + fileName + ".xlsx");
		wb.write(opstr);
		opstr.close();

	}

	public String latestFileName() {
		File theNewestFile = null;
		File dir = new File(System.getProperty("user.dir") + "/src/test/resources/excelfiles/");
		FileFilter fileFiler = new WildcardFileFilter("*.xls");
		File[] files = dir.listFiles(fileFiler);
		if (files.length > 0) {
			Arrays.sort(files, LastModifiedFileComparator.LASTMODIFIED_REVERSE);
			theNewestFile = files[0];
		}
		return theNewestFile.toString();
	}

	public void reportLog(String srcFileName, String reportName, String extension) {

		File srcFile = new File(System.getProperty("user.dir") + "\\src\\test\\resources\\excelfiles\\" + srcFileName
				+ "." + extension);
		String destDir = System.getProperty("user.dir") + "\\src\\test\\resources\\reportLog";
		DateFormat dateFormat = new SimpleDateFormat("dd-MMM-yyyy__hh_mm_ssaa");
		String destFile = reportName + " - " + dateFormat.format(new Date()) + "." + extension;
		try {
			FileUtils.copyFile(srcFile, new File(destDir + "/" + destFile));

		} catch (IOException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}
	}
}