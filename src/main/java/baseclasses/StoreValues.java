package baseclasses;

import com.aventstack.extentreports.Status;

import reporting.Logging;

public class StoreValues extends Logging
{		 
	 public static void Storetext(String elemantName, String VeriableName)
	 {
		elemantName = ByElement.GetTextOn(elemantName);
		 PublicContext.EvalMap.put(VeriableName, elemantName);
		 logger1.info("Value '"+elemantName+"' stored in veriable '"+VeriableName+"'");
		 PublicContext.ReportLogger.log(Status.PASS, "Value '"+elemantName+"' stored in veriable '"+VeriableName+"'");
	 }
	 
/*	 public static void Verifytext(String elemantName, String VeriableName)
	 {
		elemantName = ByElement.GetTextOn(elemantName);
		 EvalMap.put(VeriableName, elemantName);
		 logger.info("Value '"+elemantName+"' stored in veriable '"+VeriableName+"'");
	 }*/
	 
	 public static void eval(String VeriableName)
	 {		
		 logger1.info("Value find for '"+VeriableName+"' is '"+PublicContext.EvalMap.get(VeriableName)+"'");
		 PublicContext.ReportLogger.log(Status.PASS, "Value find for '"+VeriableName+"' is '"+PublicContext.EvalMap.get(VeriableName)+"'");
	 }
	 
}
