package common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.Properties;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import reporting.Logging;

public class ReadProerties extends Logging{
	public static Properties getObjectRepository(String propfilepath) {
			try{

				URL url = new Object(){}.getClass().getClassLoader().getResource(propfilepath);
				File fileinfolder = new File(url.toURI());
				String[] filesinDir = fileinfolder.list();

				if (filesinDir != null) {
					String fname = "";
					for (int i = 0; i < filesinDir.length; i++) {
						fname = filesinDir[i];
						if (!fname.contains(".")) {
							getObjectRepository(propfilepath + "/" + fname);
						} else if (fname.endsWith(".properties")) {
							PublicContext.pageElementProperties.load(new FileInputStream(new File(fileinfolder + "/" + fname)));
							Logging.logger1.info(fileinfolder+File.separator+fname + " has loaded");							
						}

					}
				PublicContext.ReportLogger.log(Status.PASS, "All files has loaded successfully");
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return PublicContext.pageElementProperties;
	}

	public static String[] propsObjectsSplits(String objectname) {
		try{
		return	PublicContext.pageElementProperties.getProperty(objectname).split("=", 2);
		}
		catch (NullPointerException e) {
			return null;
		}
	}

	public static String propsObjectsSplit(String objectname) {
		try {
			return PublicContext.pageElementProperties.getProperty(objectname);
		} catch (NullPointerException e) {
			return null;
		}
	}
}
