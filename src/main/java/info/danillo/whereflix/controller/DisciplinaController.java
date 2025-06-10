package info.danillo.whereflix.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/disciplinas")
public class DisciplinaController {

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    /**
     * Exibe a lista de disciplinas.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de listagem de disciplinas.
     */
    @GetMapping
    public String listarDisciplinas(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAllByOrderByNomeAsc());
        return "disciplinas";
    }

    @GetMapping("/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("disciplina", new Disciplina());
        return "disciplina-cadastrar"; // Nome do arquivo HTML
    }
    
    @PostMapping("/cadastrar")
    public String salvarDisciplina(@ModelAttribute Disciplina disciplina, Model model) {
        // Validação: Campo "nome" vazio
        if (disciplina.getNome() == null || disciplina.getNome().trim().isEmpty()) {
            model.addAttribute("errorMessage", "O nome da disciplina é obrigatório.");
            return "disciplina-cadastrar";
        }

        // Validação: Nome duplicado
        if (disciplinaRepository.existsByNome(disciplina.getNome())) {
            model.addAttribute("errorMessage", "Já existe uma disciplina com este nome.");
            return "disciplina-cadastrar";
        }

        // Salvar disciplina
        disciplinaRepository.save(disciplina);
        return "redirect:/disciplinas";
    }

    /**
     * Exibe o formulário para editar uma disciplina.
     *
     * @param id ID da disciplina a ser editada.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de edição de disciplina.
     */
    @GetMapping("/atualizar/{id}")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model model) {
        Disciplina disciplina = disciplinaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada: " + id));
        model.addAttribute("disciplina", disciplina);
        return "disciplina-atualizar";
    }

    /**
     * Processa a atualização de uma disciplina.
     *
     * @param id ID da disciplina a ser atualizada.
     * @param disciplina Objeto disciplina preenchido no formulário.
     * @return Redireciona para a página de lista de disciplinas.
     */
    @PostMapping("/atualizar/{id}")
    public String atualizarDisciplina(@PathVariable Integer id, @ModelAttribute Disciplina disciplina, Model model) {
        // Verifica se já existe outra disciplina com o mesmo nome
        if (disciplinaRepository.existsByNome(disciplina.getNome()) &&
            !disciplinaRepository.findById(id).map(Disciplina::getNome).orElse("").equals(disciplina.getNome())) {
            model.addAttribute("errorMessage", "Já existe uma disciplina com este nome.");
            model.addAttribute("disciplina", disciplina);
            return "disciplina-atualizar"; // Retorna à página de edição com a mensagem de erro
        }

        // Atualiza a disciplina
        disciplina.setId(id);
        disciplinaRepository.save(disciplina);
        return "redirect:/disciplinas";
    }

    /**
     * Exclui uma disciplina pelo ID.
     *
     * @param id ID da disciplina a ser excluída.
     * @return Redireciona para a página de lista de disciplinas.
     */
    @GetMapping("/excluir/{id}")
    public String excluirDisciplina(@PathVariable Integer id, Model model) {
        try {
            disciplinaRepository.deleteById(id);
            return "redirect:/disciplinas";
        } catch (Exception e) {
            model.addAttribute("errorMessage", "Não é possível excluir a disciplina, pois ela está vinculada a um ou mais filmes.");
            model.addAttribute("disciplinas", disciplinaRepository.findAll());
            return "disciplinas";
        }
    }

}
