import java.util.List;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;

public class DSL {
	
	private WebDriver driver;
	
	public DSL(WebDriver driver) {
		this.driver = driver;
	}

	public void escrever(String id_campo, String texto) {
		escrever(By.id(id_campo), texto);
	
	}
	public void escrever(By by, String texto) {
		driver.findElement(by).clear();
		driver.findElement(by).sendKeys(texto);
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
	
	public int obterQuantidadeOpcoesCombo(String id) {				 
		return selectList(id).getOptions().size();		
	}
	
	public boolean verificarOpcaoCombro(String id, String valor) {
		boolean encontrou = false;
		for(WebElement option: selectList(id).getOptions()) {
			if(option.getText().equals(valor)){
				encontrou = true;
				break;
			}
		}
		return encontrou;
	}
	
	public List<WebElement> obterValoresCombo(String id) {
		return selectList(id).getAllSelectedOptions();	
	}
	
	public void deSelecionarCombo(String id, String valor) {
		selectList(id).deselectByVisibleText(valor);
	}
	
	public String alertaObterTextoEAceita() {		
		String msg = alertaObterTexto();
		focarAlert().accept();
		return msg;
	}
	
	public String alertaObterTextoENega() {
		String msg = alertaObterTexto();		
		focarAlert().dismiss();
		return msg;
	}
	
	private Alert focarAlert() {
		return driver.switchTo().alert();		
	}
	
	public String alertaObterTexto() {
		return focarAlert().getText();
	}
	
	public void alertaEscrever(String valor) {
		focarAlert().sendKeys(valor);
		alertaObterTextoEAceita();
	}	
	
	public void entrarFrame(String frame) {
		driver.switchTo().frame(frame);	
	}
	
	public void sairFrame() {
		driver.switchTo().defaultContent();
	}
	
	public void trocarJanela(String popup) {
		driver.switchTo().window(popup);
	}
	
	/**** JS ****/
	
	public Object executarJS(String cmd, Object... params) {		
		JavascriptExecutor js = (JavascriptExecutor) driver;
		return js.executeScript(cmd, params);
	}
	
}
