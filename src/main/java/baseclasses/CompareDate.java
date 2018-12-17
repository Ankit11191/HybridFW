package baseclasses;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import com.aventstack.extentreports.Status;

import reporting.Logging;

public class CompareDate {

	public static void getDaysDifferance(String actualDateAndDays) {
		
		String actualDate=actualDateAndDays.split("=")[1].split(",")[0];
		String days=actualDateAndDays.split("=")[1].split(",")[1];
		actualDate = PublicContext.EvalMap.get(actualDate);
		
		String status="False";
		Date date1 = null;
		Date date2 = null;
		try {
			date1=new SimpleDateFormat("MM/dd/yyyy").parse(actualDate);
			Calendar c = Calendar.getInstance();
	        c.setTime(new Date());
			date2=new SimpleDateFormat("MM/dd/yyyy").parse(new SimpleDateFormat("MM/dd/yyyy").format(c.getTime()));
			long daysDifference =  (date2.getTime()-date1.getTime())/86400000;
			if(daysDifference<Integer.parseInt(days))
			{
				status="True";
			}
		} catch (Exception e) {
			Logging.logger1.info(e.getLocalizedMessage());
			PublicContext.ReportLogger.log(Status.FAIL, e.getLocalizedMessage() + "\t"+"<a href='"+ScreenShot.getScreenhot()+"'>Screenshot</a>");
			PublicContext.EvalMap.put(actualDateAndDays.split("=")[0], status);
		}
	}
}
