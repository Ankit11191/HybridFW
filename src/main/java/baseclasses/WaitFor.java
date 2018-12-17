package baseclasses;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import common.ReadProerties;

public class WaitFor {
	
	public static void implicitlyWaitFor()
	{
		WebDriver drivreturn = PublicContext.drivreturn;
		drivreturn.manage().timeouts().implicitlyWait(Integer.parseInt(ReadProerties.propsObjectsSplit("ImplicitWaitTime")),TimeUnit.SECONDS) ;
		
	}
	
	public static void explicitlyWaitingforElememt(String ElementName)
	{
		WebDriverWait wait = new WebDriverWait(PublicContext.drivreturn,Integer.parseInt(ReadProerties.propsObjectsSplit("ExplicitWaitTime")));
		wait.until(ExpectedConditions.elementToBeClickable(ByElement.webElement(ElementName)));
	}
	public static void sleep(String timing)
	{
		try {
			Thread.sleep(Integer.parseInt(timing)*1000);
		} catch (NumberFormatException | InterruptedException e) {
			e.printStackTrace();
		}
	}
}