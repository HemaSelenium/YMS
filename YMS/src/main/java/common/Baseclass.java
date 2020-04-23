package common;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Reporter;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;



import io.appium.java_client.MobileElement;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.remote.MobileCapabilityType;
import io.appium.java_client.service.local.AppiumDriverLocalService;

public class Baseclass {
	public static AndroidDriver<MobileElement> driver ;
	protected static AppiumHelper helper = new AppiumHelper();
	
	public static String Repo_Username="fresh_val";
	public static String Repo_Password="test123";
	public static String YardPerson_Username="valuator_new";
	public static String YardPerson_Password="valuator_new";
	
	public static String NewPassword;
	private static final DateFormat sdf = new SimpleDateFormat("dd-MM-YYYY");
	static Calendar cal = Calendar.getInstance();
   	public static String Screenshotfolder="./ScreenShots/"+sdf.format(cal.getTime())+"/";
	public static String Apkpath="./APP/";
	public static String apkname;
	public static File file1 = new File("C:\\Users\\Hema Sumanjali\\OneDrive - Mahindra & Mahindra Ltd\\AutoInspekt\\"); 
	public static File file2 = new File("C:\\Users\\Hema Sumanjali\\git\\AutoinspektAPP\\AutoinspektApp\\APP\\"); 
	public static File file3 = new File("C:\\Users\\Hema Sumanjali\\OneDrive - Mahindra & Mahindra Ltd\\AutoInspekt-AfterSuite\\");
	 
	protected static Logger log=Logger.getLogger(Baseclass.class.getName());
	   static AppiumDriverLocalService service;
	   static String service_url;
	   public static File folder = new File(Apkpath);
	  // public static File InstalledApk = new File((getLatestFilefromDir(folder).getName()));
	   
	   @BeforeSuite
	   public void APKFIleAvailability() throws IOException {
		   //FileMove.FIleMove();
		 //  CopyFileFromDrive();
		   DOMConfigurator.configure("log4j.xml");
			
			if(getLatestFilefromDir(folder).getName().equals(null)) {
				//getLatestFilefromDir(folder);
				throw new NullPointerException("APK is not available To install ");
			}
			else {
				log.info("Available APK To Test is : "+getLatestFilefromDir(folder).getName());
				getLatestFilefromDir(folder);
				
			}
			log.info(" ");
			
	   }
	   	   
	   @BeforeSuite
	  
	public void setUp() throws IOException{
	
	DesiredCapabilities caps = new DesiredCapabilities();
	caps.setCapability(CapabilityType.BROWSER_NAME,"");
		
	
	//Emulator
	
	//caps.setCapability(MobileCapabilityType.UDID, "3c5c437");
	caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Pixel XL API 28 2");
	//caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "9.0");
		
	/*//	Hema
		caps.setCapability(MobileCapabilityType.UDID, "3c5c437");
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Redmi");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.1.0");
		*/
		
		/*//Sony
		caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Sony");
       caps.setCapability(MobileCapabilityType.UDID, "WUJ01L89HK");
		caps.setCapability(MobileCapabilityType.PLATFORM_NAME, "Android");
		caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0");
		
*/
	caps.setCapability(MobileCapabilityType.APP, getLatestFilefromDir(folder).getAbsolutePath());
		//caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "6.0.1");



/*//sindu
caps.setCapability(MobileCapabilityType.UDID, "ZF622337CZ");
caps.setCapability(MobileCapabilityType.DEVICE_NAME, "Moto");
caps.setCapability(MobileCapabilityType.PLATFORM_VERSION, "8.0.0");*/

		caps.setCapability("noReset", true);
	//caps.setCapability("fullReset","false"); 
		caps.setCapability("unicodeKeyboard", "true");
		caps.setCapability("automationName", "UiAutomator2");
		caps.setCapability("appPackage", "com.mahindra.ibbtrade_pro");
	//	caps.setCapability("appActivity", "com.mahindra.ibbtrade_pro.Activity.SplashActivity");
		caps.setCapability("appActivity", "com.mahindra.ibbtrade_pro.splashScreen.view.SplashActivity");
		caps.setCapability("autoGrantPermissions",true);
		driver = new AndroidDriver<MobileElement>(new URL("http://0.0.0.0:4723/wd/hub"), caps);
			driver.manage().timeouts().implicitlyWait(1, TimeUnit.MINUTES);
		
		
	}
	   @Test
	   public void login() throws Exception {
		  helper.login(driver, Repo_Username, Repo_Password);
	   }

@AfterClass
public void TestcaseEnd() {
	Reporter.log("==================================================================================================");
	
}
	@AfterSuite
	public void tearDown() throws InterruptedException, IOException {
	Thread.sleep(1000);
		//RemoveFile();
		
		driver.quit();
		Reporter.log("========Testcase ========"+driver.getClass().getSimpleName()+"====== Ended ========");
		Reporter.log("===================   ==================    =======================   =====================");
		//service.stop();
		 // service.stop();
	}
	public static void TakeScreenShot(AndroidDriver<MobileElement> driver,String FileName) throws IOException{
		File scrFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
		FileUtils.copyFile(scrFile,new File("./ScreenShots/"+FileName+".png"));
	}
	public static String listFilesForFolder(final File folder) {
		
		    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	apkname=fileEntry.getName();
	       	    log.info(fileEntry.getName());
	            
	        
	    }
	   
	}
		return apkname;
	}

	private static File getLatestFilefromDir(final File folder){
	  //  File dir = new File(folder);
	    File[] files = folder.listFiles();
	   // log.info("Total Files Available in the Folder : "+files.length);
	    if (files == null || files.length == 0) {
	    	log.info("There is No APK file To Build Test cases");
	        return null;
	    }
	    

	    File lastModifiedFile = files[0];
	    //File lastModifiedFile = files[files.length-1];
	    for (int i = 0; i <=files.length-1; i++) {
	       if (lastModifiedFile.lastModified() < files[i].lastModified()) {
	           lastModifiedFile = files[i];
	           //log.info("Last Modified File: "+lastModifiedFile.getName());
	       }
	    }
	    return lastModifiedFile;
	}






}
