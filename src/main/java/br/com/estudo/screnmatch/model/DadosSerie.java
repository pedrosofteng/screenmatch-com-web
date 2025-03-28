package br.com.estudo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
// IGNORA TUDO QUE NÃO FOR OQ COLOQUEI ABAIXO, POR PADRÃO ELE VEM FALSE
// então ele tenta converter tudo que tá no json
public record DadosSerie(@JsonAlias("Title") String titulo,
                         @JsonAlias("totalSeasons") Integer totalTemporadas,
                         @JsonAlias("imdbRating") String avaliacao) {

    /*@JsonProperty("Title") - le e escreve title, quando converter
    @JsonAlias("Title") String titulo - le title e escreve titulo pra converter*/

    // posso fazer list tambem @JsonAlias("Title", "Titulo")
}
