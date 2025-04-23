package br.com.estudo.screnmatch.principal;

import br.com.estudo.screnmatch.model.DadosSerie;
import br.com.estudo.screnmatch.model.DadosTemporada;
import br.com.estudo.screnmatch.model.Episodio;
import br.com.estudo.screnmatch.model.Serie;
import br.com.estudo.screnmatch.repository.EpisodioRepository;
import br.com.estudo.screnmatch.repository.SerieRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MenuSerie extends Menu {
    protected List<Episodio> episodios = new ArrayList<>();
    protected List<DadosSerie> listaDadosSeries = new ArrayList<>();
    protected List<Serie> listaSerie = new ArrayList<>();
    protected DadosSerie dadosSerie;
    protected Serie serie;

    public MenuSerie(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        super(serieRepository, episodioRepository);
    }


    public void exibir() {
        numero = 0;
        buscarNaWeb();
        if (numero == 2) {
            System.out.println("Saindo...");
        } else {
            pecorrerTemporadasEpisodios();
            pecorrerEpisodios();
            salvarBancoDeDados();

            while (numero != 9) {
                mensagem = """
                        \n[1] Ver todas temporadas e episódios
                        [2] Ver os 5 melhores episódios
                        [3] Consulta de episódio por ano
                        [4] Consulta do episódio por trecho de título
                        [5] Média de avaliação por temporada
                        [6] Dados gerais da série
                        [7] Listar todas séries procuradas
                        [8] Buscar outra série
                        [9] Sair
                        """;

                System.out.println(mensagem);
                System.out.print("Escolha uma opção: ");
                numero = validar.lerInt();

                switch (numero) {
                    case 1:
                        verTemporadasEpisodios();
                        break;
                    case 2:
                        cincoMelhoresNotas();
                        break;
                    case 3:
                        escolhaAno();
                        break;
                    case 4:
                        buscarPorTitulo();
                        break;
                    case 5:
                        mediaPorTemporada();
                        break;
                    case 6:
                        estatisticasGerais();
                        break;
                    case 7:
                        mostrarListaSeries();
                        break;
                    case 8:
                        pesquisarSerie();
                        buscarNaWeb();

                        if (dadosSerie.resposta() == false) {
                            System.out.println("\nSérie não encontrada.\n");
                        } else {
                            System.out.println(dadosSerie);
                        }
                        break;
                    case 9:
                        break;
                }
            }
        }
    }

    private void verTemporadasEpisodios() {
        temporadas.stream()
                .forEach(t -> {
                    System.out.println(
                            "Temporada: " + t.numero());

                    t.episodios().stream()
                            .forEach(e -> System.out.println(
                                    "Titulo: " + e.tituloEpisodio() +
                                    " || Número: " + e.numeroEpisodio()
                            ));
                });
    }

    private void cincoMelhoresNotas() {

        episodios.stream()
                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                                            .peek(e -> System.out.println("\nComparando as avaliações ||" + e + "\n"))
                .limit(5)
//                                            .peek(e -> System.out.println("\nLimitando a 5 ||" + e + "\n"))
                .forEach(System.out::println);
    }

    private void escolhaAno() {
        System.out.print("\nA partir de que ano deseja ver os episódios? ");
        var ano = validar.lerInt();

        LocalDate dataBusca = LocalDate.of(ano, 1, 1);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        episodios.stream()
                .filter(e -> e.getAnoDeLancamento() != null &&
                             e.getAnoDeLancamento().isAfter(dataBusca))
                .forEach(e -> System.out.println(
                        "\nTemporada: " + e.getTemporadas() +
                        "\nEpisódio: " + e.getNumeroEpisodio() +
                        "\nTítulo: " + e.getTituloEpisodio() +
                        "\nData de lançamento: " + e.getAnoDeLancamento().format(dtf)
                ));
    }

    private void buscarPorTitulo() {
        System.out.print("Digite o nome do episódio para busca: ");
        String trechoTitulo = validar.lerString();
        Optional<Episodio> episodioBusca = episodios.stream()
                .filter(e -> e.getTituloEpisodio()
                        .toUpperCase().contains(trechoTitulo.toUpperCase()))
                .findFirst();
        if (episodioBusca.isPresent()) {
            System.out.println("Episódio encontrado!");
            System.out.println("\nTemporada: " + episodioBusca.get().getTemporadas() +
                               "\nEpisódio: " + episodioBusca.get().getTituloEpisodio() +
                               "\nNúmero: " + episodioBusca.get().getNumeroEpisodio() +
                               "\nLançamento: " + episodioBusca.get().getAnoDeLancamento() +
                               "\nAvaliação: " + episodioBusca.get().getAvaliacao());
        } else {
            System.out.println("Episódio não encontrado!");
        }
    }

    private void mediaPorTemporada() {
        Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.groupingBy(Episodio::getTemporadas,
                        Collectors.averagingDouble((Episodio::getAvaliacao))));
        avaliacaoPorTemporada.entrySet().stream()
                .forEach(e -> {
                    System.out.print(
                            "Temporada: " + e.getKey());

                    System.out.printf(" || Média: %.2f%n", e.getValue());
                });
    }

    private void estatisticasGerais() {
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.printf("%nMédia: %.2f", est.getAverage());
        System.out.println("\nMínima: " + est.getMin() +
                           "\nMáxima: " + est.getMax() +
                           "\nTotal de avaliações: " + est.getCount() +
                           "\nSoma das avaliações: " + est.getSum());
    }

    private void mostrarListaSeries() {
        mensagem = """
                [1] Mostrar lista do banco de dados
                [2] Mostrar lista local
                
                Qual deseja ver? """;
        System.out.println(mensagem);
        numero = validar.lerInt();

        if (numero == 1) {
            // ele exige um construtor padrão na Serie || public Serie() {}
            listaSerie = repositorySerie.findAll();
            listaSerie.forEach(System.out::println);
        } else {
            listaSerie.stream()
                    .sorted(Comparator.comparing(Serie::getAvaliacao).reversed())
                    .forEach(System.out::println);
        }
    }

    public void pesquisarSerie() {
        System.out.print("Digite o nome da série: ");
        endereco = validar.lerUrl();
    }

    public void buscarNaWeb() {
        while (true) {
            pesquisarSerie();
            urlFinal = url.getENDERECO_OMDB() + endereco + url.getAPI_KEY_OMDB();
            json = consumoApi.obterDados(urlFinal);
            dadosSerie = converter.obterDados(json, DadosSerie.class);

            if (dadosSerie.resposta() == false) {
                System.out.println("\nSérie não encontrada.");
                while (numero != 2) {
                    mensagem = """
                            \n[1] Consultar novamente
                            [2] Sair""";
                    System.out.println(mensagem);
                    numero = validar.lerInt();

                    if (numero == 1 || numero == 2) {
                        break;
                    } else {
                        System.out.println("\nDigite um número válido.");
                        continue;
                    }
                }

                if (numero == 1) {
                    continue;
                } else {
                    break;
                }

            } else {
                while (true) {
                    System.out.println(dadosSerie);
                    mensagem = """
                            \nEssa série que deseja ver? 
                            [1] Sim
                            [2] Não""";

                    System.out.println(mensagem);
                    numero = validar.lerInt();

                    if (numero == 1 || numero == 2) {
                        break;
                    } else {
                        System.out.println("\nDigite um número válido.");
                    }
                }

                if (numero == 1) {
                    break;
                } else {
                    continue;
                }
            }
        }
    }

    public void salvarBancoDeDados() {
        while (true) {
            mensagem = """
                    \n[1] Banco de dados Postgre
                    [2] Lista de séries locais
                    
                    Deseja salvar aonde? """;

            System.out.println(mensagem);
            numero = validar.lerInt();

            if (numero == 1) {
                repositorySerie.save(serie);
                // como a interface é Serie, temos que salvar somente Serie
                break;
            } else if (numero == 2) {
                listaDadosSeries.add(dadosSerie);
                listaSerie = listaDadosSeries.stream()
                        .map(Serie::new)
                        // .map( d -> new Serie(d))
                        .toList();

                break;
            } else {
                System.out.println("Digite uma opção válida.");
            }
        }
    }

    public void pecorrerTemporadasEpisodios() {
        for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
            urlFinal = url.getENDERECO_OMDB() + endereco +
                       url.getSEASON() + i + url.getAPI_KEY_OMDB();
            json = consumoApi.obterDados(urlFinal);
            DadosTemporada dadosTemporada = converter.obterDados
                    (json, DadosTemporada.class);
            dadosTemporada.episodios().stream()
                    .filter(e -> !e.numeroEpisodio()
                            .equalsIgnoreCase("N/A") && !e.avaliacao()
                            .equalsIgnoreCase("N/A") && !e.anoDeLancamento()
                            .equalsIgnoreCase("N/A"))
                    .map(e -> Double.parseDouble(e.avaliacao()))
                    .toList();

            temporadas.add(dadosTemporada);
        }
    }

    public void pecorrerEpisodios() {
        episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numero(), e)))
                .collect(Collectors.toList());

        this.serie = new Serie(dadosSerie);
        this.serie.setEpisodios(episodios);
    }
}
