package br.com.estudo.screenmatch.principal;

import br.com.estudo.screenmatch.model.DadosTemporada;
import br.com.estudo.screenmatch.model.Url;
import br.com.estudo.screenmatch.repository.EpisodioRepository;
import br.com.estudo.screenmatch.repository.SerieRepository;
import br.com.estudo.screenmatch.service.ConsumoApi;
import br.com.estudo.screenmatch.service.ConverterDados;
import br.com.estudo.screenmatch.service.ValidarInformacoes;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    protected ValidarInformacoes validar = new ValidarInformacoes();
    protected ConsumoApi consumoApi = new ConsumoApi();
    protected ConverterDados converter = new ConverterDados();
    protected Url url = new Url();
    protected List<DadosTemporada> temporadas = new ArrayList<>();
    protected String endereco = "";
    protected String json = "";
    protected String urlFinal = "";
    protected int numero = 0;
    protected String mensagem = "";
    // tem que declarar na main também
    protected SerieRepository repositorySerie;
    protected EpisodioRepository repositoryEpisodio;

    public Menu(SerieRepository repositorySerie, EpisodioRepository repositoryEpisodio) {
        this.repositorySerie = repositorySerie;
        this.repositoryEpisodio = repositoryEpisodio;
    }

    public void exibir() {

        while (numero != 3) {
            mensagem = """
                    \n[1] Consultar filmes
                    [2] Consultar séries
                    [3] Sair\n""";

            System.out.println(mensagem);
            System.out.print("Escolha uma opção: ");
            numero = validar.lerInt();

            switch (numero) {
                case 1:
                    MenuFilme menuFilme = new MenuFilme(repositorySerie, repositoryEpisodio);
                    menuFilme.exibir();
                    escolhaMenu();

                    if (numero == 1) {
                        break;
                    } else {
                        numero = 3;
                        break;
                    }
                case 2:
                    MenuSerie menuSerie = new MenuSerie(repositorySerie, repositoryEpisodio);
                     menuSerie.exibir();
                    escolhaMenu();

                    if (numero == 1) {
                        break;
                    } else {
                        numero = 3;
                        break;
                    }
            }
        }
    }

    private void escolhaMenu() {
        mensagem = """
                \n[1] Consultar mais filmes/series
                [2] Sair\n""";

        System.out.println(mensagem);
        System.out.print("Escolha uma opção: ");
        numero = validar.lerInt();
    }
}