package br.com.estudo.screenmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.OptionalDouble;
// Entity = indicando para JPA que essa classe vai pro banco de dados
// Table = mudando nome da clase no banco de dados
@Entity
@Table(name= "series")
public class Serie {
    // gerando o Id para o banco de dados, como vou gerar ele, de que forma
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    // unique = true { diz que não posso ter dois valores repetidos na tabelas, valores únicos somente }
    @Column(name = "titulo", unique = true)
    private String tituloSerie;
    private Integer totalTemporadas;
    private Double avaliacao;
    private boolean resposta;
    private String atores;
    private String sinopse;
    private String poster;
    // precisamos mostrar pro banco de dados que essa é uma categoria Enum tipo String ou Ordinal
    @Enumerated(EnumType.STRING)
    private Categoria genero;
    // @Transient = no banco de dados ignora esse atributo
    // cascade = salve os episódios pela série
    // fetch = traz os episódios
    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Episodio> episodios = new ArrayList<>();

    public Serie() {}
    // JPA EXIGE UM CONSTRUTOR PADRÃO = repository.findAll()/

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<Episodio> getEpisodios() {
        return episodios;
    }

    public void setEpisodios(List<Episodio> episodios) {
        episodios.forEach(e -> e.setSerie(this));
        // para cada episódios, vamos colocar a série em questão como referência
        this.episodios = episodios;
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
