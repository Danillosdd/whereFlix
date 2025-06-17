package info.danillo.whereflix.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

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
    public String salvarStreaming(@ModelAttribute Streaming streaming,
                                 @RequestParam("foto") MultipartFile foto,
                                 Model model) {
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

        // Upload da foto
        if (foto != null && !foto.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File pasta = new File(uploadDir);
            if (!pasta.exists()) pasta.mkdirs();

            String nomeArquivo = foto.getOriginalFilename();
            Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

            // Verifica se já existe uma imagem com o mesmo nome
            if (Files.exists(caminhoFoto)) {
                model.addAttribute("errorMessage", "Já existe uma imagem com esse nome. Renomeie o arquivo e tente novamente.");
                return "streaming-cadastrar";
            }

            try {
                foto.transferTo(caminhoFoto.toFile());
                streaming.setFoto(nomeArquivo);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
                return "streaming-cadastrar";
            }
        } else {
            streaming.setFoto(null);
        }

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
    public String atualizarStreaming(@PathVariable Integer id,
                                 @ModelAttribute Streaming streaming,
                                 @RequestParam(value = "foto", required = false) MultipartFile foto,
                                 Model model) {
        Streaming streamingAntiga = streamingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Streaming não encontrada: " + id));

        // Atualiza campos
        streamingAntiga.setNome(streaming.getNome());
        streamingAntiga.setCurso(streaming.getCurso());

        // Atualiza a imagem se foi enviada uma nova
        if (foto != null && !foto.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File pasta = new File(uploadDir);
            if (!pasta.exists()) pasta.mkdirs();

            // Deleta a foto antiga, se existir
            if (streamingAntiga.getFoto() != null) {
                File fotoAntiga = new File(uploadDir + streamingAntiga.getFoto());
                if (fotoAntiga.exists()) fotoAntiga.delete();
            }

            String nomeArquivo = foto.getOriginalFilename();
            Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

            // Verifica se já existe uma imagem com o mesmo nome
            if (Files.exists(caminhoFoto)) {
                model.addAttribute("errorMessage", "Já existe uma imagem com esse nome. Renomeie o arquivo e tente novamente.");
                model.addAttribute("streaming", streamingAntiga);
                return "streaming-atualizar";
            }

            try {
                foto.transferTo(caminhoFoto.toFile());
                streamingAntiga.setFoto(nomeArquivo);
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
                model.addAttribute("streaming", streamingAntiga);
                return "streaming-atualizar";
            }
        }
        // Se não enviou nova imagem, mantém a imagem antiga

        streamingRepository.save(streamingAntiga);
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

    @PostMapping("/streamings/excluir")
    public String excluirStreaming(@RequestParam Integer id) {
        Streaming streaming = streamingRepository.findById(id).orElse(null);

        if (streaming != null && streaming.getFoto() != null) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File arquivo = new File(uploadDir + streaming.getFoto());
            if (arquivo.exists()) {
                arquivo.delete();
            }
        }

        streamingRepository.deleteById(id);
        return "redirect:/streamings";
    }

}
