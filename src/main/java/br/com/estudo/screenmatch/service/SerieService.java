package br.com.estudo.screenmatch.service;

import br.com.estudo.screenmatch.dto.EpisodioDTO;
import br.com.estudo.screenmatch.dto.SerieDTO;
import br.com.estudo.screenmatch.model.Categoria;
import br.com.estudo.screenmatch.model.Episodio;
import br.com.estudo.screenmatch.model.Serie;
import br.com.estudo.screenmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// sinalizar pro spring que é uma classe de serviço, ele vai gerenciar
// acesso a bancos, lidar com transações -> service
@Service
public class SerieService {

    @Autowired
    private SerieRepository serieRepository;

    public List<SerieDTO> obterTodasAsSeries() {
        return converteDados(serieRepository.findAll());
    }

    // troquei findall para findTop5

    public List<SerieDTO> obterTop5Series() {
        return converteDados(serieRepository.findTop5ByOrderByAvaliacaoDesc());
    }

    private List<SerieDTO> converteDados(List<Serie> series) {
        return series.stream()
                .map(s -> new SerieDTO(
                        s.getTituloSerie(),
                        s.getId(),
                        s.getTotalTemporadas(),
                        s.getAvaliacao(),
                        s.isResposta(),
                        s.getAtores(),
                        s.getSinopse(),
                        s.getPoster(),
                        s.getGenero(),
                        s.getEpisodios().stream()
                                .map(EpisodioDTO::new)
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    public List<SerieDTO> obterLancamentos() {
        return converteDados(serieRepository.top5Lançamentos());
    }

    public SerieDTO obterPorId(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return new SerieDTO(s.getTituloSerie(),
                    s.getId(),
                    s.getTotalTemporadas(),
                    s.getAvaliacao(),
                    s.isResposta(),
                    s.getAtores(),
                    s.getSinopse(),
                    s.getPoster(),
                    s.getGenero(),
                    s.getEpisodios().stream()
                            .map(EpisodioDTO::new)
                            .collect(Collectors.toList()));
        }
        return null;
        // esse findById precisa ser tratado, pois pode vir nulo
    }

    public List<EpisodioDTO> obterTodasTemporadas(Long id) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            return s.getEpisodios().stream()
                    .map(e -> new EpisodioDTO(e.getTituloEpisodio(),
                            e.getNumeroEpisodio(),
                            e.getTemporadas()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<EpisodioDTO> obterEpisodioDaTemporada(Long id, Long numero) {
        Optional<Serie> serie = serieRepository.findById(id);

        if (serie.isPresent()) {
            Serie s = serie.get();
            List<Episodio> episodio = serieRepository.buscarPorTemporadaDaSerie(id, numero);
            return episodio.stream()
                    .map(e -> new EpisodioDTO(e.getTituloEpisodio(), e.getNumeroEpisodio(), e.getTemporadas()))
                    .collect(Collectors.toList());
        }
        return null;
    }

    public List<SerieDTO> obterCategoria(String categoria) {
        Categoria categoriaNova = Categoria.fromPortugues(categoria);
        return converteDados(serieRepository.findByGenero(categoriaNova));
    }
}
