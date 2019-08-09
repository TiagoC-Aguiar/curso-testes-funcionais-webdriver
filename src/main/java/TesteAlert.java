import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteAlert {

	private WebDriver driver;

	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	}
	
	@After
	public void finaliza() {
		driver.quit();
	}

	@Test
	public void deveInteragirComAlertSimples() {
		driver.findElement(By.id("alert")).click();
		Alert alert = driver.switchTo().alert();
		String msgAlerta = alert.getText();
		Assert.assertEquals("Alert Simples", msgAlerta);
		alert.accept();

		driver.findElement(By.id("elementosForm:nome")).sendKeys(msgAlerta);
	}

	@Test
	public void deveInteragirComAlertConfirm() {
		WebElement confirm = driver.findElement(By.id("confirm"));

		confirm.click();
		Alert alert = driver.switchTo().alert();
		alert.dismiss();
		Assert.assertEquals("Negado", alert.getText());
		alert.accept();

		confirm.click();
		alert.accept();
		Assert.assertEquals("Confirmado", alert.getText());
		alert.accept();
	}

	@Test
	public void deveInteragirComAlertPrompt() {
		driver.findElement(By.id("prompt")).click();
		Alert alert = driver.switchTo().alert();

		Assert.assertEquals("Digite um numero", alert.getText());
		alert.sendKeys("12");
		alert.accept();
		Assert.assertEquals("Era 12?", alert.getText());
		alert.accept();
		Assert.assertEquals(":D", alert.getText());
		alert.accept();
	}
}
