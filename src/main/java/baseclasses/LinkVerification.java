package baseclasses;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.aventstack.extentreports.Status;

import reporting.Logging;


public class LinkVerification {
	public static void main(String[] arg){

		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+File.separator+"Driver"+File.separator+"chromedriver.exe");
		WebDriver driver =new ChromeDriver();
		driver.get("http://sdlc193.atl.ec.equifax.com:6385/MortgageTestTool/mTT.htm");
		driver.manage().window().maximize();
		List<WebElement> ankit = driver.findElements(By.tagName("a"));

		Logging.logger1.info("size"+ankit.size());
			for(int i=0;i<ankit.size();i++)
			{
				verifyLinks(ankit.get(i).getAttribute("href"), ankit.get(i).getText());
			}
			driver.close();
			driver.quit();
	}

	public static void verifyLinks(String link, String tagName)
	{
		try
		{
			URL url=new URL(link);
			HttpURLConnection connection=(HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(10000);
			try
			{
				connection.connect();
				if(connection.getResponseCode()==200)
				{	
					Logging.logger1.info(link+" :"+connection.getResponseCode());		
					PublicContext.ReportLogger.log(Status.PASS, link+" :"+connection.getResponseCode());

					try(BufferedWriter bw3 = new BufferedWriter(new FileWriter("C:\\Eclips\\200Success_"+new  SimpleDateFormat("yyyy_MM_dd").format(new Date())+".txt",true)))
					{
						bw3.write(link+" :"+connection.getResponseCode()+"\n");
					}
				}
				else
				{
					Logging.logger1.info(link+" :"+connection.getResponseCode());
					PublicContext.ReportLogger.log(Status.FAIL, link+" :"+connection.getResponseCode());
					try(BufferedWriter bw2 = new BufferedWriter(new FileWriter("C:\\Eclips\\404Failed_"+new  SimpleDateFormat("yyyy_MM_dd").format(new Date())+".txt",true)))
					{
						bw2.write(link+" :"+connection.getResponseCode()+"\n");
					}
				}
			}
			catch(Exception e)
			{
				Logging.logger1.info(link+" :"+e.getLocalizedMessage());
				PublicContext.ReportLogger.log(Status.FAIL, link+" :"+e.getLocalizedMessage());
				try(BufferedWriter bw1 = new BufferedWriter(new FileWriter("C:\\Eclips\\100ConnectionTimeOut_"+new  SimpleDateFormat("yyyy_MM_dd").format(new Date())+".txt",true)))
				{
					bw1.write(link+" :"+e.getLocalizedMessage()+"\n");
				}

			}
			
		}
		catch (Exception e) 
		{	
			e.printStackTrace();
		}
	}
}
