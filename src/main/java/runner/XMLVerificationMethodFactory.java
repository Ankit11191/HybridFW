package runner;

import java.util.ArrayList;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import baseclasses.XMLTagsVerification;
import reporting.Logging;

public class XMLVerificationMethodFactory extends Logging{
	
	public static ArrayList<String> methods(String[] SelectAction, ArrayList<String> RowData)
	{
		ArrayList<String> returnData=new ArrayList<String>();
		String Action=SelectAction[1].toLowerCase();
		switch (Action) 
		{
			case "getparentnodeattribute":

				returnData = XMLTagsVerification.getParentNodeAttribute(SelectAction[0],RowData);
				break;
			
			case "getchildnodevalue":

				returnData = XMLTagsVerification.getChildNodeValue(SelectAction[0],RowData);
				break;
				
			case "getchildnodeattribute":

				returnData = XMLTagsVerification.getChildNodeAttribute(SelectAction[0],RowData);
				break;
				
			default:
				logger1.info("For "+Action+" Element "+SelectAction[0]+" is not available");
				PublicContext.ReportLogger.log(Status.FAIL, "For "+Action+" Element "+SelectAction[0]+" is not available");
				break;
		}
		return returnData;
	}
}
