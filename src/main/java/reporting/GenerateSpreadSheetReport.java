package reporting;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.aventstack.extentreports.Status;

import baseclasses.PublicContext;

public class GenerateSpreadSheetReport extends Logging{
	@SuppressWarnings("resource")
	public static void TestOutputinExcel(ArrayList<ArrayList<String>> data) {
		String FileName = "ExecutionReport_"+new  SimpleDateFormat("yyyy_MM_dd_hh_mm_ss").format(new Date());
		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(FileName);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(headerFont);
		Row headerRow = sheet.createRow(0);
		String[] columns = { "Status", "pageElement", "Action", "Value" };
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for (ArrayList<String> rowData : data) {
			Font fontColur = workbook.createFont();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			int cellNum = 0;
			Row row = sheet.createRow(rowNum++);
			for (String cellData : rowData) {
				Cell cell = row.createCell(cellNum++);
				if (cellData == "Fail") {
					fontColur.setColor(IndexedColors.RED.getIndex());
					cellStyle.setFont(fontColur);
					cell.setCellValue(cellData);
					cell.setCellStyle(cellStyle);
				} else {
					if(cellData.contains("${Store."))
					{
						cellData=PublicContext.EvalMap.get(cellData.replaceAll("[^\\w]", "").replace("Store",""));
					}
					fontColur.setColor(IndexedColors.DARK_TEAL.getIndex());
					cellStyle.setFont(fontColur);
					cell.setCellValue(cellData);
					cell.setCellStyle(cellStyle);
				}
			}
		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(PublicContext.locationPathSpreadSheets + File.separator + FileName + ".xlsx");

			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger1.info("Report is successfully Generated at location : "+PublicContext.locationPathSpreadSheets + File.separator + FileName + ".xlsx");
		PublicContext.ReportLogger.log(Status.PASS, "Report is successfully Generated at location : "+PublicContext.locationPathSpreadSheets + File.separator + FileName + ".xlsx"
		+"\t"+"<a href='"+PublicContext.locationPathSpreadSheets + File.separator + FileName + ".xlsx"+"'>Attachment</a>");
}
	
	@SuppressWarnings("resource")
	public static String XMLOutputinExcel(ArrayList<ArrayList<String>> data, String FileName) {

		Workbook workbook = new XSSFWorkbook();
		Sheet sheet = workbook.createSheet(FileName);

		Font headerFont = workbook.createFont();
		headerFont.setBold(true);
		headerFont.setFontHeightInPoints((short) 14);
		headerFont.setColor(IndexedColors.BLUE.getIndex());

		CellStyle headerCellStyle = workbook.createCellStyle();
		headerCellStyle.setFillForegroundColor(IndexedColors.AQUA.getIndex());
		headerCellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);
		headerCellStyle.setBorderBottom(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderLeft(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderRight(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setBorderTop(CellStyle.BORDER_THIN);
		headerCellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
		headerCellStyle.setFont(headerFont);
		Row headerRow = sheet.createRow(0);
		String[] columns = { "TagName", "Value" };
		for (int i = 0; i < columns.length; i++) {
			Cell cell = headerRow.createCell(i);
			cell.setCellValue(columns[i]);
			cell.setCellStyle(headerCellStyle);
		}

		int rowNum = 1;
		for (ArrayList<String> rowData : data) {
			Font fontColur = workbook.createFont();
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setBorderBottom(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderLeft(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderRight(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			cellStyle.setBorderTop(CellStyle.BORDER_THIN);
			cellStyle.setRightBorderColor(IndexedColors.BLACK.getIndex());
			int cellNum = 0;
			Row row = sheet.createRow(rowNum++);
			for (String cellData : rowData) {
				Cell cell = row.createCell(cellNum++);
				if (cellData == "No such elemnt found") {
					fontColur.setColor(IndexedColors.RED.getIndex());
					cellStyle.setFont(fontColur);
					cell.setCellValue(cellData);
					cell.setCellStyle(cellStyle);
				} else {
					fontColur.setColor(IndexedColors.DARK_YELLOW.getIndex());
					cellStyle.setFont(fontColur);
					cell.setCellValue(cellData);
					cell.setCellStyle(cellStyle);
				}
			}
		}
		for (int i = 0; i < columns.length; i++) {
			sheet.autoSizeColumn(i);
		}
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(PublicContext.locationPathSpreadSheets + File.separator + FileName + ".xlsx");

			workbook.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "Success";

	}

}
