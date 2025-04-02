package br.com.estudo.screnmatch.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class ConverterDados implements IConverteDados{
    private ObjectMapper mapper = new ObjectMapper();
    // mapper é o objeto do Jackson que faz a conversão, json - object java

    @Override
    public <T> T obterDados(String json, Class<T> tClass) {
        // DadosSerie dadosSerie = converter.obterDados(json, DadosSerie.class);
        // <T> = Indica classe DadosSerie, String, Integer, int, double
        // T = Aonde a conversão JSON vai armazenar os dados
        // <T> e T são armazenados em DadosSerie, mas o <T> é o tipo da classe
        // que ele vai ler, e T é aonde ele será armazenado.
        // tClass: No metodo, tClass vai armazenar essa referência para a classe DadosSerie.

        // RESUMO <T>, Class<T> QUAL TIPO DE CLASSE
        // T e tClass AONDE VAI ARMAZENAR // PARAMETRO
        // AMBOS SÃO A MESMA COISA QUE BUCETA HEIN
        // <T> e T = DadosSerie
        // Class<T> e tClass = DadosSerie dentro do parênteses
        try {
            return mapper.readValue(json, tClass);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        // mapper.readValue = lê o json e transforma em classe, nessa classe no caso
        // ELE PRECISA DE TRY CATCH!!!!!!!!!!!
    }
}
