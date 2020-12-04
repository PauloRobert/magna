package br.com.framework.config;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import org.apache.poi.sl.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.JavascriptException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;

import cucumber.api.Scenario;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;

public class hooks {
	private static WebDriver driver;
	private static Scenario scenario;

	Workbook workbook;
	Sheet sheets;

	XSSFWorkbook lendoArquivo;
	XSSFSheet sheet;
	FileInputStream arquivoOficial;
	String browserSelecionado;

	@Before
	public void ConfiguracaoInicial(Scenario scenario) {
		hooks.scenario = scenario;

		System.out.println("*** Iniciando Teste ***");
		System.out.println("Executando cenario " + scenario.getName());

		// WebDriverManager elimina a necessidade de baixar drivers de forma manual
		try {
			arquivoOficial = new FileInputStream(new File("tools/browser.xlsx"));
			System.out.println("O arquivo esta na pasta. \n");

			try {
				lendoArquivo = new XSSFWorkbook(arquivoOficial);
				sheet = lendoArquivo.getSheetAt(0);

				// Aqui você indica em qual linha da planilha ele vai ler o browser
				browserSelecionado = sheet.getRow(1).getCell(0).getStringCellValue();

				if ("chrome".equals(browserSelecionado)) {

					ChromeOptions options = new ChromeOptions();
					// options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, "true");
					options.setAcceptInsecureCerts(true);

					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(options);

				} else if ("chrome_headless".equals(browserSelecionado)) {

					ChromeOptions options = new ChromeOptions();
					// options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, "true");
					options.setAcceptInsecureCerts(true);
					options.setHeadless(true);
					options.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT, true);

					WebDriverManager.chromedriver().setup();
					driver = new ChromeDriver(options);
				} else if ("firefox".equals(browserSelecionado)) {

					FirefoxOptions options = new FirefoxOptions();
					options.setAcceptInsecureCerts(true);

					WebDriverManager.firefoxdriver().setup();
					driver = new FirefoxDriver(options);

				} else if ("edge".equals(browserSelecionado)) {

					EdgeOptions options = new EdgeOptions();
					options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);

					WebDriverManager.edgedriver().setup();
					driver = new EdgeDriver(options);
					
					
				} else if ("ie".equals(browserSelecionado)) {
					WebDriverManager.iedriver().setup();
					driver = new InternetExplorerDriver();
				}

				arquivoOficial.close();
				driver.manage().window().maximize();
				driver.manage().deleteAllCookies();
				driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
				System.out.println("Lendo a Sheet. \n");

			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				System.out.println(
						"###### \n " + "Não foi possivel ler a planilha: \n" + e.getMessage() + "###### \n \n");

			}

		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("\n#######ERRO################# \n" + "Não foi possivel ler a planilha: \n"
					+ e.getMessage() + "\n##########ERRO############## \n \n");
		}

	}

	@After
	public void FecharJanela() {

		System.out.println("*** Fim dos testes ***");
		driver.quit();

	}

	public static Scenario getScenario() {
		return scenario;
	}

	public static WebDriver getDriver() {
		return driver;
	}

}
