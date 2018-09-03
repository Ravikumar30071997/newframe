package utilities;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

public class Browser {
	
	private WebDriver driver;
	private WebDriverWait wait;
	private WebElement ele;
	private List<WebElement> eleList;
	private Select dplist;
	
	private static int datanum=4;
	
	public Browser() {
		String exepath = "C:\\Users\\M1046846\\Downloads\\chromedriver\\chromedriver.exe";
		System.setProperty("webdriver.chrome.driver", exepath);
		driver = new ChromeDriver();
		wait = new WebDriverWait(driver, 5);
	}
	public WebDriver getDriver()
	{
		return driver;
	}
	public void openBrowser(ArrayList<String> data)
	{
		driver.get(data.get(3));
		driver.manage().window().maximize();
	}
	public void closeBrowser(ArrayList<String> data)
	{
		driver.quit();
	}
	public WebElement find(String path)
	{
		ele=wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		return ele;
	}
	public List<WebElement> findall(String path)
	{
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(path)));
		eleList=driver.findElements(By.xpath(path));
		return eleList;
	}
	public void clickOn(ArrayList<String> data)
	{
		ele=this.find(data.get(datanum));
		ele.click();
	}
	public void sendData(ArrayList<String> data)
	{
		ele=this.find(data.get(datanum));
		ele.sendKeys(data.get(datanum+1));
	}
	public void selectElement(ArrayList<String> data)
	{
		ele=this.find(data.get(datanum));
		dplist= new Select(ele);
		dplist.selectByIndex(Integer.parseInt(data.get(datanum+1)));
	}
}
