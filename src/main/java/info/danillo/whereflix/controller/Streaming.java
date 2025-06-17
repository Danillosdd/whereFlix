package info.danillo.whereflix.controller;

import jakarta.persistence.Basic;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

/**
 * Classe que representa a entidade Streaming no sistema.
 * Contém informações básicas sobre a streaming, como nome e curso.
 */
@Entity
public class Streaming {

    /**
     * Identificador único da streaming, gerado automaticamente pelo banco de dados.
     */
    @Id
    @GeneratedValue
    private Integer id;

    /**
     * Nome da streaming.
     */
    @Basic
    private String nome;

    /**
     * Curso ao qual a streaming pertence.
     */
    @Basic
    private String curso;

    @Basic
    private String foto;

    /**
     * Construtor padrão necessário para o JPA.
     */
    public Streaming() {
    }

    /**
     * Construtor para inicializar uma streaming com nome e curso.
     *
     * @param nome Nome da streaming.
     * @param curso Curso ao qual a streaming pertence.
     */
    public Streaming(String nome, String curso) {
        this.nome = nome;
        this.curso = curso;
    }

    // Getters e Setters

    /**
     * Obtém o identificador único da streaming.
     *
     * @return ID da streaming.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Define o identificador único da streaming.
     *
     * @param id ID da streaming.
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Obtém o nome da streaming.
     *
     * @return Nome da streaming.
     */
    public String getNome() {
        return nome;
    }

    /**
     * Define o nome da streaming.
     *
     * @param nome Nome da streaming.
     */
    public void setNome(String nome) {
        this.nome = nome;
    }

    /**
     * Obtém o curso ao qual a streaming pertence.
     *
     * @return Curso da streaming.
     */
    public String getCurso() {
        return curso;
    }

    /**
     * Define o curso ao qual a streaming pertence.
     *
     * @param curso Curso da streaming.
     */
    public void setCurso(String curso) {
        this.curso = curso;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    /**
     * Retorna uma representação textual da streaming.
     *
     * @return String representando a streaming.
     */
    @Override
    public String toString() {
        return "Streaming{" +
                "id=" + id +
                ", nome='" + nome + '\'' +
                ", curso='" + curso + '\'' +
                '}';
    }
}
