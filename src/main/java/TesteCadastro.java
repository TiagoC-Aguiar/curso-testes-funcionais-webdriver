import org.junit.Assert;
import org.junit.Test;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;

public class TesteCadastro {

//	@Test	
	public void deveRealizarCadastro(){
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Wagner");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Costa");
		driver.findElement(By.id("elementosForm:sexo")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		
		new Select(driver.findElement(By.id("elementosForm:escolaridade")))
			.selectByVisibleText("Mestrado");
		
		new Select(driver.findElement(By.id("elementosForm:esportes")))
			.selectByValue("Corrida");
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		
		Assert.assertEquals("Mensagem de cadastrado", "Cadastrado!", driver.findElement(
				By.id("resultado")).findElement(By.tagName("span")).getText());
		
		Assert.assertEquals("Nome cadastrado", "Wagner", driver.findElement(
				By.id("descNome")).findElement(By.tagName("span")).getText());
		Assert.assertEquals("Sobrenome Cadastrado", "Costa", driver.findElement(
				By.id("descSobrenome")).findElement(By.tagName("span")).getText());
		Assert.assertEquals("Sexo Cadastrado", "Masculino", driver.findElement(
				By.id("descSexo")).findElement(By.tagName("span")).getText());
		Assert.assertEquals("Comida cadastrada", "Comida: Pizza", driver.findElement(By.id("descComida")).getText());
		Assert.assertEquals("Escolaridade cadastrada", "Escolaridade: mestrado", driver.findElement(By.id("descEscolaridade")).getText());
		Assert.assertEquals("Esporte cadastrado", "Esportes: Corrida", driver.findElement(By.id("descEsportes")).getText());
		
		driver.quit();
	}
	
	@Test
	public void deveValidarNome() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Mensagem de alerta: enome obrigatório", 
				"Nome eh obrigatorio", alert.getText());
		alert.accept();
		driver.quit();
	}

	@Test
	public void deveValidarSobrenome() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Wanderson");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Msg alerta: sobrenome obrigatório", "Sobrenome eh obrigatorio", 
				alert.getText());
		alert.accept();
		driver.quit();		
	}

	@Test
	public void deveValidarSexo() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Wanderson");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Algusto");	
//		driver.findElement(By.id("elementosForm:sexo")).click();
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Sexo obrigatório", "Sexo eh obrigatorio", alert.getText());
		driver.quit();
	}
	
	@Test
	public void deveValidarComigdaVegetariana() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
	
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Wanderson");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Algusto");	
		driver.findElement(By.id("elementosForm:sexo")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:3")).click();
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Vegetariano", "Tem certeza que voce eh vegetariano?", alert.getText());
		alert.accept();
		driver.quit();
	}
	
	@Test
	public void deveValidarEsportes() {
		System.setProperty("webdriver.chrome.driver", "/home/tiago/Drivers/chromedriver");
		WebDriver driver = new ChromeDriver();
		driver.manage().window().setSize(new Dimension(1300, 1050));
		driver.get("file:///" + System.getProperty("user.dir") + "/src/main/resources/componentes.html");
		
		driver.findElement(By.id("elementosForm:nome")).sendKeys("Wanderson");
		driver.findElement(By.id("elementosForm:sobrenome")).sendKeys("Algusto");	
		driver.findElement(By.id("elementosForm:sexo")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:0")).click();
		driver.findElement(By.id("elementosForm:comidaFavorita:2")).click();
		Select select = new Select(driver.findElement(By.id("elementosForm:esportes")));
		select.selectByVisibleText("Natacao");
		select.selectByValue("nada");
		driver.findElement(By.id("elementosForm:cadastrar")).click();
		Alert alert = driver.switchTo().alert();
		Assert.assertEquals("Esportes", "Voce faz esporte ou nao?", alert.getText());
		alert.accept();
		driver.quit();
	}
}
