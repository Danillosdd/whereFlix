package info.danillo.whereflix.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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
    public String salvarStreaming(
            @RequestParam String nome,
            @RequestParam("foto") MultipartFile foto,
            Model model) {

        // Validação: Campo "nome" vazio
        if (nome == null || nome.trim().isEmpty()) {
            model.addAttribute("errorMessage", "O nome da streaming é obrigatório.");
            model.addAttribute("streaming", new Streaming());
            return "streaming-cadastrar";
        }

        // Validação: Nome duplicado
        if (streamingRepository.existsByNome(nome)) {
            model.addAttribute("errorMessage", "Já existe uma streaming com este nome.");
            model.addAttribute("streaming", new Streaming());
            return "streaming-cadastrar";
        }

        // Upload da foto
        String nomeArquivo = null;
        if (foto != null && !foto.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File pasta = new File(uploadDir);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            nomeArquivo = foto.getOriginalFilename();
            Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

            // Verifica se já existe uma imagem com o mesmo nome
            if (Files.exists(caminhoFoto)) {
                model.addAttribute("errorMessage", "Já existe uma imagem com esse nome. Renomeie o arquivo e tente novamente.");
                model.addAttribute("streaming", new Streaming());
                return "streaming-cadastrar";
            }
            try {
                foto.transferTo(caminhoFoto.toFile());
            } catch (Exception e) {
                model.addAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
                model.addAttribute("streaming", new Streaming());
                return "streaming-cadastrar";
            }
        }

        Streaming streaming = new Streaming();
        streaming.setNome(nome);
        streaming.setFoto(nomeArquivo);

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
    public String atualizarStreaming(
        @PathVariable Integer id,
        @RequestParam String nome,
        @RequestParam(value = "foto", required = false) MultipartFile foto,
        Model model
    ) {
        Streaming streaming = streamingRepository.findById(id).orElse(null);
        if (streaming != null) {
            streaming.setNome(nome);

            // Atualiza a imagem se foi enviada uma nova
            if (foto != null && !foto.isEmpty()) {
                String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
                File pasta = new File(uploadDir);
                if (!pasta.exists()) {
                    pasta.mkdirs();
                }
                // Deleta a foto antiga, se existir
                if (streaming.getFoto() != null) {
                    File fotoAntiga = new File(uploadDir + streaming.getFoto());
                    if (fotoAntiga.exists()) {
                        fotoAntiga.delete();
                    }
                }
                // Salva a nova foto
                String nomeArquivo = foto.getOriginalFilename();
                Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

                try {
                    foto.transferTo(caminhoFoto.toFile());
                    streaming.setFoto(nomeArquivo); // Atualiza o nome da imagem no objeto Streaming
                } catch (Exception e) {
                    model.addAttribute("errorMessage", "Erro ao salvar a imagem: " + e.getMessage());
                    model.addAttribute("streaming", streaming);
                    return "streaming-atualizar";
                }
            }
            // Se não enviou nova imagem, mantém a imagem antiga

            streamingRepository.save(streaming);
        }
        return "redirect:/streamings";
    }

    /**
     * Exclui uma streaming pelo ID.
     *
     * @param id ID da streaming a ser excluída.
     * @return Redireciona para a página de lista de streamings.
     */
    @GetMapping("/streamings/excluir/{id}")
    public String excluirStreaming(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        try {
            Streaming streaming = streamingRepository.findById(id).orElse(null);
            if (streaming != null && streaming.getFoto() != null) {
                String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
                File foto = new File(uploadDir + streaming.getFoto());
                if (foto.exists()) {
                    foto.delete();
                }
            }
            streamingRepository.deleteById(id);
            redirectAttributes.addFlashAttribute("mensagemSucesso", "Streaming excluído com sucesso!");
        } catch (DataIntegrityViolationException e) {
            redirectAttributes.addFlashAttribute("mensagemErro", "Não é possível excluir este streaming pois ele está vinculado a um ou mais filmes.");
        }
        return "redirect:/streamings";
    }

    @GetMapping("/api/streamings")
    @ResponseBody
    public List<Streaming> listarStreamings() {
        return streamingRepository.findAllByOrderByNomeAsc();
    }

}
