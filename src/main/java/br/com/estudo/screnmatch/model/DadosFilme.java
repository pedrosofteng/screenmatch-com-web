package br.com.estudo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilme(@JsonAlias("Title") String tituloFilme,
                         @JsonAlias("Year") String anoDeLancamento,
                         @JsonAlias("Runtime") String tempoDeDuracao,
                         @JsonAlias("Response") boolean resposta) {

    @Override
    public String toString() {
        return "\nTitulo: " + tituloFilme +
                "\nAno de lançamento: " + anoDeLancamento +
                "\nTempo de duração: " + tempoDeDuracao + "\n";

    }
}
