package common;

import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.util.ArrayList;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import reporting.Logging;

public class ReadExcelSheet extends Logging {

	static ArrayList<String> fileNameAndPAth = new ArrayList<String>();

	private static ArrayList<String> getObjectRepository(String propfilepath) {
		try {
			URL url = new Object() {
			}.getClass().getClassLoader().getResource(propfilepath);
			File fileinfolder = new File(url.toURI());
			String[] filesinDir = fileinfolder.list();

			if (filesinDir != null) {
				String fname = "";
				for (int i = 0; i < filesinDir.length; i++) {
					fname = filesinDir[i];
					if (!fname.contains(".")) {
						getObjectRepository(propfilepath + "/" + fname);
					} else if (fname.endsWith(".xls") || fname.endsWith(".xlsx")) {
						String filename = fileinfolder + "\\" + fname;
						fileNameAndPAth.add(filename);
					}
				}
			}

		} catch (Exception e) {
			e.getStackTrace();
		}
		return fileNameAndPAth;
	}

	public static ArrayList<Sheet> fileName(String sheetName) {
		ArrayList<String> aaaa = getObjectRepository("./resources");
		FileInputStream file = null;
		ArrayList<Sheet> sh = new ArrayList<Sheet>();
		Workbook wb = null;
		for (String FileNamePath : aaaa) {
			try {
				file = new FileInputStream(FileNamePath);
				wb = WorkbookFactory.create(file);
			} catch (Exception e) {
				e.printStackTrace();
			}
			for (int i = wb.getNumberOfSheets() - 1; i >= 0; i--) {
				if (wb.getSheetAt(i).getSheetName().equals(sheetName)) {
					logger1.info("File path is : directory\\"+FileNamePath.substring(FileNamePath.indexOf("resources")) + " : " + wb.getSheetAt(i).getSheetName());
					PublicContext.ReportLogger.log(Status.PASS, "File path is : directory\\"+FileNamePath.substring(FileNamePath.indexOf("resources")) + " : " + wb.getSheetAt(i).getSheetName());
					sh.add(wb.getSheet(sheetName));
				}
			}
		}
		return sh;
	}

}
