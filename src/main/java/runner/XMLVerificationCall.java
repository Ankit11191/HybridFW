package runner;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import common.ReadFromExcelFile;
import reporting.GenerateSpreadSheetReport;
import reporting.Logging;

public class XMLVerificationCall extends Logging{
	
	public static void verifyXML(String sourceFileName,String TargetFileName) {
		
		ArrayList<ArrayList<String>> name=ReadFromExcelFile.getCellValue("XMLVerificationTagFiles"+File.separator+sourceFileName+".xlsx");

		ArrayList<ArrayList<String>> OutPutRow=new ArrayList<ArrayList<String>>();
		
		for(ArrayList<String> inner :name)
		{	ArrayList<String> RowData=new ArrayList<String>();
			String[] ivalue=inner.toArray(new String[0]);
			String[] stepDefination=Arrays.copyOfRange(ivalue, 1, ivalue.length);
			if(ivalue[0].equalsIgnoreCase("Y"))
			{
				OutPutRow.add(XMLVerificationMethodFactory.methods(stepDefination,RowData));
			}
		}
		String dateFormat=new  SimpleDateFormat("_yyyy_MM_dd_hh_mm_ss").format(new Date());
		if(GenerateSpreadSheetReport.XMLOutputinExcel(OutPutRow, TargetFileName+dateFormat)=="Success")
		{
			logger1.info("File Print Successfully. Please find at location : "+System.getProperty("user.dir") + File.separator + "OutputFiles"+ 
					File.separator+"XMLVerificationUpdateOutputFile" + File.separator +TargetFileName+dateFormat+".xlsx");
			PublicContext.ReportLogger.log(Status.PASS, "File Print Successfully. Please find at location : "+System.getProperty("user.dir") + File.separator + "OutputFiles"+ 
					File.separator+"XMLVerificationUpdateOutputFile" + File.separator +TargetFileName+dateFormat+".xlsx");

		}
		else
		{
			logger1.info("Please see error Log");
			PublicContext.ReportLogger.log(Status.FAIL, "Please see error Log");
		}
	}

}
