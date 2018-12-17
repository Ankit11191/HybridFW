package common;

import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;
import reporting.Logging;

public class ReadFromExcelFileWithSheetName {

	public static ArrayList<ArrayList<String>> getCellValue(String sheetName) {
		ArrayList<ArrayList<String>> rowvalue = new ArrayList<ArrayList<String>>();
		ArrayList<Sheet> sheet = ReadExcelSheet.fileName(sheetName);
		if (sheet.size() < 2) {
			if (sheet.size() == 0) {
				System.err.println("No Such name sheet is available");
			} else {
				for (Sheet sh : sheet) {

					Iterator<Row> itrrow = sh.rowIterator();

					DataFormatter dataFormatter = new DataFormatter();

					while (itrrow.hasNext()) {
						Row row = itrrow.next();
						if (row.getRowNum() > 0) {
							ArrayList<String> cellvalue = new ArrayList<String>();
							Iterator<Cell> itrCell = row.cellIterator();

							while (itrCell.hasNext()) {
								Cell cell = itrCell.next();
								cellvalue.add(dataFormatter.formatCellValue(cell));
							}
							rowvalue.add(cellvalue);
						}
					}
				}
				return rowvalue;
			}

		} else {
			Logging.logger1.error("More then one sheets are available in repository");
			PublicContext.ReportLogger.log(Status.FAIL, "More then one sheets are available in repository");

		}
		return rowvalue;
	}
}