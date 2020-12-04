package br.com.framework.steps;

import br.com.framework.action.LoginAction;
import cucumber.api.java.pt.Dado;
import cucumber.api.java.pt.Entao;
import cucumber.api.java.pt.Quando;

public class LoginSteps {

	// Instaciando a classe Actions para executar as ações na pagina
	LoginAction Login = new LoginAction();

	@Dado("^que acesso o site$")
	public void que_acesso_o_site() throws Throwable {
		Login.AcessaSite();

	}

	
	@Quando("^preencher os campos de login e senha$")
	public void preencher_os_campos_de_login_e_senha() throws Throwable {
		Login.efetuarlogin();


	}

	@Quando("^Clicar no botão acessar$")
	public void clicar_no_botão_acessar() throws Throwable {
		Login.clicarNoBotao();

	}
	
	@Entao("^verei a pagina Home$")
	public void verei_a_pagina_Home() throws Throwable {
		Login.paginaHome();

	}


}
