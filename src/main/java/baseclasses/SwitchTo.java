package baseclasses;

import java.util.Set;

import com.aventstack.extentreports.Status;

import reporting.Logging;

public class SwitchTo extends Logging{
	public static void Window(String WindowName)
	{
		Set<String> WindowsName=PublicContext.drivreturn.getWindowHandles();
		
		for(String wName : WindowsName)
		{
			PublicContext.drivreturn.switchTo().window(wName);
			String titleName=PublicContext.drivreturn.getTitle();
			if(titleName.equals(WindowName))
			{
				logger1.info("Successfully Switched to window : " + titleName);
				PublicContext.ReportLogger.log(Status.PASS, "Successfully Switched to window : " + titleName);
				break;
			}
		}
	}

	public static void Frame(String FrameName)
	{
		PublicContext.drivreturn.switchTo().frame(FrameName);
		logger1.info("Successfully Switched to Frame : " + FrameName);
		PublicContext.ReportLogger.log(Status.PASS, "Successfully Switched to Frame : " + FrameName);
	}
	public static void DefaultFrame()
	{
		PublicContext.drivreturn.switchTo().defaultContent();
		logger1.info("Successfully Switched to Default Frame");
		PublicContext.ReportLogger.log(Status.PASS, "Successfully Switched to Default Frame");
	}
}
