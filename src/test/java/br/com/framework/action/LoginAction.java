package br.com.framework.action;

import org.openqa.selenium.Keys;

import br.com.framework.config.hooks;
import br.com.framework.generics.GenericMethods;
import br.com.framework.pages.LoginPage;

//Estender a classe Pages
public class LoginAction extends LoginPage {

	// Instanciar os métodos genéricos para serem utilizados nesta classe
	GenericMethods generic = new GenericMethods(driver, hooks.getScenario());

	public void AcessaSite() {
		driver.navigate()
				.to("https://hsmgimax.prefeitura.sp.gov.br/maximo/webclient/login/login.jsp?appservauth=false");

	}

	public void efetuarlogin() {
		generic.aguardarElemento(inputUsuario, 1);
		generic.limpaCampo(inputUsuario);
		generic.limpaCampo(inputSenha);
		generic.preencherCampo(inputUsuario, "maxadmin");
		generic.preencherCampo(inputSenha, "Password01");
		generic.escreveRelatorio(true, "Dados preenchidos com sucesso.", true);
		generic.aguardar(1);
	}

	public void clicarNoBotao() {
		generic.click(botaoAcessar);
		generic.escreveRelatorio(true, "Cliquei no botão acesse para abrir o portal", true);
		generic.aguardarElemento(botaoHome, 2);

	}

	public void paginaHome() {
		generic.aguardarElemento(botaoHome, 1);
		generic.escreveRelatorio(true, "Pagina Home apareceu", true);
		generic.aguardar(5);
		

		generic.click(menuOrdemServico);
		generic.aguardar(5);
		generic.escreveRelatorio(true, "Clicando no Menu", true);

		generic.click(opcaoOrdemServicoSGZ);
		generic.aguardar(5);
		generic.elementoExiste(tabelaPesquisa);
		generic.aguardar(3);
		generic.escreveRelatorio(true, "A tabela Pesquisa apareceu", true);
		generic.click(inputPesquisarStatus);
		generic.preencherCampo(inputPesquisarStatus, "=APROVADO");
		generic.escreveRelatorio(true, "Preenchendo o campo Status", true);
		generic.aguardar(3);
		generic.keysCampo(inputPesquisarStatus, Keys.ENTER);
		generic.aguardar(6);
		generic.escreveRelatorio(true, "Tabela Preenchida", true);
		generic.aguardar(6);
		

	}

}
