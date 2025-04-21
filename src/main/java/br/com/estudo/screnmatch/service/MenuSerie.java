package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.model.DadosSerie;
import br.com.estudo.screnmatch.model.DadosTemporada;
import br.com.estudo.screnmatch.model.Episodio;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class MenuSerie extends Menu {

    public void exibir() {
        System.out.print("Digite o nome da série: ");
        endereco = validar.lerUrl();

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
            numero = validar.lerInt();

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
                    break;
                case 4:
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
                    break;
                case 5:
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
                    break;
                case 6:
                    DoubleSummaryStatistics est = episodios.stream()
                            .filter(e -> e.getAvaliacao() > 0.0)
                            .collect(Collectors.summarizingDouble(Episodio::getAvaliacao));

                    System.out.printf("%nMédia: %.2f", est.getAverage());
                    System.out.println("\nMínima: " + est.getMin() +
                            "\nMáxima: " + est.getMax() +
                            "\nTotal de avaliações: " + est.getCount() +
                            "\nSoma das avaliações: " + est.getSum());
                    break;
            }
        }
    }
}
