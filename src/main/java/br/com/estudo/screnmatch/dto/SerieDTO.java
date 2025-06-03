package br.com.estudo.screnmatch.dto;

import br.com.estudo.screnmatch.model.Categoria;
import br.com.estudo.screnmatch.model.Episodio;

import java.util.List;

public record SerieDTO(String tituloSerie,
                       Long id,
                       Integer totalTemporadas,
                       Double avaliacao,
                       boolean resposta,
                       String atores,
                       String sinopse,
                       String poster,
                       Categoria genero,
                       List<Episodio> episodios) {
    // coloquei tudo que tem em Serie aqui nesse record pra mandar pro front em json


}
