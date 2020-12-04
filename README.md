# Testes Máximo


Framework de automação em Java, com as seguintes funcionalidades:

  - Estrutura bem desenhada em Config, page Objects, Steps, Actions e Runner.
  - ScreenShoot dos Testes
  - Relatórios em Html
 

# Estrutura de Pastas

1.  Config
	- Contém a Classe  **Hooks** que abriga a configuração inicial como o Setup do Driver, o Before e o After.
2. Generics
	- Contem a classe **GenericMethods** que abriga os métodos genéricos da automação.
3. Pages
	- Onde é realizado o mapeamento dos objetos de pagina.
4. Runner
	- Classe responsavel por executar os testes propriamente ditos e gerar o report do cucumber.
	- Botão direito em cima da classe e clique em Run
5. Steps
	- Classe que abriga o BDD executado com o comando Cucumber.
6. Classe que abriga a codificação propriamente dita, carrega toda a lógica de automação.

	

### Bibliotecas Utilizadas

Mantenha o POM sempre atualizado: 			
			
- junit	   	
- selenium-java     	
- cucumber-java     	
- cucumber-core     	
- cucumber-junit    	
- commons-io			
- webdrivermanager



### Instalação

- git clone 'https://github.com/PauloRobert/magna' master
- Import o projeto no Eclipse como Maven
- Ele vai começar a baixar as dependência do pom.xml automaticamente após a importação
- Comece a codificar os demais testes e BDDs:)


License
----

MIT
