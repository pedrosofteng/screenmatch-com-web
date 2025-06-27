package br.com.estudo.screenmatch.principal;

import br.com.estudo.screenmatch.model.*;
import br.com.estudo.screenmatch.repository.EpisodioRepository;
import br.com.estudo.screenmatch.repository.SerieRepository;

import java.util.*;

public class MenuSerie extends Menu {
    protected Optional<Serie> serieBuscada;
    protected List<Episodio> episodios = new ArrayList<>();
    protected List<DadosSerie> listaDadosSeries = new ArrayList<>();
    protected List<Serie> listaSerie = new ArrayList<>();
    protected DadosSerie dadosSerie;
    protected Serie serie;
    protected Episodio episodio;
    protected String trechoTitulo;

    public MenuSerie(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        super(serieRepository, episodioRepository);
    }


    public void exibir() {
        numero = -1;
        MetodosSerie metodos = new MetodosSerie(repositorySerie, repositoryEpisodio);

        while (numero != 0) {
            mensagem = """
                    \n[0] Sair
                    [1] Ver todas temporadas e episódios
                    [2] Ver os 5 melhores episódios
                    [3] Consulta de episódio por ano
                    [4] Consulta do episódio por trecho de título
                    [5] Média de avaliação por temporada
                    [6] Dados gerais da série
                    [7] Listar todas séries procuradas
                    [8] Buscar série
                    [9] Busca por Gênero(Postgre)
                    [10] Busca por Ator(Postgre)
                    [11] Buscar por número de temporadas e avaliação(Postgre)
                    [12] Buscar episódios por trecho(Postgre)
                    """;

            System.out.println(mensagem);
            System.out.print("Escolha uma opção: ");
            numero = validar.lerInt();

            switch (numero) {
                case 0:
                    break;
                case 1:
                    metodos.verTemporadasEpisodios();
                    break;
                case 2:
                    metodos.cincoMelhoresNotas();
                    break;
                case 3:
                    metodos.escolhaAno();
                    break;
                case 4:
                    metodos.buscarPorTituloSerie();
                    break;
                case 5:
                    metodos.mediaPorTemporada();
                    break;
                case 6:
                    metodos.estatisticasGerais();
                    break;
                case 7:
                    metodos.mostrarListaSeries();
                    break;
                case 8:
                    metodos.buscarNaWeb();
                    break;
                case 9:
                    metodos.buscaPorGenero();
                    break;
                case 10:
                    metodos.buscaPorAtor();
                    break;
                case 11:
                    metodos.buscarPorTemporadasAvaliacao();
                    break;
                case 12:
                    metodos.buscarEpisodiosTrecho();
                    break;

            }
        }
    }
}
