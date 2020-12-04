package br.com.framework.pages;

import br.com.framework.config.hooks;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class LoginPage {

	protected WebDriver driver;

	public LoginPage() {
		driver = hooks.getDriver();
		PageFactory.initElements(driver, this);
	}
	

	@FindBy(id = "j_username")
	public WebElement inputUsuario;

	@FindBy(id = "j_password")
	public WebElement inputSenha;
	
	@FindBy(id = "loginbutton")
	public WebElement botaoAcessar;
	
	@FindBy(id = "titlebar-tb_homeButton")
	public WebElement botaoHome;
	
	//### Menu
	
	@FindBy(linkText = "Ordens de Serviço")
	public WebElement menuOrdemServico;
	
	@FindBy(linkText = "Ordem de Serviço SGZ")
	public WebElement opcaoOrdemServicoSGZ;
	
	@FindBy(id = "m397b0593_tabbody_table")
	public WebElement tabelaPesquisa;

	@FindBy(id = "m6a7dfd2f_tfrow_[C:6]_txt-tb")
	public WebElement inputPesquisarStatus;	
	
	
}
