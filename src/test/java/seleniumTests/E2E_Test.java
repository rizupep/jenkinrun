package seleniumTests;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.TakesScreenshot;
import org.testng.ITestResult;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;

import com.relevantcodes.extentreports.LogStatus;
import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class E2E_Test {
	private static WebDriver driver;
	public static ExtentReports report;
	public static ExtentTest log;

	public static void getScreenshot(ITestResult result) throws IOException {
		if (result.isSuccess()) {

			String path = takeScreenshot(result.getName());
			log.addScreenCapture(path);
			log.log(LogStatus.PASS, "Test case is passed", path);

		} else {
			String path = takeScreenshot(result.getName());
			log.addScreenCapture(path);
			log.log(LogStatus.FAIL, "Test case is failed", path);
		}
		
	}

	public static String takeScreenshot(String name) throws IOException {
		File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		File dst = new File("D:\\Data-Framework\\Data-Framework\\e-commerce\\screenshot\\" + name + ".jpg");
		FileUtils.copyFile(srcFile, dst);
		return dst.toString();
	}

	public static void main(String[] args) throws InterruptedException {

		report = new ExtentReports("D:\\Data-Framework\\Data-Framework\\EndToEnd\\report\\sample.html");
		log = report.startTest("Report");

		System.setProperty("webdriver.chrome.driver",
				"D:\\Data-Framework\\Data-Framework\\Full Stack\\drivers\\chromedriver.exe");
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		driver.get("http://www.shop.demoqa.com");

		driver.navigate().to("http://shop.demoqa.com/?s=" + "dress" + "&post_type=product");

		List<WebElement> items = driver.findElements(By.cssSelector(".noo-product-inner"));
		items.get(0).click();

		WebElement addToCart = driver.findElement(By.cssSelector("button.single_add_to_cart_button"));
		addToCart.click();

		WebElement cart = driver.findElement(By.cssSelector(".cart-button"));
		cart.click();

		WebElement continueToCheckout = driver.findElement(By.cssSelector(".checkout-button.alt"));
		continueToCheckout.click();

		Thread.sleep(5000);
		WebElement firstName = driver.findElement(By.cssSelector("#billing_first_name"));
		firstName.sendKeys("Lakshay");

		WebElement lastName = driver.findElement(By.cssSelector("#billing_last_name"));
		lastName.sendKeys("Sharma");

		WebElement emailAddress = driver.findElement(By.cssSelector("#billing_email"));
		emailAddress.sendKeys("test@gmail.com");

		WebElement phone = driver.findElement(By.cssSelector("#billing_phone"));
		phone.sendKeys("07438862327");

		/*
		 * Select countryDropDown = new
		 * Select(driver.findElement(By.id("select2-chosen-2")));
		 * countryDropDown.selectByVisibleText("Tamil Nadu");
		 */

		WebElement countryDropDown = driver.findElement(By.cssSelector("#billing_country_field .select2-arrow"));
		countryDropDown.click();
		Thread.sleep(2000);

		/*
		 * WebElement countryDropDown =
		 * driver.findElement(By.id("select2-chosen-2"));
		 * countryDropDown.click();
		 * 
		 * WebElement countryDropDownValue =
		 * driver.findElement(By.id("s2id_autogen2_search"));
		 * countryDropDownValue.sendKeys("Tamil");
		 * 
		 * WebElement countryDropDownValueTM =
		 * driver.findElement(By.id("select2-result-label-754"));
		 * countryDropDownValueTM.click();
		 */
		/*
		 * WebElement countryDropDown = driver.findElement(By.
		 * cssSelector("#billing_country_field .select2-arrow"));
		 * countryDropDown.click(); Thread.sleep(2000);
		 */

		List<WebElement> countryList = driver.findElements(By.cssSelector("#select2-drop ul li"));
		for (WebElement country : countryList) {
			if (country.getText().equals("India")) {
				country.click();
				Thread.sleep(3000);
				break;
			}
		}

		WebElement city = driver.findElement(By.cssSelector("#billing_city"));
		city.sendKeys("Delhi");

		WebElement address = driver.findElement(By.cssSelector("#billing_address_1"));
		address.sendKeys("Shalimar Bagh");

		WebElement postcode = driver.findElement(By.cssSelector("#billing_postcode"));
		postcode.sendKeys("110088");

		WebElement shipToDifferetAddress = driver.findElement(By.cssSelector("#ship-to-different-address-checkbox"));
		shipToDifferetAddress.click();
		Thread.sleep(3000);

		WebElement paymentMethod = driver.findElement(By.xpath("//*[@id=\"payment_method_cod\"]"));
		paymentMethod.click();

		WebElement acceptTC = driver.findElement(By.cssSelector("#terms.input-checkbox"));
		acceptTC.click();

		WebElement placeOrder = driver.findElement(By.cssSelector("#place_order"));
		placeOrder.submit();

		driver.quit();

		report.endTest(log);
		report.flush();
		
	}

}
