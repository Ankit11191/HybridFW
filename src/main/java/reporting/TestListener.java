package reporting;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

import baseclasses.PublicContext;

public class TestListener implements ITestListener{

	private static String testCaseName;
    private static ExtentReports extent;
	public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();

	public void onTestStart(ITestResult result) {
		PublicContext.ReportLogger = extent.createTest(testCaseName);
		test.set(PublicContext.ReportLogger);
	}

	public void onTestSuccess(ITestResult result) {
        test.get().pass("Test passed");			
        PublicContext.ReportLogger = extent.createTest("Done all post-conditions and re-apply pre-conditions");
        }

	public void onTestFailure(ITestResult result) {
	        test.get().fail(result.getTestName());
	        PublicContext.ReportLogger = extent.createTest("Done all post-conditions and re-apply pre-conditions");
	}

	public void onTestSkipped(ITestResult result) {
        test.get().skip(result.getTestName());
        PublicContext.ReportLogger = extent.createTest("Done all post-conditions and re-apply pre-conditions");
	}

	public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        PublicContext.ReportLogger = extent.createTest("Done all post-conditions and re-apply pre-conditions");		
	}

	public void onStart(ITestContext context) {
		testCaseName = context.getName();
		extent = extentTest.createInstance(testCaseName);
		PublicContext.ReportLogger = extent.createTest("Checked all pre-conditions");
	}

	public void onFinish(ITestContext context) {
		extent.flush();
	}
}