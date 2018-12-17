package runner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.testng.ITestContext;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import baseclasses.VerifyText;
import common.ConditionalStatemants;

public class MainToExecuteActions extends InternalExections {
	
	@DataProvider(parallel=true)
	public Object[] DataProvider(ITestContext context)
	{
		String testParam = context.getCurrentXmlTest().getParameter("TestCaseFileName");

		List<HashMap<String, String>> dataProviderList = DataProviderRowsRead.getDataProviderRowsMappedList(testParam);
		Object[] result = new Object[dataProviderList.size()];
		logger1.info("Total "+dataProviderList.size()+" test case/s will executed!!!");
		PublicContext.ReportLogger.log(Status.PASS, "Total "+dataProviderList.size()+" test case/s will executed!!!");
		for (int i = 0; i < dataProviderList.size(); i++) {
					result[i] = dataProviderList.get(i);
		    }
		return result;
	}
	
	@Test(dataProvider="DataProvider")
	public void TestCase(HashMap<String, String> DPRowMap) throws Exception {
		
		ArrayList<ArrayList<String>> name=PublicContext.MargeTestCase;
		
		Iterator<ArrayList<String>> in=name.iterator();
		while(in.hasNext())
		{
			ArrayList<String> inner=in.next();
			String[] ivalue=inner.toArray(new String[0]);
			String[] stepDefination=Arrays.copyOfRange(ivalue, 1, ivalue.length);						
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
					else if(key.contains(value))
					{
						stepDefination[i]=key.replace(value, entry.getValue());
					}
				} 
			}

			if(ivalue[0].equalsIgnoreCase("Y"))
			{
				if(stepDefination[1].equalsIgnoreCase("commonsteps"))
				{
					logger1.info("Moving to common file folder "+stepDefination[2]);
					PublicContext.ReportLogger.log(Status.PASS, "Moving to common file folder "+stepDefination[2]);
					CommonSteps.steps(stepDefination[2],DPRowMap);
				}

				else if(stepDefination[1].equalsIgnoreCase("iftrue") || stepDefination[1].equalsIgnoreCase("ifnottrue"))
				{

					ArrayList<ArrayList<String>> conditional=new ArrayList<ArrayList<String>>();
					while(in.hasNext())
					{
						ArrayList<String> cinner = in.next();
						String[] civalue=cinner.toArray(new String[0]);
						String[] cstepDefination=Arrays.copyOfRange(civalue, 1, civalue.length);
						if(!cstepDefination[1].equalsIgnoreCase("endif") && civalue[0].equalsIgnoreCase("Y"))
						{
							ArrayList<String> lst=new ArrayList<String>();
							for(int i=0;i<cstepDefination.length;i++)
							{
								lst.add(cstepDefination[i]);
							}
							conditional.add(lst);
						}
						else
						{
							break;
						}
					}
					if(stepDefination[1].equalsIgnoreCase("iftrue"))
					{
						ConditionalStatemants.ifTrue(stepDefination[2], conditional,DPRowMap);
					}
					else if(stepDefination[1].equalsIgnoreCase("ifnottrue"))
					{
						ConditionalStatemants.ifnotTrue(stepDefination[2], conditional,DPRowMap);
					}
						
					if(stepDefination[1].equalsIgnoreCase("endif"))
					{
						logger1.info("conditional statemant has closed");
						PublicContext.ReportLogger.log(Status.PASS, "conditional statemant has closed");
					}
				}
				
				else if(stepDefination[1].equalsIgnoreCase("whiletrue"))
				{

					ArrayList<ArrayList<String>> conditional=new ArrayList<ArrayList<String>>();
					while(in.hasNext())
					{
						ArrayList<String> cinner = in.next();
						String[] civalue=cinner.toArray(new String[0]);
						String[] cstepDefination=Arrays.copyOfRange(civalue, 1, civalue.length);
						if(!cstepDefination[1].equalsIgnoreCase("endwhile") && civalue[0].equalsIgnoreCase("Y"))
						{
							ArrayList<String> lst=new ArrayList<String>();
							for(int i=0;i<cstepDefination.length;i++)
							{
								lst.add(cstepDefination[i]);
							}
							conditional.add(lst);
						}
						else
						{
							break;
						}
					}
					ConditionalStatemants.whileTrue(stepDefination[2], conditional,DPRowMap);
					if(stepDefination[1].equalsIgnoreCase("endwhile"))
					{
						logger1.info("conditional statemant has closed");
						PublicContext.ReportLogger.log(Status.PASS, "conditional statemant has closed");
					}
				}
				else
				{
					MethodFactory.methods(stepDefination);
				}
				ArrayList<String> step=new ArrayList<String>();
				if(!stepDefination[1].equalsIgnoreCase("commonsteps") || stepDefination[1].equalsIgnoreCase("iftrue")|| stepDefination[1].equalsIgnoreCase("ifnottrue"))
				{
					step.add("Pass");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
				}
			}
			else
			{
				logger1.info("Step action "+stepDefination[1]+" has skipped");
				PublicContext.ReportLogger.log(Status.PASS, "Step action "+stepDefination[1]+" has skipped");
			}
		}
		VerifyText.closeSoftAssert();
		
	}
}