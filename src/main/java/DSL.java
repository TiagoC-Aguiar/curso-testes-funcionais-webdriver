import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DSL {
	
	private WebDriver driver;
	
	public DSL(WebDriver driver) {
		this.driver = driver;
	}

	public void escrever(String id_campo, String texto) {
		driver.findElement(By.id(id_campo)).sendKeys(texto);
	}
	
	public String obterValorCampo(String id_campo) {
		return driver.findElement(By.id(id_campo)).getAttribute("value");
	}
	
	public void clicarRadio(String id) {
		driver.findElement(By.id(id)).click();
	}
	
	public boolean isRadioMarcado(String id) {
		return driver.findElement(By.id(id)).isSelected();
	}
	
	private Select selectList(String id) {
		WebElement element = driver.findElement(By.id(id));
		return new Select(element);
	}
	
	public void selecionar(String id, String valor) {
		selectList(id).selectByVisibleText(valor);
	}
	
	public String obterValorCombo(String id) {		
		return selectList(id).getFirstSelectedOption().getText();
	}
	
	public void clicarBotao(String id) {
		driver.findElement(By.id(id)).click();		
	}
	
	public void clicarLink(String textoLink) {
		driver.findElement(By.linkText(textoLink)).click();
	}
	
	public String obterTexto(By by) {
		return driver.findElement(by).getText();
	}
	
	public String obterTexto(String id) {
		return obterTexto(By.id(id));
	}
	
}
