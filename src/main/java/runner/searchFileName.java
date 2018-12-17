package runner;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributeView;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class searchFileName {
	
	public static String findFileName()
	{	
		File folder = new File(System.getProperty("user.dir") + File.separator + "DownloadedFiles");
		File[] listOfFiles = folder.listFiles();
		ArrayList<String> FilesName = new ArrayList<String>();
		for (int i = 0; i < listOfFiles.length; i++) {
			FilesName.add(listOfFiles[i].getName());
		}
		
		Map<String, Long> fileInfo=new HashMap<String, Long>();
		for (String FileName : FilesName) {
			String pathadd = System.getProperty("user.dir") + File.separator + "DownloadedFiles" + File.separator+ FileName;
			Path p = Paths.get(pathadd);
			BasicFileAttributes view = null;
			try {
				view = Files.getFileAttributeView(p, BasicFileAttributeView.class).readAttributes();
			} catch (IOException e) {
				e.printStackTrace();
			}
			fileInfo.put(FileName, view.creationTime().toMillis());
		}
		
		String FileName=null;
		Long maxValueInMap=(Collections.max(fileInfo.values())); 
		for(Map.Entry<String, Long> disp:fileInfo.entrySet())
		{
			if (disp.getValue()==maxValueInMap) {
				FileName=disp.getKey();
	        }
		}

		return FileName;
	}

}
