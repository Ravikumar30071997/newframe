package reportgeneration;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class Screenshots {

	private WebDriver driver;
	private String path;

	public Screenshots(WebDriver d, String p) {
		driver = d;
		path = p;
	}

	public void capture(String name) throws IOException, InterruptedException {
		TakesScreenshot scrShot = (TakesScreenshot) driver;
		File src = scrShot.getScreenshotAs(OutputType.FILE);
		File dsrc = new File(path + name + ".png");
		FileUtils.copyFile(src, dsrc);
	}

}
