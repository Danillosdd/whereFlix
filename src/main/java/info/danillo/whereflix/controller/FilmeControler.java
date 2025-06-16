package info.danillo.whereflix.controller;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade
 * Filme. Inclui funcionalidades de CRUD para filmes, telefones e streamings.
 */
@Controller
public class FilmeControler {

    @Autowired
    private FilmeRepository filmeRepository;

    @Autowired
    private StreamingRepository streamingRepository;

    @Autowired
    private TelefoneFilmeRepository telefoneFilmeRepository;

    @Autowired
    private TipoRepository tipoRepository;

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Autowired
    private QualidadeRepository qualidadeRepository;

    /**
     * Exibe a lista de todos os filmes cadastrados.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de listagem de filmes.
     */
    @GetMapping("/filmes")
    public String getFilmes(Model model) {
        List<Filme> filmesBd = filmeRepository.findAllByOrderByTituloAsc();
        model.addAttribute("filmes", filmesBd);
        model.addAttribute("mensagem", "Todos os filmes cadastrados");
        return "filmes";
    }

    /**
     * Busca filmes pelo título.
     *
     * @param titulo  Título a ser pesquisado.
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de listagem de filmes.
     */
    @GetMapping("/filmes/busca")
    public String getBusca(@RequestParam String titulo, Model model) {
        List<Filme> filmes = filmeRepository.buscarPorTitulo(titulo);
        model.addAttribute("filmes", filmes);
        model.addAttribute("tituloPesquisado", titulo);
        return "filmes";
    }

