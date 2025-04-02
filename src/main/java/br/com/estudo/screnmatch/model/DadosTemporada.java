package br.com.estudo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DadosTemporada(@JsonAlias("Season") String numero,
                             @JsonAlias("Episodes") List<DadosEpisodios> episodios) {
    @Override
    public List<DadosEpisodios> episodios() {
        return episodios;
    }

    @Override
    public String toString() {
        return "\nTemporada: " + numero +
                "\nEpisodios: " + episodios + "\n";
    }
}
