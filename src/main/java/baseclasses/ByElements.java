package baseclasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import com.aventstack.extentreports.Status;

import common.ReadProerties;
import reporting.Logging;

public class ByElements extends Logging {
	
	public static List<WebElement> webElement(String elemantName)
	{
		List<WebElement> element = null;
		
		switch (ReadProerties.propsObjectsSplits(elemantName)[0].toUpperCase() ) {
		case "ID":
			element =PublicContext.drivreturn.findElements(By.id(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "XPATH":
			element = PublicContext.drivreturn.findElements(By.xpath(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "NAME":
			element = PublicContext.drivreturn.findElements(By.name(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "CSSSELECTOR":
			element = PublicContext.drivreturn.findElements(By.cssSelector(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "CLASSNAME":
			element = PublicContext.drivreturn.findElements(By.className(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "LINKTEXT":
			element = PublicContext.drivreturn.findElements(By.linkText(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "partialLinkText":
			element = PublicContext.drivreturn.findElements(By.partialLinkText(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "TAGNAME":
			element = PublicContext.drivreturn.findElements(By.tagName(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;

		default:
			logger1.info("No Such Elemant Found");
			PublicContext.ReportLogger.log(Status.FAIL, "No Such Elemant Found" + "\t"+"<a href='"+ScreenShot.getScreenhot()+"'>Screenshot</a>");
			break;
		}
		return element;
	}	

	
	public static int Count(String elemantName, String variableToStore)
	{
		int size= webElement(elemantName).size();
		logger1.info("Total "+size+" record/s found");
		PublicContext.ReportLogger.log(Status.PASS, "Total "+size+" record/s found");
		PublicContext.EvalMap.put(variableToStore,String.valueOf(size));
		return size;
	}
	
	public static List<WebElement> link(String elemantName, String paraMeter)
	{
		List<WebElement> webElementList = webElement(elemantName);
		logger1.info("Total "+webElementList.size()+" record/s found");
		PublicContext.ReportLogger.log(Status.PASS, "Total "+webElementList.size()+" record/s found");
		PublicContext.EvalMap.put(paraMeter, String.valueOf(webElementList.size()));
		return webElementList;
	}
}
