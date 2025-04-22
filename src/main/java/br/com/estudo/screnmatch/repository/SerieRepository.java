package br.com.estudo.screnmatch.repository;

import br.com.estudo.screnmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

// JpaRepository = de quem vou ser repository
// Serie = classe || Long = id
public interface SerieRepository extends JpaRepository<Serie, Long> {
}
