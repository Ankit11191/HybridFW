package validators;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

import reporting.Logging;

public class ReExecuteFailedTestCases implements IRetryAnalyzer  {
	private int retryCnt = 0;	
	private int maxCnt = 1;
	public boolean retry(ITestResult result) {
		if(retryCnt<maxCnt)
		{
			Logging.logger1.error("Retrying " + result.getName() + " again and the count is " + (retryCnt+1));
            retryCnt++;
            return true;
		}
		return false;
	}
}
