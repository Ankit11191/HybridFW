package runner;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import reporting.Logging;

public class EmailRead {

	@SuppressWarnings("deprecation")
	public static String[] VerificationCode(String UserName, String password){
		String WebAddress="https://mail-sa-atl.eis.equifax.com/owa";

		ChromeOptions chromeOptions=new ChromeOptions();
		chromeOptions.addArguments("start-maximized");
		
		System.setProperty("webdriver.chrome.driver",System.getProperty("user.dir")+"\\Driver\\chromedriver.exe");
		WebDriver driver=new ChromeDriver(chromeOptions);
		
		driver.get(WebAddress);
		driver.findElement(By.id("username")).sendKeys("EIS\\"+UserName);

		driver.findElement(By.id("password")).sendKeys(password);

		driver.findElement(By.xpath(".//input[@class='btn']")).click();
		
		String UNAME_VCODE[] = null;
		boolean verify= driver.findElements(By.id("imgLiveLogo")).size()!=0;
		if(verify)
		{
			driver.findElement(By.xpath(".//input[@type='text'][@value]")).sendKeys("Verify your identity in Salesforce");

			driver.findElement(By.id("imgS")).click();
			
			WebDriverWait wait=new WebDriverWait(driver, 45);
			
			wait.until(ExpectedConditions.textToBePresentInElement((By.xpath("//span[contains(text(),'You recently logged in to Salesforce')]")),
					"You recently logged in to Salesforce from a browser or app that we don't recognize."));
			
			String Uname1=driver.findElement(By.xpath("//span[contains(text(),'Username')]")).getText().substring(10);

			String Vcode1=driver.findElement(By.xpath("//span[contains(text(),'Verification Code')]")).getText().substring(19);
					
			UNAME_VCODE=new String[]{Uname1,Vcode1};
		}
		else
		{
			Logging.logger1.error("User Name or Password is not correct ");
			PublicContext.ReportLogger.log(Status.PASS, "Moving to common file folder "+"User Name or Password is not correct ");
		}
		driver.close();
		driver.quit();
		return UNAME_VCODE;
	}

}
