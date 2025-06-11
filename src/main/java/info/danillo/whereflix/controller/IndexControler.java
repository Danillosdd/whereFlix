package info.danillo.whereflix.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade
 * Filme. Inclui funcionalidades de CRUD para filmes, telefones e disciplinas.
 */
@Controller
public class IndexControler {

    /**
     * Página inicial do sistema.
     *
     * @return Nome da página inicial.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

}
