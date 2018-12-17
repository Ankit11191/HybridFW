package common;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.Status;

import baseclasses.ByElement;
import baseclasses.PublicContext;
import reporting.Logging;

public class JavaScriptExecuter {

	static WebDriver driver;
	public static void executeScript(String element, String Command)
	{
		PublicContext.js=(JavascriptExecutor) PublicContext.drivreturn;
		PublicContext.js.executeScript(Command, ByElement.CheckElement(element));
		Logging.logger1.info("Java Script has Executed successfully at element loaction "+element);
		PublicContext.ReportLogger.log(Status.PASS, "Java Script has Executed successfully at element loaction "+element);
	}
}
