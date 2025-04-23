package br.com.estudo.screnmatch.repository;

import br.com.estudo.screnmatch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
}
