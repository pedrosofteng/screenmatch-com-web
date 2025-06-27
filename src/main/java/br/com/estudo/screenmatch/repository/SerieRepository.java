package br.com.estudo.screenmatch.repository;

import br.com.estudo.screenmatch.model.Categoria;
import br.com.estudo.screenmatch.model.Episodio;
import br.com.estudo.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

// JpaRepository = de quem vou ser repository
// Serie = classe || Long = id
public interface    SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTituloSerieContainingIgnoreCase(String nomeSerie);
    // a partir do nome do metodo a JPA consegue aplicar alguns métodos de busca automaticamente

    List<Serie> findByAtoresContainingIgnoreCaseAndAvaliacaoGreaterThanEqual(String nomeDoAtor, Double avaliacao);
    // findByAtoresContainingIgnoreCase
    // findby = ache
    // Atores = atores || atributo da classe Serie
    // ContainingIgnoreCase = ignore letras maiúsculas ou minúsculas
    // And = e
    // Avaliacao = avaliacao || atributo da classe Serie
    // GreaterThanEqual = maior ou igual a

    List<Serie> findTop5ByOrderByAvaliacaoDesc();
    //    findTop5By: busca os 5 primeiros registros.
//    OrderByAvaliacaoDesc: ordena os registros pela propriedade avaliacao em ordem decrescente (do maior para o menor)
// desc = decrescente, reversed

    List<Serie> findByGenero(Categoria categoria);

    Optional<List<Serie>>
    findByTotalTemporadasLessThanEqualAndAvaliacaoGreaterThanEqual (int totalTemporadas, int notaAvaliacao);

    @Query("select s from Serie s WHERE s.totalTemporadas <= :totalTemporadas AND s.avaliacao >= :notaAvaliacao")
    List<Serie> seriesTemporadaEAvaliacao(int totalTemporadas, int notaAvaliacao);
// CONCEITO DE JPQL
    // WHERE = aonde que ou if
    // SELECT (parametro) FROM (classe/tabela banco de dados) (parametro)
    // JOIN = entrar/juntar tabelas uma dentro da outra
    // ":" = representa que é uma variável
    // %% = containing
    // ILIKE = IgnoreCase
    // utilizamos o mesmo nome usado nas classes aqui, exatamente como está descrito lá
    // ORDER = ordernar crescente
    // DESC = reversed
    // LIMIT = quantidade + (numero)
    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE e.tituloEpisodio ILIKE %:trechoEpisodio%")
    List<Episodio> encontrarEpisodioTitulo(String trechoEpisodio);

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s = :serie ORDER BY e.avaliacao DESC LIMIT 5")
    List<Episodio> topEpisodiosSerie(Serie serie);

    // perguntar pro JPETO oq esse JPQL faz
    // ele ordena as séries baseado no último ano de lançamento dos episódios
    // mas uma série só, não pode repetir a série baseado no episódio
    @Query("SELECT s FROM  Serie s JOIN s.episodios e GROUP BY s ORDER BY MAX(e.anoDeLancamento) DESC LIMIT 5")
    List<Serie> top5Lançamentos();

    @Query("SELECT e FROM Serie s JOIN s.episodios e WHERE s.id = :id AND e.temporadas = :numero")
    List<Episodio> buscarPorTemporadaDaSerie(Long id, Long numero);
}
