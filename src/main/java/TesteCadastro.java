import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class TesteCadastro {
	
	private WebDriver driver;
	private DSL dsl;
	private CampoTreinamentoPage page;
	
	@Before
	public void setUp() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		dsl = new DSL(driver);
		page = new CampoTreinamentoPage(driver);
	}
	
	@After
	public void tearDown() {
		driver.quit();
	}	

	@Test	
	public void deveRealizarCadastro(){	
		page.setNome("Wagner");
		page.setSobrenome("Costa");
		page.setSexoMasculino();
		page.setComidaPizza();
		page.setEscolaridade("Mestrado");
		page.setEsportes("Corrida");
		page.cadastrar();

		Assert.assertTrue(page.obterResultadoCadastro().startsWith("Cadastrado!"));		
		Assert.assertTrue(page.obterNomeCadastro().endsWith("Wagner"));
		Assert.assertEquals("Sobrenome: Costa", page.obterSobrenomeCadastro());
		Assert.assertEquals("Sexo: Masculino", page.obterSexoCadastro());
		Assert.assertEquals("Comida: Pizza", page.obterComidaCadastro());
		Assert.assertEquals("Escolaridade: mestrado", page.obterEscolaridadeCadastro());
		Assert.assertEquals("Esportes: Corrida", page.obterEsporteCadastro());
	}
	
	@Test
	public void deveValidarNome() {		
		page.cadastrar();		
		Assert.assertEquals("Nome eh obrigatorio", dsl.alertaObterTextoEAceita());		
	}

	@Test
	public void deveValidarSobrenome() {		
		page.setNome("Wanderson");
		page.cadastrar();		
		Assert.assertEquals("Sobrenome eh obrigatorio", dsl.alertaObterTextoEAceita());		
	}

	@Test
	public void deveValidarSexo() {		
		page.setNome("Wanderson");
		page.setSobrenome("Algusto");
		page.cadastrar();
		Assert.assertEquals("Sexo obrigatório", "Sexo eh obrigatorio", dsl.alertaObterTextoEAceita());
	}
	
	@Test
	public void deveValidarComigdaVegetariana() {
		page.setNome("Ana");
		page.setSobrenome("Beatriz");
		page.setSexoFeminino();
		page.setComidaCarne();
		page.setComidaVegetariano();
		page.cadastrar();
				
		Assert.assertEquals("Tem certeza que voce eh vegetariano?", dsl.alertaObterTextoEAceita());		
	}
	
	@Test
	public void deveValidarEsportes() {	
		page.setNome("Alex");
		page.setSobrenome("Ramos");
		page.setSexoMasculino();
		page.setComidaCarne();
		page.setComidaPizza();
		page.setEsportes("Natacao", "O que eh esporte?");		
		page.cadastrar();		
		Assert.assertEquals("Esportes", "Voce faz esporte ou nao?", dsl.alertaObterTextoEAceita());		
	}
}
