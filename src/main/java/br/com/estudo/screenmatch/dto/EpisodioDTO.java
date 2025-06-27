package br.com.estudo.screenmatch.dto;

import br.com.estudo.screenmatch.model.Episodio;

import java.time.LocalDate;

public record EpisodioDTO(String titulo,
                          String numeroEpisodio,
                          Integer temporada
) {
    public EpisodioDTO(Episodio episodio) {
        this(
                episodio.getTituloEpisodio(),
                episodio.getNumeroEpisodio(),
                episodio.getTemporadas()
        );
    }
}