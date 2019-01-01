package testsuitebase;

import java.util.List;

import org.testng.TestNG;
import org.testng.xml.Parser;
import org.testng.xml.XmlSuite;

public class Driver {

	static Driver d1 = new Driver();

	public static void main(String[] args) {
		TestNG testng = new TestNG();
		String xmlFileName = "C:/Users/spart/eclipse=workspace/DD_frame/src/main/testng.xml";

		List<XmlSuite> suite;
		try {
			suite = (List<XmlSuite>) (new Parser(xmlFileName).parse());
			testng.setXmlSuites(suite);
			testng.run();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

	}
}
