package utilities;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ReadFromExcel {

	@SuppressWarnings("deprecation")
	public HashMap<Integer, ArrayList<String>> getData(String path, String sheetName) throws IOException {

		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		String temp;
		Sheet sheet = this.getSheet(path, sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 0; i <= rowCount; i++)
		{
			ArrayList<String> arr = new ArrayList<String>();
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++)
			{
				try {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
					temp = row.getCell(j).toString();
					if (!(("").equals(temp) || ("null").equals(temp))) {
						arr.add(temp);
					}
				} catch (NullPointerException e) {
					// just skip
				}
			}
			map.put(i, arr);
		}
		return map;
	}
	
	public Sheet getSheet(String filePath, String sheetName) throws IOException {
		File file = new File(filePath);
		FileInputStream stream = new FileInputStream(file);
		Workbook book = new XSSFWorkbook(stream);
		Sheet sheet = book.getSheet(sheetName);
		book.close();
		return sheet;
	}
	
	public void displayData(HashMap<Integer, ArrayList<String>> data)
	{
		if(data==null)
		{
			System.out.println("data not present");
			return;
		}
		int i;
		for(i=0;i<data.size();i++)
		{
			System.out.println(i+" -> "+data.get(i));
		}
	}

}
