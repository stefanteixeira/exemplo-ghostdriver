package ghostdriver.setup;

import java.io.File;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriver;
import org.openqa.selenium.phantomjs.PhantomJSDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;

public class DriverFactory
{
	private static WebDriver driver;
	private static Wait<WebDriver> wait;
	protected static DesiredCapabilities dcaps;

//	 private static String BROWSER = "ie";
//	 private static String BROWSER = "firefox";
//	 private static String BROWSER = "chrome";
	private static String BROWSER = "phantom";

	public static WebDriver getDriver()
	{
		if (driver == null)
		{
			createDriver();
		}
		if(((RemoteWebDriver) driver).getSessionId() == null)
		{
			createDriver();
		}

		return driver;
	}

	public static WebDriver createDriver()
	{
		if (BROWSER == "ie")
		{
			dcaps = DesiredCapabilities.internetExplorer();
			File file = new File("src/test/resources/IEDriverServer.exe");
			System.setProperty("webdriver.ie.driver", file.getAbsolutePath());
			dcaps.setJavascriptEnabled(true);
			driver = new InternetExplorerDriver(dcaps);
		}
		if (BROWSER == "firefox")
		{
			driver = new FirefoxDriver();
			driver.manage().window().maximize();
		}
		if (BROWSER == "chrome")
		{
			File file = new File("src/test/resources/chromedriver.exe");
			System.setProperty("webdriver.chrome.driver",
					file.getAbsolutePath());
			driver = new ChromeDriver();
			driver.manage().window().maximize();
		}
		if (BROWSER == "phantom")
		{
			dcaps = DesiredCapabilities.phantomjs();
			File file = new File("src/test/resources/phantomjs.exe");
			dcaps.setCapability(
					PhantomJSDriverService.PHANTOMJS_EXECUTABLE_PATH_PROPERTY,
					file.getAbsolutePath());
			dcaps.setJavascriptEnabled(true);
			dcaps.setCapability("takesScreenshot", false);
			ArrayList<String> cliArgsCap = new ArrayList<String>();
			cliArgsCap.add("--web-security=false");
			cliArgsCap.add("--ssl-protocol=any");
			cliArgsCap.add("--ignore-ssl-errors=true");
			cliArgsCap.add("--proxy-type=none");
			dcaps.setCapability(PhantomJSDriverService.PHANTOMJS_CLI_ARGS,
					cliArgsCap);
			driver = new PhantomJSDriver(dcaps);
			driver.manage().window().maximize();
		}

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		wait = null;

		return driver;
	}

	public static Wait<WebDriver> getWait()
	{
		if (wait == null)
		{
			wait = createWait(10, 2);
		}

		return wait;
	}

	public static String getBrowser()
	{
		return BROWSER;
	}

	public static Wait<WebDriver> createWait(long withTimeout, long pollingEvery)
	{
		return new FluentWait<WebDriver>(getDriver())
				.withTimeout(withTimeout, TimeUnit.SECONDS)
				.pollingEvery(pollingEvery, TimeUnit.SECONDS)
				.ignoring(NoSuchElementException.class)
				.ignoring(StaleElementReferenceException.class);
	}

	public static Wait<WebDriver> createWaitDefault(long withTimeout,
			long pollingEvery)
	{
		return new FluentWait<WebDriver>(getDriver()).withTimeout(withTimeout,
				TimeUnit.SECONDS).pollingEvery(pollingEvery, TimeUnit.SECONDS);
	}
}
