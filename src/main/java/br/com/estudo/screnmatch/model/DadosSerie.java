package br.com.estudo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
// IGNORA TUDO QUE NÃO FOR OQ COLOQUEI ABAIXO, POR PADRÃO ELE VEM FALSE
// então ele tenta converter tudo que tá no json
public record DadosSerie(@JsonAlias("Title") String tituloSerie,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao,
                         @JsonAlias("Response") boolean resposta) {
    @Override
    public String toString() {
        return "\nTitulo: " + tituloSerie +
                "\nTemporadas: " + totalTemporadas +
                "\nAvaliação: " + avaliacao + "\n";
    }

    @Override
    public Integer totalTemporadas() {
        return totalTemporadas;
    }

    /*@JsonProperty("Title") - le e escreve title, quando converter
    @JsonAlias("Title") String titulo - le title e escreve titulo pra converter*/

    // posso fazer list tambem @JsonAlias("Title", "Titulo")
}
