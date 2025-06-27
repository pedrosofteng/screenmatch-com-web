package br.com.estudo.screenmatch.dto;

import br.com.estudo.screenmatch.model.Categoria;
import br.com.estudo.screenmatch.model.Serie;

import java.util.List;

public record SerieDTO(
        String titulo,
        Long id,
        Integer totalTemporadas,
        Double avaliacao,
        boolean resposta,
        String atores,
        String sinopse,
        String poster,
        Categoria genero,
        List<EpisodioDTO> episodios
) {
    public SerieDTO(Serie serie) {
        this(
                serie.getTituloSerie(),
                serie.getId(),
                serie.getTotalTemporadas(),
                serie.getAvaliacao(),
                serie.isResposta(),
                serie.getAtores(),
                serie.getSinopse(),
                serie.getPoster(),
                serie.getGenero(),
                serie.getEpisodios().stream().map(EpisodioDTO::new).toList()
        );
    }
}
