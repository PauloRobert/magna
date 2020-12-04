package br.com.framework.runner;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
		features = {"src/test/resources/features"},
		plugin = {
					"pretty", 
					"html:target/cucumber-html-report", 
					"json:target/cucumber-json-report/cucumber.json",
					"junit:target/junit.xml"
				},
		
				
				//a saída do console é muito mais legível se estiver como True, o Default é False 
				//Fonte: https://testingneeds.wordpress.com/2015/09/15/junit-runner-with-cucumberoptions/
				monochrome = true,
				glue = {""}
		)
public class TestRunner {

	
}
