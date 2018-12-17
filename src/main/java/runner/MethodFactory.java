package runner;

import com.aventstack.extentreports.Status;

import baseclasses.ByElement;
import baseclasses.ByElements;
import baseclasses.CompareDate;
import baseclasses.PublicContext;
import baseclasses.StoreValues;
import baseclasses.SwitchTo;
import baseclasses.VerifyText;
import baseclasses.WaitFor;
import common.JavaScriptExecuter;
import reporting.Logging;
import validators.CallWebService;

public class MethodFactory extends Logging{
	
	public static void methods(String[] SelectAction)
	{
		String Action=SelectAction[1].toLowerCase();
		switch (Action) 
		{
			case "sendkeysto":
				ByElement.SendKeysTo(SelectAction[0], SelectAction[2]);
				break;
			
			case "clickon":
				ByElement.ClickOn(SelectAction[0]);
				break;
				
			case "clear":
				ByElement.Clear(SelectAction[0]);
				break;
				
			case "explicitlywaitingforelememt":
				WaitFor.explicitlyWaitingforElememt(SelectAction[0]);
				break;
				
			case "switchtowindow":
				SwitchTo.Window(SelectAction[2]);
				break;
				
			case "switchtoframe":
				SwitchTo.Frame(SelectAction[2]);
				break;
				
			case "switchtodefaultframe":
				SwitchTo.DefaultFrame();
				break;

			case "selectfromdropdown":
				ByElement.SelectFromDropDown(SelectAction[0], SelectAction[2]);
				break;
				
			case "storetext":
				StoreValues.Storetext(SelectAction[0], SelectAction[2]);
				break;
				
			case "eval":
				StoreValues.eval(SelectAction[2]);
				break;
								
			case "verifytext":
				VerifyText.verifyTextS1(SelectAction[0], SelectAction[2]);
				break;
				
			case "verifystring":
				VerifyText.verifyTextS(SelectAction[0], SelectAction[2]);
				break;

			case "clickoncheckbox":
				ByElement.clickoncheckbox(SelectAction[0], SelectAction[2]);
				break;
				
			case "ischeckboxenabled":
				ByElement.IsCheckBoxEnabled(SelectAction[0]);
				break;
				
			case "getsize":
				ByElements.Count(SelectAction[0], SelectAction[2]);
				break;

			case "gettexton":
				ByElement.GetTextOn(SelectAction[0]);
				break;
				
			case "verifyxml":
				XMLVerificationCall.verifyXML(SelectAction[0],SelectAction[2]);
				break;	
				
			case "refresh":
				ByElement.Refresh();
				break;
				
			case "enteron":
				ByElement.EnterOn(SelectAction[0]);
				break;

			case "comparedate":
				CompareDate.getDaysDifferance(SelectAction[2]);
				break;	

			case "sleep":
				WaitFor.sleep(SelectAction[2]);
				break;		

			case "callingwebservice":
				CallWebService.calling(SelectAction[2]);
			break;
			case "executescript":
				JavaScriptExecuter.executeScript(SelectAction[0],SelectAction[2]);
			break;
			default:
				logger1.info(Action+"  sorry No method");
				PublicContext.ReportLogger.log(Status.FAIL, Action+"  sorry No method");
				System.exit(0);
		}
	}
}
