package br.com.estudo.screenmatch.service;

public interface IConverteDados {
    // CLASS<T> = indica a classe genérica que vou atribuir
    <T> T obterDados(String json, Class<T> tClass);
    // <T> T = GENÉRICO, representa que eu não sei qual a classe ou o tipo que vou ter
    // ele vai se adaptar a demanda
}
