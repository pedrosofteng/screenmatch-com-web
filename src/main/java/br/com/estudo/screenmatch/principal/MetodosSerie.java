package br.com.estudo.screenmatch.principal;

import br.com.estudo.screenmatch.model.*;
import br.com.estudo.screenmatch.repository.EpisodioRepository;
import br.com.estudo.screenmatch.repository.SerieRepository;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MetodosSerie extends MenuSerie {

    public MetodosSerie(SerieRepository serieRepository, EpisodioRepository episodioRepository) {
        super(serieRepository, episodioRepository);
    }

    protected void verTemporadasEpisodios() {
        postgreOuListaLocal();
        switch (numero) {
            case 1:
                buscarSerieBancoDeDados();
                if (serieBuscada.isPresent()) {
                    serieBuscada.stream()
                            .forEach(s -> s.getEpisodios().stream().forEach(System.out::println));
                } else {
                    System.out.println("Série não encontrada.");
                }
                break;
            case 2:
                buscarSerieListaLocal();
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
                break;
        }
    }

    protected void cincoMelhoresNotas() {
        postgreOuListaLocal();

        switch (numero) {
            case 1:
                List<Episodio> repositoryTop = repositorySerie.topEpisodiosSerie(buscarSerieBancoDeDados());
                repositoryTop.forEach(System.out::println);

//                List<Serie> serieTop = repositorySerie.findTop5ByOrderByAvaliacaoDesc();
//                serieTop.forEach(s -> System.out.println(
//                        "Titulo: " + s.getTituloSerie() + " || Avaliação: " + s.getAvaliacao()
//                ));
                break;
            case 2:
                buscarSerieListaLocal();
                episodios = serieBuscada.stream()
                        .flatMap(serie -> serie.getEpisodios().stream()) // só abre o stream dos episódios
                        .collect(Collectors.toList());

                episodios.stream()
                        .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                                            .peek(e -> System.out.println("\nComparando as avaliações ||" + e + "\n"))
                        .limit(5)
//                                            .peek(e -> System.out.println("\nLimitando a 5 ||" + e + "\n"))
                        .forEach(System.out::println);
                break;
        }
    }

    protected void escolhaAno() {
        buscarSerieListaLocal();
        if (serieBuscada.isPresent()) {
            System.out.print("\nA partir de que ano deseja ver os episódios? ");
            var ano = validar.lerInt();

            LocalDate dataBusca = LocalDate.of(ano, 1, 1);
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            List<Episodio> episodioData = episodios.stream()
                    .filter(e -> e.getAnoDeLancamento() != null &&
                                 e.getAnoDeLancamento().isAfter(dataBusca))
                    .collect(Collectors.toList());

            episodioData.stream()
                    .forEach(e -> System.out.println(
                            "\nTemporada: " + e.getTemporadas() +
                            "\nEpisódio: " + e.getNumeroEpisodio() +
                            "\nTítulo: " + e.getTituloEpisodio() +
                            "\nData de lançamento: " + e.getAnoDeLancamento().format(dtf)
                    ));

            if (episodioData.isEmpty()) {
                System.out.println("Não existe nenhum episódio lançando depois desse ano, tente anos anteriores.");
            }
        }
    }

    protected void buscarPorTituloSerie() {
        postgreOuListaLocal();
        switch (numero) {
            case 1:
                System.out.println("\nDigite o trecho do titulo: ");
                trechoTitulo = validar.lerString();
                Optional<Serie> serieBuscada = repositorySerie.findByTituloSerieContainingIgnoreCase(trechoTitulo);

                if (serieBuscada.isPresent()) {
                    System.out.println("\nDados da série: " + serieBuscada.get());
                } else {
                    System.out.println("Serie não encontrada.");
                }
                break;
            case 2:
                System.out.print("Digite o nome do episódio para busca: ");
                trechoTitulo = validar.lerString();
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
                break;
        }
    }

    protected void mediaPorTemporada() {
        buscarSerieListaLocal();
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

    protected void estatisticasGerais() {
        buscarSerieListaLocal();
        DoubleSummaryStatistics est = episodios.stream()
                .filter(e -> e.getAvaliacao() > 0.0)
                .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

        System.out.printf("%nMédia: %.2f", est.getAverage());
        System.out.println("\nMínima: " + est.getMin() +
                           "\nMáxima: " + est.getMax() +
                           "\nTotal de avaliações: " + est.getCount() +
                           "\nSoma das avaliações: " + est.getSum());
    }

    protected void mostrarListaSeries() {
        postgreOuListaLocal();

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

    protected void buscaPorAtor() {
        System.out.println("\nDigite o nome do ator: ");
        var nomeDoAtor = validar.lerString();
        System.out.println("Avaliações a partir de que valor? ");
        var nota = validar.lerDouble();
        List<Serie> serieEncontrada =
                repositorySerie.findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(nomeDoAtor, nota);
        System.out.println("\nSeries que o ator atuou: ");
        serieEncontrada.forEach(e -> System.out.println(
                "Titulo: " + e.getTituloSerie() + " || Avaliação: " + e.getAvaliacao()
        ));
    }

    protected void buscaPorGenero() {
        System.out.println("Digite o gênero que deseja buscar: ");
        var genero = validar.lerString();
        Categoria categoria = Categoria.fromPortugues(genero);
        List<Serie> serieCategoria = repositorySerie.findByGenero(categoria);

        serieCategoria.forEach(s -> System.out.println(
                "Titulo: " + s.getTituloSerie() + " || Gênero: " + s.getGenero()
        ));
    }

    protected void buscarPorTemporadasAvaliacao() {
        System.out.println("Até quantas temporadas a série pode ter? ");
        var numeroTemporadas = validar.lerInt();
        System.out.println("Até maior ou igual qual nota de avaliação? ");
        var notaAvaliacao = validar.lerInt();

        List<Serie> listaSerieTemporadaAvaliacao = repositorySerie.seriesTemporadaEAvaliacao(numeroTemporadas, notaAvaliacao);
        listaSerieTemporadaAvaliacao.forEach(System.out::println);


//        Optional<List<Serie>> seriesComTemporadasAvaliacao = repositorySerie
//                .findByTotalTemporadasLessThanEqualAndAvalicaoGreatherThanEqual(numeroTemporadas, notaAvaliacao);
//
//        if (seriesComTemporadasAvaliacao.isEmpty()) {
//            System.out.println("Série não encontrada.");
//        } else {
//            seriesComTemporadasAvaliacao.ifPresent(System.out::println);
//        }

//        List<Serie> outraForma = repositorySerie.findAll();
//        List<Serie> atualizado = outraForma.stream()
//                .filter(s -> {
//                    boolean b = s.getTotalTemporadas() <= numeroTemporadas;
//                    boolean a = s.getTotalTemporadas() >= notaAvaliacao;
//                    return b && a;
//                })
//                .collect(Collectors.toList());
//
//        if (!atualizado.isEmpty()) {
//            atualizado.forEach(System.out::println);
//        } else {
//            System.out.println("Nenhuma Série encontrada.");
//        }
    }

    protected void buscarEpisodiosTrecho() {
        buscarSerieBancoDeDados();
        if (serieBuscada.isPresent()) {
            System.out.println("Qual o nome do trecho de episódio que deseja ver? ");
            var trechoEpisodio = validar.lerString();
            List<Episodio> episodiosEncontrados = repositorySerie.encontrarEpisodioTitulo(trechoEpisodio);
            episodiosEncontrados.forEach(System.out::println);
        }
    }

    protected void pesquisarSerie() {
        System.out.print("Digite o nome da série: ");
        endereco = validar.lerUrl();
    }

    protected void buscarNaWeb() {
        while (true) {
            numero = -1;
            pesquisarSerie();
            urlFinal = url.getENDERECO_OMDB() + endereco + url.getAPI_KEY_OMDB();
            json = consumoApi.obterDados(urlFinal);
            dadosSerie = converter.obterDados(json, DadosSerie.class);

            // resposta == false
            if (!dadosSerie.resposta() || dadosSerie == null) {
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

                    if (numero == 1) {
                        pecorrerTemporadasEpisodios();
                        pecorrerEpisodios();
                        salvarBancoDeDados();
                        numero = 1;
                        break;
                    } else if (numero == 2) {
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

    protected void salvarBancoDeDados() {
        while (true) {
            postgreOuListaLocal();

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

    protected void pecorrerTemporadasEpisodios() {
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

    protected void pecorrerEpisodios() {
        episodios = temporadas.stream()
                .flatMap(t -> t.episodios().stream()
                        .map(e -> new Episodio(t.numero(), e)))
                .collect(Collectors.toList());

        this.serie = new Serie(dadosSerie);
        this.serie.setEpisodios(episodios);
    }

    protected Serie buscarSerieBancoDeDados() {
        lerTrechoTitulo();
        serieBuscada = repositorySerie.findByTituloSerieContainingIgnoreCase(trechoTitulo);
        numero = -1;

        if (serieBuscada.isPresent()) {
            return serieBuscada.get();
        } else {
            System.out.println("Série não encontrada.");
            return null;
        }
    }

    protected Serie buscarSerieListaLocal() {
        lerTrechoTitulo();
        serieBuscada = listaSerie.stream()
                .filter(s -> s.getTituloSerie().equalsIgnoreCase(trechoTitulo))
                .findFirst();
        numero = -1;

        if (serieBuscada.isPresent()) {
            return serieBuscada.get();
        } else {
            System.out.println("Série não encontrada.");
            return null;
        }
    }

    protected String lerTrechoTitulo() {
        System.out.print("\nDigite um trecho do título da série que deseja buscar: ");
        trechoTitulo = validar.lerString();
        return trechoTitulo;
    }

    protected int postgreOuListaLocal() {
        System.out.print("\n[1] Buscar no postgre \n[2] Buscar na lista local \n\nEscolha uma opção: ");
        numero = validar.lerInt();
        return numero;
    }
}
