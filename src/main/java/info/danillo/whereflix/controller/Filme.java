package info.danillo.whereflix.controller;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

/**
 * Classe que representa a entidade Filme no sistema.
 * Contém informações básicas do filme, como matrícula, nome e email,
 * além de relacionamentos com telefones e disciplinas.
 */
@Entity
public class Filme {

    // Identificador único do filme, gerado automaticamente pelo banco de dados.
    @Id
    @GeneratedValue
    private Integer id;

    // Nome completo do filme.
    private String nome;

    // Endereço de email do filme.
    private String email;

    // Relacionamento One-to-Many com a entidade TelefoneFilmes.
    // Um filme pode ter vários telefones.
    @OneToMany(mappedBy = "filme", cascade = CascadeType.REMOVE, orphanRemoval = true)
    private List<TelefoneFilme> telefones;

    // Relacionamento Many-to-Many com a entidade Disciplina.
    // Um filme pode estar matriculado em várias disciplinas.
    @ManyToMany
    private List<Disciplina> disciplinas;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Filme() {
    }

    /**
     * Construtor para inicializar um filme com matrícula, nome e email.
     *
     * @param nome Nome completo do filme.
     * @param email Endereço de email do filme.
     */
    public Filme(String nome, String email) {
        this.nome = nome;
        this.email = email;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único do filme.
     *
     * @return ID do filme.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único do filme.
     *
     * @param id ID do filme.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome completo do filme.
     *
     * @return Nome do filme.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome completo do filme.
     *
     * @param nome Nome do filme.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o endereço de email do filme.
     *
     * @return Email do filme.
     */
    public String getEmail() {
        return email;
    }

    /**
     * Define o endereço de email do filme.
     *
     * @param email Email do filme.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Obtém a lista de telefones associados ao filme.
     *
     * @return Lista de telefones.
     */
    public List<TelefoneFilme> getTelefones() {
        return telefones;
    }

    /**
     * Define a lista de telefones associados ao filme.
     *
     * @param telefones Lista de telefones.
     */
    public void setTelefones(List<TelefoneFilme> telefones) {
        this.telefones = telefones;
    }

    /**
     * Obtém a lista de disciplinas em que o filme está matriculado.
     *
     * @return Lista de disciplinas.
     */
    public List<Disciplina> getDisciplinas() {
        return disciplinas;
    }

    /**
     * Define a lista de disciplinas em que o filme está matriculado.
     *
     * @param disciplinas Lista de disciplinas.
     */
    public void setDisciplinas(List<Disciplina> disciplinas) {
        this.disciplinas = disciplinas;
    }
}
