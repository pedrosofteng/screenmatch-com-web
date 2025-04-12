package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.model.*;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Menu {
    private Scanner scanner = new Scanner(System.in);
    private ConsumoApi consumoApi = new ConsumoApi();
    private ConverterDados converter = new ConverterDados();
    private Url url = new Url();
    List<DadosTemporada> temporadas = new ArrayList<>();

    public void exibir() {
        String endereco = "";
        String json = "";
        String urlFinal = "";
        int numero = 0;

        while (numero != 3) {
            try {
                String mensagem = """
                        \n[1] Consultar filmes
                        [2] Consultar séries
                        [3] Sair\n""";

                System.out.println(mensagem);
                System.out.print("Escolha uma opção: ");
                numero = scanner.nextInt();
                scanner.nextLine(); // consumir quebra de linha

                switch (numero) {
                    case 1:
                        System.out.print("\nDigite o nome do filme: ");
                        endereco = scanner.nextLine();
                        endereco = URLEncoder.encode(endereco, StandardCharsets.UTF_8);

                        urlFinal = url.getENDERECO_OMDB() + endereco + url.getAPI_KEY_OMDB();
                        json = consumoApi.obterDados(urlFinal);

                        DadosFilme dadosFilme = converter.obterDados(json, DadosFilme.class);
                        if (dadosFilme.resposta() == false) {
                            System.out.println("\nFilme não encontrado.\n");
                        } else {
                            System.out.println(dadosFilme);
                        }

                        while (true) {
                            mensagem = """
                                    [1] Consultar mais
                                    [2] Sair\n""";

                            System.out.println(mensagem);
                            System.out.print("Escolha uma opção: ");
                            numero = scanner.nextInt();
                            if (numero == 1) {
                                break;
                            } else if (numero == 2) {
                                numero = 3;
                                break;
                            } else {
                                System.out.println("Digite um número válido.");
                            }
                        }
                        break;
                    case 2:
                        System.out.print("\nDigite o nome da série: ");
                        endereco = scanner.nextLine();
                        endereco = URLEncoder.encode(endereco, StandardCharsets.UTF_8);

                        urlFinal = url.getENDERECO_OMDB() + endereco + url.getAPI_KEY_OMDB();
                        json = consumoApi.obterDados(urlFinal);
                        DadosSerie dadosSerie = converter.obterDados(json, DadosSerie.class);

                        if (dadosSerie.resposta() == false) {
                            System.out.println("\nSérie não encontrada.\n");
                        } else {
                            System.out.println(dadosSerie);


                            // pecorrer todos os dados de episódios e temporadas
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

                            List<Episodio> episodios = temporadas.stream()
                                    .flatMap(t -> t.episodios().stream()
                                            .map(e -> new Episodio(t.numero(), e)))
                                    .collect(Collectors.toList());

                            mensagem = """
                                    \n[1] Ver todas temporadas e episódios
                                    [2] Ver os 5 melhores episódios
                                    [3] Consulta de episódio por ano
                                    [4] Consulta do episódio por trecho de título
                                    [5] Média de avaliação por temporada
                                    [6] Dados gerais da série
                                    """;

                            System.out.println(mensagem);
                            System.out.print("Escolha uma opção: ");
                            numero = scanner.nextInt();
                            scanner.nextLine();
                            // quebra de linha

                            switch (numero) {
                                case 1:
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
                                case 2:
                                    episodios.stream()
                                            .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
//                                            .peek(e -> System.out.println("\nComparando as avaliações ||" + e + "\n"))
                                            .limit(5)
//                                            .peek(e -> System.out.println("\nLimitando a 5 ||" + e + "\n"))
                                            .forEach(System.out::println);
                                    break;
                                case 3:
                                    System.out.print("\nA partir de que ano deseja ver os episódios? ");
                                    var ano = scanner.nextInt();
                                    scanner.nextLine();

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
                                    break;
                                case 4:
                                    System.out.print("Digite o nome do episódio para busca: ");
                                    String trechoTitulo = scanner.nextLine();
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
                                case 5:
                                    Map<Integer, Double> avaliacaoPorTemporada = episodios.stream()
                                            .filter(e -> e.getAvaliacao() > 0.0)
                                            .collect(Collectors.groupingBy(Episodio::getTemporadas,
                                                    Collectors.averagingDouble((Episodio::getAvaliacao))));
                                    avaliacaoPorTemporada.entrySet().stream()
                                            .forEach(e -> System.out.println(
                                                    "Temporada: " + e.getKey() +
                                                            " || Média: " + e.getValue()
                                            ));
                                    break;
                                case 6:
                                    DoubleSummaryStatistics est = episodios.stream()
                                            .filter(e -> e.getAvaliacao() > 0.0)
                                            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

                                    System.out.println("\nMédia: " + est.getAverage() +
                                            "\nMínima: " + est.getMin() +
                                            "\nMáxima: " + est.getMax() +
                                            "\nTotal de avaliações: " + est.getCount() +
                                            "\nSoma das avaliações: " + est.getSum());
                                    break;
                            }
                        }

                        mensagem = """
                                \n[1] Consultar mais
                                [2] Sair\n""";

                        System.out.println(mensagem);
                        System.out.print("Escolha uma opção: ");
                        numero = scanner.nextInt();
                        if (numero == 1) {
                            break;
                        } else {
                            numero = 3;
                            break;
                        }
                }
            } catch (InputMismatchException e) {
                System.out.println("Inválido, tente novamente.");
                scanner.nextLine();
                continue;
            }
        }
    }
}