package maven_testing_framework.framework;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import reportgeneration.GenarateHtmlReport;
import reportgeneration.GenerateExcelReport;
import reportgeneration.Screenshots;
import utilities.Browser;
import utilities.ReadFromExcel;

public class Main extends JPanel {
	 static JProgressBar pbar;
	 JButton button;
	 JFrame frame;
	 int max;
	int caseCount=0;
	private String name;
	private String scenarioName;
	private GenerateExcelReport report;
	private String ssPath="\\reportPics\\";
	private ReadFromExcel obData;
	private HashMap<Integer, ArrayList<String>> scenarios;
	private HashMap<Integer, ArrayList<String>> testCases;
	private HashMap<Integer, ArrayList<String>> testMethods;
	//private final String resourcePath="D:\\project reports\\testResources\\";
	//private final String mainReport="D:\\project reports\\selenium\\reportFile\\";
	private String resourcePath="\\testResources\\";
	private String mainPath="\\reportFiles\\";
	private String curDir;
	int currentCase=0;
	int failCount=0;
	String msg=null;
	String passMsg="<br>All cases have passed<br>";
	String failMsg="<br><b><u><font size=\"5\">Failed test cases:</font></b></u><br>";
	static JLabel label;
	
	@BeforeTest
	public  void BeforeMain()
	{
		Main call = new Main();
			this.setPath();
			pbar = new JProgressBar();
		    pbar.setMinimum(0);
		   max=this.getCaseCount();
		    frame = new JFrame("Progress Bar");
		    
		   
			label = new JLabel("My label");
		    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // else hide by default
		    frame.setContentPane(call); // class extends to jpanel which adds components // jpanel is generic class used to group components
		    //frame.pack();  // auto set size
		    frame.setSize(700,100);
		    label.setVisible(true);
		    frame.add(label);
		    frame.setVisible(true);
		    pbar.setMaximum(max);
		    // add to JPanel
		    frame.add(pbar);
		    pbar.setString("Starting execution...");
		    button=new JButton("View report");
		    frame.add(button);
		    pbar.setForeground(Color.blue);
		    
	}
	
	public static void main(String[] args) throws Exception {
		
		Main call2 = new Main();
		call2.BeforeMain();
		//call.setPath();
		/*frame = new JFrame("Progress Bar");
		label = new JLabel("My label");
	    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // else hide by default
	    frame.setContentPane(call); // class extends to jpanel which adds components // jpanel is generic class used to group components
	    frame.pack();  // auto set size
	    //frame.setSize(500,75);
	    label.setText("<html>Starting execution...</html>");
	    label.setVisible(true);
	    frame.add(label);
	    frame.setVisible(true);
	   
	    //pbar.setStringPainted(true);*/
		call2.mainControll();
		call2.afterTest();
		 
	}
	//@BeforeTest
	public void setPath()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH_mm_ss");
		LocalDateTime now = LocalDateTime.now();
		
