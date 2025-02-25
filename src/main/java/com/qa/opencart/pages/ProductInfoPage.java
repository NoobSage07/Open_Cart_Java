package com.qa.opencart.pages;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import com.qa.opencart.constants.AppConstants;
import com.qa.opencart.utils.ElementUtil;

public class ProductInfoPage {
	
	private WebDriver driver;
	private ElementUtil eleUtil;
	
	
	private By productHeader = By.tagName("h1");
	private By productMetaData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[1]/li");
	private By productPriceData = By.xpath("(//div[@id='content']//ul[@class='list-unstyled'])[2]/li");
	private By prouctImage = By.cssSelector("ul.thumbnails img");

	private Map<String, String> productMap;
	

	public ProductInfoPage(WebDriver driver) {
		this.driver = driver;
		eleUtil = new ElementUtil(driver);
	}
	
	public String getProductHeader() {
		String productHeaderValue = 
				eleUtil.waitForElementVisible(productHeader, AppConstants.DEFAULT_SHORT_TIME_OUT).getText();
		System.out.println("Product Header ===> " + productHeaderValue);
		return productHeaderValue;
	}
	
	
//	Brand: Apple
//	Product Code: Product 18
//	Reward Points: 800
//	Availability: In Stock
	private void getProductMetaData() {
		List<WebElement> metaList = eleUtil.getElements(productMetaData);
		for(WebElement meta : metaList) {
			String metaText = meta.getText();
			String metaData[] = metaText.split(":");
			String metaKey = metaData[0].trim();
			String metaValue = metaData[1].trim();
			productMap.put(metaKey, metaValue);
		}
	}
	
//	$2,000.00
//	Ex Tax: $2,000.00
	private void getProductPriceData() {
		List<WebElement> priceList = eleUtil.getElements(productPriceData);
		String price = priceList.get(0).getText();
		String exTaxPrice = priceList.get(1).getText().split(":")[1].trim();
		productMap.put("productprice", price);
		productMap.put("extaxprice", exTaxPrice);
	} 
	
	public Map<String, String> getProductData() {
		productMap = new HashMap<String,String>();//no order
//		productMap = new LinkedHashMap<String,String>();//to maintain the insertion order
//		productMap = new TreeMap<String,String>();//to maintain the alphabetical order

		productMap.put("productheader", getProductHeader());
		getProductMetaData();
		getProductPriceData();
		System.out.println("Product Data:" + productMap);
		return productMap;
	}
	
	
	public int getProductImagesCount() {
		int imagesCount = 
				eleUtil.waitForElementsPresence(prouctImage, AppConstants.DEFAULT_MEDIUM_TIME_OUT).size();
		System.out.println("Images count ====> " + imagesCount);
		return imagesCount;
	}
	
	
	
	
	
	
	
	

}
