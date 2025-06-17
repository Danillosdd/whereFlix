package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LancamentoRestController {

    @Autowired
    private IndexRepository indexRepository;

    @GetMapping("/lancamentos")
    public List<Filme> getLancamentos(@RequestParam(required = false) String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            // Retorna os 4 lançamentos mais recentes de qualquer tipo
            return indexRepository.findTop4ByOrderByAnoDesc();
        } else {
            // Retorna os 4 lançamentos mais recentes do tipo selecionado
            return indexRepository.findTop4ByTipoNomeOrderByAnoDesc(tipo);
        }
    }

    @GetMapping("/bem-avaliados")
    public List<Filme> getBemAvaliados(@RequestParam(required = false) String tipo) {
        if (tipo == null || tipo.isEmpty()) {
            return indexRepository.findTop8ByOrderByAvaliacaoDesc();
        } else {
            return indexRepository.findTop8ByTipoNomeOrderByAvaliacaoDesc(tipo);
        }
    }
}