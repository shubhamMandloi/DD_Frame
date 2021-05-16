/**
 *
 */
package utility;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.testng.Reporter;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Properties;

/**
 * @author spart
 *
 */
public class Read_XLS {
    public String fileLocation = null;
    public FileInputStream ipstr = null;
    public FileOutputStream opstr = null;
    public HSSFWorkbook wb = null;
    public HSSFSheet ws = null;
    //public WebDriver driver;
    public Properties prop;
    //public HashMap<String, String> qa;

    public Read_XLS(String fileLocation) {
        this.fileLocation = fileLocation;
        try {
            ipstr = new FileInputStream(fileLocation);
            wb = new HSSFWorkbook(ipstr);
            ws = wb.getSheetAt(1);
            ipstr.close();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    public Properties getObjectRepository(String filePath) {
        try {
            prop = new Properties();
            FileInputStream objFile = new FileInputStream(filePath);
            prop.load(objFile);

        } catch (Exception e) {
            // TODO: handle exception
            System.out.println(e.getMessage());
        }
        return prop;
    }

    public int retrieveNoOfRows(String wsName) {

        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1)
            return 0;
        else {
            ws = wb.getSheetAt(sheetIndex);
            return ws.getLastRowNum() + 1;
        }

    }

    public int retrieveNoOfCols(String wsName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        if (sheetIndex == -1)
            return 0;
        else {
            ws = wb.getSheetAt(sheetIndex);
            return ws.getRow(0).getLastCellNum();
        }
    }

    public String retrieveToRunFlag(String wsName, String colName, String rowName) {
        int sheetIndex = wb.getSheetIndex(wsName);
        int rowNum = retrieveNoOfRows(wsName);
        int colNum = retrieveNoOfCols(wsName);
        int rowNumber = -1, colNumber = -1;
        if (sheetIndex == -1)
            return "";
        else {
            HSSFRow suiteRow = ws.getRow(0);
            for (int i = 0; i < colNum; i++) {
                if (suiteRow.getCell(i).getStringCellValue().equals(colName.trim()))
                    colNumber = i;
            }
            if (colNumber == -1)
                return "";
            for (int i = 0; i < rowNum; i++) {
                HSSFRow suiteCol = ws.getRow(i);
                if (suiteCol.getCell(0).getStringCellValue().equals(rowName.trim()))
                    rowNumber = i;
            }
            if (rowNumber == -1)
                return "";

            HSSFRow row = ws.getRow(rowNumber);
            HSSFCell cell = row.getCell(colNumber);
            if (cell == null)
                return "";
            else
                return cell.getStringCellValue().toString();
        }
    }


	public String getEnvUrl(String wsName, String environment) throws IOException {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			int rowNumber = -1;
			String cellData = "";

			for (int i = 0; i < rowNum; i++) {
				HSSFRow row = ws.getRow(i);
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(environment)) {
					rowNumber = i;
					break;
				}
			}
			HSSFCell cell = ws.getRow(rowNumber).getCell(1);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				System.out.println("Cell is empty");
				Reporter.log("Cell is empty");
			} else {
				if (cell.getCellType() == CellType.STRING)
					cellData = cell.getStringCellValue();
			}
			return cellData;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "";
		}
	}

	public HashMap<String, String> getEnvUrl(String wsName) {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			HashMap<String, String> urlList = new HashMap<String, String>();
			ws = wb.getSheet(wsName);
			for (int i = 0; i < rowNum; i++) {
				HSSFRow row = ws.getRow(i);

				if (row.getCell(2).getStringCellValue().equalsIgnoreCase("Y"))
					urlList.put(row.getCell(0).getStringCellValue(), row.getCell(1).getStringCellValue());
			}
			return urlList;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}
	}

	public ArrayList<HashMap<String, String>> getLoginCredentials(String wsName, String role) {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			ArrayList<HashMap<String, String>> credentials = new ArrayList<HashMap<String, String>>();
			HashMap<String, String> users = new HashMap<String, String>();
			ws = wb.getSheet(wsName);
			for (int i = 0; i < rowNum; i++) {
				// ws = wb.getSheet(wsName);
				HSSFRow row = ws.getRow(i);
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(role)) {
					if (row.getCell(3).getStringCellValue().equalsIgnoreCase("Y")) {
						users.put("username", row.getCell(1).getStringCellValue());
						users.put("password", row.getCell(2).getStringCellValue());
						credentials.add(users);
					}

				}

			}

			return credentials;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return null;
		}

	}

	public String getRole(String wsName, String testCaseName) throws IOException {
		try {
			int rowNum = retrieveNoOfRows(wsName);
			int colNum = retrieveNoOfCols(wsName);
			int rowNumber = -1, colNumber = -1;
			String cellData = "";

			for (int i = 0; i < rowNum; i++) {
				HSSFRow row = ws.getRow(i);
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
					rowNumber = i;
					break;
				}
			}
			for (int i = 0; i < colNum; i++) {
				HSSFRow row = ws.getRow(i);
				if (row.getCell(0).getStringCellValue().equalsIgnoreCase("Role")) {
					colNumber = i;
					break;
				}
			}
			HSSFCell cell = ws.getRow(rowNumber).getCell(colNumber);
			if (cell == null || cell.getCellType() == CellType.BLANK) {
				System.out.println("Cell is empty");
				Reporter.log("Cell is empty");
			} else {
				if (cell.getCellType() == CellType.STRING)
					cellData = cell.getStringCellValue();
			}
			return cellData;
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			return "";
		}
	}

	public void saveExcelFile() throws Exception {
		opstr = new FileOutputStream(fileLocation);
		wb.write(opstr);
		opstr.close();
	}

    public  boolean writeResultTC_Result(String wsName, String colName, String testCaseName, String result) {
        try {
            int sheetIndex = wb.getSheetIndex(wsName);
            if (sheetIndex == -1)
                return false;
            int colNum = retrieveNoOfCols(wsName);
            System.out.println(colNum);
            int colNumber = -1, rowNumber = -1;
            int rowNum = retrieveNoOfRows(wsName);

            for (int i = 0; i < rowNum; i++) {
                HSSFRow row = ws.getRow(i);
                if (row.getCell(0).getStringCellValue().equalsIgnoreCase(testCaseName)) {
                    rowNumber = i;
                    break;
                }

            }
            HSSFRow suiteRow = ws.getRow(0);
            for (int i = 0; i < colNum; i++) {
                if (suiteRow.getCell(i).getStringCellValue().equals(colName.trim())) {
                    colNumber = i;
                    break;
                }
            }
            if (colNumber == -1)
                return false;
            System.out.println("Col number is " + colNumber + " Row number is " + rowNumber);

            int flag = 0;
            HSSFRow row = ws.getRow(rowNumber);

            if (colName.equalsIgnoreCase("TimeLoad")) {
                if (row.getCell(colNumber - 1).getStringCellValue().equalsIgnoreCase("PASS"))
                    flag = 1;
                if (row.getCell(colNumber - 1).getStringCellValue().equalsIgnoreCase("FAIL"))
                    flag = 2;
            }
            HSSFCell cell = row.getCell(colNumber);
            if (cell == null)
                cell = row.createCell(colNumber);
            cell.setCellValue(result);

            HSSFCellStyle style = wb.createCellStyle();
            if (result.equalsIgnoreCase("PASS") || flag == 1)
                style.setFillForegroundColor(IndexedColors.GREEN.getIndex());
            else {
                if (result.equalsIgnoreCase("FAIL") || flag == 2)
                    style.setFillForegroundColor(IndexedColors.RED.getIndex());
                else
                    style.setFillForegroundColor(IndexedColors.YELLOW.getIndex());
            }
            style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
            cell.setCellStyle(style);
            HSSFFont font = wb.createFont();
            font.setColor(IndexedColors.BLACK.getIndex());
            style.setFont(font);
            cell.setCellStyle(style);

            opstr = new FileOutputStream(fileLocation);
            wb.write(opstr);
            opstr.close();
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
            System.out.println(e.getMessage());
            return false;
        }
        return true;
    }


}