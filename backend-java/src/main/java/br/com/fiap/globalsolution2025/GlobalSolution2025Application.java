package br.com.fiap.globalsolution2025;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "API NOME",description = "API RESTFUL do nosso projeto da Global Solution",version = "v1"))
public class GlobalSolution2025Application {

	public static void main(String[] args) {
		SpringApplication.run(GlobalSolution2025Application.class, args);
	}

}
