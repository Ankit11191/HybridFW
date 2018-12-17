package runner;

import java.util.ArrayList;
import java.util.HashMap;

import org.testng.ITestContext;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;

import baseclasses.PublicContext;
import baseclasses.RunExe;
import baseclasses.ScreenShot;
import baseclasses.WaitFor;
import common.DriverConfig;
import common.ReadProerties;
import reporting.GenerateSpreadSheetReport;
import reporting.Logging;
import reporting.TestListener;
import validators.SheetValidation;

public class InternalExections extends Logging {
	int testcasecount=0;
	@BeforeSuite
	public void killOldBrowser(ITestContext context) {
		RunExe.killBrowser();
	}
	@BeforeTest
	public void getTestCount() {
		testcasecount=0;
	}

	@Parameters("BrowserName")
	@BeforeMethod
	public void beforeClass(ITestContext context, String BrowserName)
	{ 
		PublicContext.MargeTestCase=new ArrayList<ArrayList<String>>();
		SheetValidation.Validation(context.getCurrentXmlTest().getParameter("TestCaseFileName"));
		PublicContext.tempMapforHandelDataProvider=new HashMap<String, String>();
		PublicContext.drivreturn = DriverConfig.getInstance().driver(BrowserName);
		PublicContext.drivreturn.get(ReadProerties.propsObjectsSplit("APP_URL"));
		logger1.info("Application "+ReadProerties.propsObjectsSplit("APP_URL")+" has launched successfully");
		PublicContext.ReportLogger.log(Status.PASS, "Application "+ReadProerties.propsObjectsSplit("APP_URL")+" has launched successfully");
		WaitFor.implicitlyWaitFor();
	}
	@AfterMethod
	public void takeScreenShotOnFailure(ITestResult testResult, ITestContext context) {
		if (ITestResult.FAILURE == testResult.getStatus()) {
			PublicContext.ReportLogger.log(Status.FAIL, MarkupHelper.createLabel(testResult.getName() + " - Test Case Failed", ExtentColor.RED));
			PublicContext.ReportLogger.log(Status.FAIL, MarkupHelper.createLabel(testResult.getInstanceName() + " - Test Case Failed", ExtentColor.ORANGE));
			 
			ArrayList<String> step=new ArrayList<String>();
				step.add("Fail");
				step.add("");
				step.add("ScreenShot has captured at");
				step.add(ScreenShot.getScreenhot());				
				PublicContext.Reporting.add(step);
		}
		logger1.info("Browser has closed successfully");
		PublicContext.ReportLogger.log(Status.PASS, "Browser has closed successfully");
		DriverConfig.getInstance().closeBrowser();
		GenerateSpreadSheetReport.TestOutputinExcel(PublicContext.Reporting);
		logger1.info("---------------------------------------------------------------------------------------------------");
		++testcasecount;
		PublicContext.ConsolidatedReportforall.put(context.getCurrentXmlTest().getParameter("TestCaseFileName"),TestListener.test.get().getStatus().toString()+":"+ testcasecount);
	}
	@AfterSuite
	public void AfterSuit(ITestContext context) {
		System.out.println(PublicContext.ConsolidatedReportforall);
		
	}
}