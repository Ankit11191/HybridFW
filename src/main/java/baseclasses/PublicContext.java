package baseclasses;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;

public class PublicContext {
	public static WebDriver drivreturn;
	public static Map<String, String> ConsolidatedReportforall;
	public static ExtentReports extentReports;
	public static ExtentTest ReportLogger;
	public static Properties pageElementProperties = new Properties();
	public static Map<String, String> EvalMap=new HashMap<String, String>();
	public static ArrayList<ArrayList<String>> Reporting=new ArrayList<ArrayList<String>>();
	public static Map<String, String> tempMapforHandelDataProvider;
	public static String locationPath = System.getProperty("user.dir")+File.separator+ "ExtendReport"+File.separator+"ExecutionReport"+new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss").format(new Date());
	public static String locationPathLogFiles= locationPath +File.separator+ "LogFiles";
	public static String locationPathSpreadSheets=locationPath +File.separator+ "SpreadSheets";
	public static String locationPathforDownloads=locationPath +File.separator+ "Downloads";
	public static String locationPathforScreenShots=locationPath +File.separator+ "ScreenShots";
	public static ArrayList<ArrayList<String>> MargeTestCase=new ArrayList<ArrayList<String>>();
	public static JavascriptExecutor js;
}