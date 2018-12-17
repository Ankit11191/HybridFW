package validators;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class ReadText {

	public static String read(String FileName){
		try {
			File file = new File(System.getProperty("user.dir")+FileName);
			String line = "";
			String oldtext = "";
			BufferedReader reader = null;

			try {
				reader = new BufferedReader(new FileReader(file));
				while ((line = reader.readLine()) != null) {
					oldtext += line + "\r\n";
				}
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return oldtext;
		}
		catch(Exception e)
		{
			return e.getMessage();
		}
	}
}
