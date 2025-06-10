package info.danillo.whereflix.controller;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Classe que representa a entidade Disciplina no sistema.
 * Contém informações básicas sobre a disciplina, como nome e curso.
 */
@Entity
public class Disciplina {

    /**
     * Identificador único da disciplina, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Nome da disciplina.
     */
    @Basic
    private String nome;

    /**
     * Curso ao qual a disciplina pertence.
     */
    @Basic
    private String curso;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Disciplina() {
    }

    /**
     * Construtor para inicializar uma disciplina com nome e curso.
     *
     * @param nome Nome da disciplina.
     * @param curso Curso ao qual a disciplina pertence.
     */
    public Disciplina(String nome, String curso) {
        this.nome = nome;
        this.curso = curso;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único da disciplina.
     *
     * @return ID da disciplina.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único da disciplina.
     *
     * @param id ID da disciplina.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome da disciplina.
     *
     * @return Nome da disciplina.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da disciplina.
     *
     * @param nome Nome da disciplina.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o curso ao qual a disciplina pertence.
     *
     * @return Curso da disciplina.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Define o curso ao qual a disciplina pertence.
     *
     * @param curso Curso da disciplina.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    /**
     * Retorna uma representação textual da disciplina.
     *
     * @return String representando a disciplina.
     */
    @Override
    public String toString() {
        return "Disciplina{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
