package com.qa.opencart.base;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Properties;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;

import com.qa.opencart.factory.DriverFactory;
import com.qa.opencart.pages.AccountsPage;
import com.qa.opencart.pages.LoginPage;
import com.qa.opencart.pages.ProductInfoPage;
import com.qa.opencart.pages.RegisterPage;
import com.qa.opencart.pages.ResultsPage;

import io.qameta.allure.Step;

public class BaseTest {

	WebDriver driver;
	DriverFactory df;
	protected Properties prop;

	protected LoginPage loginPage;
	protected AccountsPage accPage;
	protected ResultsPage resultsPage;
	protected ProductInfoPage productInfoPage;
	protected RegisterPage registerPage;

	// protected SoftAssert softAssert;

	@Step("Setup with browser : {0} and init the propeties")
	@Parameters({ "browser", "browserversion", "testname" })
	@BeforeTest
	public void setup(@Optional String browserName, @Optional String browserVersion, @Optional String testName) {
		df = new DriverFactory();
		prop = df.initProp();

		// check if browser param is coming from testng.xml
		if (browserName != null) {
			prop.setProperty("browser", browserName);
			prop.setProperty("browserversion", browserVersion);
			prop.setProperty("testname", testName);

		}

		driver = df.initDriver(prop);
		loginPage = new LoginPage(driver);
		// softAssert = new SoftAssert();
	}

	@Step("close the browser")
	@AfterTest
	public void tearDown() {
		driver.quit();
		driver.quit();
	}

//	@AfterSuite
//	public void openHtmlReport() {
//		// extent html report
//		try {
//			File htmlFile = new File("reports/TestExecutionReport.html");
//			if (htmlFile.exists()) {
//				Desktop.getDesktop().browse(htmlFile.toURI());
//			} else {
//				System.out.println("Report file not found: " + htmlFile.getAbsolutePath());
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		// allure
//		try {
//			// Serve the Allure report
//			ProcessBuilder builder = new ProcessBuilder("/usr/local/bin/allure", "serve", "allure-results");
//			builder.inheritIO();
//			Process process = builder.start();
//			process.waitFor();
//
//			// The `allure serve` command automatically opens the report in a browser.
//			System.out.println("Allure report served successfully.");
//
//		} catch (IOException | InterruptedException e) {
//			e.printStackTrace();
//			System.out.println("Failed to serve Allure report.");
//		}
//
//	}

}
