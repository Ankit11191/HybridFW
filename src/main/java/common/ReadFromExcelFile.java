package common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadFromExcelFile {

	public static Workbook workbook(String FileName) {
		FileInputStream productname = null;

		try {
			productname = new FileInputStream(System.getProperty("user.dir") + File.separator + FileName);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Workbook wb = null;
		try {
			wb = (Workbook) WorkbookFactory.create(productname);
		} catch (Exception e) {

			e.printStackTrace();
		}
		return wb;
	}

	public static ArrayList<ArrayList<String>> getCellValue(String FileName) {

		String[] FSN = FileName.split(";");
		
		Workbook wb = (Workbook) ReadFromExcelFile.workbook(FSN[0]);
		wb.setForceFormulaRecalculation(true);

		FormulaEvaluator evaluator = wb.getCreationHelper().createFormulaEvaluator();
		Sheet sh = null;
		if (FSN.length == 2) {
			sh = wb.getSheet(FSN[1]);
		} else {
			sh = wb.getSheetAt(0);
		}
		Iterator<Row> itrrow = sh.rowIterator();

		ArrayList<ArrayList<String>> rowvalue = new ArrayList<ArrayList<String>>();

		DataFormatter dataFormatter = new DataFormatter();

		while (itrrow.hasNext()) {
			Row row = itrrow.next();
			Cell cell;
			for (int i = 0; i < row.getLastCellNum(); i++) {
				cell = row.getCell(i, Row.CREATE_NULL_AS_BLANK);
			}

			if (row.getRowNum() > 0) {
				ArrayList<String> cellvalue = new ArrayList<String>();
				Iterator<Cell> itrCell = row.cellIterator();
				while (itrCell.hasNext()) {

					cell = itrCell.next();
					
					cellvalue.add(dataFormatter.formatCellValue(evaluator.evaluateInCell(cell)));
				}
				rowvalue.add(cellvalue);
			}
		}
		return rowvalue;
	}


	public static void updateCellValue(String FileName, String rowID) {

		String filepath = System.getProperty("user.dir") + File.separator + FileName;

		Workbook wb = ReadFromExcelFile.workbook(FileName);

		Sheet sh = wb.getSheetAt(0);

		Iterator<Row> itrrow = sh.rowIterator();
		DataFormatter dataFormatter = new DataFormatter();

		while (itrrow.hasNext()) {
			Row row = itrrow.next();
			if (row.getRowNum() > 0) {
				Iterator<Cell> itrCell = row.cellIterator();
				while (itrCell.hasNext()) {
					Cell cell = itrCell.next();
					dataFormatter.formatCellValue(cell);

					if (dataFormatter.formatCellValue(cell).equals(rowID)) {
						cell = row.getCell(2);
						cell.setCellValue("Pass 121");

						FileOutputStream outputStream = null;
						try {
							outputStream = new FileOutputStream(filepath);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						try {
							wb.write(outputStream);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}

				}
			}
		}
	}
}