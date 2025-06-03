//package br.com.estudo.screnmatch;
//
//import br.com.estudo.screnmatch.principal.Menu;
//import br.com.estudo.screnmatch.repository.EpisodioRepository;
//import br.com.estudo.screnmatch.repository.SerieRepository;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//@SpringBootApplication
//public class ScrenmatchApplicationSemWeb implements CommandLineRunner {
//
//	@Autowired
//	private EpisodioRepository repositoryEpisodio;
//
//	@Autowired
//	private SerieRepository repositorySerie;
//	// coloquei pro spring gerenciar, pra conseguir fazer SerieRepository repository = new SerieRepository();
//	// como é interface não conseguimos, mas com spring ele gerencia tudo daqui pra isso acontecer
//
//	public static void main(String[] args) {
//		SpringApplication.run(ScrenmatchApplicationSemWeb.class, args);
//	}
//
//	@Override
//	public void run(String... args) throws Exception {
//		Menu menu = new Menu(repositorySerie, repositoryEpisodio);
//		menu.exibir();
//	}
//}
