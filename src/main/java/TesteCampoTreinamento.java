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

	@After
	public void finaliza() {
//		driver.quit();
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
		String idEscolaridade = "elementosForm:escolaridade";
		Assert.assertEquals(8, dsl.obterQuantidadeOpcoesCombo(idEscolaridade));
		Assert.assertTrue(dsl.verificarOpcaoCombro(idEscolaridade, "Mestrado"));
	}
	
	@Test
	public void deveVerificarValoresComboMultiplo(){		
		String idEsportes = "elementosForm:esportes";
		dsl.selecionar(idEsportes, "Natacao");
		dsl.selecionar(idEsportes, "Corrida");
		dsl.selecionar(idEsportes, "O que eh esporte?");
		
		List<WebElement> opMarcadas = dsl.obterValoresCombo(idEsportes);
		Assert.assertEquals(3, opMarcadas.size());		

		dsl.deSelecionarCombo(idEsportes, "Corrida");
		opMarcadas = dsl.obterValoresCombo(idEsportes);
		Assert.assertEquals(2, opMarcadas.size());
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
	
	@Test
	public void testTextFieldDuplo() {
		String campoNome = "elementosForm:nome";
		dsl.escrever(campoNome, "Wagner");
		Assert.assertEquals("Wagner", dsl.obterValorCampo(campoNome));
		dsl.escrever(campoNome, "Acquino");
		Assert.assertEquals("Acquino", dsl.obterValorCampo(campoNome));		
	}
	
	@Test
	public void testJavaScript() {	
		WebElement element = driver.findElement(By.id("elementosForm:nome"));
		dsl.executarJS("arguments[0].style.border = arguments[1]", element, "solid 4px #c49");
	}
	
	@Test
	public void deveClicarBotaoTabela() {
		dsl.clicarBotaoTabela("Escolaridade", "Mestrado", "Radio", "elementosForm:tableUsuarios");
	}
	
}


