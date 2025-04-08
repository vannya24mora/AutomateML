package testBrowsers;

import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.appium.java_client.android.AndroidDriver;

public class TestFirefox_03 {
	public static FirefoxDriver driver;
	public static AndroidDriver driverAnd;
	String browserName = "Firefox";
	String urlLoginSky = "https://www.mercadolibre.com";

	static List<Object[]> datosTest = new ArrayList<>();

	@BeforeClass
	public void setupClass() {

		System.out.println("BeforeClass setup");
		FirefoxOptions options = new FirefoxOptions();
		options.addArguments("--start-maximized");
		options.addArguments("--enable-plugins");
		options.addArguments("--always-authorize-plugins=true");

		driver = new FirefoxDriver(options);
//		Point positionInit = new Point(2000, 0); // en firefox no se puede mover el driver

//		driver.manage().window().setPosition(positionInit);
		driver.manage().window().maximize();
		driver.manage().window().setSize(driver.manage().window().getSize());
	}

	public void setup() {
	
		int intentos = 1; // inicia con 1 porque 5/0 es 0¿?
		By bttnMx = By.xpath("//*[contains(@id, 'MX')]");
		try {
			driver.manage().deleteAllCookies();
			driver.get(urlLoginSky);
			Duration timeMax = Duration.ofMillis(1000);
			WebDriverWait wait = new WebDriverWait(driver, timeMax);
			wait.until(ExpectedConditions.presenceOfElementLocated(bttnMx));
			System.out.println("Cargo la pagina login");
		} catch (Exception e) {
			try {
				driver.manage().deleteAllCookies();
				driver.get(urlLoginSky);
			} catch (Exception e2) {
				System.out.println("No se pudo refrescar la pagina setup");
			}
			System.out.println("No cargo" + e.getMessage());
			intentos++;
		}

	}

//@AfterMethod
	public void tearDown() {
		if (driver != null) {
			driver.quit();
		}

	}

	@AfterClass
	public void closeDriver() {
		try {
			if (driver != null) {
				driver.quit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	

	@Test(priority = 1)
	public void prueba1() {
		int order=0;
		setup();
		delayms(2000);
		takeSS("ScreenShots/",driver,order++);
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofMillis(1500));
		By bttnMx = By.xpath("//*[contains(@id, 'MX')]");
		By searchBar = By.xpath("//*[contains(@class, 'nav-search-input')]");
		By filterNew=By.xpath("//*[contains(@class, 'ui-search-filter-name')][contains(text(), 'Nuevo')]");
		By filterCdmx=By.xpath("//*[contains(@class, 'ui-search-filter-name')][contains(text(), 'Distrito Federal')]");
		By orderFilter=By.xpath("//*[contains(@class, 'andes-dropdown__display-values')]");
		By filterPrice=By.xpath("//*[contains(@id, 'menu-list-option-price_asc')]");
		By tittleElmt=By.xpath("//*[contains(@class, 'poly-component__title-wrapper')]");//poly-component__title-wrapper
		By priceElmt=By.xpath("//*[contains(@class, 'poly-price__current')]//*[contains(@class, 'andes-money-amount__fraction')]");
		
		try {
			wait.until(ExpectedConditions.elementToBeClickable(bttnMx));
			driver.findElement(bttnMx).click();		
			delayms(700);
			takeSS("ScreenShots/",driver,order++);
			try {
				driver.findElement(By.xpath("//*[contains(@class, 'onboarding-cp-button andes-button andes-button--transparent andes-button--small')]")).click();
			} catch (Exception e) {
				System.out.println("No se vio el mensaje Ingresa CP");
			}
			wait.until(ExpectedConditions.elementToBeClickable(searchBar));
			delayms(700);
			driver.findElement(searchBar).sendKeys("playstation 5");
			delayms(700);
			takeSS("ScreenShots/",driver,order++);
			driver.findElement(searchBar).sendKeys(Keys.RETURN);
			delayms(2500);
			takeSS("ScreenShots/",driver,order++);
			driver.findElement(filterNew).click();
			delayms(2500);
			takeSS("ScreenShots/",driver,order++);
			scrollIntoView(driver, filterCdmx);
			driver.findElement(filterCdmx).click();
			delayms(2500);
			takeSS("ScreenShots/",driver,order++);
			driver.findElement(orderFilter).click();
			delayms(1500);
			takeSS("ScreenShots/",driver,order++);
			wait.until(ExpectedConditions.elementToBeClickable(filterPrice));
			driver.findElement(filterPrice).click();
			delayms(1500);
			takeSS("ScreenShots/",driver,order++);
			List<WebElement> priceElmts=driver.findElements(priceElmt);
			List<WebElement> nameElmts=driver.findElements(tittleElmt);
			for (int i = 0; i < 5; i++) {
				System.out.println(nameElmts.get(i).getText()+"; \n $"+priceElmts.get(i).getText()+"\n ----\n");
				
				
			}
			
			
			
			
			
			
		} catch (Exception e) {
			System.out.println("Error realizando la prueba: \n"+e);
		} finally {
			tearDown();
		}
	}
	
	
public static void takeSS(String pathSS, WebDriver driver, int order) {			
			File capturaPantalla = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
			String rutaGuardado = pathSS + "_" + order +  ".png";
			// Copiar el archivo de captura de pantalla a la ubicación deseada
			try {
				FileUtils.copyFile(capturaPantalla, new File(rutaGuardado));
			} catch (IOException e) {
				e.printStackTrace();
			}
	}

public static void delayms(int millis) {

	try {
		Thread.sleep(millis);
	} catch (InterruptedException e) {
		e.printStackTrace();
	}

}

public static void scrollIntoView(WebDriver driver, By locator) {
	WebElement elmt = driver.findElement(locator);
	JavascriptExecutor jse = (JavascriptExecutor) driver;
	jse.executeScript("arguments[0].scrollIntoView(true);", elmt);
	
}
	

}
