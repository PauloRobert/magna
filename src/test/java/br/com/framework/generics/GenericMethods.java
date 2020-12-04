package br.com.framework.generics;

import cucumber.api.Scenario;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class GenericMethods {

	private WebDriver driver;
	private Scenario scenario;

	public GenericMethods(WebDriver webDriver, Scenario scenario) {

		this.scenario = scenario;
		this.driver = webDriver;
	}

	public void escreveRelatorio(boolean status, String msg, boolean printScreen) {
		scenario.write(msg);

		if (printScreen) {

			try {
				scenario.embed(((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES), "image/png");

			} catch (final UnsupportedOperationException naoSuportado) {
				System.err.println(naoSuportado.getMessage());
			} catch (final WebDriverException e) {
				scenario.write("ALERTA. A captura de tela falhou: " + e.getMessage());
			}

		}

		if (status == false) {
			Assert.fail(msg);
		}

	}

	public static String dataHoraParaArquivo() {

		Timestamp ts = new Timestamp(System.currentTimeMillis());
		return new SimpleDateFormat("ddmmyyyyhhmmss").format(ts);

	}

	// public void click(WebElement elemento, int timeOut) {
	public void click(WebElement elemento) {

		try {
			aguardarElemento(elemento, 10);
			elemento.click();
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível efetuar o click no elemento " + elemento, true);
		}

	}

	public void limpaCampo(WebElement elemento) {

		try {
			aguardarElemento(elemento, 10);
			elemento.clear();
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível limpar o campo " + elemento, true);
		}

	}

	public void preencherCampo(WebElement elemento, String valor) {

		try {
			aguardarElemento(elemento, 10);
			elemento.sendKeys(valor);
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível preencher o campo " + elemento, true);
		}

	}
	
	
	public void keysCampo(WebElement elemento, Keys Keys) {

		try {
			aguardarElemento(elemento, 10);
			elemento.sendKeys(Keys);
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível preencher o campo " + elemento, true);
		}

	}

	public void aguardarElemento(WebElement elemento, int timeOut) {

		try {

			WebDriverWait wait = new WebDriverWait(driver, timeOut);

			if (wait.until(ExpectedConditions.visibilityOfAllElements(elemento)) == null) {
				escreveRelatorio(false, "O elemento " + elemento + " não foi carregado!", true);
			}

		} catch (Exception e) {
			escreveRelatorio(false, "O elemento " + elemento + " não foi carregado!", true);
		}

	}

	public void validaMsgAlert(WebElement elemento, String msgEsperada, int timeOut) {

		try {

			aguardarElemento(elemento, timeOut);
			String msgObtida = elemento.getText();

			if (!msgObtida.contains(msgEsperada)) {
				escreveRelatorio(false, "A mensagem do alerta é diferente do esperado!/n" + "Esperada: " + msgEsperada
						+ "/n" + "Obtida: " + msgObtida, true);
			}

		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível validar a mensagem do alerta!", true);
		}

	}

	// Ordem crescente - true
	// Ordem decrescente - false
	public ArrayList<String> ordenaArrayList(ArrayList<String> lista, boolean ordem) {

		if (ordem == true) {
			Collections.sort(lista);
		} else if (ordem == false) {
			Collections.sort(lista, Collections.reverseOrder());
		}

		return lista;
	}

	public String[][] lerTabela(WebElement tabela) {

		int linhas = tabela.findElements(By.tagName("tr")).size() - 1;
		int colunas = tabela.findElements(By.tagName("tr")).get(1).findElements(By.tagName("td")).size();

		String[][] listaTabela = new String[linhas][colunas];

		for (int indiceLinha = 1; indiceLinha <= linhas; indiceLinha++) {

			WebElement linha = tabela.findElements(By.tagName("tr")).get(indiceLinha);

			for (int indiceColuna = 0; indiceColuna <= colunas - 1; indiceColuna++) {
				listaTabela[indiceLinha - 1][indiceColuna] = linha.findElements(By.tagName("td")).get(indiceColuna)
						.getText();
			}
		}

		return listaTabela;

	}

	// public String[] leColunaTabela(WebElement tabela, String nomeColuna){
	//
	// int numeroColuna = obterIndiceColuna(tabela, nomeColuna);
	// int linhas = tabela.findElements(By.tagName("tr")).size() - 1;
	// String[] listaColuna = new String[linhas];
	//
	// for(int indiceLinha = 1; indiceLinha<=linhas; indiceLinha++) {
	//
	// WebElement linha =
	// tabela.findElements(By.tagName("tr")).get(indiceLinha);
	// listaColuna[indiceLinha - 1] =
	// linha.findElements(By.tagName("td")).get(numeroColuna).getText();
	// }
	//
	// return listaColuna;
	//
	// }

	public ArrayList<String> lerColunaTabela(WebElement tabela, String nomeColuna) {

		int numeroColuna = obterIndiceColuna(tabela, 1, nomeColuna);
		int linhas = tabela.findElements(By.tagName("tr")).size() - 1;
		ArrayList<String> listaColuna = new ArrayList<String>();
		// String[] listaColuna = new String[linhas];

		for (int indiceLinha = 1; indiceLinha <= linhas; indiceLinha++) {

			WebElement linha = tabela.findElements(By.tagName("tr")).get(indiceLinha);
			// listaColuna[indiceLinha - 1] =
			// linha.findElements(By.tagName("td")).get(numeroColuna).getText();
			listaColuna.add(linha.findElements(By.tagName("td")).get(numeroColuna).getText());
		}

		return listaColuna;

	}

	public int obterIndiceColuna(WebElement tabela, int linhaCabecalho, String nomeColuna) {

		int indiceColuna = 0;
		boolean localizada = false;
		WebElement linha = tabela.findElements(By.tagName("tr")).get(linhaCabecalho);
		int colunas = linha.findElements(By.tagName("td")).size();

		for (indiceColuna = 0; indiceColuna <= colunas - 1; indiceColuna++) {
			if (linha.findElements(By.tagName("td")).get(indiceColuna).getText().trim().toLowerCase()
					.equals(nomeColuna.toLowerCase())) {
				localizada = true;
				break;
			}
		}

		if (localizada == false) {
			escreveRelatorio(false, "Não foi possível localizar a coluna " + nomeColuna + " na tabela!", true);
		}

		return indiceColuna;

	}

	public int obterIndiceLinha(WebElement tabela, String nomeColuna, String valorLinha) {

		boolean localizada = false;
		// int numColuna = obterIndiceColuna(tabela, 3, nomeColuna);
		int numLinhas = tabela.findElements(By.tagName("tr")).size() - 3;
		// WebElement coluna =
		// tabela.findElements(By.tagName("td")).get(numColuna);
		int row = 0;

		for (int numLinha = 4; numLinha <= numLinhas; numLinha++) {
			WebElement linha = tabela.findElements(By.tagName("tr")).get(numLinha);

			List<WebElement> colunas = linha.findElements(By.tagName("td"));

			for (WebElement coluna : colunas) {
				if (coluna.getText().equals(valorLinha)) {
					localizada = true;
					row = numLinha;
					break;
				}
			}

			if (localizada)
				break;
		}

		if (localizada == false) {
			escreveRelatorio(false, "Não foi possível localizar a linha " + valorLinha + " na tabela!", true);
		}

		return row;

	}

	public void compararListas(ArrayList<String> lista1, ArrayList<String> lista2) {

		if (lista1.size() == lista2.size()) {

			for (int i = 0; i <= lista1.size(); i++) {
				if (lista1.get(i) != lista1.get(i)) {
					escreveRelatorio(false, "A listas contém valores diferentes", true);
				}
			}

		} else {
			escreveRelatorio(false, "As listas não contém o mesmo tamanho!", true);
		}

	}

	public String obterTexto(WebElement elemento) {

		try {

			aguardarElemento(elemento, 10);
			return elemento.getText();

		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível obter texto do elemento " + elemento, true);
			return "";
		}

	}

	public boolean elementoVisivel(WebElement elemento) {

		try {
			aguardarElemento(elemento, 10);
			return elemento.isDisplayed();

		} catch (Exception e) {
			escreveRelatorio(false, "O elemento " + elemento + " não esta visível!", true);
			return false;
		}

	}

	public boolean elementoExiste(WebElement elemento) {

		if (elemento == null) {
			return false;
		}

		return true;

	}

	public void aguardar(int timeOut) {

		timeOut = timeOut * 1000;

		try {
			Thread.sleep(timeOut);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void selecionarRadioPorName(String name, String value) {

		boolean find = false;
		List<WebElement> rdos = driver.findElements(By.name(name));

		for (WebElement rdo : rdos) {

			String valorDt = value.toLowerCase().replace(" ", "");
			String valorAtual = "\"" + rdo.getAttribute("value") + "\"";

			if (valorDt.equals(valorAtual)) {
				rdo.click();
				find = true;
				break;
			}
		}

		if (find == false)
			escreveRelatorio(false, "Não foi possível selecionar " + value, true);

	}

	public void selecionarRadioPorName2(String name, String value) {

		boolean find = false;
		List<WebElement> rdos = driver.findElements(By.name(name));

		for (WebElement rdo : rdos) {

			String valorDt = value;
			String valorAtual = "\"" + rdo.getAttribute("value") + "\"";

			if (valorDt.equals(valorAtual)) {
				rdo.click();
				find = true;
				break;
			}
		}

		if (find == false)
			escreveRelatorio(false, "Não foi possível selecionar " + value, true);

	}

	public void selecionarComboBoxByValue(WebElement elemento, String item) {

		Select comboBox = new Select(elemento);

		try {
			comboBox.selectByValue(item);
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível selecionar o item " + item, true);
		}

	}

	public void selecionarComboBoxByIndex(WebElement elemento, String item) {

		Select comboBox = new Select(elemento);

		try {
			comboBox.selectByIndex(1);
		} catch (Exception e) {
			escreveRelatorio(false, "Não foi possível selecionar o item " + item, true);
		}

	}

	public void LerArquivoTxt(String caminho) throws IOException {

		File LocalArquivo = new File(caminho);
		String conteudo;

		BufferedReader Arquivo_Fisico = new BufferedReader(new FileReader(LocalArquivo));
		if (LocalArquivo.exists() && LocalArquivo.isFile()) {
			System.out.println("O arquivo existe, abaixo estão os dados: \n");
			while ((conteudo = Arquivo_Fisico.readLine()) != null)
				System.out.println(conteudo);
			Arquivo_Fisico.close();

		} else {
			System.out.println("O Arquivo não esta no local indicado! \n");
		}

	}

	public void ConexaoMysql(String IpPorta, String UsuarioBanco, String SenhaBanco)
			throws ClassNotFoundException, SQLException {
		// Connection URL Syntax: "jdbc:mysql://ipaddress:portnumber/db_name"
		String dbUrl = "";

		// Database Username
		String username = "";

		// Database Password
		String password = "";

		// Query to Execute
		String query = "select *  from users;";

		// Load mysql jdbc driver
		Class.forName("com.mysql.jdbc.Driver");

		// Create Connection to DB
		Connection con = DriverManager.getConnection(dbUrl, username, password);

		// Create Statement Object
		Statement stmt = con.createStatement();

		// Execute the SQL Query. Store results in ResultSet
		ResultSet rs = stmt.executeQuery(query);

		// While Loop to iterate through all data and print results
		while (rs.next()) {
			String myName = rs.getString(1);
			String myAge = rs.getString(2);
			String MyPassword = rs.getString(4);
			System.out.println(myName + "  " + myAge + " " + MyPassword);
		}
		// closing DB Connection
		con.close();
	}

	public void LerJson() {

	}

}
