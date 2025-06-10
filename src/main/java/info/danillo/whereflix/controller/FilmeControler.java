package info.danillo.whereflix.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Controlador responsável por gerenciar as operações relacionadas à entidade
 * Aluno. Inclui funcionalidades de CRUD para alunos, telefones e disciplinas.
 */
@Controller
public class FilmeControler {

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private DisciplinaRepository disciplinaRepository;

    @Autowired
    private TelefoneAlunoRepository telefoneAlunoRepository;

    /**
     * Página inicial do sistema.
     *
     * @return Nome da página inicial.
     */
    @GetMapping("/")
    public String index() {
        return "index";
    }

    /**
     * Exibe a lista de todos os alunos cadastrados.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de listagem de alunos.
     */
    @GetMapping("/alunos")
    public String getAlunos(Model model) {
        List<Aluno> alunosBd = alunoRepository.findAll();
        model.addAttribute("alunos", alunosBd);
        model.addAttribute("mensagem", "Todos os alunos cadastrados");
        return "alunos";
    }

    /**
     * Busca alunos pelo nome.
     *
     * @param nome Nome a ser pesquisado.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de listagem de alunos.
     */
    @GetMapping("/alunos/busca")
    public String getBusca(@RequestParam String nome, Model model) {
        List<Aluno> alunos = alunoRepository.buscarPorNome(nome);
        model.addAttribute("alunos", alunos);
        model.addAttribute("nomePesquisado", nome);
        return "alunos";
    }

    /**
     * Exibe o formulário para cadastrar um novo aluno.
     *
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de cadastro de aluno.
     */
    @GetMapping("/alunos/cadastrar")
    public String getCreate(Model model) {
        model.addAttribute("disciplinas", disciplinaRepository.findAllByOrderByNomeAsc());
        return "aluno-cadastrar";
    }

    /**
     * Processa o cadastro de um novo aluno.
     *
     * @param matricula Número de matrícula do aluno.
     * @param nome Nome do aluno.
     * @param email Email do aluno.
     * @param disciplinas IDs das disciplinas selecionadas.
     * @param model Objeto para adicionar atributos à view.
     * @return Redireciona para a página de listagem de alunos.
     */
    @PostMapping("/alunos/cadastrar")
    public String postCreate(
            @RequestParam Integer matricula,
            @RequestParam String nome,
            @RequestParam String email,
            @RequestParam List<Integer> disciplinas,
            Model model) {
        // Validações
        if (nome == null || nome.isEmpty() || email == null || email.isEmpty()) {
            model.addAttribute("erro", "Nome e email são obrigatórios.");
            return "aluno-cadastrar";
        }

        if (matricula == null || matricula <= 0) {
            model.addAttribute("erro", "Matrícula deve ser um número positivo.");
            return "aluno-cadastrar";
        }

        if (alunoRepository.existsByMatricula(matricula)) {
            model.addAttribute("erro", "Matrícula já cadastrada.");
            return "aluno-cadastrar";
        }

        // Criação do aluno
        Aluno aluno = new Aluno(matricula, nome, email);
        List<Disciplina> disciplinasSelecionadas = new ArrayList<>();
        for (Integer idDisciplina : disciplinas) {
            Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada: " + idDisciplina));
            disciplinasSelecionadas.add(disciplina);
        }
        aluno.setDisciplinas(disciplinasSelecionadas);
        alunoRepository.save(aluno);

        return "redirect:/alunos";
    }

    /**
     * Exibe o formulário para atualizar um aluno.
     *
     * @param id ID do aluno.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de atualização de aluno.
     */
    @GetMapping("/alunos/atualizar/{id}")
    public String getUpdate(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
        model.addAttribute("aluno", aluno);

        // Buscar disciplinas ordenadas por nome
        model.addAttribute("todasDisciplinas", disciplinaRepository.findAllByOrderByNomeAsc());
        return "aluno-atualizar";
    }

