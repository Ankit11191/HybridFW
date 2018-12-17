package baseclasses;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import reporting.Logging;

public class ScreenShot extends Logging{

	public static String getScreenhot(){
	
	String dateName = new SimpleDateFormat("dd_MM_yy_hh_mm_ss").format(new Date());
	String screenshotName=(new Object() { }.getClass().getEnclosingClass()).getSimpleName();
	
	File source  = ((TakesScreenshot) PublicContext.drivreturn).getScreenshotAs(OutputType.FILE);

	String destination = PublicContext.locationPathforScreenShots+File.separator+screenshotName+"_"+dateName+".png";
	File finalDestination = new File(destination);
	try {
		FileUtils.copyFile(source, finalDestination);
	} catch (IOException e) {
		e.printStackTrace();
	}
	String destinationToFile= "file:\\\\\\"+destination;
	logger1.info("Captured ScreenShot has storted at location : "+destination);

	return destinationToFile;
	}
}