		curDir = System.getProperty("user.dir")+"\\selenium";
		mainPath=curDir+"\\"+dtf.format(now)+mainPath;
		resourcePath=curDir+resourcePath;
		ssPath=curDir+"\\"+dtf.format(now)+ssPath;
		curDir=curDir+"\\"+dtf.format(now);
	}
	
	@AfterTest
	public void afterTest() throws IOException
	{
		GenarateHtmlReport ob=new GenarateHtmlReport("newReport.html",curDir);
		ob.generate("testReport.xlsx", "report");
		 File report = new File(curDir+"\\reportFiles\\"+"newReport.html");
		 button.addActionListener(new ActionListener() {
			 
	            public void actionPerformed(ActionEvent e)
	            {
	                //Execute when button is pressed
	            	try {
						Desktop.getDesktop().open(report);
					} catch (IOException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
	            }
	        }); 
	}
	
	@Test
	public void mainControll() throws Exception
	{
		
		
		File directory = new File(mainPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		directory = new File(ssPath);
		if (!directory.exists()) {
			directory.mkdirs();
		}
		
		report=new GenerateExcelReport(mainPath+"testReport.xlsx","report");
		report.create();
		obData=new ReadFromExcel();
		scenarios=obData.getData(resourcePath+"Test_Cases.xlsx","Scenarios"); //Test_Resource_MyBooks_TB1_R1
		int i,j;
		for(i=0;i<scenarios.size();i++)
		{
			scenarioName=scenarios.get(i).get(0);
			report.createNewRow();
			report.write(scenarioName);
			this.getTestCases(scenarios.get(i));
			for(j=0;j<testCases.size();j++)
			{
				name=scenarioName+" "+testCases.get(j).get(0);
				msg=name+"  ";
				report.createNewRow();
				report.write("");
				report.write(testCases.get(j).get(0));
				this.getTestMethods(testCases.get(j));
				this.test1(testMethods);
			}
		}
		report.finish();
		if(failCount==0)
		{
			label.setText("<html>Execution is completed"+passMsg+"</html>");
			pbar.setForeground(Color.green);

		}
		else
		{
			//label.setText("<html>"+failMsg+"</html>");
			label.setText("<html> Failed test cases: "+failCount+"/"+max+"</html>");
			pbar.setBackground(Color.red);
			pbar.setForeground(Color.green);
			this.updateBar(max-failCount);
			
		}
		
	}
	
	public HashMap<Integer, ArrayList<String>> getTestCases(ArrayList<String> scenario) throws IOException
	{
		testCases=obData.getData(resourcePath+"Test_Cases.xlsx", scenario.get(0));
		return testCases;
	}
	
	public HashMap<Integer, ArrayList<String>> getTestMethods(ArrayList<String> testCase) throws IOException
	{
		testMethods=obData.getData(resourcePath+"Test_Cases.xlsx", testCase.get(1));
		return testMethods;
	}

	public void test1(HashMap<Integer, ArrayList<String>> data2) throws Exception {
		
		if(data2==null)
			return;

		report.createNewRow();
		report.write("");
		report.write("");

		Class<Browser> Browser2Class = Browser.class;
		Object ob = Browser2Class.newInstance();
		Screenshots ss = new Screenshots(((Browser) ob).getDriver(), ssPath);
		Method m=null;

		int i = 1;
		try {
			report.write("start time");
			report.write(report.timeStamp());
			String mName = "";

			for (i = 1; i < data2.size(); i++) {
				try {
					label.setText("<html><font size=\"3\">"+msg+" => "+data2.get(i).get(1)+"</font></html>");
					//pbar.setString(msg+data2.get(i).get(1));
					mName = data2.get(i).get(2);
					//System.out.println(mName)
					m = Browser2Class.getDeclaredMethod(mName, ArrayList.class);
					m.invoke(ob, data2.get(i));
					
				} catch (NoSuchMethodException e) {
					System.out.println("no such method -"+mName);
					continue;
				} catch (Exception e) {
					throw e;
				}
				if (!("closeBrowser").equals(mName)) {
					ss.capture(name + " sucess " + i);
					report.createNewRow();
					report.write("");
					report.write("");
					report.write(data2.get(i).get(1));
					report.write(ssPath + name + " sucess " + i + ".png");
				}
			}
			report.createNewRow();
			report.write("");
			report.write("");
			report.write("end time");
			report.write(report.timeStamp());
			report.createNewRow();
			report.write("");
			report.write("");
			report.write("result");
			report.write("pass");
			
		} catch (Exception e) {
			failCount++;
			ss.capture(name + " fail " + i);
			m = Browser2Class.getDeclaredMethod("closeBrowser", ArrayList.class);
			m.invoke(ob, data2.get(i));
			report.createNewRow();
			report.write("");
			report.write("");
			report.write(data2.get(i).get(1));
			report.write(ssPath + name + " fail " + i + ".png");
			failMsg+="<br>&nbsp &nbsp &nbsp "+msg+"<br>";

			report.createNewRow();
			report.write("");
			report.write("");
			report.write("end time");
			report.write(report.timeStamp());
			report.createNewRow();
			report.write("");
			report.write("");
			report.write("result");
			report.write("fail");
		}

		finally {
		currentCase++;
		this.updateBar(currentCase);
		}
		
	}
	public int getCaseCount() 
	{
		obData=new ReadFromExcel();
		try {
			scenarios=obData.getData(resourcePath+"Test_Cases.xlsx","Scenarios");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int i,j;
		for(i=0;i<scenarios.size();i++)
		{
			try {
				this.getTestCases(scenarios.get(i));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			for(j=0;j<testCases.size();j++)
			{
				caseCount++;
			}
		}
		return caseCount;
	}
	 public void updateBar(int newValue) {
		    pbar.setValue(newValue);
		  }

}
