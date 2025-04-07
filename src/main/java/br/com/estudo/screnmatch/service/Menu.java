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
    private final String ENDERECO_OMDB = "https://www.omdbapi.com/?t=";
    private final String API_KEY_OMDB = "&apikey=b8b965e1";
    List<DadosTemporada> temporadas = new ArrayList<>();
    // final = constantes

    public void exibir() {
        String endereco = "";
        String json = "";
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

                        json = consumoApi.obterDados(ENDERECO_OMDB +
                                endereco + API_KEY_OMDB);

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

                        json = consumoApi.obterDados(ENDERECO_OMDB +
                                endereco + API_KEY_OMDB);
                        DadosSerie dadosSerie = converter.obterDados(json, DadosSerie.class);

                        if (dadosSerie.resposta() == false) {
                            System.out.println("\nSérie não encontrada.\n");
                        } else {
                            System.out.println(dadosSerie);

//                            for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
//                                json = consumoApi.obterDados(ENDERECO_OMDB +
//                                        endereco + "&Season=" + i + API_KEY_OMDB);
//                                DadosTemporada dadosTemporada = converter.obterDados(json, DadosTemporada.class);
//                                System.out.println(dadosTemporada);
//                            }
//                        }

                            for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
                                json = consumoApi.obterDados(ENDERECO_OMDB +
                                        endereco + "&Season=" + i + API_KEY_OMDB);
                                DadosTemporada dadosTemporada = converter.obterDados(json, DadosTemporada.class);
                                temporadas.add(dadosTemporada);
                            }
                        }

//                        temporadas.forEach(t -> {
//                                    System.out.println(t.toString());
//
//                                    t.episodios().forEach
//                                            (e -> System.out.println(e.toString()));
//                                });

                        // criar uma lista episódios, e vamos buscar dentro de temporadas.stream
                        /*List<DadosEpisodios> listDadosEpisodios= temporadas.stream()
                                // stream transforma em dados
                                .flatMap(t -> t.episodios().stream())
                                // lista dentro de outra lista usamos flatmap
                                .collect(Collectors.toList());*/
                                // collect armazena os dados dos episódios na lista de episódios
                                // .toList faz a mesma coisa mas torna imutável

                        /*listDadosEpisodios.stream()
                                .filter(e -> !e.avaliacao().equalsIgnoreCase("N/A"))
                                // filtra os episódios que NÃO tenha N/A """"""""""!"""""""""""
                                .sorted(Comparator.comparing(DadosEpisodios::avaliacao).reversed())
                                .limit(5)
                                .forEach(System.out::println);*/

                        List<Episodio> episodios = temporadas.stream()
                                .flatMap(t -> t.episodios().stream()
                                        .map(e -> new Episodio(t.numero(), e)))
                                .collect(Collectors.toList());

                        episodios.stream()
                                .sorted(Comparator.comparing(Episodio::getAvaliacao).reversed())
                                .limit(5)
                                .forEach(System.out::println);

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