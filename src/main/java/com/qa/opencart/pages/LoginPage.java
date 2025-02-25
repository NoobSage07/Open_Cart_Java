package com.qa.opencart.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

import io.qameta.allure.Step;

public class LoginPage {
	private WebDriver driver;
	private ElementUtil eleUtil;

	// 1. private By locators: page objects
	private By username = By.id("input-email");
	private By password = By.id("input-password");
	private By loginBtn = By.xpath("//input[@value='Login']");
	private By forgotPwdLink = By.linkText("Forgotten Password");
	private By registerLink = By.linkText("Register");
	private By logo = By.cssSelector("img.img-responsive");
	private By loginErrorMessg = By.cssSelector("div.alert.alert-danger.alert-dismissible");

	// 2. Public Page Const...
	public LoginPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}

	// 3. Public Page Actions/Methods
	@Step("getting login page title value")
	public String getLoginPageTitle() {
		String title = eleUtil.waitForTitleContainsAndReturn(AppConstants.LOGIN_PAGE_TITLE,
				AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("login page title: " + title);
		return title;
	}

	@Step("getting login page url value")
	public String getLoginPageURL() {
		String url = eleUtil.waitForURLContainsAndReturn(AppConstants.LOGIN_PAGE_FRACTION_URL,
				AppConstants.DEFAULT_SHORT_TIME_OUT);
		System.out.println("login page title: " + url);
		return url;
	}

	@Step("checking isForgotPwdLink exist on the login page....")
	public boolean isForgotPwdLinkExist() {
		return eleUtil.isElementDisplayed(forgotPwdLink);
	}

	@Step("checking logo exist on the login page....")
	public boolean isLogoExist() {
		return eleUtil.isElementDisplayed(logo);
	}

	@Step("login with username : {0} and password: {1}")
	public AccountsPage doLogin(String userName, String pwd) {
		System.out.println("creds are: " + userName + " : " + pwd);
		eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT).sendKeys(userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		return new AccountsPage(driver);
	}

	@Step("login with username : {0} and password: {1}")
	public boolean doInvalidLogin(String userName, String pwd) {
		System.out.println("Invalid creds are: " + userName + " : " + pwd);
		
		WebElement usernameElement = eleUtil.waitForElementVisible(username, AppConstants.DEFAULT_MEDIUM_TIME_OUT);
		eleUtil.doSendKeys(usernameElement, userName);
		eleUtil.doSendKeys(password, pwd);
		eleUtil.doClick(loginBtn);
		String errorMesg = eleUtil.waitForElementVisible(loginErrorMessg, AppConstants.DEFAULT_SHORT_TIME_OUT)
				.getText();
		System.out.println("Login error --->" + errorMesg);
		if (errorMesg.contains(AppConstants.LOGIN_ERROR_MESSAGE)) {
			return true;
		}
		return false;
	}

	@Step("navigating to regiter page")
	public RegisterPage navigateToRegisterPage() {
		eleUtil.doClick(registerLink);
		return new RegisterPage(driver);
	}

}
