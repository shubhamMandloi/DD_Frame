/**
 * 
 */
package utility;

import java.io.FileInputStream;
import java.util.Properties;

/**
 * @author spart
 *
 */
public class CopyExcel extends SeleniumUtils {

	public void copyXlsFiles() {

		Properties Config = new Properties();

		FileInputStream fip;

		try {
			fip = new FileInputStream(System.getProperty("user.dir") + "\\src\\Config\\COnfig.properties");

			Config.load(fip);

		} catch (Exception e) {

			e.printStackTrace();
		}

		copyExcel(Config.getProperty("FileName"));

	}

	/**
	 * @param args
	 */
}