    /**
     * Exibe o formulário para cadastrar um novo filme.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de cadastro de filme.
     */
    @GetMapping("/filmes/cadastrar")
    public String mostrarFormularioCadastro(Model model) {
        model.addAttribute("filme", new Filme());
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAllByOrderByNomeAsc()); // <-- aqui
        model.addAttribute("streamings", streamingRepository.findAllByOrderByNomeAsc());
        model.addAttribute("qualidades", qualidadeRepository.findAll());
        return "filme-cadastrar";
    }

    /**
     * Processa o cadastro de um novo filme.
     *
     * @param titulo     Título do filme.
     * @param tipo       Tipo do filme.
     * @param categoria  Categoria do filme.
     * @param qualidade  Qualidade do filme.
     * @param duracao    Duração do filme.
     * @param avaliacao  Classificação do filme.
     * @param ano        Ano de lançamento do filme.
     * @param streamings IDs das streamings selecionadas.
     * @param model      Objeto para adicionar atributos à view.
     * @return Redireciona para a página de listagem de filmes.
     */
    @PostMapping("/filmes/cadastrar")
    public String cadastrarFilme(
            @RequestParam String titulo,
            @RequestParam Integer tipo,
            @RequestParam Integer categoria,
            @RequestParam Integer qualidade,
            @RequestParam Integer duracao,
            @RequestParam Double avaliacao,
            @RequestParam Integer ano,
            @RequestParam List<Integer> streamings,
            @RequestParam("foto") MultipartFile foto,
            Model model) {

        // Caminho absoluto do diretório de imagens na raiz do projeto
        String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
        File pasta = new File(uploadDir);
        if (!pasta.exists()) {
            pasta.mkdirs();
        }
        String nomeArquivo = foto.getOriginalFilename();
        Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

        // Verifica se já existe uma imagem com o mesmo nome
        if (Files.exists(caminhoFoto)) {
            model.addAttribute("erro", "Já existe uma imagem com esse nome. Renomeie o arquivo e tente novamente.");
            model.addAttribute("tipos", tipoRepository.findAll());
            model.addAttribute("categorias", categoriaRepository.findAllByOrderByNomeAsc());
            model.addAttribute("streamings", streamingRepository.findAllByOrderByNomeAsc());
            model.addAttribute("qualidades", qualidadeRepository.findAll());
            return "filme-cadastrar";
        }

        try {
            // Salva o arquivo no diretório
            foto.transferTo(caminhoFoto.toFile());
        } catch (Exception e) {
            model.addAttribute("erro", "Erro ao salvar a imagem: " + e.getMessage());
            return "filme-cadastrar";
        }

        // Validação: Título repetido
        if (filmeRepository.existsByTituloIgnoreCase(titulo)) {
            model.addAttribute("erro", "Já existe um filme com este título.");
            model.addAttribute("streamings", streamingRepository.findAllByOrderByNomeAsc());
            return "filme-cadastrar";
        }

        // Validações
        if (titulo == null || titulo.isEmpty() || tipo == null) {
            model.addAttribute("erro", "Título e tipo são obrigatórios.");
            return "filme-cadastrar";
        }

        // Criação do filme
        Filme filme = new Filme();
        filme.setTitulo(titulo);

        // CORREÇÃO: buscar o objeto Tipo pelo ID
        Tipo tipoObj = tipoRepository.findById(tipo)
                .orElseThrow(() -> new IllegalArgumentException("Tipo não encontrado: " + tipo));
        filme.setTipo(tipoObj);

        Categoria cat = categoriaRepository.findById(categoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + categoria));
        filme.setCategoria(cat);

        Qualidade qualidadeObj = qualidadeRepository.findById(qualidade)
                .orElseThrow(() -> new IllegalArgumentException("Qualidade não encontrada: " + qualidade));
        filme.setQualidade(qualidadeObj);

        List<Streaming> streamingsSelecionadas = new ArrayList<>();
        for (Integer idStreaming : streamings) {
            Streaming streaming = streamingRepository.findById(idStreaming)
                    .orElseThrow(() -> new IllegalArgumentException("Streaming não encontrada: " + idStreaming));
            streamingsSelecionadas.add(streaming);
        }
        filme.setStreamings(streamingsSelecionadas);
        filme.setDuracao(duracao);
        filme.setAvaliacao(avaliacao);
        filme.setAno(ano);
        filme.setFoto(nomeArquivo);
        filmeRepository.save(filme);

        return "redirect:/filmes";
    }

    /**
     * Exibe o formulário para atualizar um filme.
     *
     * @param id    ID do filme.
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de atualização de filme.
     */
    @GetMapping("/filmes/atualizar/{id}")
    public String getAtualizarFilme(@PathVariable Integer id, Model model) {
        Filme filme = filmeRepository.findById(id)
            .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));
        model.addAttribute("filme", filme);
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAllByOrderByNomeAsc()); // <-- aqui
        model.addAttribute("qualidades", qualidadeRepository.findAll());
        model.addAttribute("todasStreamings", streamingRepository.findAll());
        return "filme-atualizar";
    }

    /**
     * Processa a atualização de um filme.
     *
     * @param id         ID do filme.
     * @param titulo     Título do filme.
     * @param tipo       Tipo do filme.
     * @param categoria  Categoria do filme. // <-- Adicione este parâmetro
     * @param streamings IDs das streamings selecionadas.
     * @param model      Objeto para adicionar atributos à view.
     * @return Redireciona para a página de listagem de filmes.
     */
    @PostMapping("/filmes/atualizar")
    public String atualizarFilme(
            @RequestParam Integer id,
            @RequestParam String titulo,
            @RequestParam Integer tipo,
            @RequestParam Integer categoria,
            @RequestParam Integer qualidade,
            @RequestParam Integer duracao,
            @RequestParam Double avaliacao,
            @RequestParam Integer ano,
            @RequestParam List<Integer> streamings,
            @RequestParam(value = "foto", required = false) MultipartFile foto,
            Model model) {

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));

        filme.setTitulo(titulo);
        filme.setDuracao(duracao);
        filme.setAvaliacao(avaliacao);
        filme.setAno(ano);

        // Associa o tipo
        Tipo tipoObj = tipoRepository.findById(tipo)
                .orElseThrow(() -> new IllegalArgumentException("Tipo não encontrado: " + tipo));
        filme.setTipo(tipoObj);

        // Associa a categoria
        Categoria cat = categoriaRepository.findById(categoria)
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada: " + categoria));
        filme.setCategoria(cat);

        // Associa a qualidade
        Qualidade qualidadeObj = qualidadeRepository.findById(qualidade)
                .orElseThrow(() -> new IllegalArgumentException("Qualidade não encontrada: " + qualidade));
        filme.setQualidade(qualidadeObj);

        // Atualiza a imagem se foi enviada uma nova
        if (foto != null && !foto.isEmpty()) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File pasta = new File(uploadDir);
            if (!pasta.exists()) {
                pasta.mkdirs();
            }
            // Deleta a foto antiga, se existir
            if (filme.getFoto() != null) {
                File fotoAntiga = new File(uploadDir + filme.getFoto());
                if (fotoAntiga.exists()) {
                    fotoAntiga.delete();
                }
            }
            // Salva a nova foto
            String nomeArquivo = foto.getOriginalFilename();
            Path caminhoFoto = Paths.get(uploadDir + nomeArquivo);

            try {
                foto.transferTo(caminhoFoto.toFile());
                filme.setFoto(nomeArquivo); // Atualiza o nome da imagem no objeto Filme
            } catch (Exception e) {
                model.addAttribute("erro", "Erro ao salvar a imagem: " + e.getMessage());
                model.addAttribute("filme", filme);
                model.addAttribute("tipos", tipoRepository.findAll());
                model.addAttribute("categorias", categoriaRepository.findAllByOrderByNomeAsc());
                model.addAttribute("qualidades", qualidadeRepository.findAll());
                model.addAttribute("todasStreamings", streamingRepository.findAll());
                return "filme-atualizar";
            }
        }
        // Se não enviou nova imagem, mantém a imagem antiga

        filmeRepository.save(filme);
        return "redirect:/filmes";
    }

    /**
     * Exibe o formulário para excluir um filme.
     *
     * @param id    ID do filme.
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de exclusão de filme.
     */
    @GetMapping("/filmes/excluir/{id}")
    public String excluirFilme(@PathVariable Integer id, Model model) {
        Filme filme = filmeRepository.findById(id).orElseThrow();
        model.addAttribute("filme", filme);
        model.addAttribute("tipos", tipoRepository.findAll());
        model.addAttribute("categorias", categoriaRepository.findAll());
        model.addAttribute("qualidades", qualidadeRepository.findAll());
        model.addAttribute("streamings", streamingRepository.findAll());
        return "filme-excluir";
    }

    /**
     * Processa a exclusão de um filme.
     *
     * @param id ID do filme.
     * @return Redireciona para a página de listagem de filmes.
     */
    @PostMapping("filmes/excluir")
    public String excluirFilme(@RequestParam Integer id) {
        Filme filme = filmeRepository.findById(id).orElse(null);

        if (filme != null && filme.getFoto() != null) {
            String uploadDir = System.getProperty("user.dir") + File.separator + "upload" + File.separator;
            File arquivo = new File(uploadDir + filme.getFoto());
            if (arquivo.exists()) {
                arquivo.delete();
            }
        }

        filmeRepository.deleteById(id);
        return "redirect:/filmes";
    }

    /**
     * Exibe o formulário para atualizar os telefones de um filme.
     *
     * @param id    ID do filme.
     * @param model Objeto para adicionar atributos à view.
     * @return Título da página de atualização de telefones.
     */
    @GetMapping("/filmes/atualizar/telefone/{id}")
    public String updateTelefone(@PathVariable Integer id, Model model) {
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));
        model.addAttribute("filme", filme);
        return "filme-telefone";
    }

    /**
     * Processa a adição de um novo telefone para o filme.
     *
     * @param id           ID do filme.
     * @param novoTelefone Número do telefone.
     * @param whatsapp     Indica se o telefone é WhatsApp.
     * @return Redireciona para a página de atualização de telefones.
     */
    @PostMapping("/filmes/atualizar/telefone")
    public String postUpdateTelefone(
            @RequestParam int id,
            @RequestParam(required = false) String novoTelefone,
            @RequestParam String whatsapp,
            Model model) {
        // Validação do número de telefone
        if (novoTelefone == null || novoTelefone.trim().isEmpty()) {
            model.addAttribute("erro", "O número de telefone não pode estar vazio.");
            Filme filme = filmeRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));
            model.addAttribute("filme", filme); // Adiciona o objeto filme ao modelo
            return "filme-telefone";
        }

        String telefoneSomenteNumeros = novoTelefone.replaceAll("[^0-9]", "");
        boolean isWhatsapp = Boolean.parseBoolean(whatsapp);

        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));

        TelefoneFilme telefone = new TelefoneFilme();
        telefone.setNumero(telefoneSomenteNumeros);
        telefone.setWhatsapp(isWhatsapp);
        telefone.setFilme(filme);

        telefoneFilmeRepository.save(telefone); // Salva o telefone com ID gerado automaticamente

        return "redirect:/filmes/atualizar/telefone/" + id;
    }

    /**
     * Processa a exclusão de um telefone do filme.
     *
     * @param id         ID do filme.
     * @param idTelefone ID do telefone.
     * @return Redireciona para a página de atualização de telefones.
     */
    @PostMapping("/filmes/excluir/telefone")
    public String excluirTelefone(@RequestParam int id, @RequestParam int idTelefone, Model model) {
        // Busca o filme pelo ID
        Filme filme = filmeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Filme não encontrado: " + id));

        // Busca e remove o telefone
        TelefoneFilme telefone = telefoneFilmeRepository.findById(idTelefone)
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado: " + idTelefone));
        telefoneFilmeRepository.delete(telefone);

        // Atualiza o modelo com o filme e seus telefones
        filme.setTelefones(telefoneFilmeRepository.findByFilmeId(id)); // Atualiza a lista de telefones
        model.addAttribute("filme", filme);

        return "filme-telefone";
    }
}
