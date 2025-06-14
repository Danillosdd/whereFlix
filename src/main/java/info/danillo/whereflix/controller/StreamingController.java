package info.danillo.whereflix.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class StreamingController {

    @Autowired
    private StreamingRepository streamingRepository;

    /**
     * Exibe a lista de streamings.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de listagem de streamings.
     */
    @GetMapping("/streamings")
    public String listarStreamings(Model model) {
        model.addAttribute("streamings", streamingRepository.findAllByOrderByNomeAsc());
        model.addAttribute("mensagem", "Todos as streamings cadastradas");
        return "streamings";
    }

    /**
     * Busca streaming pelo nome.
     *
     * @param nome  Nome a ser pesquisado.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de listagem de filmes.
     */
    @GetMapping("/streamings/busca")
    public String getBusca(@RequestParam String nome, Model model) {
        List<Streaming> streamings = streamingRepository.buscarPorNome(nome);
        model.addAttribute("streamings", streamings);
        model.addAttribute("nomePesquisado", nome);
        return "streamings";
    }

    @GetMapping("/streamings/cadastrar")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("streaming", new Streaming());
        return "streaming-cadastrar"; // Nome do arquivo HTML
    }

    @PostMapping("/streamings/cadastrar")
    public String salvarStreaming(@ModelAttribute Streaming streaming, Model model) {
        // Validação: Campo "nome" vazio
        if (streaming.getNome() == null || streaming.getNome().trim().isEmpty()) {
            model.addAttribute("errorMessage", "O nome da streaming é obrigatório.");
            return "streaming-cadastrar";
        }

        // Validação: Nome duplicado
        if (streamingRepository.existsByNome(streaming.getNome())) {
            model.addAttribute("errorMessage", "Já existe uma streaming com este nome.");
            return "streaming-cadastrar";
        }

        // Salvar streaming
        streamingRepository.save(streaming);
        return "redirect:/streamings";
    }

    /**
     * Exibe o formulário para editar uma streaming.
     *
     * @param id    ID da streaming a ser editada.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de edição de streaming.
     */
    @GetMapping("/streamings/atualizar/{id}")
    public String exibirFormularioEdicao(@PathVariable Integer id, Model model) {
        Streaming streaming = streamingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Streaming não encontrada: " + id));
        model.addAttribute("streaming", streaming);
        return "streaming-atualizar";
    }

    /**
     * Processa a atualização de uma streaming.
     *
     * @param id         ID da streaming a ser atualizada.
     * @param streaming Objeto streaming preenchido no formulário.
     * @return Redireciona para a página de lista de streamings.
     */
    @PostMapping("/streamings/atualizar/{id}")
    public String atualizarStreaming(@PathVariable Integer id, @ModelAttribute Streaming streaming, Model model) {
        // Verifica se já existe outra streaming com o mesmo nome
        if (streamingRepository.existsByNome(streaming.getNome()) &&
                !streamingRepository.findById(id).map(Streaming::getNome).orElse("").equals(streaming.getNome())) {
            model.addAttribute("errorMessage", "Já existe uma streaming com este nome.");
            model.addAttribute("streaming", streaming);
            return "streaming-atualizar"; // Retorna à página de edição com a mensagem de erro
        }

        // Atualiza a streaming
        streaming.setId(id);
        streamingRepository.save(streaming);
        return "redirect:/streamings";
    }

    /**
     * Exclui uma streaming pelo ID.
     *
     * @param id ID da streaming a ser excluída.
     * @return Redireciona para a página de lista de streamings.
     */
    @GetMapping("/streamings/excluir/{id}")
    public String excluirStreaming(@PathVariable Integer id, Model model) {
        try {
            streamingRepository.deleteById(id);
            return "redirect:/streamings";
        } catch (Exception e) {
            model.addAttribute("errorMessage",
                    "Não é possível excluir a streaming, pois ela está vinculada a um ou mais filmes.");
            model.addAttribute("streamings", streamingRepository.findAll());
            return "streamings";
        }
    }

}
