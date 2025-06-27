package br.com.estudo.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosFilme(@JsonAlias("Title") String tituloFilme,
                         @JsonAlias("Year") String anoDeLancamento,
                         @JsonAlias("Runtime") String tempoDeDuracao,
                         @JsonAlias("Response") boolean resposta,
                         @JsonAlias("Actors") String atores,
                         @JsonAlias("Plot") String sinopse,
                         @JsonAlias("Poster") String poster) {

    @Override
    public String toString() {
        return "\nTitulo: " + tituloFilme +
                "\nAno de lançamento: " + anoDeLancamento +
                "\nTempo de duração: " + tempoDeDuracao +
                "\nAtores: " + atores +
                "\nSinopse: " + sinopse + "\n";

    }
}
