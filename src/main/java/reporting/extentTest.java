package reporting;

import java.io.File;

import org.openqa.selenium.Platform;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;
import com.aventstack.extentreports.reporter.configuration.Theme;

import baseclasses.PublicContext;

public class extentTest {

		private static ExtentReports extent;
		private static String reportName;
		private static String locationPathHtmlreports =PublicContext.locationPath +File.separator+ "Htmlreports";

	    public static ExtentReports createInstance(String TestCaseName) {
	        reportName=TestCaseName;
	        reportName=reportName.replaceAll(" ", "");
	    	String fileName = getReportFileLocation(Platform.WINDOWS);
	        ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(fileName);
	        htmlReporter.config().setTestViewChartLocation(ChartLocation.BOTTOM);
	        htmlReporter.config().setChartVisibilityOnOpen(true);
	        htmlReporter.config().setTheme(Theme.STANDARD);
	        htmlReporter.config().setDocumentTitle(TestCaseName);
	        htmlReporter.config().setEncoding("utf-8");
	        htmlReporter.config().setReportName(TestCaseName);
	        extent = new ExtentReports();
	        extent.attachReporter(htmlReporter);
	        return extent;
	    }
	    private static String getReportFileLocation (Platform platform) {
	    		createReportPath(PublicContext.locationPath);
	    		createReportPath(PublicContext.locationPathLogFiles);
            	createReportPath(PublicContext.locationPathSpreadSheets);            	
                createReportPath(locationPathHtmlreports);
	            createReportPath(PublicContext.locationPathforDownloads);
	            createReportPath(PublicContext.locationPathforScreenShots);
	                
	            String reportFileLocation = locationPathHtmlreports +File.separator+ reportName+"_"+String.valueOf(System.currentTimeMillis())+".html";
	        
	            return reportFileLocation;
	    }
	 
	    private static void createReportPath (String path) {
	        File testDirectory = new File(path);
	        if (!testDirectory.exists()) {
	            if (testDirectory.mkdir()) {
	            	Logging.logger1.info("Directory: " + path + " is created!" );
	            } else {
	            	Logging.logger1.error("Failed to create directory: " + path);
	            }
	        }
	    }
}