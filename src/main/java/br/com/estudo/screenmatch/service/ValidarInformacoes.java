package br.com.estudo.screenmatch.service;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.InputMismatchException;
import java.util.Scanner;

public class ValidarInformacoes {
    private Scanner scanner = new Scanner(System.in);

    public String lerUrl() {
        String url = scanner.nextLine();
        url = URLEncoder.encode(url, StandardCharsets.UTF_8);
        return url;
    }

    public String lerString() {
        return scanner.nextLine();
    }

    public int lerInt() {
        int numero;
        while(true) {
            try {
                numero = scanner.nextInt();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número inteiro válido.");
                scanner.nextLine(); // limpa a entrada inválida pra não travar o scanner.
                numero = 0;
            }
        }
        return numero;
    }

    public double lerDouble() {
        double numero;
        while(true) {
            try {
                numero = scanner.nextDouble();
                scanner.nextLine();
                break;
            } catch (InputMismatchException e) {
                System.out.println("Digite um número válido.");
                continue;
            }
        }
        return numero;
    }
}
