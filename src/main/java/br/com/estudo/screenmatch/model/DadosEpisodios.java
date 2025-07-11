package br.com.estudo.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosEpisodios(@JsonAlias("Title") String tituloEpisodio,
                             @JsonAlias("Episode") String numeroEpisodio,
                             @JsonAlias("imdbRating") String avaliacao,
                             @JsonAlias("Released") String anoDeLancamento) {

    @Override
    public String toString() {
        return "\nTitulo: " + tituloEpisodio + "\n" +
                "Episódio: " + numeroEpisodio + "\n" +
                "Avaliação: " + avaliacao;
    }
}
