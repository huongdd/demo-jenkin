package demo;

import java.util.concurrent.TimeUnit;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.AfterTest;

public class Testlogin {
	WebDriver driver;

	By resultIframe = By.xpath("//iframe[@id='result']");
	By nameTextbox = By.xpath("//input[@id='fname']");
	By passwordTextbox = By.xpath("//input[@id='pass']");
	By emailTextbox = By.xpath("//input[@id='em']");
	By submitButton = By.xpath("//input[@value='SUBMIT']");

	@BeforeTest
	public void beforeTest() {
		driver = new FirefoxDriver();
		driver.get("https://automationfc.github.io/html5/index.html");
		driver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
		driver.switchTo().frame(driver.findElement(resultIframe));
	}

	@Test
	public void TC_01_Validate_Name_Empty() {
		driver.findElement(nameTextbox).sendKeys("");
		driver.findElement(submitButton).click();
		String nameMessage = getHTML5ValidationMessage(driver.findElement(nameTextbox));
		Assert.assertEquals("Please fill out this field.", nameMessage);
	}

	@Test
	public void TC_02_Validate_Password_Empty() {
		driver.findElement(nameTextbox).sendKeys("Dam Dao");
		driver.findElement(passwordTextbox).sendKeys("");
		driver.findElement(submitButton).click();
		String passwordMessage = getHTML5ValidationMessage(driver.findElement(passwordTextbox));
		Assert.assertEquals("Please fill out this field.", passwordMessage);
	}

	@Test
	public void TC_03_Validate_Email_Empty() {
		driver.findElement(nameTextbox).sendKeys("Dam Dao");
		driver.findElement(passwordTextbox).sendKeys("123456");
		driver.findElement(emailTextbox).sendKeys("");
		driver.findElement(submitButton).click();
		String emailMessage = getHTML5ValidationMessage(driver.findElement(emailTextbox));
		Assert.assertEquals("Please fill out this field.", emailMessage);
	}

	@Test
	public void TC_04_Validate_Email_Invalid() {
		driver.findElement(nameTextbox).sendKeys("Dam Dao");
		driver.findElement(passwordTextbox).sendKeys("123456");
		driver.findElement(emailTextbox).sendKeys("dam@gmail");
		driver.findElement(submitButton).click();
		String emailInvalidMessage = getHTML5ValidationMessage(driver.findElement(emailTextbox));
		Assert.assertEquals("Please match the requested format.", emailInvalidMessage);
	}

	@Test
	public void TC_05_Validate_Email_Incorrect() {
		driver.findElement(nameTextbox).sendKeys("Dam Dao");
		driver.findElement(passwordTextbox).sendKeys("123456");
		driver.findElement(emailTextbox).sendKeys("dam@gmail@email.com");
		driver.findElement(submitButton).click();
		String emailIncorrectMessage = getHTML5ValidationMessage(driver.findElement(emailTextbox));
		Assert.assertEquals("Please enter an email address.", emailIncorrectMessage);
	}

	public String getHTML5ValidationMessage(WebElement element) {
		JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
		return (String) jsExecutor.executeScript("return arguments[0].validationMessage;", element);
	}

	@AfterTest
	public void afterTest() {
		driver.quit();
	}

}
