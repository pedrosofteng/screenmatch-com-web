package br.com.estudo.screenmatch.repository;

import br.com.estudo.screenmatch.model.Episodio;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EpisodioRepository extends JpaRepository<Episodio, Long> {
}
