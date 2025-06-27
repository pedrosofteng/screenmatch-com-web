package br.com.estudo.screenmatch.controller;

import br.com.estudo.screenmatch.dto.EpisodioDTO;
import br.com.estudo.screenmatch.dto.SerieDTO;
import br.com.estudo.screenmatch.model.Categoria;
import br.com.estudo.screenmatch.service.SerieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

// para sinalizar que é um controller, vai mandar as informações de série para web
@RestController
@RequestMapping("/series")
// a gente tá dizendo que todas as funcionalidades dessa classe vão ter /series
public class SerieController {

    /* Quando você usa @Autowired, o Spring gerencia a criação e o ciclo de vida da instância de SerieService.
    Isso significa que ele pode aplicar outras funcionalidades, como a configuração de beans,
    gerenciamento de escopo e, se necessário, a injeção de outras dependências que SerieService possa ter.*/


    // SerieService servico = new SerieService();
    // criando manualmente sem as configurações do spring

    @Autowired
    private SerieService servico;

    @GetMapping
    public List<SerieDTO> obterSeries() {
        return servico.obterTodasAsSeries();
    }

    @GetMapping("/top5")
    public List<SerieDTO> obterTop5Series() {
        return servico.obterTop5Series();
    }

    @GetMapping("/lancamentos")
    public List<SerieDTO> obterLancamentos() {
        return servico.obterLancamentos();
    }

    // não sabemos qual ID é, então usamos {id} para mapear qualquer id que vier
    @GetMapping("/{id}")
    public SerieDTO obterPorId(@PathVariable("id") Long id) {
        // pathvariable passa o {id} da url como parâmetro para o Long ID
        return servico.obterPorId(id);
    }

    @GetMapping("/{id}/temporadas/todas")
    public List<EpisodioDTO> obterTodasTemporadas(@PathVariable Long id) {
        return servico.obterTodasTemporadas(id);
    }

    @GetMapping("/{id}/temporadas/{numero}")
    public List<EpisodioDTO> obterEpisodioDaTemporada(@PathVariable Long id,@PathVariable Long numero) {
        return servico.obterEpisodioDaTemporada(id, numero);
    }

    @GetMapping("/categoria/{categoria}")
    public List<SerieDTO> obterCategoria(@PathVariable String categoria) {
        return servico.obterCategoria(categoria);
    }

    // sinalizando não só na main mas no controller que o spring vai gerenciar essa instância
//    @Autowired
//    private SerieRepository serieRepository;

    // localhosto:8080/series -> vai ser mapeado logo abaixo
    // quando tiver um get com /series vai usar o metodo abaixo
//    @GetMapping("/series")
//    public List<SerieDTO> obterSeries() {
//        return serieRepository.findAll().stream()
//                .map(s -> new SerieDTO(s.getTituloSerie(), s.getId(),
//                        s.getTotalTemporadas(), s.getAvaliacao(),
//                        s.isResposta(), s.getAtores(), s.getSinopse(), s.getPoster(),
//                        s.getGenero(), s.getEpisodios()))
//                .collect(Collectors.toList());
//}

        // serieRepository = Serie
        // vamos bucar por cada série e criar uma nova SerieDTO baseado com o que eu tenho no banco de dados
        // não preciso mapear com @JsonAlias no SerieDTO pois os nomes dos atributos de Serie e SerieDTO são os mesmos
        // TEM QUE COLOCAR NA ORDEM DO SERIEDTO
        // se vier título -> new SerieDTO(s.titulo())

}
