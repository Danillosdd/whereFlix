package info.danillo.whereflix.controller;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * Classe que representa a entidade Aluno no sistema.
 * Contém informações básicas do aluno, como matrícula, nome e email,
 * além de relacionamentos com telefones e disciplinas.
 */
@Entity
public class Filme {

    // Identificador único do aluno, gerado automaticamente pelo banco de dados.
    @Id
    @GeneratedValue
    private Integer id;

    // Número de matrícula do aluno.
    private Integer matricula;

    // Nome completo do aluno.
    private String nome;

    // Endereço de email do aluno.
    private String email;

    // Relacionamento One-to-Many com a entidade TelefoneAluno.
    // Um aluno pode ter vários telefones.
    @OneToMany(mappedBy = "aluno", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TelefoneAluno> telefones;

    // Relacionamento Many-to-Many com a entidade Disciplina.
    // Um aluno pode estar matriculado em várias disciplinas.
    @ManyToMany
    private List<Disciplina> disciplinas;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Filme() {
    }

    /**
     * Construtor para inicializar um aluno com matrícula, nome e email.
     *
     * @param matricula Número de matrícula do aluno.
     * @param nome Nome completo do aluno.
     * @param email Endereço de email do aluno.
     */
    public Filme(Integer matricula, String nome, String email) {
        this.matricula = matricula;
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único do aluno.
     *
     * @return ID do aluno.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único do aluno.
     *
     * @param id ID do aluno.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o número de matrícula do aluno.
     *
     * @return Número de matrícula.
     */
    public Integer getMatricula() {
        return matricula;
    }

    /**
     * Define o número de matrícula do aluno.
     *
     * @param matricula Número de matrícula.
     */
    public void setMatricula(Integer matricula) {
        this.matricula = matricula;
    }

    /**
     * Obtém o nome completo do aluno.
     *
     * @return Nome do aluno.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome completo do aluno.
     *
     * @param nome Nome do aluno.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o endereço de email do aluno.
     *
     * @return Email do aluno.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o endereço de email do aluno.
     *
     * @param email Email do aluno.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a lista de telefones associados ao aluno.
     *
     * @return Lista de telefones.
     */
    public List<TelefoneAluno> getTelefones() {
        return telefones;
    }

    /**
     * Define a lista de telefones associados ao aluno.
     *
     * @param telefones Lista de telefones.
     */
    public void setTelefones(List<TelefoneAluno> telefones) {
        this.telefones = telefones;
    }

    /**
     * Obtém a lista de disciplinas em que o aluno está matriculado.
     *
     * @return Lista de disciplinas.
     */
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    /**
     * Define a lista de disciplinas em que o aluno está matriculado.
     *
     * @param disciplinas Lista de disciplinas.
     */
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
