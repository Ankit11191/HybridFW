package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

public class ReadFromExcelFileColumnWise {
	
	public static Map<String, ArrayList<String>> columnWiseCalling(String FileName) {

		String[] FSN=FileName.split(";");
		
		Workbook wb = ReadFromExcelFile.workbook(FSN[0]);

		Sheet sh = wb.getSheet("DataProvider");

		Row row = null;

		DataFormatter dataFormatter = new DataFormatter();

		Map<String, ArrayList<String>> excelMap=new HashMap<String, ArrayList<String>>();

		
		for (int i = 0; i < sh.getRow(0).getPhysicalNumberOfCells(); i++) {
			Iterator<Row> itrrow = sh.rowIterator();
			ArrayList<String> valueList = new ArrayList<String>();
			String key = itrrow.next().getCell(i).toString();
			for (int j = 0; j < sh.getLastRowNum(); j++) {
				row = itrrow.next();
					valueList.add(dataFormatter.formatCellValue(row.getCell(i)));
			}
			excelMap.put(key, valueList);
		}
		return excelMap;
	}
}