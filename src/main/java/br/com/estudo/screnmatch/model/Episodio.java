package br.com.estudo.screnmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "episodios")
public class Episodio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String tituloEpisodio;
    private String numeroEpisodio;
    private Double avaliacao;
    private Integer temporadas;
    private LocalDate anoDeLancamento;
    @ManyToOne
    private Serie serie;

    public Episodio(Integer numero, DadosEpisodios dadosEpisodios) {

        if (dadosEpisodios.avaliacao().equals("N/A")) {
            this.avaliacao = 0.0;
        } else {
            this.avaliacao = Double.parseDouble(dadosEpisodios.avaliacao());
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

    public Episodio() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Serie getSerie() {
        return serie;
    }

    public Double getAvaliacao() {
        return avaliacao;
    }

    public String getTituloEpisodio() {
        return tituloEpisodio;
    }

    public String getNumeroEpisodio() {
        return numeroEpisodio;
    }

    public Integer getTemporadas() {
        return temporadas;
    }

    public LocalDate getAnoDeLancamento() {
        return anoDeLancamento;
    }

    public void setSerie(Serie serie) {
        this.serie = serie;
    }
}
