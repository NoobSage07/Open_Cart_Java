package com.qa.opencart.factory;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Properties;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.io.FileHandler;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.qa.opencart.errors.AppError;
import com.qa.opencart.exceptions.BrowserException;
import com.qa.opencart.exceptions.FrameworkException;
import com.qa.opencart.logger.Log;

import io.qameta.allure.Step;

/**
 * 
 * @author naveenautomationlabs
 *
 */
public class DriverFactory {

	WebDriver driver;
	Properties prop;

	public static String isHighlight;

	public static ThreadLocal<WebDriver> tlDriver = new ThreadLocal<WebDriver>();

	OptionsManager optionsManager;

	/**
	 * This method is used to init the driver on the basis of given browsername.
	 * 
	 * @param browserName
	 * @return it returns driver
	 */
	@Step("initializing the driver with properties: {0}")
	public WebDriver initDriver(Properties prop) {

		String browserName = prop.getProperty("browser");
		// System.out.println("browser name is : :" + browserName);
		Log.info(prop.getProperty("testname") + " and browser name is : " + browserName);

		isHighlight = prop.getProperty("highlight");

		optionsManager = new OptionsManager(prop);

		switch (browserName.toLowerCase().trim()) {
		case "chrome":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				init_remoteDriver("chrome");
			} else {
				// run tcs in local:
				tlDriver.set(new ChromeDriver(optionsManager.getChromeOptions()));
			}
			break;
		case "firefox":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				init_remoteDriver("firefox");
			} else {
				// run tcs in local:
				tlDriver.set(new FirefoxDriver(optionsManager.getFirefoxOptions()));
			}
			break;
		case "edge":
			if (Boolean.parseBoolean(prop.getProperty("remote"))) {
				// run tcs on remote/container:
				init_remoteDriver("edge");
			} else {
				// run tcs in local:
				tlDriver.set(new EdgeDriver(optionsManager.getEdgeOptions()));
			}
			break;

		case "safari":
			tlDriver.set(new SafariDriver());
			break;

		default:
			// System.out.println(AppError.INVALID_BROWSER_MESG + browserName + " is
			// invalid");
			Log.error(AppError.INVALID_BROWSER_MESG + browserName + " is invalid");
			throw new BrowserException(AppError.INVALID_BROWSER_MESG);
		}

		getDriver().manage().window().maximize();
		getDriver().manage().deleteAllCookies();
		getDriver().get(prop.getProperty("url"));

		return getDriver();

	}

	private void init_remoteDriver(String browserName) {
		// System.out.println("running tests on grid with browser : " + browserName);
		Log.info("running tests on grid with browser: " + browserName);

		try {

			switch (browserName.toLowerCase().trim()) {
			case "chrome":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getChromeOptions()));
				break;
			case "firefox":
				tlDriver.set(
						new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getFirefoxOptions()));
				break;
			case "edge":
				tlDriver.set(new RemoteWebDriver(new URL(prop.getProperty("huburl")), optionsManager.getEdgeOptions()));
				break;

			default:
				System.out.println("please pass the right remote browser name....");
				throw new BrowserException(AppError.INVALID_BROWSER_MESG);
			}

		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

	}

	/**
	 * this method is returning the driver with threadlocal
	 * 
	 * @return
	 */
	public static WebDriver getDriver() {
		return tlDriver.get();
	}

	/**
	 * this method is used to init the properties from the config file
	 * 
	 * @return
	 */

	// mvn clean install -Denv="qa"

	public Properties initProp() {
		prop = new Properties();
		FileInputStream ip = null;

		String envName = System.getProperty("env");
		// System.out.println("running tests on env: " + envName);
		Log.info("Running tests on env:  " + envName);

		try {
			if (envName == null) {
				// System.out.println("env is null....hence running tests on QA env");
				Log.warn("env is null....hence running tests on QA env");
				ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
			} else {
				switch (envName.toLowerCase().trim()) {
				case "qa":
					ip = new FileInputStream("./src/test/resources/config/qa.config.properties");
					break;
				case "dev":
					ip = new FileInputStream("./src/test/resources/config/dev.config.properties");
					break;
				case "stage":
					ip = new FileInputStream("./src/test/resources/config/stage.config.properties");
					break;
				case "uat":
					ip = new FileInputStream("./src/test/resources/config/uat.config.properties");
					break;
				case "prod":
					ip = new FileInputStream("./src/test/resources/config/config.properties");
					break;

				default:
					// System.out.println("plz pass the right env name..." + envName);
					Log.error("plz pass the right env name..." + envName);
					throw new FrameworkException("INVALID ENV NAME");
				}
			}

			prop.load(ip);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return prop;
	}

	/**
	 * take screenshot
	 */

	public static String getScreenshot(String methodName) {
		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);// temp dir
		String path = System.getProperty("user.dir") + "/screenshot/" + methodName + "_" + System.currentTimeMillis()
				+ ".png";
		File destination = new File(path);
		try {
			FileHandler.copy(srcFile, destination);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return path;
	}

}
