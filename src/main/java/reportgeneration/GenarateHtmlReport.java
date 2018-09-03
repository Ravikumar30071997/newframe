package reportgeneration;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class GenarateHtmlReport {

	private PrintWriter out,out2;
	private String startTime, endTime, result, testName, scenario, detailsHtmlLink;
	//private String path="D:\\project reports\\reportFile\\";
	
	//private final String resourcePath="D:\\project reports\\testResources\\";
	private final String mainReport="\\reportFiles\\";
	private final String subReport="\\reportFiles\\individual\\";
	private String curDir;
	
	private String subColor;

	public GenarateHtmlReport(String name,String curDir) throws FileNotFoundException, UnsupportedEncodingException {
		
		this.curDir=curDir;
		File directory = new File(curDir+subReport);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		out = new PrintWriter(curDir+mainReport+name, "UTF-8");
		out2=null;
	}

	public static void main(String[] args) throws IOException {

		GenarateHtmlReport ob = new GenarateHtmlReport("newReport.html","D:\\spring_projects\\framework\\selenium\\2018-08-28 17_23_29");
		ob.generate("testReport.xlsx", "report");
	}

	public HashMap<Integer, ArrayList<String>> getData(String path, String name) throws IOException {
		HashMap<Integer, ArrayList<String>> data = this.getDataFromExcel(path, name);
		return data;
	}

	public void startHtmlDocument() {
		out.println("<!DOCTYPE HTML>");
		out.println("<html lang=\"en\">");
		out.println("<head>");
		out.println("<style>");
		out.println("table {");
		out.println("table-layout: fixed;");
		out.println("width: 100%;");
		out.println("}");
		out.println("</style>");
		out.println("<title>Test Results</title>");
		out.println("<meta charset=\"UTF-8\" />");
		out.println("</head>");
		out.println("<body>");
		out.println("<table align=\"center\">");
		out.println("<tr>");
		out.println("<th>");
		out.println("<h1>");
		out.println("<i>Test results</i>");
		out.println("</h1>");
		out.println("</th>");
		out.println("</tr>");
		out.println("<tr>");
		out.println("<td>");
		out.println("<table>");
		out.println("<tr style=\"color: White; background-color: black;\">");
		out.println("<th>Test Scenarios</th>");
		out.println("<th>Test Cases</th>");
		out.println("<th>Result</th>");
		out.println("<th>Start Time</th>");
		out.println("<th>End Time</th>");
		out.println("<th>Details</th>");
		out.println("</tr>");
	}

	public void endHtmlDocument()
	{
		out.println("</table>");
		out.println("</td>");
		out.println("</tr>");
		out.println("</table>");
		out.println("</body>");
		out.println("</html>");
		out.close();
	}

	public void startDetailsHtmlDocument(HashMap<Integer, ArrayList<String>> data) throws FileNotFoundException, UnsupportedEncodingException
	{
		detailsHtmlLink=curDir+subReport+scenario+"_"+testName+".html";
		out2 = new PrintWriter(detailsHtmlLink, "UTF-8");
		out2.println("<!DOCTYPE HTML>");
		out2.println("<html lang=\"en\">");
		out2.println("<head>");
		out2.println("<style>");
		out2.println("table {");
		out2.println("table-layout: fixed;");
		out2.println("width: 100%;");
		out2.println("}");
		out2.println("</style>");
		out2.println("<title>Details</title>");
		out2.println("<meta charset=\"UTF-8\" />");
		out2.println("</head>");
		out2.println("<body>");
		out2.println("<table align=\"center\">");
		out2.println("<tr style=\"color: black; background-color: #3498DB;\">");
		//out2.println("<th>");
		out2.println("<th>"+scenario+"</th>");
		//out2.println("</th>");
		out2.println("</tr>");
		out2.println("<tr style=\"color: black; background-color: #3498DB;\">");
		//out2.println("<th>");
		out2.println("<th>"+testName+"</th>");
		//out2.println("</th>");
		out2.println("</tr>");
		
		out2.println("<tr style=\"color: blue; background-color: yellow;\">");
		out2.println("<td>"+data.get(0).get(2)+" : "+data.get(0).get(3)+"</td>");
		out2.println("</tr>");
		
		int size=data.size();
		out2.println("<tr style=\"color: blue; background-color: yellow;\">");
		out2.println("<td>"+data.get(size-2).get(2)+" : "+data.get(size-2).get(3)+"</td>");
		out2.println("</tr>");
		
		out2.println("<tr style=\"color: blue; background-color: yellow;\">");
		out2.println("<td>"+data.get(size-1).get(2)+" : "+data.get(size-1).get(3)+"</td>");
		out2.println("</tr>");
			
		out2.println("<tr>");
		out2.println("<td>");
		out2.println("<table>");
		out2.println("<tr style=\"color: White; background-color: black;\">");
		out2.println("<th>Test Steps</th>");
		out2.println("<th>Screen Shots</th>");
		out2.println("</tr>");
	}
	
	public void constructDetailsHtmlDocument(HashMap<Integer, ArrayList<String>> data) throws FileNotFoundException, UnsupportedEncodingException
	{
		int size=data.size();
		this.startDetailsHtmlDocument(data);
		subColor="<tr style=\"color: black; background-color: #F1948A;\" align=\"center\">";
		if( ("pass").equals(data.get(size-1).get(3)) )
		{
			subColor="<tr style=\"color: black; background-color: #58D68D;\" align=\"center\">";
		}
//		out2.println(subColor);
//		out2.println("<td>"+data.get(0).get(2)+"</td>");
//		out2.println("<td>"+data.get(0).get(3)+"</td>");
//		out2.println("</tr>");
		
		this.insertImages(data);
		
//		out2.println(subColor);
//		out2.println("<td>"+data.get(size-2).get(2)+"</td>");
//		out2.println("<td>"+data.get(size-2).get(3)+"</td>");
//		out2.println("</tr>");
		
//		out2.println(subColor);
//		out2.println("<td>"+data.get(size-1).get(2)+"</td>");
//		out2.println("<td>"+data.get(size-1).get(3)+"</td>");
//		out2.println("</tr>");
		
		this.endDetailsHtmlDocument(data);
	}
	
	public void endDetailsHtmlDocument(HashMap<Integer, ArrayList<String>> data)
	{
		out2.println("</table>");
		out2.println("</td>");
		out2.println("</tr>");
		out2.println("</table>");
		out2.println("</body>");
		out2.println("</html>");
		out2.close();
	}
	
	public void addScenario()
	{
		out.println("<tr style=\"color: White; background-color: black;\">");
		out.println("<td>"+scenario+"</td>");
		out.println("</tr>");
	}
	
	public void addTest()
	{
		String color;
		if( ("pass").equals(result) )
		{
			color="style=\"color: white; background-color: green;\"";
		}
		else
		{
			color="style=\"color: white; background-color: red;\"";
		}
		out.println("<tr "+color+">");
		out.println("<td></td>");
		out.println("<td>"+testName+"</td>");
		out.println("<td>"+result+"</td>");
		out.println("<td>"+startTime+"</td>");
		out.println("<td>"+endTime+"</td>");
		out.println("<td><a href=\"" + detailsHtmlLink + "\" target=\"_blank\" style=\"color: white;\">Details</a></td>");
		out.println("</tr>");
	}
	
	public void generate(String source, String sourceSheet) throws IOException {
		this.startHtmlDocument();
		HashMap<Integer, ArrayList<String>> data=this.getData(curDir+mainReport+source, sourceSheet);
		HashMap<Integer, ArrayList<String>> subdata=new HashMap<Integer, ArrayList<String>>();
		int i,j=0;
		String temp;
		for(i=0;i<data.size();i++)
		{
			if( !("null").equals(data.get(i).get(0)) )
			{
				scenario=data.get(i).get(0);
				this.addScenario();
			}
			else if( !("null").equals(data.get(i).get(1)) )
			{
				testName=data.get(i).get(1);
			}
			else
			{
				subdata.put(j, data.get(i));
				j++;
				temp=data.get(i).get(2);
				if( ("start time").equals(temp) )
				{
					startTime=data.get(i).get(3);
				}
				else if( ("end time").equals(temp) )
				{
					endTime=data.get(i).get(3);
				}
				else if( ("result").equals(temp) )
				{
					result=data.get(i).get(3);
					
					this.constructDetailsHtmlDocument(subdata);
					
					subdata=new HashMap<Integer, ArrayList<String>>();
					j=0;
					
					this.addTest();
				}
			}
		}
		
		this.endHtmlDocument();
	}

	public void insertImages(HashMap<Integer, ArrayList<String>> data) {
		if (data == null) {
			return;
		}
		int i;
		String path1,path2;
		for (i = 1; i < data.size()-2; i++) {
			path1 = data.get(i).get(3);
			path2 = path1.replaceAll("\\\\", "/");
			
			out2.println(subColor);
			out2.println("<td>"+data.get(i).get(2)+"</td>");
			out2.println("<td><a href=\"" + path2 + "\" target=\"_blank\" style=\"color: black;\">screen shot</a>");
			out2.println("</tr>");

		}
	}

	@SuppressWarnings("deprecation")
	public HashMap<Integer, ArrayList<String>> getDataFromExcel(String path, String sheetName) throws IOException {

		HashMap<Integer, ArrayList<String>> map = new HashMap<Integer, ArrayList<String>>();
		String temp;
		Sheet sheet = this.getSheet(path, sheetName);
		int rowCount = sheet.getLastRowNum() - sheet.getFirstRowNum();
		for (int i = 0; i <= rowCount; i++) {
			ArrayList<String> arr = new ArrayList<String>();
			Row row = sheet.getRow(i);
			for (int j = 0; j < row.getLastCellNum(); j++) {
				try {
					row.getCell(j).setCellType(Cell.CELL_TYPE_STRING);
					temp = row.getCell(j).toString();
					if (("").equals(temp)) {
						temp = "null";
					}
					arr.add(temp);
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

	public void displayData(HashMap<Integer, ArrayList<String>> data) {
		if (data == null) {
			System.out.println("data not present");
			return;
		}
		int i;
		for (i = 0; i < data.size(); i++) {
			System.out.println(i + " -> " + data.get(i));
		}
	}

}
