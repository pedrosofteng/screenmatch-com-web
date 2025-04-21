package br.com.estudo.screnmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;

public class Serie {
    private String tituloSerie;
    private Integer totalTemporadas;
    private Double avaliacao;
    private boolean resposta;
    private String atores;
    private String sinopse;
    private String poster;
    private Categoria genero;

    public Serie(DadosSerie dadosSerie) {
        this.tituloSerie = dadosSerie.tituloSerie();
        this.totalTemporadas = dadosSerie.totalTemporadas();
        this.resposta = dadosSerie.resposta();
        this.atores = dadosSerie.atores();
        this.sinopse = dadosSerie.sinopse();
        this.poster = dadosSerie.poster();
        this.avaliacao = OptionalDouble.of(Double.valueOf(dadosSerie.avaliacao())).orElse(0);
        // OptionalDouble = eu tento obter um double, se não conseguir
        // .orElse = retorne 0.
        this.genero = Categoria.fromString(dadosSerie.genero().split(",")[0].trim());
        // "Action, Drama, Adventure"

        // generos.split(",")  partes = ["Action", " Drama", " Adventure"]
        // split = separa um String grande, em array de String´s.
        // [0] indexamos esse array e pegamos o primeiro item dele. || partes[0] = "Action"
        // trim = pega tudo que é caractere somente. || " Drama".trim() → "Drama"
    }

    public String getTituloSerie() {
        return tituloSerie;
    }

    public Integer getTotalTemporadas() {
        return totalTemporadas;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public boolean isResposta() {
        return resposta;
    }

    public String getAtores() {
        return atores;
    }

    public String getSinopse() {
        return sinopse;
    }

    public String getPoster() {
        return poster;
    }

    public Categoria getGenero() {
        return genero;
    }

    @Override
    public String toString() {
        return "\nSérie: " + tituloSerie +
                "\nTotal de temporadas: " + totalTemporadas +
                "\nAvaliação: " + avaliacao +
                "\nGênero: " + genero +
                "\nAtores: " + atores +
                "\nSinopse: " + sinopse +
                "\nPoster: " + poster;
    }
}
