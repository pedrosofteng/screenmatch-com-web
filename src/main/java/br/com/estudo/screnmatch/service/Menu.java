package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.model.DadosEpisodios;
import br.com.estudo.screnmatch.model.DadosFilme;
import br.com.estudo.screnmatch.model.DadosSerie;
import br.com.estudo.screnmatch.model.DadosTemporada;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Scanner;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    ConsumoApi consumoApi = new ConsumoApi();
    ConverterDados converter = new ConverterDados();

    public void exibir() {
        String endereco = "";
        String json = "";
        int numero = 0;

        while (numero != 3) {
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

                    json = consumoApi.obterDados("https://www.omdbapi.com/?t=" +
                            endereco + "&apikey=b8b965e1");

                    DadosFilme dadosFilme = converter.obterDados(json, DadosFilme.class);
                    System.out.println(dadosFilme);

                    mensagem = """
                            [1] Consultar mais
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
                case 2:
                    System.out.print("\nDigite o nome da série: ");
                    endereco = scanner.nextLine();
                    endereco = URLEncoder.encode(endereco, StandardCharsets.UTF_8);

                    json = consumoApi.obterDados("https://www.omdbapi.com/?t=" +
                            endereco + "&apikey=b8b965e1");
                    DadosSerie dadosSerie = converter.obterDados(json, DadosSerie.class);
                    System.out.println(dadosSerie);

                    for (int i = 1; i <= dadosSerie.totalTemporadas(); i++) {
                        json = consumoApi.obterDados("https://www.omdbapi.com/?t=" +
                                endereco + "&Season=" + i + "&apikey=b8b965e1");
                        DadosTemporada dadosTemporada = converter.obterDados(json, DadosTemporada.class);
                        System.out.println(dadosTemporada);
                    }

                    mensagem = """
                            [1] Consultar mais
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
            }
        }
    }