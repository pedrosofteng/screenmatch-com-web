package br.com.estudo.screnmatch.principal;

import br.com.estudo.screnmatch.model.DadosFilme;
import br.com.estudo.screnmatch.repository.SerieRepository;

public class MenuFilme extends Menu {

    public MenuFilme(SerieRepository repository) {
        super(repository);
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
