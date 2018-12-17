package runner;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import common.ReadFromExcelFile;

public class DataProviderRowsRead {
	
	@SuppressWarnings("unused")
	public static List<HashMap<String, String>> getDataProviderRowsMappedList(String TestCaseFileName)
	{	
		List<HashMap<String, String>> dataProviderRowsMappedList = new ArrayList<HashMap<String, String>>();
		List<String> headers = new ArrayList<String>();
		
		String[] FSN=TestCaseFileName.split(";");
		Workbook wb = ReadFromExcelFile.workbook("SourceFiles"+File.separator+FSN[0]);
		Sheet sh = wb.getSheet("DataProvider");
		
		Iterator<Row> rows = sh.rowIterator();
	    Row header = sh.getRow(0);
	    Row row = null;
	    Cell cell = null;
	    int noOfColumns = 0;
		for(int i=0;i<header.getLastCellNum();i++)
		{
			if(header.getCell(i)!=null)
			{
				headers.add(header.getCell(i).getStringCellValue().toString());
				noOfColumns++;
			}
		}
		rows.next();
		while(rows.hasNext())
		{
			row = (Row) rows.next();
			for(int i=0; i<row.getLastCellNum(); i++)
			{
				cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
			}
			HashMap<String, String> map = new HashMap<String, String>();
			String value;
			if(row.getCell(0).getStringCellValue().equalsIgnoreCase("Y"))
				{
				for (int i = 0; i < noOfColumns; i++)
			      {
			        value = "";
			        if (row.getCell(i) == null)
			        {
			          map.put(headers.get(i), value);
			        }
			        else
			        {
			          row.getCell(i).setCellType(1);
			          value = row.getCell(i).getStringCellValue().trim();
			          map.put(headers.get(i), value);
			        }
			      }
				dataProviderRowsMappedList.add(map);
			}
		}
		if(dataProviderRowsMappedList.isEmpty())
		{
			HashMap<String, String> map1 = new HashMap<String, String>();
			map1.put("", "");
			dataProviderRowsMappedList.add(map1);
		}
		return dataProviderRowsMappedList;
	}
}
