package common;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;

import baseclasses.PublicContext;
import reporting.Logging;

public class DriverConfig extends Logging{
	
	private DriverConfig()
    {
    }

	private String BrowserName;
	private static DriverConfig config;
    private static WebDriver driverIN;
	private static void setInstance()
    {
		if(config==null)
		{
			config= new DriverConfig();
		}
    }
    public static DriverConfig getInstance()
    {
    		setInstance();
    		return config;
    }
	
	private WebDriver getDriverInstance()
	{
		String Browser=BrowserName;

		if(Browser.toUpperCase().equals("CHROME")||Browser.equalsIgnoreCase("Null"))
		{
			ChromeOptions chromeOptions=new ChromeOptions();
			chromeOptions.addArguments("start-maximized");

			Map<String, Object> prefs = new HashMap<String, Object>();
			prefs.put("download.default_directory", PublicContext.locationPathforDownloads);
			prefs.put("disable-popup-blocking", "true");
			prefs.put("safebrowsing.enabled", "true"); 
			chromeOptions.setExperimentalOption("prefs", prefs);
			System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"Driver"+File.separator+"chromedriver.exe");
			driverIN=new ChromeDriver(chromeOptions);
		}
		else if(Browser.equals("FIREFOX"))
		{
			System.setProperty("webdriver.gecko.driver",System.getProperty("user.dir")+File.separator+"Driver"+File.separator+"geckodriver.exe");
			driverIN=new FirefoxDriver();
		}
		else if(Browser.equals("IE"))
		{
			System.setProperty("webdriver.ie.driver",System.getProperty("user.dir")+File.separator+"Driver"+File.separator+"IEDriverServer.exe");
			driverIN=new InternetExplorerDriver();
		}
		return driverIN;
	}
	
	ThreadLocal<WebDriver> localDriver = new ThreadLocal<WebDriver>()
	{
		public WebDriver initialValue()
		{
			 return getDriverInstance();
		}
    };
	
	public WebDriver driver(String BrowserName)
    {
		this.BrowserName=BrowserName;
        return localDriver.get();
    }
	
	public void closeBrowser() {
		PublicContext.drivreturn.close();
		PublicContext.drivreturn.quit();
		config=null;
	}
	
}
