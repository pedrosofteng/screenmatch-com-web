package br.com.estudo.screenmatch.principal;

import br.com.estudo.screenmatch.model.DadosFilme;
import br.com.estudo.screenmatch.repository.EpisodioRepository;
import br.com.estudo.screenmatch.repository.SerieRepository;

public class MenuFilme extends Menu {


    public MenuFilme(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        super(serieRepository, episodioRepository);
    }

    public void exibir() {
        System.out.print("Digite o nome do filme: ");
        endereco = validar.lerUrl();

        urlFinal = url.getENDERECO_OMDB() + endereco + url.getAPI_KEY_OMDB();
        json = consumoApi.obterDados(urlFinal);

        DadosFilme dadosFilme = converter.obterDados(json, DadosFilme.class);
        if (dadosFilme.resposta() == false) {
            System.out.println("\nFilme não encontrado.\n");
        } else {
            System.out.println(dadosFilme);
        }
    }
}
