/**
 * 
 */
package common;

/**
 * @author Hema Sumanjali
 *
 */


import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.ITestNGMethod;
import org.testng.Reporter;


import io.appium.java_client.MobileElement;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidDriver;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;


public class AppiumHelper {

	

	private static WebElement Element = null;

	public static WebElement FindElementById(AndroidDriver<MobileElement> driver, String ID) {
		Element = driver.findElementById(ID);
		return Element;
	}

	public static WebElement FindElementByXpath(AndroidDriver<MobileElement> driver, String Xpath) {
		Element = driver.findElementByXPath(Xpath);
		return Element;
	}

	public static WebElement FindElementByAccesabilityID(AndroidDriver<MobileElement> driver, String ID) {
		Element = driver.findElementByAccessibilityId(ID);
		return Element;
	}

	public void clickOnElementByXpath(AndroidDriver<MobileElement> driver, String Xpath) throws Exception {
		try {
		AppiumHelper.FindElementByXpath(driver, Xpath).click();
		}
		catch (NoSuchElementException e) {
			scrollUp(driver);
			AppiumHelper.FindElementByXpath(driver, Xpath).click();
		}

	}

	public void clickOnElementByID(AndroidDriver<MobileElement> driver, String ID) throws Exception {
		try {
			AppiumHelper.FindElementById(driver, ID).click();
		} catch (NoSuchElementException e) {
			scrollUp(driver);
			AppiumHelper.FindElementById(driver, ID).click();
		}
	}

	public void clickOnElementByAccessabilityID(AndroidDriver<MobileElement> driver, String ID) {
		AppiumHelper.FindElementByAccesabilityID(driver, ID).click();
	}

	public void SendkeysById(AndroidDriver<MobileElement> driver, String ID, String Input) {
		AppiumHelper.FindElementById(driver, ID).sendKeys(Input);

	}

	public void SendkeysByXpath(AndroidDriver<MobileElement> driver, String Xpath, String Input) {
		AppiumHelper.FindElementById(driver, Xpath).sendKeys(Input);
	}

	public void ScrollToViewElement(AndroidDriver<MobileElement> driver, String element) throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector()).scrollIntoView(text(\"" + element + "\"));");
	}

	public void ScrollToViewElementcontainsPerticularText(AndroidDriver<MobileElement> driver, String element)
			throws InterruptedException {
		driver.manage().timeouts().implicitlyWait(2, TimeUnit.MINUTES);

		// driver.findElementByAndroidUIAutomator("new UiScrollable(new
		// UiSelector()).scrollIntoView(textContains(\""+element+"\"));");

		driver.findElementByAndroidUIAutomator(
				"new UiScrollable(new UiSelector().scrollable(true).instance(0)).scrollIntoView(new UiSelector().text(\""
						+ element + "\").instance(0))");
	}

	public void ScrollToPerticularElement(AndroidDriver<MobileElement> driver, String element) {
		driver.manage().timeouts().implicitlyWait(3, TimeUnit.MINUTES);
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
				+ ".resourceId(\"co.in.mfcwl.valuation.autoinspekt.autoinspekt:id/recycler_view_myJob\")).scrollIntoView("
				+ "new UiSelector().text(\"" + element + "\"));");
	}

	public void ScrollToElementInAddJobScreen(AndroidDriver<MobileElement> driver, String element) {
		driver.findElementByAndroidUIAutomator("new UiScrollable(new UiSelector()"
				+ ".resourceId(\"co.in.mfcwl.valuation.autoinspekt.autoinspekt:id/llTop\")).scrollIntoView("
				+ "new UiSelector().text(\"" + element + "\"));");

	}

	public void ScrollInMainPage(AndroidDriver<MobileElement> driver) {
		(new TouchAction(driver)).press(PointOption.point(535, 1702))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(474, 642))
				.release().perform();
	}

	
	
	

	public void IsToastMessageDisplayed(AndroidDriver<MobileElement> driver) {
		try {
			WebElement ToastView = driver.findElementByXPath("//android.widget.Toast[1]");
			if (ToastView.isDisplayed()) {
				this.ScrollInMainPage(driver);
				Reporter.log("toast");
			}
		} catch (NoSuchElementException e) {
			Reporter.log("exception");
		}
	}

	public void login(AndroidDriver<MobileElement> driver, String UserName, String Password) throws Exception {

		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		try {
			driver.findElementById("com.mahindra.ibbtrade_pro:id/login_username_et").click();
			driver.findElementById("com.mahindra.ibbtrade_pro:id/login_username_et").sendKeys(UserName);
			driver.findElementById("com.mahindra.ibbtrade_pro:id/login_password_et").click();
			driver.findElementById("com.mahindra.ibbtrade_pro:id/login_password_et").sendKeys(Password);
			driver.findElementById("com.mahindra.ibbtrade_pro:id/login_submit_bt").click();

		} catch (NoSuchElementException e) {

		}

	}

	

	

	public static void TakeScreenShot(AndroidDriver<MobileElement> driver, String FileName) throws IOException {
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("./ScreenShots/" + FileName + ".png"));
	}

	public static void TakeScreenShot(AndroidDriver<MobileElement> driver, ITestNGMethod testMethod)
			throws IOException {
		// TODO Auto-generated method stub
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile, new File("./ScreenShots/" + testMethod + ".png"));
	}

	public void scrollUp(AndroidDriver<MobileElement> driver) throws Exception {

		// The viewing size of the device
		Dimension size = driver.manage().window().getSize();

		// Starting y location set to 70% of the height (near bottom)
		int starty = (int) (size.height * 0.70);
		// Ending y location set to 20% of the height (near top)
		int endy = (int) (size.height * 0.20);
		// x position set to mid-screen horizontally
		int startx = size.width / 2;
		(new TouchAction(driver)).press(PointOption.point(startx, starty))
				.waitAction(WaitOptions.waitOptions(Duration.ofSeconds(0))).moveTo(PointOption.point(startx, endy))
				.release().perform();
		/*
		 * (new TouchAction(driver)) .press(PointOption.point(535,
		 * 1702)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000)))
		 * .moveTo(PointOption.point(474, 642)).release().perform(); (2080, 2030)
		 * 540,1624 540,406
		 */
	}

	public void scrollDown(AndroidDriver<MobileElement> driver) throws Exception {

		// The viewing size of the device
		(new TouchAction(driver)).press(PointOption.point(345, 471))
				.waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(336, 731))
				.release().perform();

	}

	public static String RegNum() {
		UUID uuid = UUID.randomUUID();
		String randomUUIDString = uuid.toString().substring(0, 7);
		StringBuffer sbf = new StringBuffer("AP" + randomUUIDString);
		// Reporter.log("Reg No: " +sbf.toString());
		return sbf.toString();
	}

		
	
	
	
	

	public void selectMultispare(AndroidDriver<MobileElement> driver, String strings) {
		String[] spl = strings.split(",");

		for (int i = 0; i < spl.length; i++) {
			String feature = spl[i];
		//	System.out.println("Dropdown values : " + feature);
			AppiumHelper.FindElementByXpath(driver, "//android.widget.CheckBox[@text='" + feature + "']").click();
					}


	
	
}
}
