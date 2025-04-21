package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.model.DadosTemporada;
import br.com.estudo.screnmatch.model.Url;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private MenuSerie menuSerie = new MenuSerie();
    private MenuFilme menuFilme = new MenuFilme();
    protected ValidarInformacoes validar = new ValidarInformacoes();
    protected ConsumoApi consumoApi = new ConsumoApi();
    protected ConverterDados converter = new ConverterDados();
    protected Url url = new Url();
    List<DadosTemporada> temporadas = new ArrayList<>();
    protected String endereco = "";
    protected String json = "";
    protected String urlFinal = "";
    protected int numero = 0;
    protected String mensagem = "";

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
                    menuFilme.exibir();
                    escolhaMenu();

                    if (numero == 1) {
                        break;
                    } else {
                        numero = 3;
                        break;
                    }
                case 2:
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
                \n[1] Consultar mais
                [2] Sair\n""";

        System.out.println(mensagem);
        System.out.print("Escolha uma opção: ");
        numero = validar.lerInt();
    }
}