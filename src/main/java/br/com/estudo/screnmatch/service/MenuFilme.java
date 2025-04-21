package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.model.DadosFilme;

public class MenuFilme extends Menu {

    public void exibir() {
        System.out.print("\nDigite o nome do filme: ");
        endereco = validar.lerUrl();

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
            numero = validar.lerInt();
            if (numero == 1) {
                break;
            } else if (numero == 2) {
                numero = 3;
                break;
            } else {
                System.out.println("Digite um número válido.");
            }
        }
    }
}
