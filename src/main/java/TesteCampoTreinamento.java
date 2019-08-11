import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCampoTreinamento {

	private WebDriver driver;
	private DSL dsl;
	
	@Before
	public void inicializa() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
	}
	
	@Test
	public void testeTextField(){		
		dsl.escrever("elementosForm:nome", "Teste de escrita");
		dsl.obterValorCampo("elementosForm:nome");
	}
	
	@Test
	public void deveIntegarirComTextArea(){		
		dsl.escrever("elementosForm:sugestoes", "teste\n\naasldjdlks\nUltima linha");
		Assert.assertEquals("teste\n\naasldjdlks\nUltima linha", dsl.obterValorCampo("elementosForm:sugestoes"));
	}
	
	@Test
	public void deveIntegarirComRadioButton(){		
		dsl.clicarRadio("elementosForm:sexo:0");
		Assert.assertTrue(dsl.isRadioMarcado("elementosForm:sexo:0"));
	}
	
	@Test
	public void deveIntegarirComCheckbox(){		
		dsl.clicarRadio("elementosForm:comidaFavorita:2");
		Assert.assertTrue(dsl.isRadioMarcado("elementosForm:comidaFavorita:2"));
	}
	
	@Test
	public void deveIntegarirComCombo(){		
		dsl.selecionar("elementosForm:escolaridade", "2o grau completo");		
		dsl.obterValorCombo("elementosForm:escolaridade");
	}
	
	@Test
	public void deveVerificarValoresCombo(){
		WebElement element = driver.findElement(By.id("elementosForm:escolaridade"));
		Select combo = new Select(element);
		List<WebElement> options = combo.getOptions();
		Assert.assertEquals(8, options.size());
		
		boolean encontrou = false;
		for(WebElement option: options) {
			if(option.getText().equals("Mestrado")){
				encontrou = true;
				break;
			}
		}
		Assert.assertTrue(encontrou);
	}
	
	@Test
	public void deveVerificarValoresComboMultiplo(){		
		WebElement element = driver.findElement(By.id("elementosForm:esportes"));
		Select combo = new Select(element);
		dsl.selecionar("elementosForm:esportes", "Natacao");
		dsl.selecionar("elementosForm:esportes", "Corrida");
		dsl.selecionar("elementosForm:esportes", "O que eh esporte?");
		
		List<WebElement> allSelectedOptions = combo.getAllSelectedOptions();
		Assert.assertEquals(3, allSelectedOptions.size());
		
		combo.deselectByVisibleText("Corrida");
		allSelectedOptions = combo.getAllSelectedOptions();
		Assert.assertEquals(2, allSelectedOptions.size());
	}
	
	@Test
	public void deveinteragirComBotoes(){		
		dsl.clicarBotao("buttonSimple");		
		Assert.assertEquals("Obrigado!", dsl.obterValorCampo("buttonSimple"));
	}
	
	@Test	
	public void deveinteragirComLinks(){
		dsl.clicarLink("Voltar");
		Assert.assertEquals("Voltou!", dsl.obterTexto("resultado"));		
	}
	
	@Test
	public void deveBuscarTextosPagina() {
		Assert.assertEquals("Campo de Treinamento", dsl.obterTexto(By.tagName("h3")));
		Assert.assertEquals("Cuidado onde clica, muitas armadilhas...", 
				dsl.obterTexto(By.className("facilAchar")));
	}
	
	@After
	public void finaliza() {
		driver.quit();
	}
	
}


