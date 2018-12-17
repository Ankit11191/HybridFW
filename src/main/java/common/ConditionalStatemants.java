package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import com.aventstack.extentreports.Status;

import baseclasses.ByElement;
import baseclasses.PublicContext;
import reporting.Logging;
import runner.MethodFactory;

public class ConditionalStatemants {

public static void ifTrue(String condition, ArrayList<ArrayList<String>> lst,HashMap<String, String> DPRowMap)
	{
		String[] expected = new String[32];
		String[] actual= new String[32];
		if(condition.contains(" OR ") || condition.contains(" AND "))
		{
			expected =  new String[(int) (condition.contains(" OR ")?condition.split(" OR ").length:condition.contains(" AND "))];
			actual =  new String[(int) (condition.contains(" OR ")?condition.split(" OR ").length:condition.contains(" AND "))];
		}
		
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        
		String trueCondition="";
		if(condition.contains(" OR "))
		{
			String[] conditions=condition.split(" OR "); 
			for(int i=0; i<conditions.length;i++)
			{
				if(conditions[i].split("=")[0].toLowerCase().contains("store"))
				{
					expected[i]=PublicContext.EvalMap.get(conditions[i].split("=")[0].replaceAll("[^\\w]", "").replace("Store",""));
				}
				else
				{
					expected[i] = ByElement.GetTextOn(conditions[i].split("=")[0]);
				}
				actual[i]=conditions[i].split("=")[1];
				trueCondition +=expected[i].equals(actual[i]) + " || ";
			}
			trueCondition=trueCondition.substring(0,trueCondition.length()-4);
		}
		else
		{
			String[] conditions=condition.split(" AND "); 
			for(int i=0; i<conditions.length;i++)
			{
				if(conditions[i].split("=")[0].toLowerCase().contains("store"))
				{
					expected[i]=PublicContext.EvalMap.get(conditions[i].split("=")[0].replaceAll("[^\\w]", "").replace("Store",""));
				}
				else
				{
					expected[i] = ByElement.GetTextOn(conditions[i].split("=")[0]);
				}
				actual[i]=conditions[i].split("=")[1];
				trueCondition +=expected[i].equals(actual[i]) + " && ";
			}
			trueCondition=trueCondition.substring(0,trueCondition.length()-4);
		}
				
		try {
			if((boolean) engine.eval(trueCondition))
			{
				ArrayList<String> step1=new ArrayList<String>();
				step1.add("Pass");
				step1.add("");
				step1.add("ifTrue");
				step1.add(condition+" = Pass");			
				PublicContext.Reporting.add(step1);
				Logging.logger1.info("Condition has verified according to action");
				PublicContext.ReportLogger.log(Status.PASS, "Condition has verified according to action");
				for(ArrayList<String> lst1:lst)
				{
					ArrayList<String> step=new ArrayList<String>();
					String[] stepDefination = lst1.toArray(new String[0]);
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
					MethodFactory.methods(stepDefination);
					
					step.add("Pass");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
				}
				step1.add("Pass");
				step1.add("");
				step1.add("EndIf");
				step1.add("");			
				PublicContext.Reporting.add(step1);
			}
			else
			{
				ArrayList<String> step1=new ArrayList<String>();
				step1.add("Failed");
				step1.add("");
				step1.add("ifTrue");
				step1.add(condition+" = Failed");
				for(ArrayList<String> lst1:lst)
				{
					ArrayList<String> step=new ArrayList<String>();
					String[] stepDefination = lst1.toArray(new String[0]);
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
					step.add("Skip");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
				}
				PublicContext.Reporting.add(step1);
				step1.add("Failed");
				step1.add("");
				step1.add("EndIf");
				step1.add("");			
				PublicContext.Reporting.add(step1);
				Logging.logger1.info("Condition not satisfied");
				PublicContext.ReportLogger.log(Status.PASS, "Condition not satisfied");
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public static void ifnotTrue(String condition, ArrayList<ArrayList<String>> lst,HashMap<String, String> DPRowMap)
	{
		String[] expected = new String[32];
		String[] actual= new String[32];
		if(condition.contains(" OR ") || condition.contains(" AND "))
		{
			expected =  new String[(int) (condition.contains(" OR ")?condition.split(" OR ").length:condition.contains(" AND "))];
			actual =  new String[(int) (condition.contains(" OR ")?condition.split(" OR ").length:condition.contains(" AND "))];
		}
		
		ScriptEngineManager factory = new ScriptEngineManager();
        ScriptEngine engine = factory.getEngineByName("JavaScript");
        		
		String trueCondition="";
		if(condition.contains(" OR "))
		{
			String[] conditions=condition.split(" OR "); 
			for(int i=0; i<conditions.length;i++)
			{
				if(conditions[i].split("=")[0].toLowerCase().contains("store"))
				{
					expected[i]=PublicContext.EvalMap.get(conditions[i].split("=")[0].replaceAll("[^\\w]", "").replace("Store",""));
				}
				else
				{
					expected[i] = ByElement.GetTextOn(conditions[i].split("=")[0]);
				}
				actual[i]=conditions[i].split("=")[1];
				trueCondition +=expected[i].equals(actual[i]) + " || ";
			}
			trueCondition=trueCondition.substring(0,trueCondition.length()-4);
		}
		else
		{
			String[] conditions=condition.split(" AND "); 
			for(int i=0; i<conditions.length;i++)
			{
				if(conditions[i].split("=")[0].toLowerCase().contains("store"))
				{
					expected[i]=PublicContext.EvalMap.get(conditions[i].split("=")[0].replaceAll("[^\\w]", "").replace("Store",""));
				}
				else
				{
					expected[i] = ByElement.GetTextOn(conditions[i].split("=")[0]);
				}
				actual[i]=conditions[i].split("=")[1];
				trueCondition +=expected[i].equals(actual[i]) + " && ";
			}
			trueCondition=trueCondition.substring(0,trueCondition.length()-4);
		}
		try {
			if(!(boolean) engine.eval(trueCondition))
			{
				ArrayList<String> step1=new ArrayList<String>();
				step1.add("Pass");
				step1.add("");
				step1.add("ifNotTrue");
				step1.add(condition+" = Pass");			
				PublicContext.Reporting.add(step1);
				Logging.logger1.info("Condition has verified according to action");
				PublicContext.ReportLogger.log(Status.PASS, "Condition has verified according to action");
				for(ArrayList<String> lst1:lst)
				{
					String[] stepDefination = lst1.toArray(new String[0]);
					ArrayList<String> step=new ArrayList<String>();
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
					
					MethodFactory.methods(stepDefination);
					step.add("Pass");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
				}
				step1.add("Pass");
				step1.add("");
				step1.add("EndIf");
				step1.add("");			
				PublicContext.Reporting.add(step1);
			}
			else
			{
				ArrayList<String> step1=new ArrayList<String>();
				step1.add("Failed");
				step1.add("");
				step1.add("ifTrue");
				step1.add(condition+" = Failed");
				for(ArrayList<String> lst1:lst)
				{
					ArrayList<String> step=new ArrayList<String>();
					String[] stepDefination = lst1.toArray(new String[0]);
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
					step.add("Skip");
					for(int i=0;i<stepDefination.length;i++)
					{
						step.add(stepDefination[i]);
					}
					PublicContext.Reporting.add(step);
				}
				PublicContext.Reporting.add(step1);
				step1.add("Failed");
				step1.add("");
				step1.add("EndIf");
				step1.add("");			
				PublicContext.Reporting.add(step1);
				Logging.logger1.info("Condition not satisfied");
				PublicContext.ReportLogger.log(Status.PASS, "Condition not satisfied");
			}
		} catch (ScriptException e) {
			e.printStackTrace();
		}
	}
	
	public static void whileTrue(String condition, ArrayList<ArrayList<String>> lst,HashMap<String, String> DPRowMap)
	{
		String expected;
		if(condition.split("=")[0].toLowerCase().contains("store"))
		{
			expected=PublicContext.EvalMap.get(condition.split("=")[0].replaceAll("[^\\w]", "").replace("Store",""));
		}
		else
		{
			expected = ByElement.GetTextOn(condition.split("=")[0]);
		}
		Iterator<ArrayList<String>> in=lst.iterator();
		while(!expected.equals(condition.split("=")[1]) && in.hasNext())
		{
			ArrayList<String> lst1=in.next();
			String[] stepDefination = lst1.toArray(new String[0]);
			if(stepDefination[1].equalsIgnoreCase("iftrue") || stepDefination[1].equalsIgnoreCase("ifnottrue"))
			{
				ArrayList<ArrayList<String>> conditional=new ArrayList<ArrayList<String>>();
				while(in.hasNext())
				{
					ArrayList<String> cinner = in.next();
					String[] cstepDefination=cinner.toArray(new String[0]);
					if(!cstepDefination[1].equalsIgnoreCase("endif"))
					{
						ArrayList<String> lst11=new ArrayList<String>();
						for(int i=0;i<cstepDefination.length;i++)
						{
							lst11.add(cstepDefination[i]);
						}
						conditional.add(lst11);
					}
					else
					{
						break;
					}
					}
					if(stepDefination[1].equalsIgnoreCase("iftrue"))
					{
						ifTrue(stepDefination[2], conditional,DPRowMap);
					}
					else if(stepDefination[1].equalsIgnoreCase("ifnottrue"))
					{
						ifnotTrue(stepDefination[2], conditional,DPRowMap);
					}
						
					if(stepDefination[1].equalsIgnoreCase("endif"))
					{
						Logging.logger1.info("conditional statemant has closed");
						PublicContext.ReportLogger.log(Status.PASS, "conditional statemant has closed");
					}
				}
				else if(stepDefination[1].equalsIgnoreCase("whiletrue"))
				{
	
					ArrayList<ArrayList<String>> conditional=new ArrayList<ArrayList<String>>();
					while(in.hasNext())
					{
						ArrayList<String> cinner = in.next();
						String[] cstepDefination=cinner.toArray(new String[0]);
						if(!cstepDefination[1].equalsIgnoreCase("endwhile"))
						{
							ArrayList<String> lst21=new ArrayList<String>();
							for(int i=0;i<cstepDefination.length;i++)
							{
								lst21.add(cstepDefination[i]);
							}
							conditional.add(lst21);
						}
						else
						{
							break;
						}
					}
				}
				MethodFactory.methods(stepDefination);
			}	
	}
}
