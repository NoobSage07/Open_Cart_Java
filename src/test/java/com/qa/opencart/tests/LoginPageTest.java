package com.qa.opencart.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.opencart.base.BaseTest;
import com.qa.opencart.constants.AppConstants;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Issue;
import io.qameta.allure.Link;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;

//@Listeners({AnnotationTransformer.class, ExtentReportListener.class})


@Epic("Epic 100: design open cart login page")
@Feature("Feature 101: login feature")
@Story("US 120: All the features related to open cart login page")
@Owner("Naveen Automation Labs")
@Link(name = "LoginPage", url = "https://naveenautomationlabs.com/opencart/index.php?route=account/login")
public class LoginPageTest extends BaseTest {

	@Severity(SeverityLevel.MINOR)
	@Description("login page title test....")
	@Feature("Feature 400: title test feature")
	@Test
	public void loginPageTitleTest() {
		String actTitle = loginPage.getLoginPageTitle();
		Assert.assertEquals(actTitle, AppConstants.LOGIN_PAGE_TITLE);
	}

	@Severity(SeverityLevel.NORMAL)
	@Description("login page url testing....")
	@Feature("Feature 401: title test feature")	
	@Test()
	public void loginPageURLTest() {
		String actURL = loginPage.getLoginPageURL();
		Assert.assertTrue(actURL.contains(AppConstants.LOGIN_PAGE_FRACTION_URL));
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("login page forgot pwd link exist test....")
	@Issue("Bug-123")
	@Test
	public void forgotPwdLinkExistTest() {
		Assert.assertTrue(loginPage.isForgotPwdLinkExist());
	}

	@Severity(SeverityLevel.CRITICAL)
	@Description("login page logo test....")
	@Test
	public void logoExistTest() {
		Assert.assertTrue(loginPage.isLogoExist());
	}

	@Severity(SeverityLevel.MINOR)
	@Description("user is able to login....")
	@Test(priority = Integer.MAX_VALUE)
	public void loginTest() {
		accPage = loginPage.doLogin(prop.getProperty("username"), prop.getProperty("password"));
		Assert.assertEquals(accPage.getAccountsPageTitle(), AppConstants.ACCOUNTS_PAGE_TITLE);
	}
	
	
	@Test(enabled = false)
	public void naveentest() {
		System.out.println("WIP");
	}

}
