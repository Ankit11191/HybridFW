package reporting;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

import baseclasses.PublicContext;

public class Logging {
	public static Logger logger1 = Logger.getLogger(new Object() { }.getClass().getEnclosingClass());
	static
	{
		System.setProperty("log.timestamp", new  SimpleDateFormat("yyyy_MM_dd").format(new Date()));
		System.setProperty("log.FilePath", PublicContext.locationPathLogFiles);
		PropertyConfigurator.configure("SystemConfigProp/log4j.properties");
	}
}
