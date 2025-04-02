package br.com.estudo.screnmatch;

import br.com.estudo.screnmatch.model.DadosSerie;
import br.com.estudo.screnmatch.service.ConsumoApi;
import br.com.estudo.screnmatch.service.ConverterDados;
import br.com.estudo.screnmatch.service.Menu;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

@SpringBootApplication
public class ScrenmatchApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(ScrenmatchApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Menu menu = new Menu();
		menu.exibir();
	}
}
