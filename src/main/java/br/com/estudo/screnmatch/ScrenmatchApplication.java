package br.com.estudo.screnmatch;

import br.com.estudo.screnmatch.repository.EpisodioRepository;
import br.com.estudo.screnmatch.repository.SerieRepository;
import br.com.estudo.screnmatch.config.ConfigEnv;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ScrenmatchApplication {

	// atualiza o software automaticamente -> devtools dependência
	// settings -> build -> compiler -> build project automaticamente
	// setting -> advanced settings -> allow auto make

	@Autowired
	private EpisodioRepository repositoryEpisodio;

	@Autowired
	private SerieRepository repositorySerie;

	public static void main(String[] args) {
		ConfigEnv configEnv = new ConfigEnv();
		SpringApplication.run(ScrenmatchApplication.class, args);
	}

}
