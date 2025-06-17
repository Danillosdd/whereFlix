package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade
 */
@Controller
public class IndexControler {

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private IndexRepository indexRepository;

    @Autowired
    private FilmeRepository filmeRepository;

    /**
     * Página inicial do sistema.
     *
     * @return Nome da página inicial.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tipos", tipoRepository.findAll());
        List<Filme> lancamentos = indexRepository.findTop4ByOrderByAnoDesc(); // todos os tipos
        model.addAttribute("lancamentos", lancamentos);
        List<Filme> bemAvaliados = indexRepository.findTop8ByOrderByAvaliacaoDesc();
        model.addAttribute("bemAvaliados", bemAvaliados);
        List<Filme> aleatorios = indexRepository.buscar4Aleatorios();
        model.addAttribute("aleatorios", aleatorios);
        return "index"; // Isso renderiza o template src/main/resources/templates/index.html
    }

    @GetMapping("/index")
    public String index2(Model model) {
        model.addAttribute("tipos", tipoRepository.findAll());
        return "index";
    }

    @GetMapping("/movie-details/{id}")
    public String detalhesFilme(@PathVariable Integer id, Model model) {
        Filme filme = filmeRepository.findById(id).orElse(null);
        model.addAttribute("filme", filme);
        return "movie-details";
    }

}
