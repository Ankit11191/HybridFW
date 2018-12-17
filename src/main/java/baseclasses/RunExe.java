package baseclasses;

import java.util.HashMap;

import common.ReadProerties;
import reporting.Logging;

public class RunExe extends Logging{
	
	public static void killBrowser()
	{
		try {
			Runtime.getRuntime().exec("taskkill /f /im chromedriver.exe");
			logger1.info("All Previous active drivers have killed successfully");
			PublicContext.ConsolidatedReportforall=new HashMap<String, String>();
			ReadProerties.getObjectRepository("./PageElements");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
