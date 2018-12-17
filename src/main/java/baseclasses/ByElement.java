package baseclasses;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

import com.aventstack.extentreports.Status;

import common.ReadProerties;
import reporting.Logging;

public class ByElement extends Logging {
	
	public static WebElement webElement(String elemantName)
	{
		WebElement element = null;
		if(ReadProerties.propsObjectsSplits(elemantName)[0].contains("CreateElemant("))
		{
			String value = ReadProerties.propsObjectsSplits(elemantName)[0].replaceAll("CreateElemant(", "");
			value = value.replace(")", "");
			String[] pathParts=value.split(",");
			String xpathValue="";
			for(int i=0;i<pathParts.length;i++)
			{
				if(ReadProerties.propsObjectsSplit(pathParts[i])==null)
				{
					xpathValue+=pathParts[i];
				}
				else
				{
					xpathValue+=ReadProerties.propsObjectsSplit(pathParts[i]);
				}
				System.out.println(xpathValue);
			}
			return PublicContext.drivreturn.findElement(By.xpath(xpathValue));
		}
		
		switch (ReadProerties.propsObjectsSplits(elemantName)[0].toUpperCase() ) {
		case "ID":
			element =PublicContext.drivreturn.findElement(By.id(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "XPATH":
			element = PublicContext.drivreturn.findElement(By.xpath(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "NAME":
			element = PublicContext.drivreturn.findElement(By.name(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "CSSSELECTOR":
			element = PublicContext.drivreturn.findElement(By.cssSelector(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "CLASSNAME":
			element = PublicContext.drivreturn.findElement(By.className(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "LINKTEXT":
			element = PublicContext.drivreturn.findElement(By.linkText(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "partialLinkText":
			element = PublicContext.drivreturn.findElement(By.partialLinkText(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;
		case "TAGNAME":
			element = PublicContext.drivreturn.findElement(By.tagName(ReadProerties.propsObjectsSplits(elemantName)[1]));
			break;

		default:
			logger1.info("No Such Elemant type is Found please check the element repositry");
			PublicContext.ReportLogger.log(Status.FAIL, "No Such Elemant Found"+ "\t"+"<a href='"+ScreenShot.getScreenhot()+"'>Screenshot</a>");
			break;
		}
		return element;
	}	
	
	public static void SendKeysTo(String elemantName, String valueToBePass)
	{
		String valueToBePassOriginal=valueToBePass;
		if(valueToBePass.startsWith("EncryptedPassword"))
		{
			valueToBePass=PasswordHandler.decode(valueToBePass);
		}
		webElement(elemantName).sendKeys(valueToBePass);
		logger1.info("'"+valueToBePassOriginal+"' has entered successfully at element loaction "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "'"+valueToBePassOriginal+"' has entered successfully at element loaction "+elemantName);
	}
		
	public static WebElement CheckElement(String elemantName)
	{
		return webElement(elemantName);
	}
	
	public static void Clear(String elemantName)
	{
		webElement(elemantName).clear();
	}
	public static void ClickOn(String elemantName)
	{
		webElement(elemantName).click();
		logger1.info("Successfully clicked on element loaction "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "Successfully clicked on element loaction "+elemantName);
	}
	
	
	
	public static void ClearOn(String elemantName)
	{
		webElement(elemantName).clear();	
		logger1.info("Successfully clicked on element loaction "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "Successfully clicked on element loaction "+elemantName);
	}
	
	public static String GetTextOn(String elemantName)
	{
		String txt= webElement(elemantName).getText();
		logger1.info("'"+txt+"' find on element loaction "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "'"+txt+"' find on element loaction "+elemantName);
		return txt;
	}
	
	public static void SelectFromDropDown(String elemantName,String ValueToBeSelect)
	{
		Select select= new Select(webElement(elemantName));
		List<WebElement> values = select.getOptions();
		for(WebElement element:values)
		{
			if(ValueToBeSelect.equals(element.getText()))
			{				
				select.selectByVisibleText(ValueToBeSelect);
				break;
			}
		}
		logger1.info("'"+ValueToBeSelect+"' find on element loaction "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "'"+ValueToBeSelect+"' find on element loaction "+elemantName);
	}
	
	public static void clickoncheckbox(String elemantName,String value)
	{
		Boolean any=Boolean.parseBoolean(value);
		if(!ByElement.IsCheckBoxEnabled(elemantName)==any)
		{
			ByElement.ClickOn(elemantName);
			logger1.info("Successfully clicked on CheckBox "+elemantName);
			PublicContext.ReportLogger.log(Status.PASS, "Successfully clicked on CheckBox "+elemantName);
		}

	}
	
	public static boolean IsCheckBoxEnabled(String elemantName)
	{
		boolean status = webElement(elemantName).isSelected();
		logger1.info(elemantName+" checkbox is clicked "+ status);
		PublicContext.ReportLogger.log(Status.PASS, elemantName+" checkbox is clicked "+ status);
		return status;
	}
	
	public static void Refresh()
	{
		PublicContext.drivreturn.navigate().refresh();
		logger1.info("Browser has refeshed successfully");
		PublicContext.ReportLogger.log(Status.PASS, "Browser has refeshed successfully");
	}
	
	public static void MoveTo(String elemantName)
	{
		((JavascriptExecutor) PublicContext.drivreturn).executeScript("arguments[0].scrollIntoView(true);", webElement(elemantName));
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		logger1.info("Successfully Switched to Default Frame");
		PublicContext.ReportLogger.log(Status.PASS, "Successfully Switched to Default Frame");
	}
	
	public static void EnterOn(String elemantName)
	{
		webElement(elemantName).sendKeys(Keys.ENTER);	
		logger1.info("Successfully clicked on keyboard enter button "+elemantName);
		PublicContext.ReportLogger.log(Status.PASS, "Successfully clicked on keyboard enter button "+elemantName);
	}
		
}