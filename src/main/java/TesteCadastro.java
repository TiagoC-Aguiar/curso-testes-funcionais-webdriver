import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCadastro {
	
	private WebDriver driver;
	private DSL dsl;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}	

	@Test	
	public void deveRealizarCadastro(){	
		dsl.escrever("elementosForm:nome", "Wagner");
		dsl.escrever("elementosForm:sobrenome", "Costa");
		dsl.clicarRadio("elementosForm:sexo");		
		dsl.clicarRadio("elementosForm:comidaFavorita:2");
		dsl.selecionar("elementosForm:escolaridade", "Mestrado");
		dsl.selecionar("elementosForm:esportes", "Corrida");
		dsl.clicarBotao("elementosForm:cadastrar");

		Assert.assertTrue(dsl.obterTexto("resultado").startsWith("Cadastrado!"));		
		Assert.assertTrue(dsl.obterTexto("descNome").endsWith("Wagner"));
		Assert.assertEquals("Sobrenome: Costa", dsl.obterTexto("descSobrenome"));
		Assert.assertEquals("Sexo: Masculino", dsl.obterTexto("descSexo"));
		Assert.assertEquals("Comida: Pizza", dsl.obterTexto("descComida"));
		Assert.assertEquals("Escolaridade: mestrado", dsl.obterTexto("descEscolaridade"));
		Assert.assertEquals("Esportes: Corrida", dsl.obterTexto("descEsportes"));
	}
	
	@Test
	public void deveValidarNome() {		
		dsl.clicarBotao("elementosForm:cadastrar");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Mensagem de alerta: nome obrigatório", 
				"Nome eh obrigatorio", alert.getText());
		alert.accept();		
	}

	@Test
	public void deveValidarSobrenome() {		
		dsl.escrever("elementosForm:nome", "Wanderson");
		dsl.clicarBotao("elementosForm:cadastrar");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Msg alerta: sobrenome obrigatório", "Sobrenome eh obrigatorio", 
				alert.getText());
		alert.accept();	
	}

	@Test
	public void deveValidarSexo() {		
		dsl.escrever("elementosForm:nome", "Wanderson");
		dsl.escrever("elementosForm:sobrenome", "Algusto");
		dsl.clicarBotao("elementosForm:cadastrar");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo obrigatório", "Sexo eh obrigatorio", alert.getText());
	}
	
	@Test
	public void deveValidarComigdaVegetariana() {
		dsl.escrever("elementosForm:nome", "Wanderson");
		dsl.escrever("elementosForm:sobrenome", "Algusto");
		dsl.clicarRadio("elementosForm:sexo");
		dsl.clicarRadio("elementosForm:comidaFavorita:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:3");
		dsl.clicarRadio("elementosForm:cadastrar");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Vegetariano", "Tem certeza que voce eh vegetariano?", alert.getText());
		alert.accept();	
	}
	
	@Test
	public void deveValidarEsportes() {	
		dsl.escrever("elementosForm:nome", "Wanderson");
		dsl.escrever("elementosForm:sobrenome", "Algusto");
		dsl.clicarRadio("elementosForm:sexo");
		dsl.clicarRadio("elementosForm:comidaFavorita:0");
		dsl.clicarRadio("elementosForm:comidaFavorita:2");
		dsl.selecionar("elementosForm:esportes", "Natacao");
		dsl.selecionar("elementosForm:esportes", "O que eh esporte?");
		dsl.clicarRadio("elementosForm:cadastrar");
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Esportes", "Voce faz esporte ou nao?", alert.getText());
		alert.accept();		
	}
}
