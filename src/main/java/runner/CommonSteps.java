package runner;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import common.ReadFromExcelFile;
import reporting.Logging;

public class CommonSteps extends Logging {

	public static void steps(String filepath,HashMap<String, String> DPRowMap) {
		ArrayList<ArrayList<String>> name = ReadFromExcelFile.getCellValue("SourceFiles"+File.separator+filepath);

		ArrayList<String> step1=new ArrayList<String>();
		step1.add("Pass");
		step1.add("");
		step1.add("CommonSteps");
		step1.add("Successfully Moved to : "+filepath);			
		PublicContext.Reporting.add(step1);
		
		for (ArrayList<String> inner : name) {
			
			String[] ivalue = inner.toArray(new String[0]);
			String[] stepDefination = Arrays.copyOfRange(ivalue, 1, ivalue.length);
			for(int i=0;i<stepDefination.length;i++)
			{
				String key=stepDefination[i];
				for(Map.Entry<String, String> entry : DPRowMap.entrySet())
				{
					String value= "DataProvider." + entry.getKey();
					if(key.equals(value))
					{
						String GenaretNumber="CONVERTTODATETIME";
						String tempValue=entry.getValue();
						if(entry.getValue().toUpperCase().endsWith(GenaretNumber) && !PublicContext.tempMapforHandelDataProvider.containsKey(value))
						{
							tempValue=tempValue.replaceAll(GenaretNumber, ""+System.currentTimeMillis());
							PublicContext.tempMapforHandelDataProvider.put(value, tempValue);
						}
						else if(!PublicContext.tempMapforHandelDataProvider.containsKey(value))
						{
							PublicContext.tempMapforHandelDataProvider.put(value, tempValue);
						}
						stepDefination[i]=PublicContext.tempMapforHandelDataProvider.get(value);
					}
				}
			}
			if (ivalue[0].equalsIgnoreCase("Y")) {
				String commonSteps="commonsteps";
				if(stepDefination[1].toLowerCase().equals(commonSteps))
				{
					logger1.info("Moving to common file folder "+stepDefination[2]);
					PublicContext.ReportLogger.log(Status.PASS, "Moving to common file folder "+stepDefination[2]);
					steps(stepDefination[2],DPRowMap);
				}
				else
				{
				try {
					MethodFactory.methods(stepDefination);
				} catch (Exception e) {
					e.printStackTrace();
				}
				}
				ArrayList<String> step=new ArrayList<String>();
					step.add("Pass");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
			}
			
		}
		step1.add("Pass");
		step1.add("");
		step1.add("");
		step1.add("Moving Back to : "+filepath);			
		PublicContext.Reporting.add(step1);
	}
}