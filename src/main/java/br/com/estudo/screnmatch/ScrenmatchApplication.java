package br.com.estudo.screnmatch;

import br.com.estudo.screnmatch.model.DadosSerie;
import br.com.estudo.screnmatch.service.ConsumoApi;
import br.com.estudo.screnmatch.service.ConverterDados;
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
		Scanner scanner = new Scanner(System.in);
		String endereco = scanner.nextLine();
		endereco = URLEncoder.encode(endereco, StandardCharsets.UTF_8);

		ConsumoApi consumoApi = new ConsumoApi();
		String json = consumoApi.obterDados("https://www.omdbapi.com/?t=" +
				endereco + "&apikey=b8b965e1");
		System.out.println(json);
		ConverterDados converter = new ConverterDados();
		DadosSerie dadosSerie = converter.obterDados(json, DadosSerie.class);

		/* utilizamos uma classe com conversor do jackson para fazer o trabalho
		de só converter os dados, implementamos uma interface a essa classe
		com termo genéricos <T> T, ou seja ela irá converter para qualquer classe
		que a gente sinalizar <T> = Classe T = nome da variável da classe

		private ObjectMapper mapper = new ObjectMapper();
    	// mapper é o objeto do Jackson que faz a conversão, json - object java

    	@Override
    	public <T> T obterDados(String json, Class<T> tClass) {
        	try {
            	return mapper.readValue(json, tClass);
        	} catch (JsonProcessingException e) {
            	throw new RuntimeException(e);
        	}

        Poderia ter feito também
        ObjectMapper mapper = new ObjectMapper();
        DadosSerie dadosSerie = mapper.readValue(json, DadosSerie.class)

        mas isolamos para o main ficar mais liso
		 */
	}
}
