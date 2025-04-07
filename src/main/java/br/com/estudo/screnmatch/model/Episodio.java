package br.com.estudo.screnmatch.model;

import java.time.LocalDate;

public class Episodio {
    private String tituloEpisodio;
    private String numeroEpisodio;
    private String avaliacao;
    private String temporadas;
    private LocalDate anoDeLancamento;

    public String getAvaliacao() {
        return avaliacao;
    }

    public String getTituloEpisodio() {
        return tituloEpisodio;
    }

    public String getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public String getTemporadas() {
        return temporadas;
    }

    public LocalDate getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public Episodio(String numero, DadosEpisodios dadosEpisodios) {

        if (dadosEpisodios.avaliacao().equals("N/A")) {
            this.avaliacao = "0";
        } else {
            this.avaliacao = dadosEpisodios.avaliacao();
        }

        this.numeroEpisodio = dadosEpisodios.numeroEpisodio();
        this.tituloEpisodio = dadosEpisodios.tituloEpisodio();
        this.temporadas = numero;

        if (dadosEpisodios.anoDeLancamento().equals("N/A")) {
            this.anoDeLancamento = null;
        } else {
            this.anoDeLancamento = LocalDate.parse(dadosEpisodios.anoDeLancamento());
        }
    }

    @Override
    public String toString() {
        return "\nTemporada: " + temporadas + "\n" +
                "Episodio: " + numeroEpisodio + "\n" +
                "Titulo: " + tituloEpisodio + "\n" +
                "Avaliação: " + avaliacao + "\n" +
                "Lançamento: " + anoDeLancamento;

    }
}
