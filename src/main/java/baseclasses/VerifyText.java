package baseclasses;

import org.testng.Assert;
import org.testng.asserts.SoftAssert;

import com.aventstack.extentreports.Status;

import reporting.Logging;

public class VerifyText {
	static SoftAssert softAssert=new SoftAssert();
	public static void verifyTextS(String expectedText, String actualText)
	{
		expectedText = ByElement.GetTextOn(expectedText);
		Logging.logger1.info(expectedText);
		softAssert.assertEquals(expectedText, actualText);
		if(!expectedText.equals(actualText))
		{
			ScreenShot.getScreenhot();
			Logging.logger1.info(expectedText +" and "+ actualText+" is not same.");
			PublicContext.ReportLogger.log(Status.FAIL, "expectedText : "+expectedText +" and "+ "actualText : " +actualText+" is not same." + "\t"+"<a href='"+ScreenShot.getScreenhot()+"'>Screenshot</a>");
		}
		else
		{
			Logging.logger1.info("text has verified successfully");
			PublicContext.ReportLogger.log(Status.PASS, "text has verified successfully");
		}
	}
	public static void verifyTextS1(String expectedText, String actualText)
	{
		expectedText = ByElement.GetTextOn(expectedText);
		if(actualText.toLowerCase().contains("store"))
		{
			actualText=PublicContext.EvalMap.get(actualText.replaceAll("[^\\w]", "").replace("Store",""));
			softAssert.assertEquals(expectedText, actualText);
		}
		if(!expectedText.equals(actualText))
		{
			Logging.logger1.info(expectedText +" and "+ actualText+" is not same.");
			PublicContext.ReportLogger.log(Status.FAIL, "expectedText : "+expectedText +" and "+ "actualText : " +actualText+" is not same." + "\t"+"<a href='"+ScreenShot.getScreenhot()+"'>Screenshot</a>");

		}
		else
		{
			Logging.logger1.info(expectedText +" and "+ actualText+" has been verified successfully");
			PublicContext.ReportLogger.log(Status.PASS, expectedText +" and "+ actualText+" has been verified successfully");
		}
	}
	public static void verifyTestCase(Boolean expectedText, String TextMessage)
	{
		softAssert.assertTrue(expectedText, TextMessage);
	}
	public static void closeSoftAssert()
	{
		softAssert.assertAll();
	}
	public static void verifyTextH(String expectedText, String actualText)
	{		
		Assert.assertEquals(expectedText, actualText);
	}
}
