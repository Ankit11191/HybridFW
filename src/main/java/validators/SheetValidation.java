package validators;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import common.ReadFromExcelFile;
import reporting.Logging;

public class SheetValidation {

	public static ArrayList<ArrayList<String>> Validation(String SheetName){
		/*File fileName=new File(System.getProperty("user.dir") + File.separator +"SourceFiles"+File.separator+SheetName.split(";")[0]);
		if(!fileName.renameTo(fileName))
		{		
			Logging.logger1.error("TestCase File "+SheetName.split(";")[0]+" is open please close it");
			PublicContext.ReportLogger.log(Status.FAIL, "TestCase File "+SheetName.split(";")[0]+" is open please close it");
			System.exit(0);
		}*/
		Logging.logger1.info("Validating Test Case : "+SheetName);
		PublicContext.ReportLogger.log(Status.PASS, "Validating Test Case : "+SheetName);
		
		ArrayList<ArrayList<String>> TestCaseSteps = ReadFromExcelFile.getCellValue("SourceFiles"+File.separator+SheetName);
		int i=0;
		for(ArrayList<String> lst: TestCaseSteps)
		{
			String[] ivalue=lst.toArray(new String[0]);
			String[] stepDefination=Arrays.copyOfRange(ivalue, 1, ivalue.length);
			int actionCount=0;
			if("Y".equals(ivalue[0]))
			{		
				if(stepDefination[1].equalsIgnoreCase("commonsteps"))
				{
					Validation(stepDefination[2]);
				}
				for(int j=0;j<stepDefination.length;j++)
				{
					if(stepDefination[j]!="")
					{
						++actionCount;
					}
				}
				if(!ActionValidationMap.validate(stepDefination[1],actionCount))
				{
					Logging.logger1.error("Step #"+ ++i+"("+stepDefination[1]+" and "+stepDefination[0]+")" +" is invalid at sheet "+SheetName+" due to inncorrect values or page element");
					PublicContext.ReportLogger.log(Status.FAIL, "Step #"+ ++i+"("+stepDefination[1]+" and "+stepDefination[0]+")" +" is invalid at sheet "+SheetName+" due to inncorrect values or page element");
					System.exit(0);
				}
				if(stepDefination[0]!="")
				{
					if((PublicContext.pageElementProperties.getProperty(stepDefination[0])==null))
					{
						Logging.logger1.error("Step #"+ ++i +" is invalid in sheet "+SheetName  +" due to page element "+ stepDefination[0] +" not found in repository");
						PublicContext.ReportLogger.log(Status.FAIL, "Step #"+ ++i +" is invalid in sheet "+SheetName  +" due to page element "+ stepDefination[0] +" not found in repository");
						System.exit(0);
					}
				}
				++i;
				if(stepDefination[1].equalsIgnoreCase("commonsteps"))
				{
					continue;
				}
				else
				{
					PublicContext.MargeTestCase.add(lst);
				}
			}
		}
		return PublicContext.MargeTestCase;
	}
}