    /**
     * Processa a atualização de um aluno.
     *
     * @param id ID do aluno.
     * @param nome Nome do aluno.
     * @param matricula Número de matrícula do aluno.
     * @param email Email do aluno.
     * @param disciplinas IDs das disciplinas selecionadas.
     * @param model Objeto para adicionar atributos à view.
     * @return Redireciona para a página de listagem de alunos.
     */
    @PostMapping("/alunos/atualizar")
    public String updateAluno(
            @RequestParam int id,
            @RequestParam String nome,
            @RequestParam Integer matricula,
            @RequestParam String email,
            @RequestParam List<Integer> disciplinas,
            Model model) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));

        // Atualiza os dados do aluno
        aluno.setNome(nome);
        aluno.setMatricula(matricula);
        aluno.setEmail(email);

        List<Disciplina> disciplinasSelecionadas = new ArrayList<>();
        for (Integer idDisciplina : disciplinas) {
            Disciplina disciplina = disciplinaRepository.findById(idDisciplina)
                    .orElseThrow(() -> new IllegalArgumentException("Disciplina não encontrada: " + idDisciplina));
            disciplinasSelecionadas.add(disciplina);
        }
        aluno.setDisciplinas(disciplinasSelecionadas);
        alunoRepository.save(aluno);

        return "redirect:/alunos";
    }

    /**
     * Exibe o formulário para excluir um aluno.
     *
     * @param id ID do aluno.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de exclusão de aluno.
     */
    @GetMapping("/alunos/excluir/{id}")
    public String exibirTelaExclusao(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
        model.addAttribute("aluno", aluno);
        return "aluno-excluir";
    }

    /**
     * Processa a exclusão de um aluno.
     *
     * @param id ID do aluno.
     * @return Redireciona para a página de listagem de alunos.
     */
    @PostMapping("alunos/excluir")
    public String excluirAluno(@RequestParam Integer id) {
        alunoRepository.deleteById(id);
        return "redirect:/alunos";
    }

    /**
     * Exibe o formulário para atualizar os telefones de um aluno.
     *
     * @param id ID do aluno.
     * @param model Objeto para adicionar atributos à view.
     * @return Nome da página de atualização de telefones.
     */
    @GetMapping("/alunos/atualizar/telefone/{id}")
    public String updateTelefone(@PathVariable Integer id, Model model) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
        model.addAttribute("aluno", aluno);
        return "aluno-telefone";
    }

    /**
     * Processa a adição de um novo telefone para o aluno.
     *
     * @param id ID do aluno.
     * @param novoTelefone Número do telefone.
     * @param whatsapp Indica se o telefone é WhatsApp.
     * @return Redireciona para a página de atualização de telefones.
     */
    @PostMapping("/alunos/atualizar/telefone")
    public String postUpdateTelefone(
            @RequestParam int id,
            @RequestParam(required = false) String novoTelefone,
            @RequestParam String whatsapp,
            Model model) {
        // Validação do número de telefone
        if (novoTelefone == null || novoTelefone.trim().isEmpty()) {
            model.addAttribute("erro", "O número de telefone não pode estar vazio.");
            Aluno aluno = alunoRepository.findById(id)
                    .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));
            model.addAttribute("aluno", aluno); // Adiciona o objeto aluno ao modelo
            return "aluno-telefone";
        }

        String telefoneSomenteNumeros = novoTelefone.replaceAll("[^0-9]", "");
        boolean isWhatsapp = Boolean.parseBoolean(whatsapp);

        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));

        TelefoneAluno telefone = new TelefoneAluno();
        telefone.setNumero(telefoneSomenteNumeros);
        telefone.setWhatsapp(isWhatsapp);
        telefone.setAluno(aluno);

        telefoneAlunoRepository.save(telefone); // Salva o telefone com ID gerado automaticamente

        return "redirect:/alunos/atualizar/telefone/" + id;
    }

    /**
     * Processa a exclusão de um telefone do aluno.
     *
     * @param id ID do aluno.
     * @param idTelefone ID do telefone.
     * @return Redireciona para a página de atualização de telefones.
     */
    @PostMapping("/alunos/excluir/telefone")
    public String excluirTelefone(@RequestParam int id, @RequestParam int idTelefone, Model model) {
        // Busca o aluno pelo ID
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Aluno não encontrado: " + id));

        // Busca e remove o telefone
        TelefoneAluno telefone = telefoneAlunoRepository.findById(idTelefone)
                .orElseThrow(() -> new IllegalArgumentException("Telefone não encontrado: " + idTelefone));
        telefoneAlunoRepository.delete(telefone);

        // Atualiza o modelo com o aluno e seus telefones
        aluno.setTelefones(telefoneAlunoRepository.findByAlunoId(id)); // Atualiza a lista de telefones
        model.addAttribute("aluno", aluno);

        return "aluno-telefone";
    }
}
