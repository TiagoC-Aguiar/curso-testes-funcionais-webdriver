import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteFramesEJanelas {

	private WebDriver driver;
	private DSL dsl;
	private String textArea = "textarea";

	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
	}
	
	@After
	public void finaliza() {
		driver.quit();
	}
	
	@Test	
	public void deveInteragirComFrames(){	
		dsl.entrarFrame("frame1");
		dsl.clicarBotao("frameButton");
		String msg = dsl.alertaObterTextoEAceita();
		Assert.assertEquals("Mensagem de alerta", "Frame OK!", msg);		
		dsl.sairFrame();
		dsl.escrever("elementosForm:nome", msg);
	}
	
	@Test
	public void deveInteragirComJanelas() {
		dsl.clicarBotao("buttonPopUpEasy");
		dsl.trocarJanela("Popup");
		dsl.escrever(By.tagName(textArea), "Deu certo?");
		driver.close();
		dsl.trocarJanela("");
		dsl.escrever(By.tagName(textArea), "E agora?");
	}
	
	@Test
	public void deveInteragirComJanelasSemTitulo() {		
		dsl.clicarBotao("buttonPopUpHard");
		System.out.println(driver.getWindowHandle());
		System.out.println(driver.getWindowHandles());
		dsl.trocarJanela((String) driver.getWindowHandles().toArray()[1]);
		dsl.escrever(By.tagName(textArea), "Deu certo?");
		dsl.trocarJanela((String) driver.getWindowHandles().toArray()[0]);
//		driver.findElement(By.tagName("textarea")).sendKeys("E agora?");
		dsl.escrever(By.tagName(textArea), "e agora?");
	}
	
	@Test
	public void deveDigitarNome() {		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("");
	}
	
}
