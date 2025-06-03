package br.com.estudo.screnmatch.service;

import br.com.estudo.screnmatch.dto.SerieDTO;
import br.com.estudo.screnmatch.model.Serie;
import br.com.estudo.screnmatch.repository.SerieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
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
        return series.stream().map(s -> new SerieDTO(s.getTituloSerie(), s.getId(),
                        s.getTotalTemporadas(), s.getAvaliacao(),
                        s.isResposta(), s.getAtores(), s.getSinopse(), s.getPoster(),
                        s.getGenero(), s.getEpisodios()))
                .collect(Collectors.toList());
    }
}
