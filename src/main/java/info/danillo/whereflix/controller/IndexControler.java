package info.danillo.whereflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade
 * Filme. Inclui funcionalidades de CRUD para filmes, telefones e streamings.
 */
@Controller
public class IndexControler {

    @Autowired
    private TipoRepository tipoRepository;

    /**
     * Página inicial do sistema.
     *
     * @return Nome da página inicial.
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("tipos", tipoRepository.findAll());
        return "index"; // Isso renderiza o template src/main/resources/templates/index.html
    }

    @GetMapping("/index")
    public String index2(Model model) {
        model.addAttribute("tipos", tipoRepository.findAll());
        return "index";
    }

}
