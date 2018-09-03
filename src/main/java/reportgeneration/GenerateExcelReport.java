package reportgeneration;

import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenerateExcelReport {

	private Workbook workbook;// new HSSFWorkbook() for generating `.xls` file
	private Sheet sheet;
	private String path, sheetName;
	private int lastrow, lastcolumn;
	private Row row;

	public GenerateExcelReport(String p, String sn) {

		lastrow = -1;
		lastcolumn = -1;
		path = p;
		sheetName = sn;

	}
	
	public void insertLink()
	{
		//
	}

	public void create() {
		workbook = new XSSFWorkbook(); // new HSSFWorkbook() for generating `.xls` file
		sheet = workbook.createSheet(sheetName);
	}

	public void createNewRow() {
		lastrow++;
		row = sheet.createRow(lastrow);
		lastcolumn = -1;
	}

	public void write(String data) {
		lastcolumn++;
		row.createCell(lastcolumn).setCellValue(data);
	}

	public void finish() throws IOException {
		FileOutputStream fileOut = new FileOutputStream(path);
		workbook.write(fileOut);
		fileOut.close();
		workbook.close();
	}

	public String timeStamp() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}

}
